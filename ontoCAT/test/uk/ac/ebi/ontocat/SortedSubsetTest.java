package uk.ac.ebi.ontocat;


import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.SortedSubsetDecorator;

public class SortedSubsetTest {
	static OntologyService os;

	@SuppressWarnings("serial")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		os = SortedSubsetDecorator.getService(new OlsOntologyService(),
				new ArrayList<String>() {
			{
				add("HP");
				add("EMAP");
				add("MP");
			}
		});
	}

	@Test
	public void testRankedSearch() throws OntologyServiceException {
		for (OntologyTerm ot : os.searchAll("thymus")) {
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
