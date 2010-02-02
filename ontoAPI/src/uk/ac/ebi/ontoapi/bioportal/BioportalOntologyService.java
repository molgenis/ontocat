/**
 * 
 */
package uk.ac.ebi.ontoapi.bioportal;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import uk.ac.ebi.efo.bioportal.IOntologyIDTranslation;
import uk.ac.ebi.ontoapi.Ontology;
import uk.ac.ebi.ontoapi.OntologyService;
import uk.ac.ebi.ontoapi.OntologyServiceException;
import uk.ac.ebi.ontoapi.OntologyTerm;
import uk.ac.ebi.ontoapi.bioportal.xmlbeans.ConceptBean;
import uk.ac.ebi.ontoapi.bioportal.xmlbeans.EntryBean;
import uk.ac.ebi.ontoapi.bioportal.xmlbeans.OntologyBean;
import uk.ac.ebi.ontoapi.bioportal.xmlbeans.SearchBean;
import uk.ac.ebi.ontoapi.bioportal.xmlbeans.SuccessBean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;

// TODO: Auto-generated Javadoc
/**
 * The Class BioportalService.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 */
public class BioportalOntologyService implements OntologyService {

	/** The query url. */
	private URL queryURL;

	/** The Constant _log. */
	private static final Logger _log = Logger
			.getLogger(BioportalOntologyService.class.getName());

	/** The sw xml. */
	private StringWriter swXML = null;

	/** The meta xml. */
	private StringWriter metaXML = null;

	/** The url add on. */
	private final String urlAddOn;

	/** The xstream. */
	private final XStream xstream = new XStream();

	// transformations that strip surrounding xml markup
	/** The Constant xsltBEAN. */
	private static final String xsltBEAN = "<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform'"
			+ " version='1.0'>"
			+ "<xsl:output method='xml' encoding='UTF-8'/>"
			+ "<xsl:template match='/'>"
			+ "<xsl:copy-of select='//data/classBean'/>"
			+ "<xsl:copy-of select='//data/ontologyBean'/>"
			+ "<xsl:copy-of select='//searchResultList'/>"
			+ "<xsl:copy-of select='/success/data/list'/>"
			+ "</xsl:template>"
			+ "</xsl:stylesheet>";

	/** The Constant xsltSUCCESS. */
	private static final String xsltSUCCESS = "<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform'"
			+ " version='1.0'>"
			+ "<xsl:output method='xml' encoding='UTF-8'/>"
			+ "<xsl:template match='/'>"
			+ "<success>"
			+ "<xsl:copy-of select='success/accessedResource'/>"
			+ "<xsl:copy-of select='success/accessDate'/>"
			+ "</success>"
			+ "</xsl:template>" + "</xsl:stylesheet>";

	/** The Constant urlBASE. */
	private static final String urlBASE = "http://rest.bioontology.org/bioportal/";

	private final IOntologyIDTranslation termResolver;

	/**
	 * Instantiates a new bioportal service.
	 * 
	 * @param email
	 *            the email
	 */
	public BioportalOntologyService(String email,
			IOntologyIDTranslation resolver) {
		// Now map the xml to the java beans
		// FIXME level?
		urlAddOn = "?includeproperties=1&email=" + email + "&level=1";
		termResolver = resolver;
		configureXstream();
	}

	private void configureXstream() {
		xstream.alias("classBean", ConceptBean.class);
		xstream.alias("entry", EntryBean.class);
		xstream.aliasField("int", EntryBean.class, "counter");
		xstream.alias("searchBean", SearchBean.class);
		xstream.alias("success", SuccessBean.class);
		xstream.alias("ontologyBean", OntologyBean.class);
		// xstream.alias("searchResultList", SearchResultListBean.class);
		xstream
				.addImplicitCollection(EntryBean.class,
						"UnmodifiableCollection");
		xstream.addImplicitCollection(EntryBean.class, "strings", "string",
				String.class);
		// xstream.addImplicitCollection(SearchResultListBean.class, "terms");
		xstream.alias("searchResultList", List.class);
		xstream.alias("list", List.class);
	}

