/**
 * 
 */
package uk.ac.ebi.ontocat.bioportal;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.ConceptBean;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.EntryBean;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.OntologyBean;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.SearchBean;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.SuccessBean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;

// TODO: Auto-generated Javadoc
/**
 * The Class BioportalService.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 */
public class BioportalOntologyService extends AbstractOntologyService implements OntologyService,
		Serializable {

	/** The query url. */
	private URL queryURL;

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(BioportalOntologyService.class.getName());

	/** The sw xml. */
	private transient StringWriter swXML = null;

	/** The meta xml. */
	private transient StringWriter metaXML = null;

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
			+ "</xsl:template>"
			+ "</xsl:stylesheet>";

	/** The Constant urlBASE. */
	private static final String urlBASE = "http://rest.bioontology.org/bioportal/";

	/**
	 * Instantiates a new bioportal service.
	 * 
	 * @param email
	 *            the email
	 */
	public BioportalOntologyService(String email) {
		// Now map the xml to the java beans
		// FIXME level?
		urlAddOn = "&email=" + email + "&level=1";
		configureXstream();
	}

	/**
	 * Shorthand that uses ontocat-svn email to instantiate the service
	 * 
	 * @param email
	 *            the email
	 */
	public BioportalOntologyService() {
		this("ontocat-svn@lists.sourceforge.net");
	}

	private void configureXstream() {
		xstream.alias("classBean", ConceptBean.class);
		xstream.alias("entry", EntryBean.class);
		xstream.aliasField("int", EntryBean.class, "counter");
		xstream.alias("searchBean", SearchBean.class);
		xstream.alias("success", SuccessBean.class);
		xstream.alias("ontologyBean", OntologyBean.class);
		// xstream.alias("searchResultList", SearchResultListBean.class);
		xstream.addImplicitCollection(EntryBean.class, "UnmodifiableCollection");
		xstream.addImplicitCollection(EntryBean.class, "strings", "string", String.class);
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
	private void processConceptUrl(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		processServiceURL("virtual/ontology/", ontologyAccession, termAccession);
	}

	private void processChildrenUrl(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		processServiceURL("virtual/children/", ontologyAccession, termAccession);
	}

	private void processParentsUrl(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		processServiceURL("virtual/parents/", ontologyAccession, termAccession);
	}

	private void processPathUrl(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		processServiceURL("virtual/rootpath/", ontologyAccession, termAccession);
	}

	// Adding conceptid does not work for hierarchy services
	// so you cannot pass urls as concept ids!
	// this is a temporary workaround until BP guys fix it
	private boolean temporarayBioportalFix(String signature, String termAccession) {
		if (signature.contains("parents") || signature.contains("children")
				|| signature.contains("rootpath")) {
			try {
				new URL(termAccession);
				throw new UnsupportedOperationException(
						"Currentlly URL concept ids not supported for hierarchy services");
			} catch (MalformedURLException e) {
				// it's not a URL so do nothing
			}
			return false;
		}
		return true;
	}

	private void processServiceURL(String signature, String ontologyID, String termAccession)
			throws OntologyServiceException {
		try {
			if (!termAccession.equals("") && temporarayBioportalFix(signature, termAccession)) {
				termAccession = "?conceptid=" + URLEncoder.encode(termAccession, "UTF-8");
			}
			// temporary for the fix, otherwise prepend after ontology + "/?"
			else {
				termAccession += "?";
			}
			this.queryURL = new URL(urlBASE + signature + ontologyID + "/" + termAccession
					+ urlAddOn);
			transformRESTXML();
		} catch (MalformedURLException e) {
			throw new OntologyServiceException(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Process search url.
	 * 
	 * @param ontologyAccession
	 *            the ontology id
	 * @param keyword
	 *            the term
	 * @param options
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void processSearchUrl(String ontologyAccession, String keyword,
			SearchOptions... options) throws OntologyServiceException {
		try {
			keyword = URLEncoder.encode(keyword, "UTF-8");
			this.queryURL = new URL(urlBASE + "search/" + keyword + "/?maxnumhits=10000000"
					+ urlAddOn
					+ processSearchOptions(options) + "&ontologyids=" + ontologyAccession);
			transformRESTXML();
		} catch (MalformedURLException e) {
			throw new OntologyServiceException(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private String processSearchOptions(SearchOptions[] options) {
		String val = "";
		List<SearchOptions> al = new ArrayList<SearchOptions>(Arrays.asList(options));
		if (al.contains(SearchOptions.INCLUDE_PROPERTIES)) {
			val += "&includeproperties=1";
		} else
			val += "&includeproperties=0";
		if (al.contains(SearchOptions.EXACT)) {
			val += "&isexactmatch=1";
		} else
			val += "&isexactmatch=0";

		return val;
	}

	/**
	 * Process ontology url.
	 * 
	 * @param ontologyAccession
	 *            the ontology id
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void processOntologyUrl(String ontologyAccession) throws OntologyServiceException {
		processServiceURL("virtual/ontology/", ontologyAccession, "");
	}

	private void processOntologyUrl() throws OntologyServiceException {
		try {
			this.queryURL = new URI(urlBASE + "ontologies/?" + urlAddOn).toURL();
			transformRESTXML();
		} catch (MalformedURLException e) {
			throw new OntologyServiceException(e);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Search concept id through attributes. If termAccession was not found,
	 * BioPortal might be mapping a different id instead so try resolving it and
	 * search for this id in attributes of the ontology.
	 * 
	 * @param ontologyAccession
	 *            the ontology id
	 * @param secondaryTermAccession
	 *            the ext id
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private void searchConceptIDThroughLabel(String ontologyAccession, String secondaryTermAccession)
			throws OntologyServiceException {
		processSearchUrl(ontologyAccession, secondaryTermAccession,
				SearchOptions.INCLUDE_PROPERTIES);
		// bioportal id for the concept found or exception thrown already
		processConceptUrl(ontologyAccession, this.getSearchResults().get(0).getAccession());
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
			trans.transform(new StreamSource(new StringReader(buffer)), new StreamResult(swXML));
			// transform to status SuccessBean
			Source sSUCCESS = new StreamSource(new StringReader(xsltSUCCESS));
			trans = transFact.newTransformer(sSUCCESS);
			metaXML = new StringWriter();
			trans.transform(new StreamSource(new StringReader(buffer)), new StreamResult(metaXML));
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
	private String readInputStreamAsString(InputStream in) throws OntologyServiceException {
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
				log.warn("Bioportal is timing out on us. Sleep for 5s and repeat");
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
				throw new OntologyServiceException("Term not found");
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
			throw new OntologyServiceException("Term not found");
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
	private List<OntologyTerm> getSearchResults() throws OntologyServiceException {
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

	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		processOntologyUrl();
		return this.getOntologyList();
	}

	@Override
	public Ontology getOntology(String ontologyAccession) throws OntologyServiceException {
		try {
			processOntologyUrl(ontologyAccession);
		} catch (Exception e) {
			throw new OntologyServiceException("Ontology not found");
		}
		return this.getOntologyBean();
	}

	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException {
		// warning this uses and undocumented feature!
		ConceptBean cb = (ConceptBean) getTerm(ontologyAccession, "root");
		return fetchFullTerms(cb.getChildren(), ontologyAccession);
	}

	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession, String query,
			SearchOptions... options) throws OntologyServiceException {
		// confirm the ontology exists
		getOntology(ontologyAccession);
		// search it
		processSearchUrl(ontologyAccession, query, options);
		return getSearchResults();
	}

	@Override
	public List<OntologyTerm> searchAll(String query, SearchOptions... options)
			throws OntologyServiceException {
		processSearchUrl(null, query, options);
		return getSearchResults();
	}

	@Override
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

	@Override
	public OntologyTerm getTerm(String termAccession) throws OntologyServiceException {
		OntologyTerm term = searchAll(termAccession).get(0);
		return getTerm(term.getOntologyAccession(), term.getAccession());

	}

	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		return ((ConceptBean) getTerm(ontologyAccession, termAccession)).getAnnotations();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		processChildrenUrl(ontologyAccession, termAccession);
		return fetchFullTerms((List<OntologyTerm>) getBeanFromQuery(), ontologyAccession);
	}

	@Override
	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		processParentsUrl(ontologyAccession, termAccession);
		return fetchFullTerms((List<OntologyTerm>) getBeanFromQuery(), ontologyAccession);
	}

	@SuppressWarnings("unchecked")
	private List<OntologyTerm> fetchFullTerms(List<OntologyTerm> list, String ontologyAccession)
			throws OntologyServiceException {
		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		for (OntologyTerm ot : list) {
			result.add(getTerm(ontologyAccession, ot.getAccession()));
		}
		return result;
	}

	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
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
		path.add(this.getTerm(ontologyAccession, termAccession));
		for (String tAcc : Accessions) {
			path.add(this.getTerm(ontologyAccession, tAcc));
		}
		Collections.reverse(path);
		return path;
	}

	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String makeLookupHyperlink(String termAccession) {
		try {
			getTerm(termAccession);
			return this.getQueryURL().toString();
		} catch (OntologyServiceException e) {
			log.error("Making lookup hyperlink failed for " + termAccession);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String makeLookupHyperlink(String ontologyAccession, String termAccession) {
		try {
			getTerm(ontologyAccession, termAccession);
			return this.getQueryURL().toString();
		} catch (OntologyServiceException e) {
			log.error("Making lookup hyperlink failed for " + termAccession);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getSynonyms(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		return ((ConceptBean) getTerm(ontologyAccession, termAccession)).getSynonyms();
	}

	@Override
	public List<String> getDefinitions(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		return ((ConceptBean) getTerm(ontologyAccession, termAccession)).getDefinitions();
	}
}
