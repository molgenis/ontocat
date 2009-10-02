package plugin.OntologyBrowser;

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
	 *            (format GO:00001)
	 * @param termAccession
	 *            (format GO:00001)
	 * @return term
	 * @throws OntologyServiceException
	 */
	public OntologyTermExt getTerm(String ontologyAccession, String termAccession) throws OntologyServiceException;

	public OntologyTermExt getTerm(String termAccession) throws OntologyServiceException;

	public String getLabelForAccession(String ontologyAccession, String termAccession) throws OntologyServiceException;

	/**
	 * Retrieve a path to the root of the ontology
	 * 
	 * @param termAccession
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyTermExt> getTermPath(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * List available ontology accessions
	 * 
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyEntity> getOntologies() throws OntologyServiceException;

	/**
	 * Gets an ontology with a specified ontologyAccession
	 * 
	 * @return OntologyEntity
	 * @throws OntologyServiceException
	 */
	public OntologyEntity getOntologyDescription(String ontologyAccession) throws OntologyServiceException;

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
	public List<OntologyTermExt> getRootTerms(String ontologyAccession) throws OntologyServiceException;

	/**
	 * Find child concepts for this termAccession
	 * 
	 * @param termAccession
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyTermExt> getChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Find parent concepts for this termAccession
	 * 
	 * @param accs
	 * @return
	 * @throws OntologyServiceException
	 */
	public List<OntologyTermExt> getParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException;

	/**
	 * Find annotations for this termAccession. Can be anything hence the map.
	 * 
	 * @throws OntologyServiceException
	 */
	public Map<String, String[]> getAnnotations(String ontologyAccession, String termAccession)
			throws OntologyServiceException;
}
