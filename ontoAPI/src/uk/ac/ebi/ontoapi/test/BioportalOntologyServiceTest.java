package uk.ac.ebi.ontoapi.test;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.efo.bioportal.BioportalMapping;
import uk.ac.ebi.efo.bioportal.EFOIDTranslator;
import uk.ac.ebi.ontoapi.Ontology;
import uk.ac.ebi.ontoapi.OntologyService;
import uk.ac.ebi.ontoapi.OntologyServiceException;
import uk.ac.ebi.ontoapi.OntologyTerm;
import uk.ac.ebi.ontoapi.bioportal.BioportalOntologyService;

public class BioportalOntologyServiceTest {
	protected static OntologyService os;
	protected static String ONTOLOGY_ACCESSION1 = "1029";
	protected static String ONTOLOGY_ACCESSION2 = "1070";
	protected static String ONTOLOGY_ACCESSION3 = "MSH";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new BioportalOntologyService("ontoapi-svn@lists.sourceforge.net", new EFOIDTranslator());
	}

	@Test
	public final void testGetLabelForAccession() {
		printCurrentTest();
		new EFOIDTranslator();
		for (BioportalMapping BPmap : new EFOIDTranslator().getMappings()) {
			try {
				String label = os.getTerm(BPmap.getOntologyID(), BPmap.getTestCode()).getLabel();
				System.out.println(BPmap.getOntologyID() + " " + BPmap.getTestCode() + " " + label);
			} catch (OntologyServiceException e) {
				System.out.println(BPmap.getTestCode() + " NOT FOUND");
			}
		}
		System.out.println();
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
			System.out.println(sb.toString());
		}
	}

	@Test
	public final void testGetOntologyDescription() throws OntologyServiceException {
		printCurrentTest();
		Ontology oe = os.getOntology(ONTOLOGY_ACCESSION1);
		System.out.println(oe.getAbbreviation());
		System.out.println(oe.getLabel());
		System.out.println(oe.getVersionNumber());
	}

	@Test
	public final void testSearchOntology() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.searchOntology(ONTOLOGY_ACCESSION1, "thymus"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testSearchAll() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.searchAll("thymus"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetRootTerms() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.getRootTerms(ONTOLOGY_ACCESSION2))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetChildren() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.getChildren(ONTOLOGY_ACCESSION2, "GO:0005575"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetParents() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.getParents(ONTOLOGY_ACCESSION2, "GO:0009318"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetAnnotations() {
		fail("Not yet implemented");
	}

	private void printCurrentTest() {
		System.out.println();
		System.out.println("**************************");
		System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName().substring(4));
		System.out.println("*************************");
		System.out.println();
	}

	@Test
	public final void testMesh() throws OntologyServiceException {
		printCurrentTest();

		List<Ontology> ontologies = os.getOntologies();

		String ontologyAccession = "MSH";
		for (Ontology e : ontologies) {
			if (e.getLabel().contains("Medical Subject Headings")) {
				System.out.println(e.getLabel() + " " + e.getAbbreviation() + " " + e.getVersionNumber());
				ontologyAccession = e.getOntologyAccession();
			}
		}

		List<OntologyTerm> terms = os.searchOntology(ontologyAccession, "back");

		for (OntologyTerm t : terms) {
			System.out.println(t.getAccession() + " " + t.getLabel() + " " + t.getOntologyAccession());
			Map<String, String[]> annotations = os.getAnnotations(ontologyAccession, t.getAccession());
			for (String key : annotations.keySet()) {
				print(key + " " + arrayToString(annotations.get(key), ","));
			}
			break;
		}

	}

	private static void print(String in) {
		System.out.println(in);
	}
	
	private String arrayToString(String[] a, String separator) {
	    StringBuffer result = new StringBuffer();
	    if (a.length > 0) {
	        result.append(a[0]);
	        for (int i=1; i<a.length; i++) {
	            result.append(separator);
	            result.append(a[i]);
	        }
	    }
	    return result.toString();
	}
}
