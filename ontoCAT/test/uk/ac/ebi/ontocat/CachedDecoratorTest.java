package uk.ac.ebi.ontocat;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.virtual.CachedServiceDecorator;

public class CachedDecoratorTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = CachedServiceDecorator.getService(new BioportalOntologyService());
		// GO accession
		ONTOLOGY_ACCESSION = "1070";
	}

	private long t1;
	private long t2;

	@Before
	public void runBeforeEveryTest() {
		t1 = System.nanoTime();
	}

	@After
	public void runAfterEveryTest() {
		t2 = System.nanoTime();
		log.info("Query took " + ((t2 - t1) * 1e-6) + " ms\t");
	}

}
