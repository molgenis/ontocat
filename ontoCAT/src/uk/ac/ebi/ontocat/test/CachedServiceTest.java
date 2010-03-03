package uk.ac.ebi.ontocat.test;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.efo.bioportal.EFOIDTranslator;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.virtual.CachedOntologyService;

public class CachedServiceTest {
	private static final Logger logger = Logger
			.getLogger(CachedServiceTest.class);

	private static OntologyService os;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new CachedOntologyService(new BioportalOntologyService(
				"ontocat-svn@lists.sourceforge.net", new EFOIDTranslator()));
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSearchAll() throws OntologyServiceException {
		long t1;
		long t2;
		
		os.searchAll("blood");
		os.searchAll("thymocyte");

		t1 = System.nanoTime();
		logger.info(os.searchAll("interleukin").size());
		t2 = System.nanoTime();
		logger.info("Initial query took " + ((t2 - t1) * 1e-6) + " ms\t");

		t1 = System.nanoTime();
		logger.info(os.searchAll("interleukin").size());
		t2 = System.nanoTime();
		logger.info("Cached query took " + ((t2 - t1) * 1e-6) + " ms\t");

		t1 = System.nanoTime();
		logger.info(os.searchAll("interleukin").size());
		t2 = System.nanoTime();
		logger.info("Cached query took " + ((t2 - t1) * 1e-6) + " ms\t");
	}

}
