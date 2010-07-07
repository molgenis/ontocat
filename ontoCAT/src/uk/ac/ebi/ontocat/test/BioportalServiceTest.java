package uk.ac.ebi.ontocat.test;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

public class BioportalServiceTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new BioportalOntologyService();
		// GO accession
		ONTOLOGY_ACCESSION = "1070";
	}

	@Test
	public void testSearchSubtree() throws Exception {
		BioportalOntologyService bos = new BioportalOntologyService();
		List<OntologyTerm> list1 = bos
				.searchSubtree(ONTOLOGY_ACCESSION, TERM_ACCESSION, "membrane");
		Assert.assertNotSame("Subtree search list empty!", Collections.EMPTY_LIST, list1);

		for (OntologyTerm ot : list1)
			System.out.println(ot);

		// Test the number of returned terms
		List<OntologyTerm> list2 = bos.searchOntology(ONTOLOGY_ACCESSION, "membrane");
		Assert.assertTrue("Incorrect list size returned!", list1.size() < list2.size());
		log.info(list1.size() + " < " + list2.size());
	}
}
