package uk.ac.ebi.ontocat;


import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

public class BioportalServiceTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new BioportalOntologyService();
		// GO accession, alternatively use 1506
		ONTOLOGY_ACCESSION = "1070";
	}

	@Test(timeout = 5000)
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

	@Test
	@Ignore("Takes too long to test usually")
	public void testGetAllTerms() throws Exception {
		// Fetch all terms for EFO
		Set<OntologyTerm> set = os.getAllTerms("1136");
		log.info(set.size() + " terms returned.");
		Assert.assertTrue("Less than expected terms returned", set.size() > 2600);
	}
}
