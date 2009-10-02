/**
 * 
 */
package uk.ac.ebi.efo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAxiomAnnotationAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLCommentAnnotation;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLEntityAnnotationAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyManager;

import uk.ac.ebi.efo.api.OntologyLoader;
import uk.ac.ebi.efo.api.OntologyManagerSingleton;
import uk.ac.ebi.efo.api.OntologySaver;
import uk.ac.ebi.efo.api.WrapperOntology;
import uk.ac.ebi.efo.api.WrapperOntology.AnnotationFragmentNotFoundException;

/**
 * @author $Id: WrapperOntologyTest.java 8273 2009-07-29 17:10:50Z tomasz $
 * 
 */

public class WrapperOntologyTest {

	private static OntologyLoader loader;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loader = new OntologyLoader(new File("temp/efo.owl").toURI());
	}

	/**
	 * @return
	 * 
	 * 
	 */
	@Test
	public void testGetUriFromFrag() {
		String frag = "definition";
		try {
			assertEquals("http://www.ebi.ac.uk/efo/definition", new WrapperOntology(loader.getOntology())
					.getUriFromFrag(frag).toString());
		} catch (AnnotationFragmentNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetUriFromClassID() {
		String clsID = "EFO_0000091";
		try {
			assertEquals("http://www.ebi.ac.uk/efo/EFO_0000091", new WrapperOntology(loader.getOntology())
					.getUriFromClassID(clsID).toString());
		} catch (AnnotationFragmentNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAddAnnotationOnAnntoation() throws URISyntaxException, OWLOntologyChangeException {
		OWLOntologyManager manager = OntologyManagerSingleton.INSTANCE;
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLOntology ontology = loader.getOntology();

		OWLClass cls = null;
		for (OWLClass c : ontology.getReferencedClasses()) {
			if (c.getURI().compareTo(new URI("http://www.ebi.ac.uk/efo/EFO_0000183")) == 0) {
				cls = c;
				break;
			}

		}

		// Create the axiom to be added, also first get a type string constant
		OWLCommentAnnotation ann1 = factory.getCommentAnnotation("SUKCES");
		OWLCommentAnnotation ann2 = factory.getCommentAnnotation("IT WORKS");

		OWLEntityAnnotationAxiom ax1 = factory.getOWLEntityAnnotationAxiom(cls, ann1);
		// Link axiom to ontology
		OWLAxiomAnnotationAxiom ax2 = factory.getOWLAxiomAnnotationAxiom(ax1, ann2);

		AddAxiom addAxiom2 = new AddAxiom(ontology, ax2);
		AddAxiom addAxiom = new AddAxiom(ontology, ax1);

		manager.applyChange(addAxiom);
		manager.applyChange(addAxiom2);

		OntologySaver saver = new OntologySaver(ontology);
		saver.saveTo(new File("temp/output.owl").toURI());
	}

}
