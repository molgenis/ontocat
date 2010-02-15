package uk.ac.ebi.ontocat;

import java.util.List;
import java.util.Map;

/**
 * Ontology service interface, e.g. Bioportal REST or OLS
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface OntologyService {
	/**
	 * Retrieve a term using its accession.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * 
	 * @return term
	 * @throws OntologyServiceException
	 */
	public OntologyTerm getTerm(String ontologyAccession, String termAccession) throws OntologyServiceException;

	/**
	 * Retrieve a term using only its accession. Here it is assumed that the
	 * source assumes terms have a unique accession between ontologies.
	 * 
	 * @param termAccession
	 *            . E.g. GO:00001
	 * @return
	 * @throws OntologyServiceException
	 */
	public OntologyTerm getTerm(String termAccession) throws OntologyServiceException;

	/**
	 * Retrieve a path to the root of the ontology. Returns a list of terms,
	 * starting with the root and finishing with the term itself
	 * 
	 * @param termAccession
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * List available ontology accessions
	 * 
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<Ontology> getOntologies() throws OntologyServiceException;

	/**
	 * Find terms matching key word within a particular ontology accession. This
	 * only returns a subset of information for each of the terms.
	 * 
	 * @param keyword
	 * @param ontologyAccession
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> searchOntology(String ontologyAccession, String keyword) throws OntologyServiceException;

	/**
	 * Find terms matching key word
	 * 
	 * @param keyword
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> searchAll(String keyword) throws OntologyServiceException;

	/**
	 * Find the root terms for this ontology
	 * 
	 * @param ontologyAccession
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getRootTerms(String ontologyAccession) throws OntologyServiceException;

	/**
	 * Find child concepts for this termAccession
	 * 
	 * @param termAccession
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Find parent concepts for this termAccession
	 * 
	 * @param termAccession
	 * @return terms
	 * @throws OntologyServiceException
	 */

	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Find annotations for this termAccession.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return
	 * @throws OntologyServiceException
	 */
	public Map<String, String[]> getAnnotations(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Find relations for this termAccession.
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return
	 * @throws OntologyServiceException
	 */
	public Map<String, String[]> getRelations(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Generate a hyperlink to drill down to ontology source
	 * 
	 * @param termAccession
	 * @return
	 */
	public String makeLookupHyperlink(String termAccession);

	/**
	 * Load an ontology from the service.
	 * 
	 * @param ontologyAccession
	 * @return
	 */
	public Ontology getOntology(String ontologyAccession) throws OntologyServiceException;

	/**
	 * Load synonyms for a term
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return
	 * @throws OntologyServiceException
	 */
	public String[] getSynonyms(String ontologyAccession, String termAccession) throws OntologyServiceException;
	
	/**
	 * Load definitions for a term
	 * 
	 * @param ontologyAccession
	 * @param termAccession
	 * @return
	 * @throws OntologyServiceException
	 */
	public String[] getDefinitions(String ontologyAccession, String termAccession) throws OntologyServiceException;
}
