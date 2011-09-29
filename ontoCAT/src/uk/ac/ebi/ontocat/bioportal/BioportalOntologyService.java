/**
 * Copyright (c) 2010 - 2011 European Molecular Biology Laboratory and University of Groningen
 *
 * Contact: ontocat-users@lists.sourceforge.net
 * 
 * This file is part of OntoCAT
 * 
 * OntoCAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * OntoCAT is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along
 * with OntoCAT. If not, see <http://www.gnu.org/licenses/>.
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import uk.ac.ebi.ontocat.bioportal.xmlbeans.InstanceBean;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.OntologyBean;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.SearchBean;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.SuccessBean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.mapper.MapperWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class BioportalService.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 */
public class BioportalOntologyService extends AbstractOntologyService implements OntologyService,
Serializable {
	private static final long serialVersionUID = 1L;

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
	private XStream xstream;

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
		+ "<xsl:copy-of select='//classBeanResultList'/>"
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
		+ "<xsl:copy-of select='success/data/page/numPages'/>"
		+ "</success>"
		+ "</xsl:template>" + "</xsl:stylesheet>";

	/** The Constant urlBASE. */
	private static final String urlBASE = "http://rest.bioontology.org/bioportal/";

	/**
	 * Instantiates a new bioportal service.
	 * 
	 * @param apikey
	 *            the apikey required by BioPortal for REST queries
	 */
	public BioportalOntologyService(String apikey) {
		// Now map the xml to the java beans
		// FIXME level?
		urlAddOn = "&apikey=" + apikey
		+ "&level=1";
		configureXstream();
	}

	/**
	 * Shorthand that uses ontocat apikey l to instantiate the service
	 * 
	 */
	public BioportalOntologyService() {
		this("c6ae1b27-9f86-4e3c-9dcf-087e1156eabe");
		log.debug("Instatiated BOS");
	}

	private void configureXstream() {
		// ignore new fields
		// solution from
		// CustomMapperTest.testCanBeUsedToOmitUnexpectedElements()
		// http://svn.xstream.codehaus.org/browse/xstream/trunk/xstream/src/test/com/thoughtworks/acceptance/CustomMapperTest.java?r=HEAD
		xstream = new XStream() {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@SuppressWarnings("rawtypes")
					@Override
					public boolean shouldSerializeMember(Class definedIn, String fieldName) {
						if (definedIn != Object.class){
							return super.shouldSerializeMember(definedIn,fieldName);
						} else {
							log.debug("Ignoring unexpected field <" + fieldName
									+ "> in BioPortal xml output from "
									+ queryURL);
							return false;
						}
					}
				};
			}

		};
		xstream.alias("classBean", ConceptBean.class);
		xstream.alias("entry", EntryBean.class);
		xstream.aliasField("int", EntryBean.class, "counter");
		xstream.alias("searchBean", SearchBean.class);
		xstream.alias("success", SuccessBean.class);
		xstream.alias("ontologyBean", OntologyBean.class);
		xstream.alias("instanceBean", InstanceBean.class);
		xstream.omitField(InstanceBean.class, "instanceType");
		// xstream.alias("searchResultList", SearchResultListBean.class);
		xstream.addImplicitCollection(EntryBean.class, "UnmodifiableCollection");
		xstream.addImplicitCollection(EntryBean.class, "strings", "string", String.class);
		// xstream.addImplicitCollection(SearchResultListBean.class, "terms");
		xstream.alias("searchResultList", List.class);
		xstream.alias("classBeanResultList", Set.class);
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

	// No getting hierarchy straight from concept bean
	//
	// private void processChildrenUrl(String ontologyAccession, String
	// termAccession)
	// throws OntologyServiceException {
	// processServiceURL("virtual/children/", ontologyAccession, termAccession);
	// }
	//
	// private void processParentsUrl(String ontologyAccession, String
	// termAccession)
	// throws OntologyServiceException {
	// processServiceURL("virtual/parents/", ontologyAccession, termAccession);
	// }

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
			throw new OntologyServiceException(e);
		}
	}

	private void processGetAllURL(String ontologyAccession, Integer pageSize, Integer pageNum)
	throws OntologyServiceException {
		try {
			this.queryURL = new URL(urlBASE + "virtual/ontology/" + ontologyAccession
					+ "/all?pagesize=" + pageSize + "&pagenum=" + pageNum + urlAddOn);
			transformRESTXML();
		} catch (MalformedURLException e) {
			throw new OntologyServiceException(e);
		} catch (OntologyServiceException e) {
			throw new OntologyServiceException(e);
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
			// colon in the begining will crash the service
			keyword = keyword.replaceFirst("$:", "");
			keyword = URLEncoder.encode(keyword, "UTF-8");
			this.queryURL = new URL(urlBASE + "search/" + keyword + "/?maxnumhits=10000000"
					+ urlAddOn + processSearchOptions(options) + "&ontologyids="
					+ ontologyAccession);
			transformRESTXML();
		} catch (MalformedURLException e) {
			throw new OntologyServiceException(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Another version of this method created specifically to allow subtree
	 * searching
	 * 
	 * @throws OntologyServiceException
	 * 
	 */
	private void processSearchUrl(String ontologyAccession, String termAccession, String query,
			SearchOptions[] options) throws OntologyServiceException {
		try {
			query = URLEncoder.encode(query, "UTF-8");
			String subtreeSetting = "&subtreerootconceptid="
				+ URLEncoder.encode(termAccession, "UTF-8");
			this.queryURL = new URL(urlBASE + "search/" + query + "/?maxnumhits=10000000"
					+ urlAddOn + processSearchOptions(options) + "&ontologyids="
					+ ontologyAccession + subtreeSetting);
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
		} else {
			val += "&includeproperties=0";
		}
		if (al.contains(SearchOptions.EXACT)) {
			val += "&isexactmatch=1";
		} else {
			val += "&isexactmatch=0";
		}

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

	private void processViewsUrl() throws OntologyServiceException {
		try {
			this.queryURL = new URI(urlBASE + "views/?" + urlAddOn).toURL();
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
		if (this.getSearchResults().size() == 0) {
			throw new OntologyServiceException("Term not found");
		}
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
			String buffer = getCachedQuery();
			TransformerFactory transFact = TransformerFactory.newInstance();
			// transform to results ConceptBean, SearchResultListBean,
			// classBeanResultList
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

	// Caches the immediate results of an internal REST query
	// for subsequent queries. Example term + synonyms + definitions
	private String getCachedQuery() throws OntologyServiceException {
		String result = queryCache.get(queryURL);

		if (result == null) {
			result = readInputStreamAsString(loadURL());
			queryCache.put(queryURL, result);
		}

		return result;
	}

	// A LRU Cache implementation that removes the stale
	// elements of the map limiting its size to a hundred
	// most recently used elements
	private LinkedHashMap<URL, String> queryCache = new LinkedHashMap<URL, String>() {
		private static final long serialVersionUID = 1L;
		private static final int MAX_ENTRIES = 100;

		@Override
		protected boolean removeEldestEntry(Entry<URL, String> arg) {
			return size() > MAX_ENTRIES;
		}
	};

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
				// no logging as this is expected behaviour for concept not
				// found and processed accordingly in searchConceptID
				throw new OntologyServiceException(e);
			} catch (IOException e) {
				// less expected usually means an error on BP side
				// other than response code 400
				// or thrown in parents services
				// alternatively could implement this via
				// HttpURLConnection.getResponseCode()
				if (!e.getMessage().contains("HTTP response code: 400")
						&& !e.getMessage().contains("No parents")) {
					log.error("Possible problems on BioPortal side - " + e + " on "
							+ queryURL.toString());
				}

				throw new OntologyServiceException(e);
			}
		}
		throw new OntologyServiceException("Could not access Bioportal REST services.");
	}

	/**
	 * Returns the parsed bean from bioportal services.
	 * 
	 * @return object that is the required bean
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private Object getBeanFromQuery() throws StreamException {
		// Commenting error catching for the moment
		// as those are handed down the stream
		try {
			return xstream.fromXML(swXML.toString());
			// } catch (StreamException e) {
			// throw new OntologyServiceException("Term not found");
		} catch (ConversionException e) {
			log.error("Web service signature has changed!");
			log.error(e.getMessage());
			// e.printStackTrace();
			return null;
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
	public ConceptBean getConceptBean() {
		try {
			return (ConceptBean) getBeanFromQuery();
		} catch (StreamException e) {
			return null;
		}
	}

	/**
	 * Gets the ontology bean.
	 * 
	 * @return the ontology bean
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private OntologyBean getOntologyBean() {
		try {
			return (OntologyBean) getBeanFromQuery();
		} catch (StreamException e) {
			return null;
		}
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
	private List<OntologyTerm> getSearchResults() {
		try {
			return (List<OntologyTerm>) getBeanFromQuery();
		} catch (StreamException e) {
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * Gets the success bean.
	 * 
	 * @return the success bean
	 */
	public SuccessBean getSuccessBean() {
		try {
			return (SuccessBean) xstream.fromXML(metaXML.toString());
		} catch (StreamException e) {
			return null;
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		List<Ontology> result = new ArrayList<Ontology>();
		List<Ontology> ontologies = new ArrayList<Ontology>();
		List<Ontology> views = new ArrayList<Ontology>();

		processOntologyUrl();
		ontologies = (List<Ontology>) getBeanFromQuery();
		processViewsUrl();
		views = (List<Ontology>) getBeanFromQuery();

		log.info(ontologies.size() + " ontologies and " + views.size() + " views");
		result.addAll(ontologies);
		result.addAll(views);

		return result;
	}

	@Override
	public Ontology getOntology(String ontologyAccession) throws OntologyServiceException {
		try {
			processOntologyUrl(ontologyAccession);
		} catch (OntologyServiceException e) {
			return null;
		}
		return this.getOntologyBean();
	}

	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
	throws OntologyServiceException {
		// warning this uses and undocumented feature!
		// need the no search version, so that the ontology is not searched for
		// root
		// in second pass on fail (takes too much time)
		ConceptBean cb = (ConceptBean) getTermNoSearch(ontologyAccession, "root");
		if (cb == null) {
			return Collections.emptyList();
		}
		return (List<OntologyTerm>) injectOntologyAccession(cb.getChildren(), ontologyAccession);
	}

	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession, String query,
			SearchOptions... options) throws OntologyServiceException {
		// confirm the ontology exists
		if (getOntology(ontologyAccession) == null) {
			return Collections.emptyList();
		}
		// search it
		processSearchUrl(ontologyAccession, query, options);
		return injectTermContext(getSearchResults(), query, options);
	}

	public List<OntologyTerm> searchSubtree(String ontologyAccession, String termAccession,
			String query, SearchOptions... options) throws OntologyServiceException {
		// confirm the ontology exists
		if (getOntology(ontologyAccession) == null) {
			return Collections.emptyList();
		}
		// search it
		processSearchUrl(ontologyAccession, termAccession, query, options);
		return injectTermContext(getSearchResults(), query, options);
	}

	@Override
	public List<OntologyTerm> searchAll(String query, SearchOptions... options)
	throws OntologyServiceException {
		processSearchUrl(null, query, options);
		return injectTermContext(getSearchResults(), query, options);
	}

	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
	throws OntologyServiceException {
		try {
			processConceptUrl(ontologyAccession, termAccession);
		} catch (OntologyServiceException e) { // try to catch the first one?
			try {
				searchConceptIDThroughLabel(ontologyAccession, termAccession);
			} catch (OntologyServiceException e2) {
				return null;
			}
		}
		ConceptBean ot = this.getConceptBean();
		ot.setOntologyAccession(ontologyAccession);
		return ot;
	}

	// used by the getRootTerms
	private OntologyTerm getTermNoSearch(String ontologyAccession, String termAccession)
	throws OntologyServiceException {
		try {
			processConceptUrl(ontologyAccession, termAccession);
		} catch (OntologyServiceException e) { // try to catch the first one?
			return null;
		}
		ConceptBean ot = this.getConceptBean();
		ot.setOntologyAccession(ontologyAccession);
		return ot;
	}

	@Override
	public OntologyTerm getTerm(String termAccession) throws OntologyServiceException {
		List<OntologyTerm> list = searchAll(termAccession);
		if (list.size() == 0) {
			return null;
		}
		OntologyTerm term = list.get(0);
		return getTerm(term.getOntologyAccession(), term.getAccession());

	}

	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession, String termAccession)
	throws OntologyServiceException {
		OntologyTerm ot = getTerm(ontologyAccession, termAccession);
		if (ot == null) {
			return Collections.emptyMap();
		}
		return ((ConceptBean) ot).getAnnotations();
	}

	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
	throws OntologyServiceException {
		OntologyTerm ot = getTerm(ontologyAccession, termAccession);
		if (ot == null) {
			return Collections.emptyList();
		}
		return (List<OntologyTerm>) injectOntologyAccession(
				((ConceptBean) ot).getChildren(), ontologyAccession);
	}

	@Override
	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
	throws OntologyServiceException {
		OntologyTerm ot = getTerm(ontologyAccession, termAccession);
		if (ot == null) {
			return Collections.emptyList();
		}
		return (List<OntologyTerm>) injectOntologyAccession(
				((ConceptBean) ot).getParents(), ontologyAccession);
	}

	private Collection<OntologyTerm> injectOntologyAccession(Collection<OntologyTerm> list,
			String ontologyAccession) throws OntologyServiceException {
		for (OntologyTerm ot : list) {
			ot.setOntologyAccession(ontologyAccession);
		}
		return list;
	}

	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
	throws OntologyServiceException {
		// PARSE THE XML OUTPUT
		try {
			processPathUrl(ontologyAccession, termAccession);
		} catch (OntologyServiceException e) {
			// The list needs at least one term
			List<OntologyTerm> result = new ArrayList<OntologyTerm>();
			result.add(getTerm(ontologyAccession, termAccession));
			return result;
		}

		// Empty path, looks like an error
		if (this.getSearchResults().size() == 0) {
			return Collections.emptyList();
		}
		// so it's not true ontology term per se, but the way
		// bioportal process this query it will dump the path
		// as a list of *slightly modified* classBeans
		ConceptBean firstPath = (ConceptBean) this.getSearchResults().get(0);
		String PathString = firstPath.getPathString();
		// This will be a list of accessions separated by point
		String[] Accessions = PathString.split("\\.");

		// GET TERMS FROM ACCESSIONS
		List<OntologyTerm> path = new ArrayList<OntologyTerm>();
		
		for (String tAcc : Accessions) {
			OntologyTerm ot = this.getTerm(ontologyAccession, tAcc);
			if (ot==null) throw new OntologyServiceException("Unrecognisable term in path - " + tAcc );
			path.add(ot);
		}
		// include searched acc in path
		path.add(this.getTerm(ontologyAccession, termAccession));
		
		Collections.reverse(path);
		return path;
	}

	@Override
	public Map<String, Set<OntologyTerm>> getRelations(String ontologyAccession, String termAccession)
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
		OntologyTerm ot = getTerm(ontologyAccession, termAccession);
		if (ot == null) {
			return Collections.emptyList();
		}
		return ((ConceptBean) ot).getSynonyms();
	}

	@Override
	public List<String> getDefinitions(String ontologyAccession, String termAccession)
	throws OntologyServiceException {
		OntologyTerm ot = getTerm(ontologyAccession, termAccession);
		if (ot == null) {
			return Collections.emptyList();
		}
		return ((ConceptBean) ot).getDefinitions();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<OntologyTerm> getAllTerms(String ontologyAccession) throws OntologyServiceException {
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		Integer pageCount = 0;
		Integer PAGESIZE = 300;

		// Fetch first page
		processGetAllURL(ontologyAccession, PAGESIZE, 1);
		result.addAll((Set<OntologyTerm>) getBeanFromQuery());

		// Fetch any remaining pages
		pageCount = getSuccessBean().getNumberOfPages();
		for (Integer pageNo = 2; pageNo <= pageCount; pageNo++) {
			log.info("Processing page " + pageNo + " out of " + pageCount);
			processGetAllURL(ontologyAccession, PAGESIZE, pageNo);
			result.addAll((Set<OntologyTerm>) getBeanFromQuery());
		}
		return (Set<OntologyTerm>) injectOntologyAccession(result, ontologyAccession);
	}
}
