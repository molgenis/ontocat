/*
 * 
 */
package uk.ac.ebi.ontocat.file;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLOntology;

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

// TODO: Auto-generated Javadoc
/**
 * The Class FileOntologyService. This works on both OBO and OWL files.
 */
public final class FileOntologyService extends AbstractOntologyService {
	/** The Constant log. */
	private static final Logger log = Logger.getLogger(FileOntologyService.class.getName());

	/** The ontology. */
	private final OWLOntology ontology;

	// Multiple ontology namespaces per OWL file are permitted
	private Set<String> ontoAccessions = new HashSet<String>();

	/** The slots. */
	/** The skos:synonym slot. */
	private String synonymSlot = "altLabel";

	private Matcher regexDropSyns = Pattern.compile("\"?([^\"]*)").matcher("");

	/** The skos:definition slot. */
	public String definitionSlot = "definition";

	/** The skos:label slot if not rdfs:label is used */
	private String labelSlot = "prefLabel";

	public void setSynonymSlot(String synonymSlot) {
		this.synonymSlot = synonymSlot;

	}

	public void setDefinitionSlot(String definitionSlot) {
		this.definitionSlot = definitionSlot;

	}

	public void setLabelSlot(String labelSlot) {
		this.labelSlot = labelSlot;

	}

	/** The map with classes */
	private final Map<String, OWLClass> cache = new TreeMap<String, OWLClass>();

