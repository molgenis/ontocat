package uk.ac.ebi.ontocat;

import junit.framework.Assert;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;
import uk.ac.ebi.ontocat.virtual.CachedServiceDecorator;

public class HudsonTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CachedServiceDecorator.clearCache();
		os = HudsonDecorator.getService(new BioportalOntologyService());
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

	@Test
	public void instantiateTest() {
		System.out.println("Instantiating cache");
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");
		CacheManager manager = CacheManager.create(getClass().getResource(
		"ehcache.xml"));
		Assert.assertNotNull("manager null", manager);
		Assert.assertNotNull("resource null",
				getClass().getResource("ehcache.xml"));
		Cache ServiceCache = manager.getCache("OntologyServiceCache");
		Assert.assertNotNull("service cache null", ServiceCache);
	}

}
