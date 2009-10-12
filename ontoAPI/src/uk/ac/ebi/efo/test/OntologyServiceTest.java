package uk.ac.ebi.efo.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import plugin.OntologyBrowser.OntologyEntity;
import plugin.OntologyBrowser.OntologyService;
import plugin.OntologyBrowser.OntologyServiceException;
import plugin.OntologyBrowser.OntologyTerm;
import plugin.OntologyBrowser.OntologyTermExt;
import uk.ac.ebi.efo.bioportal.BioportalService;
import uk.ac.ebi.efo.bioportal.BioportalService.BioportalMapping;

public class OntologyServiceTest {
	private static OntologyService os;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new BioportalService("ontoapi-svn@lists.sourceforge.net");
	}

	@Test
	public final void testGetLabelForAccession() {
		printCurrentTest();

		ArrayList<BioportalMapping> mappings = BioportalService.getMappings();

		for (BioportalMapping BPmap : mappings) {
			try {
				String label = os.getLabelForAccession(BPmap.getOntID(), BPmap.getTestCode());
				System.out.println(BPmap.getOntID() + " " + BPmap.getTestCode() + " " + label);
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
		for (OntologyEntity oe : os.getOntologies()) {
			StringBuilder sb = new StringBuilder();
			sb.append(oe.getAbbreviation());
			sb.append("\t");
			sb.append(oe.getDisplayLabel());
			sb.append("\t");
			sb.append(oe.getVersionNumber());
			System.out.println(sb.toString());
		}
	}

	@Test
	public final void testGetOntologyDescription() throws OntologyServiceException {
		printCurrentTest();
		OntologyEntity oe = os.getOntologyDescription("1029");
		System.out.println(oe.getAbbreviation());
		System.out.println(oe.getDisplayLabel());
		System.out.println(oe.getVersionNumber());
	}

	@Test
	public final void testSearchOntology() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : os.searchOntology("1053", "thymus"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
		;
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
		for (OntologyTermExt ot : os.getRootTerms("1070"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetChildren() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTermExt ot : os.getChildren("1070", "GO:0005575"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetParents() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTermExt ot : os.getParents("1070", "GO:0009318"))
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

		List<OntologyEntity> ontologies = os.getOntologies();

		String ontologyAccession = "MSH";
		for (OntologyEntity e : ontologies) {
			if (e.getDisplayLabel().contains("Medical Subject Headings")) {
				System.out.println(e.getDisplayLabel() + " " + e.getAbbreviation() + " " + e.getVersionNumber());
				ontologyAccession = e.getOntologyAccession();
			}
		}

		List<OntologyTerm> terms = os.searchOntology(ontologyAccession, "back");

		for (OntologyTerm t : terms) {
			System.out.println(t.getAccession() + " " + t.getLabel() + " " + t.getOntologyAccession());
			Map<String, String[]> annotations = os.getAnnotations(ontologyAccession, t.getAccession());
			for (String key : annotations.keySet()) {
				print(key + " " + annotations.get(key));
			}
			break;
		}

	}

	private static void print(String in) {
		System.out.println(in);
	}
}
