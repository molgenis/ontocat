package uk.ac.ebi.ontocat.test;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.efo.bioportal.EFOIDTranslator;
import uk.ac.ebi.ontocat.OntologyIdMapping;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.bioportal.xmlbeans.OntologyBean;
import uk.ac.ebi.ontocat.virtual.TranslatedOntologyService;

/**
 * @author $Id: EFOIDTranslatorTest.java 9019 2009-09-22 12:39:01Z tomasz $
 * 
 */

public class TranslatedOntologyServiceTest {
	private static final OntologyService os = new TranslatedOntologyService(
			new BioportalOntologyService(), new EFOIDTranslator());

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testMappings() {
		for (OntologyIdMapping BPmap : new EFOIDTranslator().getMappings()) {
			try {
				printResults(os.getTerm(BPmap.getTestCode()), BPmap
						.getTestCode());
			} catch (OntologyServiceException e) {
				fail(BPmap.getTestCode() + " NOT FOUND");
			}
		}
	}

	@Test
	public void listResolvableOntologies() throws OntologyServiceException {
		for (OntologyIdMapping BPmap : new EFOIDTranslator().getMappings()) {
			try {
				OntologyBean ob = (OntologyBean) os.getOntology(BPmap
						.getTestCode());
				printMany(new String[] { ob.getMetaAnnotation(),
						"Preferred name: " + ob.getPreferredNameSlot(),
						"Synonym: " + ob.getSynonymSlot(),
						"Terminologies: " + ob.getTargetTerminologies() });
			} catch (OntologyServiceException e) {
				e.printStackTrace();
				fail(BPmap.getTestCode() + " "
						+ BPmap.getExternalOntologyAccession() + " NOT FOUND");

			}

		}

	}

	private void printResults(OntologyTerm term, String testCode)
			throws OntologyServiceException {
		System.out.println("ID " + term.getAccession());
		System.out.println("LABEL " + term.getLabel());
		System.out.print("SYNONYMS ");

		if (os.getSynonyms(testCode, testCode) != null) {
			for (String syn : os.getSynonyms(testCode, testCode)) {
				System.out.println(syn);
			}
		}

		System.out.print("DEFINITON ");
		if (os.getDefinitions(testCode, testCode) != null) {
			for (String def : os.getDefinitions(testCode, testCode)) {
				System.out.println(def);
			}
		}
		System.out.println("SourceUrl " + os.makeLookupHyperlink(testCode));
		System.out.println();
	}

	private void printMany(String[] arr) {
		for (String str : arr) {
			System.out.println(str);
		}
		System.out.println("***********\n");
	}
}