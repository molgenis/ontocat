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
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * The Class FileOntologyService. This works on both OBO and OWL files.
 */
public class FileOntologyService extends AbstractOntologyService
implements OntologyService {
	/** The Constant log. */
	private static final Logger log = Logger
	.getLogger(FileOntologyService.class.getName());

	/** The ontology. */
	protected final OWLOntology ontology;

	// Multiple ontology namespaces per OWL file are permitted
	protected Set<String> ontoAccessions = new HashSet<String>();

	/** The slots. */
	/** The skos:synonym slot. */
	private String synonymSlot = "altLabel";

	private Matcher regexDropSyns = Pattern.compile("\"?([^\"]*)").matcher("");

	/** The skos:definition slot. */
	public String definitionSlot = "definition";

	/** The skos:label slot if not rdfs:label is used */
	private String labelSlot = "prefLabel";

	/**
	 * The uri this file service was instantiated with. It is used by the
	 * LocalisedFileService to consistently overwrite ontology accession
	 */
	private URI uriOntology;

	public URI getOntologyUri() {
		return uriOntology;
	}

	/**
	 * Sets the synonym slot in a particular ontology. Note that synonym,
	 * altLabel(default synonymSlot) and alternative_term are recognised
	 * automatically.
	 * 
	 * @param synonymSlot
	 *            the new synonym slot
	 */
	public void setSynonymSlot(String synonymSlot) {
		this.synonymSlot = synonymSlot;

	}

	/**
	 * Sets the definition slot. Note that definition is recognised
	 * automatically.
	 * 
	 * @param definitionSlot
	 *            the new definition slot
	 */
	public void setDefinitionSlot(String definitionSlot) {
		this.definitionSlot = definitionSlot;

	}

	/**
	 * Sets the label slot.
	 * 
	 * @param labelSlot
	 *            the new label slot
	 */
	public void setLabelSlot(String labelSlot) {
		this.labelSlot = labelSlot;

	}

	/** The map with classes */
	private final Map<String, OWLEntity> cache = new TreeMap<String, OWLEntity>();

	protected OntologyLoader loader;

	/**
	 * Instantiates a new file ontology service. Allows to set a user-defined
	 * accession that can be later used for queries.
	 * 
	 * @param uriOntology
	 *            where to load the ontology from, can be local file or URL
	 * @param ontologyAccession
	 *            user-defined ontology accession
	 * @throws OntologyServiceException
	 */
	public FileOntologyService(URI uriOntology, String ontologyAccession)
	throws OntologyServiceException {
		this(uriOntology);

		// add the user defined one
		ontoAccessions.add(ontologyAccession);
	}

	/**
	 * Instantiates a new file ontology service.
	 * 
	 * @param uriOntology
	 *            where to load the ontology from, can be local file or URL
	 * @throws OntologyServiceException
	 */
	public FileOntologyService(URI uriOntology) throws OntologyServiceException {
		loader = new OntologyLoader(uriOntology);
		ontology = loader.getOntology();

		// get all possible URIs in onotlogy
		// and load classes and individuals into the cache
		for (OWLEntity ent : ontology.getSignature()) {
			if (ent.isOWLClass() || ent.isOWLNamedIndividual()){
				cache.put(getFragment(ent), ent);
			}
			try {
				ontoAccessions.add(getOntologyAccession(ent));
			} catch (OntologyServiceException e) {
				log.error(e.getMessage());
			}
		}

		// add the uri it was instantiated with
		ontoAccessions.add(uriOntology.toString());
		// add the one generated by OWLAPI
		ontoAccessions.add(ontology.getOntologyID().toString()
				.replaceFirst("^<", "").replace(">$", ""));
		ontoAccessions.add(loader.getManager().getOntologyDocumentIRI(ontology)
				.toString());
		this.uriOntology = uriOntology;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getOntologies()
	 */
	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		// FIXME: this lists the main uri, but more namespaces are possible
		// FIXME: should iterate over ontoAccessions maybe?
		return new ArrayList<Ontology>() {
			private static final long serialVersionUID = 1L;

			{
				String ontologyAccession = ontology.getOntologyID().toString();
				ontologyAccession = ontologyAccession.replaceFirst("^<", "")
				.replaceFirst(">$", "");
				add(new Ontology(ontologyAccession));
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getOntology(java.lang.String)
	 */
	@Override
	public Ontology getOntology(String ontologyAccession)
	throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return null;
		}
		return getOntologies().get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getRelations(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, Set<OntologyTerm>> getRelations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		throw new UnsupportedOperationException("Not implemented. Use ReasonedFileOntologyService");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getRootTerms(java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
	throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return Collections.emptyList();
		}
		List<OntologyTerm> rootTerms = new ArrayList<OntologyTerm>();
		for (OWLEntity ent : cache.values()) {
			// class without parents, looks like root
			// needs reasoner, otherwise fails for defined classes
			if (ent.isOWLClass()) {
				OWLClass cls = ent.asOWLClass();
				if (getParents(getTerm(ent)).size() == 0
						&& !getAnnotations(cls).containsKey("is_obsolete")) {
					rootTerms.add(getTerm(cls));
				}
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
	public List<String> getSynonyms(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return Collections.emptyList();
		}

		Map<String, List<String>> annots = getAnnotations(ontologyAccession,
				termAccession);

		return findAnnotations(annots, synonymSlot, "synonym",
				"alternative_term", "altLabel");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getDefinitions()
	 */
	@Override
	public List<String> getDefinitions(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return Collections.emptyList();
		}

		Map<String, List<String>> annots = getAnnotations(ontologyAccession,
				termAccession);

		return findAnnotations(annots, definitionSlot, "def");
	}

	private List<String> findAnnotations(Map<String, List<String>> annots,
			String... Names) {
		// Using a set here to avoid duplicates
		// if the user already specified the synonymSlot
		// equal to the two last tests here
		// also duplicates could potentially be returned
		// via a list
		Set<String> result = new HashSet<String>();
		List<String> temp;

		for (String name : Names) {
			temp = annots.get(name);
			if (temp != null) {
				result.addAll(dropQuotes(temp));
			}
		}

		return new ArrayList<String>(result);
	}

	/**
	 * Drop flanking quotes in all the list values.
	 * 
	 * @param list
	 *            the list
	 * @return the list
	 */
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
		if (ontologyAccession != null
				&& !ontoAccessions.contains(ontologyAccession)) {
			return null;
		}
		return getTerm(termAccession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTerm(java.lang.String)
	 */
	@Override
	public OntologyTerm getTerm(String termAccession)
	throws OntologyServiceException {
		return getTerm(getOwlEntity(termAccession));
	}

	protected OntologyTerm getTerm(OWLEntity ent) throws OntologyServiceException {
		if (ent == null) {
			return null;
		}
		String ontologyAccession = getOntologyAccession(ent);
		String termAccession = getFragment(ent);
		String label = getLabel(ent);
		URI uri = ent.getIRI().toURI();
		return new OntologyTerm(ontologyAccession, termAccession, label, uri);
	}

	private String getOntologyAccession(OWLEntity ent)
	throws OntologyServiceException {
		Pattern pattern = Pattern.compile("^.*[//#]{1}");
		Matcher matcher = pattern.matcher(ent.toStringID());
		if (matcher.find()) {
			return matcher.group();
		} else {
			throw new OntologyServiceException(
					"Could not create ontologyAccession for " + ent);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTermPath(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		ArrayList<OntologyTerm> termPath = new ArrayList<OntologyTerm>();
		int iteration = 0;
		// seed the list with this term
		OntologyTerm term = getTerm(ontologyAccession, termAccession);
		termPath.add(term);
		if (!ontoAccessions.contains(ontologyAccession)) {
			return termPath;
		}
		// get its parents and iterate over first parent
		List<OntologyTerm> parents = getParents(ontologyAccession,
				termAccession);
		while (parents.size() != 0) {
			term = parents.get(0);
			termPath.add(term);
			parents = getParents(term.getOntologyAccession(),
					term.getAccession());
			// safety break for circular relations
			if (iteration++ > 100) {
				log.error("getTermPath(): TOO MANY ITERATIONS (" + iteration
						+ "x)");
				break;
			}
		}
		// reverse to have root as first
		Collections.reverse(termPath);
		return termPath;
	}

	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return Collections.EMPTY_LIST;
		}
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		OWLEntity ent = getOwlEntity(termAccession);
		if (ent == null) {
			return Collections.EMPTY_LIST;
		}
		if (ent.isOWLClass()) {
			for (OWLClassExpression desc : ent.asOWLClass().getSubClasses(
					ontology)) {
				if (!desc.isAnonymous()) {
					list.add(getTerm(desc.asOWLClass()));
				}
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
	public List<OntologyTerm> getParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return Collections.EMPTY_LIST;
		}
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		OWLEntity ent = getOwlEntity(termAccession);
		if (ent == null) {
			return Collections.EMPTY_LIST;
		}
		if (ent.isOWLClass()) {
			for (OWLClassExpression desc : ent.asOWLClass().getSuperClasses(
					ontology)) {
				if (!desc.isAnonymous()) {
					list.add(getTerm(desc.asOWLClass()));
				}
			}
		}
		if (ent.isOWLNamedIndividual()) {
			for (OWLClassExpression desc : ent.asOWLNamedIndividual().getTypes(
					ontology)) {
				if (!desc.isAnonymous()) {
					list.add(getTerm(desc.asOWLClass()));
				}
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
	public Set<OntologyTerm> getAllTerms(String ontologyAccession)
	throws OntologyServiceException {
		Set<OntologyTerm> result = new HashSet<OntologyTerm>();
		for (OWLEntity ent : cache.values()) {
			result.add(getTerm(ent));
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
			return getOwlEntity(termAccession).toStringID();
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
	public String makeLookupHyperlink(String ontologyAccession,
			String termAccession) {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return null;
		}
		try {
			return getOwlEntity(termAccession).toStringID();
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
		List<SearchOptions> ops = new ArrayList<SearchOptions>(
				Arrays.asList(options));
		Map<OntologyTerm, String> terms = new HashMap<OntologyTerm, String>();

		query = query.toLowerCase();

		// iterate through labels
		for (OWLEntity ent : cache.values()) {
			resolveOptionsAndMatch(terms, query, getLabel(ent), ent, ops);
		}

		// iterate through properties
		if (ops.contains(SearchOptions.INCLUDE_PROPERTIES)) {
			for (OWLEntity ent : cache.values()) {
				for (List<String> annots : getAnnotations(ent).values()) {
					for (String annot : annots) {
						resolveOptionsAndMatch(terms, query, annot, ent, ops);
					}
				}
			}
		}

		return injectTermContext(terms, query, options);
	}

	/**
	 * Resolves the search options and if match is appropriate adds the
	 * OntologyTerm to the map for later similarity processing
	 * 
	 * @param terms
	 *            map with all the terms and values they were matched on
	 * @param query
	 *            search query
	 * @param value
	 *            value being matched (e.g. label, synonym)
	 * @param cls
	 *            the class that contained the annotations
	 * @param options
	 *            the search options
	 * @return the map original map plus the matched term if any
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private Map<OntologyTerm, String> resolveOptionsAndMatch(
			Map<OntologyTerm, String> terms, String query, String value,
			OWLEntity ent, List<SearchOptions> options)
			throws OntologyServiceException {

		if (value.toLowerCase().contains(query)) {
			// just add it non-exact search
			if (!options.contains(SearchOptions.EXACT)) {
				terms.put(getTerm(ent), value);
				// otherwise check for exact match
			} else if (value.equalsIgnoreCase(query)) {
				terms.put(getTerm(ent), value);
			}
		}

		return terms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#searchOntology(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession,
			String query, SearchOptions... options)
			throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return Collections.emptyList();
		}
		return searchAll(query, options);
	}

	// helper function to manage the cache
	// FIXME: this is potentially unsafe, and should
	// FIXME: take into account ontology uri + accession, i.e. full URI
	protected OWLEntity getOwlEntity(String termAccession)
	throws OntologyServiceException {
		OWLEntity result = cache.get(termAccession);
		if (result == null){
			// try replacing : with _ as per the OBO Foundry
			// id policy http://www.obofoundry.org/id-policy.shtml
			result = cache.get(termAccession.replace(":", "_"));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getAnnotations()
	 */
	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		if (!ontoAccessions.contains(ontologyAccession)) {
			return Collections.emptyMap();
		}
		return getAnnotations(getOwlEntity(termAccession));
	}

	private Map<String, List<String>> getAnnotations(OWLEntity ent) {
		Map<String, List<String>> metadata = new HashMap<String, List<String>>();
		if (ent == null) {
			return Collections.emptyMap();
		}
		for (OWLAnnotation annot : ent.getAnnotations(ontology)) {
			String key = getFragment(annot.getProperty().getIRI().toURI());
			List<String> value = null;
			if (metadata.containsKey(key)) {
				value = metadata.get(key);
			} else {
				value = new ArrayList<String>();
			}
			// get an ArrayList from value to add another annotation
			List<String> arr = value;
			arr.add(((OWLLiteral) annot.getValue()).getLiteral());
			// and convert it back to String[] before putting back in map
			metadata.put(key, arr);
		}
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
	protected String getFragment(OWLEntity ent) {
		return getFragment(ent.getIRI().toURI());
	}

	private String getFragment(URI uri) {
		// can't use URI.getFragment() as it's not always delimited with # (see
		// EFO)
		Pattern pattern = Pattern.compile("[^//#=]*$");
		Matcher matcher = pattern.matcher(uri.toString());
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * Helper method. Extracts the label
	 * 
	 * @param termAccession2
	 * 
	 * @param uri
	 *            the uri
	 * 
	 * @return the fragment
	 * @throws OntologyServiceException
	 */
	protected String getLabel(OWLEntity ent) throws OntologyServiceException {
		// Try the slot label (SKOS or custom)
		Map<String, List<String>> clsAnnotations = getAnnotations(ent);
		List<String> labels1 = null;
		List<String> labels2 = null;
		if (clsAnnotations != null) {
			labels1 = clsAnnotations.get(labelSlot);
			labels2 = clsAnnotations.get("label");
		}

		if (labels1 != null) {
			if (labels1.size() != 1) {
				log.warn("Multple labels found on " + ent);
			}
			return labels1.get(0);
			// Try the rdfs:label if no results from slotLabel
		} else if (labels2 != null) {
			if (labels2.size() != 1) {
				log.warn("Multple labels found on " + ent);
			}
			return labels2.get(0);
			// try fragment
		} else {
			return getFragment(ent);
		}
	}

}
