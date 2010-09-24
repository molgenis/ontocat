package uk.ac.ebi.ontocat.examples;

import java.net.URISyntaxException;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

/**
 * Example 11
 * 
 * Shows how to obtain UMLS CUIs for ontology terms through BioPortal
 */
public class Example11 {

	public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
		// Instantiate BioPortal service
		OntologyService os = new BioportalOntologyService();

		// Query NCI Thesaurus
		OntologyTerm t1 = os.searchOntology("1032", "Malignant Neoplasm").get(0);
		System.out.println(t1.getLabel());
		for (String cui : os.getAnnotations(t1).get("UMLS_CUI"))
			System.out.println(cui);

		// Query AIR
		OntologyTerm t2 = os.getTerm("1430", "U000045");
		System.out.println(t2.getLabel());
		for (String cui : os.getAnnotations(t2).get("UMLS_CUI"))
			System.out.println(cui);

		// Query National drug file
		OntologyTerm t3 = os.getTerm("1352", "C7764591428519");
		System.out.println(t3.getLabel());
		for (String cui : os.getAnnotations("1352", "C7764591428519").get("UMLS_CUI"))
			System.out.println(cui);

	}
}