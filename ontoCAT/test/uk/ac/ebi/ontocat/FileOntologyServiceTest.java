package uk.ac.ebi.ontocat;


import static org.junit.Assert.assertNotSame;

import java.net.URI;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.file.FileOntologyService;

public class FileOntologyServiceTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new FileOntologyService(new URI("http://www.ebi.ac.uk/efo"));
		// Use a non-SKOS annotation for synonyms
		((FileOntologyService) os).setSynonymSlot("alternative_term");

		ONTOLOGY_ACCESSION = "http://www.ebi.ac.uk/efo/";
		TERM_ACCESSION = "EFO_0000318";
	}

	@Test
	public final void testGetAllTerms() throws OntologyServiceException {
		printCurrentTest();
		Set<OntologyTerm> set = os.getAllTerms(ONTOLOGY_ACCESSION);
		assertNotSame("Empty set returned!", 0, set.size());
		println(set.size());
	}
}
