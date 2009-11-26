/**
 * 
 */
package uk.ac.ebi.efo.bioportal;

import java.util.ArrayList;

import uk.ac.ebi.ontoapi.OntologyServiceException;


/**
 * Stores mappings and regular expressions required to convert a given string to
 * an ID searchable in Bioportal.
 * 
 * @author Tomasz Adamusiak
 * 
 */
public interface IBioportalIDTranslation {
	public ArrayList<BioportalMapping> getMappings();

	public String getOntologyAccessionFromConcept(String sConcept) throws OntologyServiceException;

	public String getTermAccessionFromConcept(String sConcept) throws OntologyServiceException;
}
