/**
 * 
 */
package uk.ac.ebi.ontocat;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Ontology mapping interface.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 */
public interface OntologyIdMapping extends Serializable {

	/**
	 * A regular expression pattern which confirms that a particular concept
	 * string represents OntologyTerm encapsulated by this mapping.
	 * 
	 * @return pattern
	 */
	public Pattern getConfirmMatchPattern();

	/**
	 * A regular expression pattern which can convert a concept string confirmed
	 * with MatchPattern to a format acceptable by the OntologyService.
	 * 
	 * @return pattern
	 */
	public Pattern getExtractIDPattern();

	/**
	 * Gets the external ontology accession
	 * 
	 * @return the ont id
	 */
	public String getExternalOntologyAccession();

	/**
	 * Gets the local ontology accession
	 * 
	 * @return the ont id
	 */
	public String getLocalOntologyAccession();

	/**
	 * Testing ontology term code used to confirm that the service can resolve
	 * the mappings correctly.
	 * 
	 * @return the test code
	 */
	public String getTestCode();

}