package uk.ac.ebi.ontocat;

import static org.junit.Assert.assertNotSame;

import java.net.URI;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;
import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;

public class ReasonedFileOntologyServiceTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new ReasonedFileOntologyService(
				new URI(
				"http://www.ebi.ac.uk/efo/efo.owl"),
		"EFO");

		ONTOLOGY_ACCESSION = "EFO";
		TERM_ACCESSION = "EFO_0000326";
	}

	@Test
	public final void testGetAllTerms() throws OntologyServiceException {
		printCurrentTest();
		Set<OntologyTerm> set = os.getAllTerms(ONTOLOGY_ACCESSION);
		assertNotSame("Empty set returned!", 0, set.size());
		println(set.size());
	}
}
