package uk.ac.ebi.ontocat.test;

import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

public abstract class OntologyServiceTest {
	protected static OntologyService os;
	protected static String ONTOLOGY_ACCESSION1;
	protected static String ONTOLOGY_ACCESSION2;
	protected static String TERM_ACCESSION1 = "GO:0005623";
	protected static String TERM_ACCESSION2 = "GO:0005575";
	protected static final Logger log = Logger
			.getLogger(OntologyServiceTest.class);

	@Test
	public final void testGetOntologies() throws OntologyServiceException {
		printCurrentTest();
		for (Ontology oe : os.getOntologies()) {
			println(oe);
		}
	}

	@Test
	public final void testGetOntology() throws OntologyServiceException {
		printCurrentTest();
		Ontology oe = os.getOntology(ONTOLOGY_ACCESSION1);
		println(oe);
	}

	@Test
	public final void testSearchOntology() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.searchOntology(ONTOLOGY_ACCESSION1, "thymus"))
			println(ot);
	}

	@Test
	public final void testSearchAll() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.searchAll("thymus"))
			println(ot);
	}

	@Test
	public final void testGetRootTerms() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> results = os.getRootTerms(ONTOLOGY_ACCESSION2);
		for (OntologyTerm ot : results)
			println(ot);
	}

	@Test
	public final void testGetTermPath() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.getTermPath(ONTOLOGY_ACCESSION2,
				TERM_ACCESSION1))
			println(ot);
	}

	@Test
	public final void testGetChildren() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.getChildren(ONTOLOGY_ACCESSION2,
				TERM_ACCESSION2))
			println(ot);
	}

	@Test
	public final void testGetParents() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.getParents(ONTOLOGY_ACCESSION2,
				TERM_ACCESSION1))
			println(ot);
	}

	@Test
	public final void testGetAnnotations() throws OntologyServiceException {
		printCurrentTest();
		for (Entry<String, List<String>> e : os.getAnnotations(
				ONTOLOGY_ACCESSION2, TERM_ACCESSION1).entrySet()) {
			println(e.getKey());
			for (String s : e.getValue()) {
				println("\t" + s);
			}
		}
	}

	@Test
	public final void testGetDefinitions() throws OntologyServiceException {
		printCurrentTest();
		System.out.println(ONTOLOGY_ACCESSION2 + " " + TERM_ACCESSION1);
		System.out.println(os.getDefinitions(ONTOLOGY_ACCESSION2,
				TERM_ACCESSION1).get(0));
	}

	private void printCurrentTest() {
		println();
		println("**************************");
		println(Thread.currentThread().getStackTrace()[2].getMethodName()
				.substring(4));
		println("*************************");
		println();
	}

	private void println() {
		System.out.println();
	}

	private static void println(String in) {
		System.out.println(in);
	}

	private static void println(OntologyTerm in) {
		System.out.println(in);
	}

	private static void println(Ontology in) {
		System.out.println(in);
	}
}
