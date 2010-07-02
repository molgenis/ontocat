package uk.ac.ebi.ontocat.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;

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
		assertNotNull("Ontology list empty!", list);
		for (Ontology oe : list) {
			println(oe);
		}
	}

	@Test
	public final void testGetOntology() throws OntologyServiceException {
		printCurrentTest();
		Ontology oe = os.getOntology(ONTOLOGY_ACCESSION);
		println(oe);
	}

	@Test
	public final void testSearchOntology() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.searchOntology(ONTOLOGY_ACCESSION, "death");
		assertNotNull("Search list empty!", list);
		for (OntologyTerm ot : list)
			println(ot);
		log.info(list.size());
	}

	@Test
	public final void testSearchAll() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.searchAll("nephroblastoma", SearchOptions.EXACT);
		assertNotNull("Search list empty!", list);

		for (OntologyTerm ot : list)
			println(ot);

	}

	@Test
	public final void testGetRootTerms() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.getRootTerms(ONTOLOGY_ACCESSION);
		assertNotNull("Root terms list empty!", list);

		for (OntologyTerm ot : list)
			println(ot);
	}

	@Test
	public final void testGetTerm() throws OntologyServiceException {
		printCurrentTest();
		OntologyTerm ot = os.getTerm(ONTOLOGY_ACCESSION, TERM_ACCESSION);
		println(ot);
		println(ot.getOntology());
	}

	@Test
	public final void testGetTermByAccessionOnly() throws OntologyServiceException {
		printCurrentTest();
		println(os.getTerm(TERM_ACCESSION));
	}

	@Test
	public final void testGetTermPath() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.getTermPath(ONTOLOGY_ACCESSION, TERM_ACCESSION);
		assertNotNull("Term path list empty!", list);
		for (OntologyTerm ot : list)
			println(ot);
	}

	@Test
	public final void testGetChildren() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.getChildren(ONTOLOGY_ACCESSION, TERM_ACCESSION);
		assertNotNull("Children list empty!", list);
		for (OntologyTerm ot : list)
			println(ot);
	}

	@Test
	public final void testGetParents() throws OntologyServiceException {
		printCurrentTest();
		List<OntologyTerm> list = os.getParents(ONTOLOGY_ACCESSION, TERM_ACCESSION);
		assertNotNull("Parents list empty!", list);
		for (OntologyTerm ot : list)
			println(ot);
	}

	@Test
	public final void testGetAnnotations() throws OntologyServiceException {
		printCurrentTest();
		Map<String, List<String>> maps = os.getAnnotations(ONTOLOGY_ACCESSION, TERM_ACCESSION);
		assertNotNull("Annotations list empty!", maps);
		for (Entry<String, List<String>> e : maps.entrySet()) {
			println(e.getKey());
			for (String s : e.getValue()) {
				println("\t" + s);
			}
		}
	}

	@Test
	public final void testGetDefinitions() throws OntologyServiceException {
		printCurrentTest();
		println(ONTOLOGY_ACCESSION + " " + TERM_ACCESSION);
		println(os.getDefinitions(ONTOLOGY_ACCESSION, TERM_ACCESSION).get(0));
	}

	@Test
	public final void testGetSynonyms() throws OntologyServiceException {
		printCurrentTest();
		println(ONTOLOGY_ACCESSION + " " + TERM_ACCESSION);
		println(os.getSynonyms(ONTOLOGY_ACCESSION, TERM_ACCESSION).get(0));
	}

	private void printCurrentTest() {
		println();
		println("**************************");
		println(Thread.currentThread().getStackTrace()[2].getMethodName().substring(4));
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
