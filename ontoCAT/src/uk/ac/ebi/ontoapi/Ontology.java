/**
 * 
 */
package uk.ac.ebi.ontoapi;

import java.util.List;

/**
 * Wraps metadata for a whole ontology. E.g. EFO, OBI.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface Ontology
{
	/**
	 * Property where this ontology stores annotations of synonym terms
	 * 
	 * @return
	 */
	public String getSynonymSlot() throws OntologyServiceException;

	/**
	 * Version of the ontology if available
	 * 
	 * @return
	 */
	public String getVersionNumber() throws OntologyServiceException;

	/**
	 * Date when this ontology was released
	 * 
	 * @return
	 * @throws OntologyServiceException 
	 */
	public String getDateReleased() throws OntologyServiceException;

	/**
	 * Pretty printing label
	 * 
	 * @return
	 */
	public String getLabel() throws OntologyServiceException;

	/**
	 * Abbreviation if applicable. E.g. 'GO'.
	 * 
	 * @return
	 */
	public String getAbbreviation() throws OntologyServiceException;

	/**
	 * Accession for the ontology. Depends on the source.
	 * 
	 * @return
	 */
	public String getOntologyAccession() throws OntologyServiceException;
	
	/**
	 * Rich description of the ontology.
	 */
	public String getDescription() throws OntologyServiceException;
	
	/**
	 * Find the root terms for this ontology
	 * 
	 * @param ontologyAccession
	 * @return terms
	 * @throws OntologyServiceException
	 */
	public List<OntologyTerm> getRootTerms() throws OntologyServiceException;


}