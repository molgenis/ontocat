/**
 * 
 */
package uk.ac.ebi.efo.bioportal;

import java.util.regex.Pattern;

import uk.ac.ebi.ontocat.OntologyIdMapping;

/**
 * @author Tomasz Adamusiak, Morris Swertz
 * @version $Id$
 *
 */
/**
 * The Class BioportalMapping.
 */
public class BioportalMapping implements OntologyIdMapping {

	/** Confirms match to this particular ontology. */
	private final Pattern pConfirmMatch;
	/** Extracts bioportal compatible id from matched string. */
	private final Pattern pExtractID;
	/** Ontology id corresponding to confirmed pattern. */
	private final String externalOntologyAccession;
	/** Testing id for this particular ontology/pattern. */

	private final String testingCode;

	public Pattern getConfirmMatchPattern() {
		return pConfirmMatch;
	}

	public Pattern getExtractIDPattern() {
		return pExtractID;
	}

	@Override
	public String getLocalOntologyAccession() {
		throw new UnsupportedOperationException("Not implemented. Unnecessary.");
	}

	@Override
	public String getExternalOntologyAccession() {
		return externalOntologyAccession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.OntologyMapping#getTestCode()
	 */
	public String getTestCode() {
		return testingCode;
	}

	/**
	 * Instantiates a new bioportal mapping.
	 * 
	 */
	public BioportalMapping(String sMatchingPattern, String sExtractPattern,
			String externalOntologyAccession, String sTestingCode) {
		pConfirmMatch = Pattern.compile(sMatchingPattern);
		pExtractID = Pattern.compile(sExtractPattern);
		this.externalOntologyAccession = externalOntologyAccession;
		testingCode = sTestingCode;
	}

	/**
	 * Instantiates a new bioportal mapping. Default extract pattern to match
	 * only numbers.
	 * 
	 */
	public BioportalMapping(String sMatchingPattern,
			String externalOntologyAccession, String sTestingCode) {
		pConfirmMatch = Pattern.compile(sMatchingPattern);
		pExtractID = Pattern.compile(": *(\\d+)");
		this.externalOntologyAccession = externalOntologyAccession;
		testingCode = sTestingCode;
	}

}
