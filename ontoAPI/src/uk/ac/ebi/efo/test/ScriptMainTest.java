/**
 * 
 */
package uk.ac.ebi.efo.test;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.efo.api.ScriptMain;

/**
 * @author $Id: ScriptMainTest.java 8459 2009-08-17 11:41:55Z tomasz $
 * 
 */

@SuppressWarnings("unused")
public class ScriptMainTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Test method for
	 * {@link uk.ac.ebi.efo.bioportal.importer.ScriptMain#print_usage()}.
	 */
	@Test
	public void testPrint_usage() {
		ScriptMain.print_usage();
	}
}
