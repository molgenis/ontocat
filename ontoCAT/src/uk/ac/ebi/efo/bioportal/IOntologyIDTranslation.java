/**
 * 
 */
package uk.ac.ebi.efo.bioportal;

import java.util.ArrayList;

import uk.ac.ebi.ontocat.OntologyServiceException;

/**
 * Ontology ID translation interface. Defines mappings and regular expressions
 * required to convert a given string to an ID searchable in OntologyService.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface IOntologyIDTranslation {
	public ArrayList<BioportalMapping> getMappings();

	public String getOntologyAccessionFromConcept(String sConcept) throws OntologyServiceException;

	public String getTermAccessionFromConcept(String sConcept) throws OntologyServiceException;
}
