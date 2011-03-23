package uk.ac.ebi.ontocat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;
import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;

public class ReasonedOWLTest extends
AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new ReasonedFileOntologyService(new URI(
		"http://www.ebi.ac.uk/efo/efo.owl"), "EFO");

		ONTOLOGY_ACCESSION = "EFO";
		TERM_ACCESSION = "EFO_0000326";
	}

	@Test
	public final void testGetRelations() throws OntologyServiceException {
		printCurrentTest();
		Map<String, Set<OntologyTerm>> map = os.getRelations(
				ONTOLOGY_ACCESSION, TERM_ACCESSION);
		assertNotSame("Empty map returned!", 0, map.size());
		assertFalse("No expected relations returned", map.get("inheres_in")
				.isEmpty());
		for (Entry<String, Set<OntologyTerm>> e : map.entrySet()) {
			System.out.println(e.getKey());
			for (OntologyTerm s : e.getValue()) {
				System.out.println("\t" + s.getLabel());
			}
		}
	}
}
