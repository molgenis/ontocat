package uk.ac.ebi.ontocat;


import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;

public class OlsOntologyServiceTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new OlsOntologyService();
		ONTOLOGY_ACCESSION = "GO";
	}
}
