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
public class OntologyIdMapping implements Serializable {
	/** Confirms match to this particular ontology. */
	protected Pattern pConfirmMatch;
	/** Extracts bioportal compatible id from matched string. */
	protected Pattern pExtractID;

	protected String externalOntologyAccession;
	protected String localOntologyAccession;

	protected String testingCode;

	public OntologyIdMapping() {
	}

	/**
	 * A regular expression pattern which confirms that a particular concept
	 * string represents OntologyTerm encapsulated by this mapping.
	 * 
	 * @return pattern
	 */
	public Pattern getConfirmMatchPattern(){
		return pConfirmMatch;
	}

	/**
	 * A regular expression pattern which can convert a concept string confirmed
	 * with MatchPattern to a format acceptable by the OntologyService.
	 * 
	 * @return pattern
	 */
	public Pattern getExtractIDPattern() {
		return pExtractID;
	}

	/**
	 * Gets the external ontology accession
	 * 
	 * @return the ont id
	 */
	public String getExternalOntologyAccession(){
		return externalOntologyAccession;
	}

	public void setExternalOntologyAccession(String value) {
		externalOntologyAccession = value;
	}

	/**
	 * Gets the local ontology accession
	 * 
	 * @return the ont id
	 */
	public String getLocalOntologyAccession(){
		return localOntologyAccession;
	}

	public void setLocalOntologyAccession(String value) {
		localOntologyAccession = value;
	}

	/**
	 * Testing ontology term code used to confirm that the service can resolve
	 * the mappings correctly.
	 * 
	 * @return the test code
	 */
	public String getTestCode(){
		return testingCode;
	}

}