	/**
	 * Process concept url.
	 * 
	 * @param ontologyID
	 *            the ontology id
	 * @param term
	 *            the term
	 * 
	 * @return true, if process concept url
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void processConceptUrl(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		processServiceURL("virtual/ontology/", ontologyAccession, termAccession);
	}

	private void processChildrenUrl(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		processServiceURL("virtual/children/", ontologyAccession, termAccession);
	}

	private void processParentsUrl(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		processServiceURL("virtual/parents/", ontologyAccession, termAccession);
	}

	private void processPathUrl(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		processServiceURL("virtual/rootpath/", ontologyAccession, termAccession);
	}

	private void processServiceURL(String signature, String ontologyID,
			String term) throws OntologyServiceException {
		try {
			this.queryURL = new URL(urlBASE + signature + ontologyID + "/"
					+ term + urlAddOn);
			transformRESTXML();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		}
	}

	/**
	 * Process search url.
	 * 
	 * @param ontologyID
	 *            the ontology id
	 * @param term
	 *            the term
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void processSearchUrl(String ontologyID, String term)
			throws OntologyServiceException {
		try {
			this.queryURL = new URL(urlBASE + "search/" + term + "/" + urlAddOn
					+ "&ontologyids=" + ontologyID);
			transformRESTXML();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		}
	}

	/**
	 * Process ontology url.
	 * 
	 * @param ontologyID
	 *            the ontology id
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void processOntologyUrl(String ontologyID)
			throws OntologyServiceException {
		processServiceURL("virtual/ontology/", ontologyID, "");
	}

	private void processOntologyUrl() throws OntologyServiceException {
		try {
			this.queryURL = new URL(urlBASE + "ontologies/" + urlAddOn);
			transformRESTXML();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		}
	}

	/**
	 * Search concept id through label.
	 * 
	 * @param ontologyID
	 *            the ontology id
	 * @param extID
	 *            the ext id
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void searchConceptIDThroughLabel(String ontologyID, String extID)
			throws OntologyServiceException {
		// If concept id was not found in source ontology bioportal might be
		// mapping label as id instead so try resolving it
		// and search for this id in attributes of the ontology
		processSearchUrl(ontologyID, termResolver
				.getTermAccessionFromConcept(extID));
		// bioportal id for the concept found
		processConceptUrl(ontologyID, this.getSearchResults().get(0)
				.getAccession());
	}

	/**
	 * Transform restxml.
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void transformRESTXML() throws OntologyServiceException {
		try {
			// buffer rest output
			String buffer = readInputStreamAsString(loadURL());
			TransformerFactory transFact = TransformerFactory.newInstance();
			// transform to results ConceptBean, SearchREsultListBean
			Source sBEAN = new StreamSource(new StringReader(xsltBEAN));
			Transformer trans = transFact.newTransformer(sBEAN);
			swXML = new StringWriter();
			trans.transform(new StreamSource(new StringReader(buffer)),
					new StreamResult(swXML));
			// transform to status SuccessBean
			Source sSUCCESS = new StreamSource(new StringReader(xsltSUCCESS));
			trans = transFact.newTransformer(sSUCCESS);
			metaXML = new StringWriter();
			trans.transform(new StreamSource(new StringReader(buffer)),
					new StreamResult(metaXML));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		}

	}

	/**
	 * Read input stream as string.
	 * 
	 * @param in
	 *            the in
	 * 
	 * @return the string
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private static String readInputStreamAsString(InputStream in)
			throws OntologyServiceException {
		try {
			StringBuffer fileData = new StringBuffer(1000);

			BufferedReader reader;
			// important to specify encoding on the input stream!
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			char[] buf = new char[1024];
			int numRead = 0;

			while ((numRead = reader.read(buf)) != -1) {
				fileData.append(buf, 0, numRead);
			}

			reader.close();
			return fileData.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		}

	}

	/**
	 * Load url.
	 * 
	 * @return the buffered input stream
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private BufferedInputStream loadURL() throws OntologyServiceException {
		for (int i = 0; i < 10; i++) {
			try {
				return new BufferedInputStream(queryURL.openStream());
			} catch (ConnectException e) {
				_log
						.warn("Bioportal is timing out on us. Sleep for 5s and repeat");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					throw new OntologyServiceException(e1);
				}
			} catch (FileNotFoundException e) {
				// no stack trace as this is expected behaviour for concept not
				// found and processed accordingly in searchConceptID
				throw new OntologyServiceException(e);
			} catch (IOException e) {
				// no stack trace as this is expected behaviour for concept not
				// found
				throw new OntologyServiceException(e);
			}
		}
		throw new OntologyServiceException(new Exception(
				"Could not access Bioportal REST services."));
	}

	/**
	 * Returns the parsed bean from bioportal services.
	 * 
	 * @return object that is the required bean
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private Object getBeanFromQuery() throws OntologyServiceException {
		try {
			return xstream.fromXML(swXML.toString());
		} catch (StreamException e) {
			throw new OntologyServiceException(e);
		} catch (ConversionException e) {
			throw new OntologyServiceException(e);
		}
	}

	/**
	 * Gets the concept bean.
	 * 
	 * @return the concept bean
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	public ConceptBean getConceptBean() throws OntologyServiceException {
		return (ConceptBean) getBeanFromQuery();
	}

	/**
	 * Gets the ontology bean.
	 * 
	 * @return the ontology bean
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private OntologyBean getOntologyBean() throws OntologyServiceException {
		return (OntologyBean) getBeanFromQuery();
	}

	/**
	 * Gets the search results.
	 * 
	 * @return the search results
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	@SuppressWarnings("unchecked")
	private List<OntologyTerm> getSearchResults()
			throws OntologyServiceException {
		return (List<OntologyTerm>) getBeanFromQuery();
	}

	@SuppressWarnings("unchecked")
	private List<Ontology> getOntologyList() throws OntologyServiceException {
		return (List<Ontology>) getBeanFromQuery();
	}

	/**
	 * Gets the success bean.
	 * 
	 * @return the success bean
	 */
	public SuccessBean getSuccessBean() {
		return (SuccessBean) xstream.fromXML(metaXML.toString());
	}

