/**
 * 
 */
package uk.ac.ebi.ontocat;

import java.util.List;
import java.util.Map;

/**
 * A term within an ontology.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface OntologyTerm {
	/**
	 * Gets the accession.
	 * 
	 * @return the accession
	 */
	public String getAccession();

	/**
	 * Gets the ontology accession.
	 * 
	 * @return the ontology accession
	 */
	public String getOntologyAccession();

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 * @throws OntologyServiceException 
	 */
	public String getLabel() throws OntologyServiceException;
	
	/**
	 * Gets the metadata.
	 * 
	 * @return the metadata
	 * @throws OntologyServiceException 
	 */
	public Map<String, String[]> getAnnotations() throws OntologyServiceException;

	/**
	 * Gets the synonyms.
	 * 
	 * @return the synonyms
	 * @throws OntologyServiceException 
	 */
	public String[] getSynonyms() throws OntologyServiceException;

	/**
	 * Gets the definitions.
	 * 
	 * @return the definitions
	 * @throws OntologyServiceException 
	 */
	public String[] getDefinitions() throws OntologyServiceException;
	
	/**
	 * Gets the relations
	 */
	public Map<String, String[]> getRelations() throws OntologyServiceException;
	
	/**
	 * Get the terms indicated as children
	 * @throws OntologyServiceException 
	 */
	public List<OntologyTerm> getChildren() throws OntologyServiceException;
	
	/**
	 * Get the terms indicated as parents
	 */
	public List<OntologyTerm> getParents() throws OntologyServiceException;
	
	/**
	 * Retrieve a path to the root of the ontology
	 * 
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getTermPath()	throws OntologyServiceException;
}
