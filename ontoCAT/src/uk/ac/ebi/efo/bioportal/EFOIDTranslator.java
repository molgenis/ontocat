/**
 * 
 */
package uk.ac.ebi.efo.bioportal;

import java.util.ArrayList;
import java.util.regex.Matcher;

import uk.ac.ebi.ontocat.OntologyIdTranslator;
import uk.ac.ebi.ontocat.OntologyIdMapping;
import uk.ac.ebi.ontocat.OntologyServiceException;

/**
 * Implementation of the OntologyIdTranslator customised for how the term IDs
 * are stored in the Experimental Factor Ontology (http://www.ebi.ac.uk/efo)
 * 
 * @author Tomasz Adamusiak
 */
public class EFOIDTranslator implements OntologyIdTranslator {
	/** The Constant mappings. */
	private static final ArrayList<BioportalMapping> mappings = initialiseMappings();

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.OntologyIdTranslator#getMappings()
	 */
	@Override
	public ArrayList<BioportalMapping> getMappings() {
		return mappings;
	}

	/**
	 * @return
	 */
	private static ArrayList<BioportalMapping> initialiseMappings() {
		ArrayList<BioportalMapping> temp = new ArrayList<BioportalMapping>();
		temp.add(new BioportalMapping("^NCBITaxon:", "(NCBITaxon:.*)", "1132",
				"NCBITaxon:2"));
		temp.add(new BioportalMapping("^MSH", ":([A-Z]\\d+)", "1351", "MSH:D012512"));
		temp.add(new BioportalMapping("^MO_", "(MO_.*)", "1131", "MO_565"));
		temp.add(new BioportalMapping("^ICD9", "ICD9.*:([0-9[\\-\\.]]*)", "1101", "ICD9CM_1988:04.03"));
		temp.add(new BioportalMapping("^NCI(?! Meta)|^C\\d+", "(C\\d+)", "1032", "NCI C3235"));
		temp.add(new BioportalMapping("^PATO:", "1107", "PATO:0001323"));
		temp.add(new BioportalMapping("^OBI_", "(OBI_.*)", "1123", "OBI_0400105"));
		temp.add(new BioportalMapping("^IDOMAL:", "1311", "IDOMAL:0000322"));
		temp.add(new BioportalMapping("^NIFSTD:|^sao|^nlx|^birnlex|^nifext", "((sao|nlx|birnlex|nifext).*)", "1084",
				"NIFSTD:birnlex_266"));
		temp.add(new BioportalMapping("^BTO:", "1005", "BTO:0001658"));
		temp.add(new BioportalMapping("^DOID:", "1009", "DOID:9778"));
		temp.add(new BioportalMapping("^FMA(ID)?:", "1053", "FMAID:9607"));
		temp.add(new BioportalMapping("^MAT:", "1152", "MAT:0000038"));
		temp.add(new BioportalMapping("^MP:", "1025", "MP:0001672"));
		temp.add(new BioportalMapping("^FBbt:", "1015", "FBbt:00000020"));
		temp.add(new BioportalMapping("^ZFS:", "1051", "ZFS:0000008"));
		temp.add(new BioportalMapping("^TGMA:", "1030", "TGMA:0000720"));
		temp.add(new BioportalMapping("^UO:", "1112", "UO:0000044"));
		temp.add(new BioportalMapping("^SNOMEDCT", "1353", "SNOMEDCT_2005_01_31:196743006"));
		temp.add(new BioportalMapping("^CL:", "1006", "CL:0000127"));
		temp.add(new BioportalMapping("^CHEBI:", "1007", "CHEBI:2365"));
		temp.add(new BioportalMapping("^GO:", "(GO:\\d+)", "1070", "GO:0009318"));
		// Can't resolve PO at the moment as it has been 'unloaded' -> see remote site
		//temp.add(new BioportalMapping("^PO:", "1108", "PO:0020097"));
		return temp;
	}

	@Override
	public String getOntologyAccessionFromConcept(String termID) throws OntologyServiceException {
		// Search through all the mappings
		// TODO: implement as a lookup table for speed
		for (OntologyIdMapping BPmap : mappings) {
			if (BPmap.getConfirmMatchPattern().matcher(termID).find()) {
				return BPmap.getOntologyAccession();
			}
		}
		throw new OntologyServiceException(new Exception("TERM NOT MAPPABLE"));
	}

	@Override
	public String getTermAccessionFromConcept(String termID) throws OntologyServiceException {
		// Search through all the mappings
		// TODO: implement as a lookup table for speed
		for (OntologyIdMapping BPmap : mappings) {
			if (BPmap.getConfirmMatchPattern().matcher(termID).find()) {
				// Extract proper ID according to extratct pattern
				Matcher matcher = BPmap.getExtractIDPattern().matcher(termID);
				if (matcher.find()) {
					return matcher.group(1);
				} else {
					return termID; // ok no number found just get the whole id
				}
			}
		}
		throw new OntologyServiceException(new Exception("TERM NOT MAPPABLE"));
	}
}