	/**
	 * Instantiates a new file ontology service.
	 * 
	 * @param uriOntology
	 *            where to load the ontology from, can be local file or URL
	 * @param fdesc
	 *            FieldDescriptor describing which annotations hold synonyms,
	 *            definitions and labels
	 */
	public FileOntologyService(URI uriOntology) {
		ontology = new OntologyLoader(uriOntology).getOntology();
		// get all possible URIs in onotlogy
		for (OWLClass cls : ontology.getReferencedClasses()) {

			cache.put(getFragment(cls), cls);
			try {
				ontoAccessions.add(getOntologyAccession(cls));
			} catch (OntologyServiceException e) {
				log.error(e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getOntologies()
	 */
	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		// FIXME: this list the main uri, but more namespaces are possible
		// FIXME: should iterate over ontoAccessions maybe?
		return new ArrayList<Ontology>() {
			{
				add(new Ontology(ontology.getURI().toString()));
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getOntology(java.lang.String)
	 */
	@Override
	public Ontology getOntology(String ontologyAccession) throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession))
			return null;
		return getOntologies().get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getRelations(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		throw new UnsupportedOperationException("Not implemented.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getRootTerms(java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession))
			return Collections.EMPTY_LIST;
		List<OntologyTerm> rootTerms = new ArrayList<OntologyTerm>();
		for (OWLClass cls : ontology.getReferencedClasses()) {
			// class without parents, looks like root
			// TODO: add reasoner, otherwise fails for defined classes
			// TODO: test if reasoner works with OBO ontologies
			if (cls.getSuperClasses(ontology).size() == 0
					&& !getAnnotations(cls).containsKey("is_obsolete")) {
				rootTerms.add(getTerm(cls));
			}
		}
		return rootTerms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getSynonyms(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<String> getSynonyms(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession))
			return Collections.EMPTY_LIST;
		List<String> result = new ArrayList<String>();
		try {
			result.addAll(dropQuotes(getAnnotations(ontologyAccession, termAccession).get(
					synonymSlot)));
		} catch (NullPointerException e) {
		}
		try {
			// prevent adding duplicates if user specified the same string
			// by setting synonym slot
			if (!synonymSlot.equalsIgnoreCase("synonym"))
				result.addAll(dropQuotes(getAnnotations(ontologyAccession, termAccession).get(
						"synonym"))); // OBO
		} catch (NullPointerException e) {
		}

		return result;
	}

	private List<String> dropQuotes(List<String> list) {
		List<String> result = new ArrayList<String>();
		for (String val : list) {
			regexDropSyns.reset(val).find();
			result.add(regexDropSyns.group(1));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTerm(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		if (ontologyAccession != null && !ontoAccessions.contains(ontologyAccession))
			return null;
		return getTerm(termAccession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTerm(java.lang.String)
	 */
	@Override
	public OntologyTerm getTerm(String termAccession) throws OntologyServiceException {
		return getTerm(getOwlClass(termAccession));
	}

	private OntologyTerm getTerm(OWLClass cls) throws OntologyServiceException {
		if (cls == null)
			return null;
		String ontologyAccession = getOntologyAccession(cls);
		String termAccession = getFragment(cls);
		String label = getLabel(cls);
		return new OntologyTerm(ontologyAccession, termAccession, label);
	}

	private String getOntologyAccession(OWLClass cls) throws OntologyServiceException {
		Pattern pattern = Pattern.compile("^.*[//#]{1}");
		Matcher matcher = pattern.matcher(cls.getURI().toString());
		if (matcher.find()) {
			return matcher.group();
		} else {
			throw new OntologyServiceException("Could not create ontologyAccession for " + cls);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTermPath(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		ArrayList<OntologyTerm> termPath = new ArrayList<OntologyTerm>();
		int iteration = 0;
		// seed the list with this term
		OntologyTerm term = getTerm(ontologyAccession, termAccession);
		termPath.add(term);
		if (!ontoAccessions.contains(ontologyAccession))
			return termPath;
		// get its parents and iterate over first parent
		List<OntologyTerm> parents = getParents(ontologyAccession, termAccession);
		while (parents.size() != 0) {
			term = parents.get(0);
			termPath.add(term);
			parents = getParents(term.getOntologyAccession(), term.getAccession());
			// safety break for circular relations
			if (iteration++ > 100) {
				log.error("getTermPath(): TOO MANY ITERATIONS (" + iteration + "x)");
				break;
			}
		}
		// reverse to have root as first
		Collections.reverse(termPath);
		return termPath;
	}

	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession))
			return Collections.EMPTY_LIST;
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		OWLClass cls = getOwlClass(termAccession);
		if (cls == null)
			return Collections.EMPTY_LIST;
		for (OWLDescription desc : cls.getSubClasses(ontology)) {
			if (!desc.isAnonymous()) {
				list.add(getTerm(desc.asOWLClass()));
			}
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getParents(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession))
			return Collections.EMPTY_LIST;
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		OWLClass cls = getOwlClass(termAccession);
		if (cls == null)
			return Collections.EMPTY_LIST;
		for (OWLDescription desc : cls.getSuperClasses(ontology)) {
			if (!desc.isAnonymous()) {
				list.add(getTerm(desc.asOWLClass()));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.AbstractOntologyService#getAllTerms(java.lang.String)
	 */
	@Override
	public Set<OntologyTerm> getAllTerms(String ontologyAccession) throws OntologyServiceException {
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		for (OWLClass cls : ontology.getReferencedClasses()) {
			result.add(getTerm(cls));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.OntologyService#makeLookupHyperlink(java.lang.String)
	 */
	@Override
	public String makeLookupHyperlink(String termAccession) {
		try {
			return getOwlClass(termAccession).getURI().toString();
		} catch (OntologyServiceException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.OntologyService#makeLookupHyperlink(java.lang.String)
	 */
	@Override
	public String makeLookupHyperlink(String ontologyAccession, String termAccession) {
		if (!ontoAccessions.contains(ontologyAccession))
			return null;
		try {
			return getOwlClass(termAccession).getURI().toString();
		} catch (OntologyServiceException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#searchAll(java.lang.String)
	 */
	@Override
	public List<OntologyTerm> searchAll(String query, SearchOptions... options)
			throws OntologyServiceException {
		List<SearchOptions> ops = new ArrayList<SearchOptions>(Arrays.asList(options));
		HashSet<OntologyTerm> terms = new HashSet<OntologyTerm>();
		// iterate through all classes annotations
		// TODO: lucene index?
		for (OWLClass cls : ontology.getReferencedClasses()) {
			// if label is only in the fragment
			if (getFragment(cls).contains(query)) {
				// filter out non exact matches
				if (ops.contains(SearchOptions.EXACT) && getFragment(cls).equalsIgnoreCase(query)) {
					terms.add(getTerm(cls));
					// just add it if nonexact search
				} else if (!ops.contains(SearchOptions.EXACT)) {
					terms.add(getTerm(cls));
				}
			}

			// regular label
			if (ops.contains(SearchOptions.EXACT) && getLabel(cls).equalsIgnoreCase(query)) {
				terms.add(getTerm(cls));
				// just add it if nonexact search
			} else if (!ops.contains(SearchOptions.EXACT) && getLabel(cls).contains(query)) {
				terms.add(getTerm(cls));
			}

			if (ops.contains(SearchOptions.INCLUDE_PROPERTIES)) {
				for (List<String> annots : getAnnotations(cls).values()) {
					for (String annot : annots) {
						if (annot.contains(query)) {
							// filter out non exact matches
							if (ops.contains(SearchOptions.EXACT) && annot.equalsIgnoreCase(query)) {
								terms.add(getTerm(cls));
							} else if (!ops.contains(SearchOptions.EXACT)) {
								terms.add(getTerm(cls));
							}
						}
					}
				}
			}
		}

		return injectTermContext(new ArrayList<OntologyTerm>(terms), options);
	}

	private List<OntologyTerm> injectTermContext(List<OntologyTerm> list, SearchOptions[] options) {
		for (OntologyTerm ot : list) {
			ot.getContext().setSearchOptions(options);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#searchOntology(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession, String query,
			SearchOptions... options) throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession))
			return Collections.EMPTY_LIST;
		return searchAll(query, options);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getDefinitions()
	 */
	@Override
	public List<String> getDefinitions(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession))
			return Collections.EMPTY_LIST;
		List<String> result = new ArrayList<String>();
		try {
			result.addAll(getAnnotations(ontologyAccession, termAccession).get(definitionSlot));
		} catch (NullPointerException e) {
		}
		try {
			// prevent adding duplicates if user specified the same string
			// by setting definition slot
			if (!synonymSlot.equalsIgnoreCase("def"))
				result.addAll(getAnnotations(ontologyAccession, termAccession).get("def")); // OBO
		} catch (NullPointerException e) {
		}

		return result;
	}

	// helper function to manage the cache
	// TODO: this is potentially unsafe, and should
	// TODO: take into account ontology uri + accession, i.e. full URI
	private OWLClass getOwlClass(String termAccession) throws OntologyServiceException {
		// fill cache
		if (cache.get(termAccession) == null) {
			for (OWLClass cls : ontology.getReferencedClasses()) {
				if (getFragment(cls).equalsIgnoreCase(termAccession)) {
					cache.put(termAccession, cls);
					return cls;
				}
			}
			cache.put(termAccession, null);
		}
		return cache.get(termAccession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getAnnotations()
	 */
	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession))
			return Collections.EMPTY_MAP;
		return getAnnotations(getOwlClass(termAccession));
	}

	private Map<String, List<String>> getAnnotations(OWLClass cls) {
		Map<String, List<String>> metadata = new HashMap<String, List<String>>();
		if (cls == null)
			return Collections.EMPTY_MAP;
		for (OWLAnnotation annot : cls.getAnnotations(ontology)) {
			String key = getFragment(annot.getAnnotationURI());
			List<String> value = null;
			if (metadata.containsKey(key))
				value = metadata.get(key);
			else
				value = new ArrayList<String>();
			// get an ArrayList from value to add another annotation
			List<String> arr = value;
			arr.add(annot.getAnnotationValueAsConstant().getLiteral());
			// and convert it back to String[] before putting back in map
			metadata.put(key, arr);
		}
		if (metadata.size() == 0)
			return null;
		return metadata;
	}

	/**
	 * Helper method. Extracts the fragment uri from non-standard OWL uris.
	 * Cannot use URI.getFragment() as it's not always delimited with # (see EFO
	 * 
	 * @param cls
	 *            the OWLClass
	 * 
	 * @return the fragment
	 */
	private String getFragment(OWLClass cls) {
		return getFragment(cls.getURI());
	}

	private String getFragment(URI uri) {
		// can't use URI.getFragment() as it's not always delimited with # (see
		// EFO)
		Pattern pattern = Pattern.compile("[^//#=]*$");
		Matcher matcher = pattern.matcher(uri.toString());
		if (matcher.find())
			return matcher.group();
		return null;
	}

	/**
	 * Helper method. Extracts the the label
	 * 
	 * @param termAccession2
	 * 
	 * @param uri
	 *            the uri
	 * 
	 * @return the fragment
	 * @throws OntologyServiceException
	 */
	private String getLabel(OWLClass cls) throws OntologyServiceException {
		// Try the slot label (SKOS or custom)
		Map<String, List<String>> clsAnnotations = getAnnotations(cls);
		List<String> labels1 = null;
		List<String> labels2 = null;
		if (clsAnnotations != null) {
			labels1 = clsAnnotations.get(labelSlot);
			labels2 = clsAnnotations.get("label");
		}

		if (labels1 != null) {
			if (labels1.size() != 1)
				log.warn("Multple labels found on " + cls);
			return labels1.get(0);
			// Try the rdfs:label if no results from slotLabel
		} else if (labels2 != null) {
			if (labels2.size() != 1)
				log.warn("Multple labels found on " + cls);
			return labels2.get(0);
			// try fragment
		} else {
			return getFragment(cls);
		}
	}

}
