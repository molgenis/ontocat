package uk.ac.ebi.ontocat.test;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.ols.OlsOntologyService;

public class OlsOntologyServiceTest extends OntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new OlsOntologyService();
		ONTOLOGY_ACCESSION1 = "IMR";
		ONTOLOGY_ACCESSION2 = "GO";
	}
}
