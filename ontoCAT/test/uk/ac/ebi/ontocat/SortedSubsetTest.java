package uk.ac.ebi.ontocat;


import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.virtual.SortedSubsetDecorator;

public class SortedSubsetTest {
	static OntologyService os;

	@SuppressWarnings("serial")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		os = SortedSubsetDecorator.getService(new BioportalOntologyService(),
				new ArrayList<String>() {
			{
				add("HP");
				add("EMAP");
				add("MP");
				add("1136");
				add("1053");
			}
		});
	}

	@Test
	public void testRankedSearch() throws OntologyServiceException {
		List<OntologyTerm> list = os.searchAll("thymus");
		assertNotSame("Empty list returned!", 0, list.size());
		for (OntologyTerm ot : list) {
			System.out.println(ot);
		}
	}

	@Test(expected = NullPointerException.class)
	public void testOutsideScopeSearchOntologyTest()
			throws OntologyServiceException {
		for (OntologyTerm ot : os.searchOntology("CHEBI", "nicotine")) {
			System.out.println(ot);
		}
	}
}
