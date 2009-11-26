package uk.ac.ebi.ontoapi.test;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;

import uk.ac.ebi.ontoapi.ols.OlsOntologyService;

public class OlsOntologyServiceTest extends BioportalOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new OlsOntologyService();
		ONTOLOGY_ACCESSION1 = "IMR";
		ONTOLOGY_ACCESSION2 = "GO";
	}
}
