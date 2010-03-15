/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.ontocat.OntologyIdMapping;
import uk.ac.ebi.ontocat.OntologyIdTranslator;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.virtual.TranslatedOntologyService;

/**
 * Example 7
 * 
 * Shows how to translate ontology to a different namespace
 * 
 */
public class Example7 {
	@SuppressWarnings("serial")
	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate ontology services to be combined
		final OntologyService osBP = new BioportalOntologyService();
		final OntologyService os = new TranslatedOntologyService(osBP,
				(new Example7()).new OLS2BPtranslator());

		// Use local ontology identifiers to query BioPortal
		List<OntologyTerm> results = new ArrayList<OntologyTerm>();
		results.addAll(os.searchOntology("EFO", "thymus"));
		results.addAll(os.searchOntology("MP", "thymus"));
		results.addAll(os.searchOntology("DOID", "thymus"));

		// Print results
		for (OntologyTerm ot : results)
			System.out.println(ot.getOntologyAccession() + "\t"
					+ ot.getAccession() + "\t" + ot.getLabel());
	}

	private class OLS2BPtranslator implements OntologyIdTranslator {
		List<OntologyIdMapping> mappings = new ArrayList<OntologyIdMapping>();

		@SuppressWarnings("serial")
		public OLS2BPtranslator() {
			// Instantiate mappings to MP, EFO and DO
			OntologyIdMapping MPmapping = new OntologyIdMapping();
			MPmapping.setExternalOntologyAccession("1025");
			MPmapping.setLocalOntologyAccession("MP");

			OntologyIdMapping EFOmapping = new OntologyIdMapping();
			EFOmapping.setExternalOntologyAccession("1136");
			EFOmapping.setLocalOntologyAccession("EFO");

			OntologyIdMapping DOmapping = new OntologyIdMapping();
			DOmapping.setExternalOntologyAccession("1009");
			DOmapping.setLocalOntologyAccession("DOID");

			mappings.add(MPmapping);
			mappings.add(EFOmapping);
			mappings.add(DOmapping);
		}

		@Override
		public ArrayList<OntologyIdMapping> getMappings() {
			throw new UnsupportedOperationException("Not implemented.");
		}

		@Override
		public String getOntologyAccFromTermAcc(String termAccession) {
			throw new UnsupportedOperationException("Not implemented.");
		}

		@Override
		public String getTranslatedOntologyAccession(String ontologyAccession)
				throws OntologyServiceException {
			for (OntologyIdMapping mapping : mappings) {
				if (mapping.getLocalOntologyAccession().equalsIgnoreCase(
						ontologyAccession)) {
					return mapping.getExternalOntologyAccession();
				}
			}
			throw new OntologyServiceException("Ontology not found.");
		}

		@Override
		public String getTranslatedTermAccession(String termAccession)
				throws OntologyServiceException {
			throw new UnsupportedOperationException("Not implemented.");
		}

	}
}
