package uk.ac.ebi.ontocat.special;


import static org.junit.Assert.assertNotSame;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;

public abstract class AbstractOntologyServiceTest {
	protected static OntologyService os;
	protected static String ONTOLOGY_ACCESSION;
	protected static String TERM_ACCESSION = "GO:0043227";
	// GO:0005623 not working for getChildren() as subclasses are partOf
	protected static final Logger log = Logger.getLogger(AbstractOntologyServiceTest.class);

	@Test
	public final void testGetOntologies() throws OntologyServiceException {
		printCurrentTest();
		List<Ontology> list = os.getOntologies();
		assertNotSame("Empty list returned!", 0, list.size());
		for (Ontology oe : list) {
			println(oe);
		}
	}


	protected static void printCurrentTest() {
		println();
		println("**************************");
		println(Thread.currentThread().getStackTrace()[2].getMethodName().substring(4));
		println("*************************");
		println();
	}

	private static void println() {
		System.out.println();
	}

	protected static void println(Object o) {
		System.out.println(o);
	}
}
