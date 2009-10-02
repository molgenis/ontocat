/**
 * 
 */
package uk.ac.ebi.efo.test;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.efo.api.OntologyLoader;
import uk.ac.ebi.efo.api.OntologySaver;

/**
 * @author $Id: OntologySaverTest.java 8599 2009-08-26 09:01:47Z tomasz $
 * 
 */
@SuppressWarnings("unused")
public class OntologySaverTest {

	private static OntologySaver saver;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		saver = new OntologySaver(new OntologyLoader(new File("temp/output.owl").toURI()).getOntology());
	}

	/**
	 * Test method for
	 * {@link uk.ac.ebi.efo.api.OntologySaver#saveTo(java.net.URI)} .
	 */
	@Test
	public void testSaveTo() {
		saver.saveTo(new File("temp/output.owl").toURI());
	}

}
