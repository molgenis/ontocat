/**
 * Copyright (c) 2010 - 2011 European Molecular Biology Laboratory and University of Groningen
 *
 * Contact: ontocat-users@lists.sourceforge.net
 * 
 * This file is part of OntoCAT
 * 
 * OntoCAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * OntoCAT is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along
 * with OntoCAT. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.efo.bioportal;

import java.util.ArrayList;
import java.util.regex.Matcher;

import uk.ac.ebi.ontocat.OntologyIdMapping;
import uk.ac.ebi.ontocat.OntologyIdTranslator;
import uk.ac.ebi.ontocat.OntologyServiceException;

/**
 * Implementation of the OntologyIdTranslator customised for how the term IDs
 * are stored in the Experimental Factor Ontology (http://www.ebi.ac.uk/efo)
 * 
 * @author Tomasz Adamusiak
 */
public class EFOIDTranslator implements OntologyIdTranslator {
	/** The Constant mappings. */
	private static final ArrayList<OntologyIdMapping> mappings = initialiseMappings();

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.efo.bioportal.OntologyIdTranslator#getMappings()
	 */
	@Override
	public ArrayList<OntologyIdMapping> getMappings() {
		return mappings;
	}

	/**
	 * @return
	 */
	private static ArrayList<OntologyIdMapping> initialiseMappings() {
		ArrayList<OntologyIdMapping> temp = new ArrayList<OntologyIdMapping>();

		temp.add(new BioportalMapping("^TAO:", "(TAO:.*)", "1110", "TAO:0000117"));
		temp.add(new BioportalMapping("^ZFA:", "(ZFA:.*)", "1051", "ZFA:0000117"));
		temp.add(new BioportalMapping("^EFO_", "(EFO_.*)", "1136", "EFO_0000538"));
		temp.add(new BioportalMapping("^MSH", ":([A-Z]\\d+)", "1351", "MSH:D012512"));
		temp.add(new BioportalMapping("^NCBITaxon:", "(NCBITaxon:.*)", "1132", "NCBITaxon:2"));
		temp.add(new BioportalMapping("^MO_", "(MO_.*)", "1131", "MO_565"));
		temp.add(new BioportalMapping("^ICD9", "ICD9.*:([0-9[\\-\\.]]*)", "1101",
		"ICD9CM_1988:04.03"));
		temp.add(new BioportalMapping("^NCI(?! Meta)|^C\\d+", "(C\\d+)", "1032", "NCI C3235"));
		temp.add(new BioportalMapping("^PATO:", "(PATO:\\d+)", "1107", "PATO:0001323"));
		temp.add(new BioportalMapping("^OBI_", "(OBI_.*)", "1123", "OBI_0400105"));
		temp.add(new BioportalMapping("^IDOMAL:", "(IDOMAL:\\d+)", "1311", "IDOMAL:0000322"));
		temp.add(new BioportalMapping("^NIFSTD:|^sao|^nlx|^birnlex|^nifext",
				"((sao|nlx|birnlex|nifext).*)", "1084", "NIFSTD:birnlex_266"));
		temp.add(new BioportalMapping("^BTO:", "(BTO:\\d+)", "1005", "BTO:0001658"));
		temp.add(new BioportalMapping("^DOID:", "(DOID:\\d+)", "1009", "DOID:9778"));
		temp.add(new BioportalMapping("^FMA(ID)?:", "1053", "FMAID:9607"));
		temp.add(new BioportalMapping("^MAT:", "(MAT:\\d+)", "1152", "MAT:0000038"));
		temp.add(new BioportalMapping("^MP:", "(MP:\\d+)", "1025", "MP:0001672"));
		temp.add(new BioportalMapping("^FBbt:", "(FBbt:\\d+)", "1015", "FBbt:00000020"));
		temp.add(new BioportalMapping("^ZFS:", "(ZFS:\\d+)", "1051", "ZFS:0000008"));
		temp.add(new BioportalMapping("^TGMA:", "(TGMA:\\d+)", "1030", "TGMA:0000720"));
		temp.add(new BioportalMapping("^UO:", "(UO:\\d+)", "1112", "UO:0000044"));
		temp.add(new BioportalMapping("^SNOMEDCT", "1353", "SNOMEDCT_2005_01_31:196743006"));
		// CL has different letters in identifiers
		temp.add(new BioportalMapping("^CL:", "(CL:\\d+)", "1006", "CL:0000384"));
		temp.add(new BioportalMapping("^CHEBI:", "(CHEBI:\\d+)", "1007", "CHEBI:2365"));
		temp.add(new BioportalMapping("^GO:", "(GO:\\d+)", "1070", "GO:0009318"));
		// Can't resolve PO at the moment as it has been 'unloaded' -> see
		// remote site
		// temp.add(new BioportalMapping("^PO:", "1108", "PO:0020097"));
		return temp;
	}

	@Override
	public String getTranslatedTermAccession(String termAccession) throws OntologyServiceException {
		// Search through all the mappings
		// TODO: implement as a lookup table for speed
		for (OntologyIdMapping mapping : mappings) {
			if (mapping.getConfirmMatchPattern().matcher(termAccession).find()) {
				// Extract proper ID according to extratct pattern
				Matcher matcher = mapping.getExtractIDPattern().matcher(termAccession);
				if (matcher.find()) {
					return matcher.group(1);
				} else {
					return termAccession; // ok no number found just get the
					// whole id
				}
			}
		}
		throw new OntologyServiceException(new Exception("TERM NOT MAPPABLE"));
	}

	// this is a bit of a hack, no interest in mapping ontology accessions
	// directly though
	@Override
	public String getTranslatedOntologyAccession(String ontologyAccession)
	throws OntologyServiceException {
		return getOntologyAccFromTermAcc(ontologyAccession);
	}

	@Override
	public String getOntologyAccFromTermAcc(String termAccession) throws OntologyServiceException {
		// Search through all the mappings
		// TODO: implement as a lookup table for speed
		for (OntologyIdMapping mapping : mappings) {
			if (mapping.getConfirmMatchPattern().matcher(termAccession).find()) {
				return mapping.getExternalOntologyAccession();
			}
		}
		throw new OntologyServiceException(new Exception("TERM NOT MAPPABLE"));
	}
}