	/**
	 * Gets the query url.
	 * 
	 * @return the query url
	 */
	public URL getQueryURL() {
		return queryURL;
	}

	// /////////////////////////
	//
	// INTERFACE
	//
	// ////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyService#getOntologies()
	 */
	public List<Ontology> getOntologies() throws OntologyServiceException {
		processOntologyUrl();
		return this.getOntologyList();
	}

	@Override
	public Ontology getOntology(String ontologyAccession)
			throws OntologyServiceException {
		processOntologyUrl(ontologyAccession);
		return this.getOntologyBean();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * plugin.OntologyBrowser.OntologyService#getRootTerms(java.lang.String)
	 */
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException {
		// warning this uses and undocumented feature!
		ConceptBean cb = (ConceptBean) getTerm(ontologyAccession, "root");
		return cb.getChildren();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * plugin.OntologyBrowser.OntologyService#searchOntology(java.lang.String,
	 * java.lang.String)
	 */
	public List<OntologyTerm> searchOntology(String ontologyAccession,
			String keyword) throws OntologyServiceException {
		processSearchUrl(ontologyAccession, keyword);
		return getSearchResults();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyService#searchAll(java.lang.String)
	 */
	public List<OntologyTerm> searchAll(String keyword)
			throws OntologyServiceException {
		return searchOntology(null, keyword);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyService#getTerm(java.lang.String,
	 * java.lang.String)
	 */
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		try {
			processConceptUrl(ontologyAccession, termAccession);
		} catch (OntologyServiceException e) { // try to catch the first one?
			searchConceptIDThroughLabel(ontologyAccession, termAccession);
		}
		ConceptBean ot = this.getConceptBean();
		ot.setOntologyAccession(ontologyAccession);
		return ot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyService#getTerm(java.lang.String)
	 */
	public OntologyTerm getTerm(String termAccession)
			throws OntologyServiceException {
		return getTerm(termResolver
				.getOntologyAccessionFromConcept(termAccession), termAccession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * plugin.OntologyBrowser.OntologyService#getAnnotations(java.lang.String,
	 * java.lang.String)
	 */
	public Map<String, String[]> getAnnotations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return getTerm(ontologyAccession, termAccession).getAnnotations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyService#getChildren(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<OntologyTerm> getChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		processChildrenUrl(ontologyAccession, termAccession);
		return insertOntologyAccession((List<?>) getBeanFromQuery(),
				ontologyAccession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyService#getParents(java.lang.String,
	 * java.lang.String)
	 */
	public List<OntologyTerm> getParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		processParentsUrl(ontologyAccession, termAccession);
		return insertOntologyAccession((List<?>) getBeanFromQuery(),
				ontologyAccession);
	}

	@SuppressWarnings("unchecked")
	private List<OntologyTerm> insertOntologyAccession(List<?> list,
			String OntologyAccession) {
		for (ConceptBean ot : (List<ConceptBean>) list) {
			ot.setBioportalService(this);
			ot.setOntologyAccession(OntologyAccession);
		}
		return (List<OntologyTerm>) list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plugin.OntologyBrowser.OntologyService#getTermPath(java.lang.String,
	 * java.lang.String)
	 */
	public List<OntologyTerm> getTermPath(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		// PARSE THE XML OUTPUT
		processPathUrl(ontologyAccession, termAccession);
		// so it's not true ontology term per se, but the way
		// bioportal process this query it will dump the path
		// as a list of *slightly modified* classBeans
		ConceptBean firstPath = (ConceptBean) this.getSearchResults().get(0);
		String PathString = firstPath.getPathString();
		// This will be a list of accessions separated by point
		String[] Accessions = PathString.split("\\.");

		// GET TERMS FROM ACCESSIONS
		List<OntologyTerm> path = new ArrayList<OntologyTerm>();
		// include searched acc in path
		path.add(this.getTerm(termAccession));
		for (String tAcc : Accessions) {
			path.add(this.getTerm(tAcc));
		}
		Collections.reverse(path);
		return path;
	}

	@Override
	public Map<String, String[]> getRelations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String makeLookupHyperlink(String termAccession) {
		return this.getQueryURL().toString();
	}

	@Override
	public String[] getSynonyms(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		return getTerm(ontologyAccession, termAccession).getSynonyms();
	}

	@Override
	public String[] getDefinitions(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return getTerm(ontologyAccession,termAccession).getDefinitions();
	}
}
