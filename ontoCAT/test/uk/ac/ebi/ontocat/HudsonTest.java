package uk.ac.ebi.ontocat;

import junit.framework.Assert;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.junit.Test;

public class HudsonTest {

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
