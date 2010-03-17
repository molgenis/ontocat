package uk.ac.ebi.ontocat.test;

import java.net.URI;

import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.file.FileOntologyService;

public class FileOntologyServiceTest extends OntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new FileOntologyService(new URI("http://www.ebi.ac.uk/efo"));
		// Use a non-SKOS annotation for synonyms
		((FileOntologyService) os).setSynonymSlot("alternative_term");

		TERM_ACCESSION1 = "EFO_0000318";
		TERM_ACCESSION2 = "EFO_0002343";
	}
}
