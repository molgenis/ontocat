package uk.ac.ebi.ontoapi.test;

import static org.junit.Assert.fail;

import java.util.Map.Entry;

import org.junit.Test;

import uk.ac.ebi.efo.bioportal.EFOIDTranslator;
import uk.ac.ebi.efo.bioportal.IOntologyMapping;
import uk.ac.ebi.ontoapi.Ontology;
import uk.ac.ebi.ontoapi.OntologyService;
import uk.ac.ebi.ontoapi.OntologyServiceException;
import uk.ac.ebi.ontoapi.OntologyTerm;

public abstract class OntologyServiceTest {
	protected static OntologyService os;
	protected static String ONTOLOGY_ACCESSION1;
	protected static String ONTOLOGY_ACCESSION2;

	@Test
	public final void testGetLabelForAccession() {
		printCurrentTest();
		new EFOIDTranslator();
		for (IOntologyMapping BPmap : new EFOIDTranslator().getMappings()) {
			try {
				String label = os.getTerm(BPmap.getOntologyAccession(), BPmap.getTestCode()).getLabel();
				println(BPmap.getOntologyAccession() + " " + BPmap.getTestCode() + " " + label);
			} catch (OntologyServiceException e) {
				println(BPmap.getTestCode() + " NOT FOUND");
			}
		}
		println();
	}

	@Test
	public final void testGetTermPath() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetOntologies() throws OntologyServiceException {
		printCurrentTest();
		for (Ontology oe : os.getOntologies()) {
			StringBuilder sb = new StringBuilder();
			sb.append(oe.getAbbreviation());
			sb.append("\t");
			sb.append(oe.getLabel());
			sb.append("\t");
			sb.append(oe.getVersionNumber());
			println(sb.toString());
		}
	}

	@Test
	public final void testGetOntology() throws OntologyServiceException {
		printCurrentTest();
		Ontology oe = os.getOntology(ONTOLOGY_ACCESSION1);
		println(oe.getAbbreviation());
		println(oe.getLabel());
		println(oe.getVersionNumber());
	}

	@Test
	public final void testSearchOntology() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.searchOntology(ONTOLOGY_ACCESSION1, "thymus"))
			println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testSearchAll() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.searchAll("thymus"))
			println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetRootTerms() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.getRootTerms(ONTOLOGY_ACCESSION2))
			println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetChildren() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.getChildren(ONTOLOGY_ACCESSION2, "GO:0005575"))
			println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetParents() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.getParents(ONTOLOGY_ACCESSION2, "GO:0009318"))
			println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetAnnotations() throws OntologyServiceException {
		printCurrentTest();
		for (Entry<String, String[]> e : os.getAnnotations(ONTOLOGY_ACCESSION2, "GO:0009318").entrySet()) {
			println(e.getKey());
			for (String s : e.getValue()) {
				println("\t" + s);
			}
		}
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
}
