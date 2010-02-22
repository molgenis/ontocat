/**
 * 
 */
package uk.ac.ebi.ontocat.examples;

import java.util.ArrayList;

import uk.ac.ebi.efo.bioportal.BioportalMapping;
import uk.ac.ebi.efo.bioportal.EFOIDTranslator;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyIdTranslator;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

// TODO: Auto-generated Javadoc
/**
 * Example 1
 * 
 * Shows how to list all the available ontologies in OLS.
 */
public class Example2 {
	/**
	 * Instantiates an anonymous OntologyIDTranslation class You would have to
	 * provide mappings between your id space to fully use it. See
	 * {@link EFOIDTranslator} for an example
	 */
	private static OntologyIdTranslator IDtranslator = new OntologyIdTranslator() {
		@Override
		public ArrayList<BioportalMapping> getMappings() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getOntologyAccessionFromConcept(String sConcept)
				throws OntologyServiceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getTermAccessionFromConcept(String sConcept)
				throws OntologyServiceException {
			// TODO Auto-generated method stub
			return null;
		}
	};


	public static void main(String[] args) throws OntologyServiceException {
		// Instantiate BioPortal service
		OntologyService os = new BioportalOntologyService(
				"ontocat-svn@lists.sourceforge.net", IDtranslator);
		// For all ontologies in BioPortal print their full label and
		// abbreviation
		for (Ontology o : os.getOntologies()) {
			StringBuilder sb = new StringBuilder();
			sb.append(o.getAbbreviation());
			sb.append("\t");
			sb.append(o.getLabel());
			System.out.println(sb.toString());
		}
	}
}
