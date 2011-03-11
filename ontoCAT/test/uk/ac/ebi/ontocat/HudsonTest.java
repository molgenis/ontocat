package uk.ac.ebi.ontocat;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HudsonTest {

	private static Cache EternalCache;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Initialise the cache singleton
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");
		CacheManager manager = CacheManager.create(HudsonTest.class
				.getResource(
				"ehcache.xml"));
		Cache ServiceCache = manager.getCache("OntologyServiceCache");
		EternalCache = manager.getCache("EternalServiceCache");
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
	}

	@Test
	public void testMe() {
		System.out.println("testMe");
	}

}
