/**
 * 
 */
package uk.ac.ebi.efo.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyAnnotationAxiom;

import uk.ac.ebi.efo.api.OntologyLoader;

/**
 * @author $Id: OntologyLoaderTest.java 8454 2009-08-17 07:52:50Z Tomasz $
 * 
 */
public class OntologyLoaderTest {
	private static OntologyLoader loader;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loader = new OntologyLoader(new File("temp/efo.owl").toURI());
	}

	/**
	 * Test method for
	 * {@link uk.ac.ebi.efo.test.OntologyLoaderTest#testOntologyLoader()} .
	 */
	@Test
	public void testOntologyLoader() {
		assertNotNull(loader);
	}
	
	/**
	 * Test method for
	 * {@link uk.ac.ebi.efo.test.OntologyLoaderTest#testGetOntology()} .
	 */
	@Test
	public void testGetOntology() {
		assertNotNull(loader.getOntology());
	}

}
