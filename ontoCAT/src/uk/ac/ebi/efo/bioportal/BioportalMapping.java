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
public class BioportalMapping extends OntologyIdMapping {

	/**
	 * Instantiates a new bioportal mapping.
	 * 
	 */
	public BioportalMapping(String sMatchingPattern, String sExtractPattern,
			String externalOntologyAccession, String sTestingCode) {
		super();
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
		super();
		pConfirmMatch = Pattern.compile(sMatchingPattern);
		pExtractID = Pattern.compile(": *(\\d+)");
		this.externalOntologyAccession = externalOntologyAccession;
		testingCode = sTestingCode;
	}

}
