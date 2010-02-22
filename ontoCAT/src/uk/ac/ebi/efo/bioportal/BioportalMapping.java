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
	private final String ontologyID;
	/** Testing id for this particular ontology/pattern. */
	private final String testingCode;

	/* (non-Javadoc)
	 * @see uk.ac.ebi.efo.bioportal.OntologyMapping#getConfirmMatchPattern()
	 */
	public Pattern getConfirmMatchPattern() {
		return pConfirmMatch;
	}

	/* (non-Javadoc)
	 * @see uk.ac.ebi.efo.bioportal.OntologyMapping#getExtractIDPattern()
	 */
	public Pattern getExtractIDPattern() {
		return pExtractID;
	}

	/* (non-Javadoc)
	 * @see uk.ac.ebi.efo.bioportal.OntologyMapping#getOntologyID()
	 */
	public String getOntologyAccession() {
		return ontologyID;
	}

	/* (non-Javadoc)
	 * @see uk.ac.ebi.efo.bioportal.OntologyMapping#getTestCode()
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
		pExtractID = Pattern.compile(": *(\\d+)");
		ontologyID = sOntologyID;
		testingCode = sTestingCode;
	}
}
