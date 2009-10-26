/**
 * 
 */
package uk.ac.ebi.efo.bioportal;

import java.util.regex.Pattern;

/**
 * @author Tomasz
 *
 */
/**
 * The Class BioportalMapping.
 */
public class BioportalMapping {

	/** Confirms match to this particular ontology. */
	private final Pattern pConfirmMatch;
	/** Extracts bioportal compatible id from matched string. */
	private final Pattern pExtractID;
	/** Ontology id corresponding to confirm pattern. */
	private final String ontologyID;
	/** Testing id for this particular ontology/pattern. */
	private final String testingCode;

	/**
	 * Gets confirming pattern.
	 * 
	 * @return pattern
	 */
	public Pattern getConfirmMatchPattern() {
		return pConfirmMatch;
	}

	/**
	 * Gets the extraction pattern.
	 * 
	 * @return pattern
	 */
	public Pattern getExtractIDPattern() {
		return pExtractID;
	}

	/**
	 * Gets the ont id.
	 * 
	 * @return the ont id
	 */
	public String getOntologyID() {
		return ontologyID;
	}

	/**
	 * Gets the test code.
	 * 
	 * @return the test code
	 */
	public String getTestCode() {
		return testingCode;
	}

	/**
	 * Instantiates a new bioportal mapping.
	 * 
	 */
	public BioportalMapping(String sMatchingPattern, String sExtractPattern, String sOntologyID, String sTestingCode) {
		pConfirmMatch = Pattern.compile(sMatchingPattern);
		pExtractID = Pattern.compile(sExtractPattern);
		ontologyID = sOntologyID;
		testingCode = sTestingCode;
	}

	/**
	 * Instantiates a new bioportal mapping. Default extract pattern to match
	 * only numbers.
	 * 
	 */
	public BioportalMapping(String sMatchingPattern, String sOntologyID, String sTestingCode) {
		pConfirmMatch = Pattern.compile(sMatchingPattern);
		pExtractID = Pattern.compile("(\\d+)");
		ontologyID = sOntologyID;
		testingCode = sTestingCode;
	}
}
