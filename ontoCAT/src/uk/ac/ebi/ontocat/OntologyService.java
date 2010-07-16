package uk.ac.ebi.ontocat;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Ontology service interface, e.g. Bioportal REST or OLS
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface OntologyService {
	/**
	 * Enumeration for search options in searchAll and searchOntology. Without
	 * setting any options the search will be the most permissive, i.e. match
	 * substrings and in all properties.
	 * <p>
	 * EXACT - only exact matching of query. EXCLUE_PROPERTIES excludes -
	 * properties from search.
	 */
	public enum SearchOptions {
		EXACT, INCLUDE_PROPERTIES
	};

	/**
	 * Retrieve a term using its accession.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * 
	 * @return OntologyTerm or null if nothing was found
	 * @throws OntologyServiceException
	 */
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Retrieve a term using only its accession. Here it is assumed that the
	 * source assumes terms have a unique accession between ontologies.
	 * 
	 * @param termAccession
	 *            . E.g. GO:00001
	 * @return OntologyTerm or null if nothing was found
	 * @throws OntologyServiceException
	 */
	public OntologyTerm getTerm(String termAccession) throws OntologyServiceException;

	/**
	 * Retrieve a path to the root of the ontology. Returns a list of terms,
	 * starting with the root and finishing with the term itself
	 * 
	 * @param termAccession
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Retrieve a path to the root of the ontology. Returns a list of terms,
	 * starting with the root and finishing with the term itself
	 * 
	 * @param term
	 * @return List of OntologyTerms that has at least one element
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getTermPath(OntologyTerm term) throws OntologyServiceException;

	/**
	 * List available ontology accessions
	 * 
	 * @return List of Ontologies or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<Ontology> getOntologies() throws OntologyServiceException;

	/**
	 * Find terms matching keyword within a particular ontology accession. This
	 * only returns a subset of information for each of the terms. Without
	 * setting any options the search will be the most permissive, i.e. match
	 * substrings and in all properties.
	 * 
	 * @param ontologyAccession
	 * @param query
	 *            - query to be sent to the OntologyService, usually a keyword
	 *            to be searched for
	 * @param options
	 *            - EXACT - only exact matching of query. EXCLUDE_PROPERTIES
	 *            excludes - properties from search.
	 * @param options
	 *            - search options for the query
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> searchOntology(String ontologyAccession, String query,
			SearchOptions... options) throws OntologyServiceException;

	/**
	 * Find terms matching key word. Without setting any options the search will
	 * match substrings but will not include properties.
	 * 
	 * @param query
	 *            - query to be sent to the OntologyService, usually a keyword
	 *            to be searched for
	 * @param options
	 *            - EXACT - only exact matching of query. INCLUDE_PROPERTIES -
	 *            includes properties in search.
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> searchAll(String query, SearchOptions... options)
			throws OntologyServiceException;

	/**
	 * Find the root terms for this ontology
	 * 
	 * @param ontologyAccession
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException;

	/**
	 * Find the root terms for this ontology
	 * 
	 * @param ontology
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getRootTerms(Ontology ontology) throws OntologyServiceException;

	/**
	 * Find child concepts for this termAccession
	 * 
	 * @param termAccession
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Find child concepts for this termAccession
	 * 
	 * @param term
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getChildren(OntologyTerm term) throws OntologyServiceException;

	/**
	 * Find parent concepts for this termAccession
	 * 
	 * @param termAccession
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Find parent concepts for this termAccession
	 * 
	 * @param termAccession
	 * @return List of OntologyTerms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getParents(OntologyTerm term) throws OntologyServiceException;

	/**
	 * Find annotations for this termAccession.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return Map of OntologyTerms or an empty map if nothing was found
	 * @throws OntologyServiceException
	 */
	public Map<String, List<String>> getAnnotations(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Find annotations for this termAccession.
	 * 
	 * @param term
	 * @return Map of OntologyTerms or an empty map if nothing was found
	 * @throws OntologyServiceException
	 */
	public Map<String, List<String>> getAnnotations(OntologyTerm term)
			throws OntologyServiceException;

	/**
	 * Find relations for this termAccession.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return Map of OntologyTerms or an empty map if nothing was found
	 * @throws OntologyServiceException
	 */
	public Map<String, List<String>> getRelations(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Find relations for this termAccession.
	 * 
	 * @param term
	 * @return Map of OntologyTerms or an empty map if nothing was found
	 * @throws OntologyServiceException
	 */
	public Map<String, List<String>> getRelations(OntologyTerm term)
			throws OntologyServiceException;

	/**
	 * Generate a hyperlink to drill down to ontology source
	 * 
	 * @param termAccession
	 * @return lookup hyperlink or null
	 */
	public String makeLookupHyperlink(String termAccession);

	/**
	 * Generate a hyperlink to drill down to ontology source
	 * 
	 * @param termAccession
	 * @return lookup hyperlink or null
	 */
	public String makeLookupHyperlink(String ontologyAccession, String termAccession);

	/**
	 * Load an ontology from the service.
	 * 
	 * @param ontologyAccession
	 * @return Ontology or null if nothing was found
	 */
	public Ontology getOntology(String ontologyAccession) throws OntologyServiceException;

	/**
	 * Load synonyms for a term
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return List of synonyms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<String> getSynonyms(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Load synonyms for a term
	 * 
	 * @param term
	 * @return List of synonyms or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<String> getSynonyms(OntologyTerm term) throws OntologyServiceException;

	/**
	 * Load definitions for a term
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return List of definitions or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<String> getDefinitions(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Load definitions for a term
	 * 
	 * @param term
	 * @return List of definitions or an empty list if nothing was found
	 * @throws OntologyServiceException
	 */
	public List<String> getDefinitions(OntologyTerm term) throws OntologyServiceException;

	/**
	 * Gets the all terms in a given ontology
	 * 
	 * @param os
	 *            the ontology service encapsulating the ontology
	 * @param ontologyAccession
	 *            the ontology accession identifying the ontology in the service
	 * 
	 * @return A set as we want to collapse duplicate terms
	 * 
	 * @throws OntologyServiceException
	 */
	public Set<OntologyTerm> getAllTerms(String ontologyAccession) throws OntologyServiceException;

	/**
	 * Gets the all children terms of a given term.
	 * 
	 * @param os
	 *            the ontology service encapsulating the ontology
	 * @param term
	 *            the term which children are to be returned
	 * 
	 * @return all the child terms of the given term in is_a hierarchy or an
	 *         empty set if no parents were found
	 */
	public Set<OntologyTerm> getAllChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Gets the all parent terms of a given term.
	 * 
	 * @param os
	 *            the ontology service encapsulating the ontology
	 * @param term
	 *            the term which parents are to be returned
	 * 
	 * @return all the parent terms of the given term in is_a hierarchy or an
	 *         empty set if no parents were found
	 */
	public Set<OntologyTerm> getAllParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

}
