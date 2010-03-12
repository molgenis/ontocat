/**
 * 
 */
package uk.ac.ebi.ontocat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ontology ID translation interface. Defines mappings and regular expressions
 * required to convert a given string to an ID searchable in an OntologyService.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface OntologyIdTranslator extends Serializable {
	public ArrayList<OntologyIdMapping> getMappings();

	public String getTranslatedOntologyAccession(String ontologyAccession)
			throws OntologyServiceException;

	public String getTranslatedTermAccession(String termAccession)
			throws OntologyServiceException;

	public String getOntologyAccFromTermAcc(String termAccession)
			throws OntologyServiceException;
}
