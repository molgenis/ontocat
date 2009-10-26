package uk.ac.ebi.efo.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import plugin.OntologyBrowser.OntologyServiceException;
import plugin.OntologyBrowser.OntologyTermExt;
import uk.ac.ebi.efo.bioportal.BioportalMapping;
import uk.ac.ebi.efo.bioportal.BioportalService;
import uk.ac.ebi.efo.bioportal.xmlbeans.OntologyBean;

/**
 * @author $Id: BioportalServiceTest.java 9019 2009-09-22 12:39:01Z tomasz $
 * 
 */

public class BioportalServiceTest {
	private static final BioportalService bw = new BioportalService("tomasz@ebi.ac.uk");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testMappings() {
		ArrayList<BioportalMapping> mappings = BioportalService.getMappings();

		for (BioportalMapping BPmap : mappings) {
			try {
				printResults(bw.getTerm(BPmap.getTestCode()));
			} catch (OntologyServiceException e) {
				System.out.println(BPmap.getTestCode() + " NOT FOUND");
				fail();
			}
		}
	}

	@Test
	public void testMappedOntologies() throws OntologyServiceException {
		ArrayList<BioportalMapping> mappings = BioportalService.getMappings();

		for (BioportalMapping BPmap : mappings) {
			OntologyBean ob = (OntologyBean) bw.getOntologyDescription(BPmap.getOntologyID());
			printMany(new String[] {
					ob.getMetaAnnotation(), "Preferred name: " + ob.getPreferredNameSlot(),
					"Synonym: " + ob.getSynonymSlot(), "Terminologies: " + ob.getTargetTerminologies()
			});
		}

	}

	@Test
	public void testSpecial() {
		// bw.searchConceptID("NCI C3235");
		try {
			printResults(bw.getTerm("GeneRIF:12900520"));
		} catch (OntologyServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	private void printResults(OntologyTermExt ontologyTerm) throws OntologyServiceException {
		System.out.println("ID " + ontologyTerm.getAccession());
		System.out.println("LABEL " + ontologyTerm.getLabel());
		System.out.print("SYNONYMS ");
		if (ontologyTerm.getSynonyms() != null) {
			for (String syn : ontologyTerm.getSynonyms()) {
				System.out.print(syn + "\t");
			}
		}
		System.out.println();
		System.out.print("DEFINITON ");
		if (ontologyTerm.getDefinitions() != null) {
			for (String def : ontologyTerm.getDefinitions()) {
				System.out.print(def + "\t");
			}
		}
		System.out.println("SourceUrl " + bw.getQueryURL());

		System.out.println(bw.getSuccessBean().getAccessDate());
		System.out.println(bw.getSuccessBean().getAccessedResource());
		System.out.println();
	}

	private void printMany(String[] arr) {
		for (String str : arr) {
			System.out.println(str);
		}
		System.out.println("***********\n");
	}
}
