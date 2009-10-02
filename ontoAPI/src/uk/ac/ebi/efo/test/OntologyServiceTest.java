package uk.ac.ebi.efo.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import plugin.OntologyBrowser.OntologyEntity;
import plugin.OntologyBrowser.OntologyServiceException;
import plugin.OntologyBrowser.OntologyTerm;
import plugin.OntologyBrowser.OntologyTermExt;
import uk.ac.ebi.efo.bioportal.BioportalService;
import uk.ac.ebi.efo.bioportal.BioportalService.BioportalMapping;

public class OntologyServiceTest {
	private static BioportalService bw;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bw = new BioportalService("tomasz@ebi.ac.uk");
	}

	@Test
	public final void testGetLabelForAccession() {
		printCurrentTest();

		ArrayList<BioportalMapping> mappings = BioportalService.getMappings();

		for (BioportalMapping BPmap : mappings) {
			try {
				String label = bw.getLabelForAccession(BPmap.getOntID(), BPmap.getTestCode());
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
		for (OntologyEntity oe : bw.getOntologies()) {
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
		OntologyEntity oe = bw.getOntologyDescription("1029");
		System.out.println(oe.getAbbreviation());
		System.out.println(oe.getDisplayLabel());
		System.out.println(oe.getVersionNumber());
	}

	@Test
	public final void testSearchOntology() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : bw.searchOntology("1053", "thymus"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
		;
	}

	@Test
	public final void testSearchAll() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTerm ot : bw.searchAll("thymus"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetRootTerms() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTermExt ot : bw.getRootTerms("1070"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetChildren() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTermExt ot : bw.getChildren("1070", "GO:0005575"))
			System.out.println(ot.getOntologyAccession() + " " + ot.getAccession() + " " + ot.getLabel());
	}

	@Test
	public final void testGetParents() throws OntologyServiceException {
		printCurrentTest();
		for (OntologyTermExt ot : bw.getParents("1070", "GO:0009318"))
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
}
