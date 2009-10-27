/**
 * 
 */
package uk.ac.ebi.efo.bioportal;

import java.util.ArrayList;

import plugin.OntologyBrowser.OntologyServiceException;

/**
 * Stores mappings and regular expressions required to convert a given string to
 * an ID serchable in Bioportal.
 * 
 * @author Tomasz
 * 
 */
public interface BioportalIdResolver {
	public ArrayList<BioportalMapping> getMappings();

	public String getOntologyAccessionFromConcept(String sConcept) throws OntologyServiceException;

	public String getTermAccessionFromConcept(String sConcept) throws OntologyServiceException;
}
