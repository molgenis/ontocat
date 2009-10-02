/**
 * 
 */
package uk.ac.ebi.efo.test;

import static org.junit.Assert.assertNotSame;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.semanticweb.owl.model.OWLOntologyChangeException;

import plugin.OntologyBrowser.OntologyServiceException;
import uk.ac.ebi.efo.api.BioportalImporter;
import uk.ac.ebi.efo.api.WrapperOntology.AnnotationFragmentNotFoundException;

/**
 * $Id: BioportalImporterTest.java 9019 2009-09-22 12:39:01Z tomasz $
 * 
 */
public class BioportalImporterTest {
	/**
	 * @throws AnnotationFragmentNotFoundException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws AnnotationFragmentNotFoundException
	 * @throws OntologyServiceException
	 * @throws OWLOntologyChangeException
	 * @throws OWLOntologyChangeException
	 * @throws InterruptedException
	 * @throws OntologyServiceException
	 * 
	 * 
	 */
	@Test
	public void testImporter() throws AnnotationFragmentNotFoundException, OWLOntologyChangeException,
			OntologyServiceException {
		URI uriTargetOntology = new File("temp/efo_release_candidate.owl").toURI();
		URI uriOutputOntology = new File("temp/output.owl").toURI();
		String email = "tomasz@ebi.ac.uk";

		BioportalImporter importer = new BioportalImporter(uriTargetOntology, uriOutputOntology, email);

		assertNotSame(0, importer.processImport());
	}

}
