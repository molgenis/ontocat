package uk.ac.ebi.ontocat.test;

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

		OntologyService osBP = new OlsOntologyService();
		os = SortedSubsetDecorator.getService(osBP, new ArrayList<String>() {
			{
				add("HP");
				add("EMAP");
				add("MP");
				add("CHEBI");
			}
		});
	}

	@Test
	public void testRankedSearch() throws OntologyServiceException {
		for (OntologyTerm ot : os.searchAll("thymus")) {
			System.out.println(ot.getAccession() + "\t"
					+ ot.getOntologyAccession() + "\t" + ot.getLabel());
		}
	}
}
