package uk.ac.ebi.ontocat.test;

import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.ols.OlsOntologyService;

public class OlsOntologyServiceTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new OlsOntologyService();
		ONTOLOGY_ACCESSION = "GO";
	}
}
