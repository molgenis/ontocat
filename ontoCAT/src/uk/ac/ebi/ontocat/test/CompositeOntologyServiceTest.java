package uk.ac.ebi.ontocat.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeOntologyService;

public class CompositeOntologyServiceTest extends OntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final OntologyService osBP = new BioportalOntologyService();
		final OntologyService osOLS = new OlsOntologyService();
		os = new CompositeOntologyService(new ArrayList<OntologyService>() {
			{
				add(osOLS);
				add(osBP);
			}
		});
		ONTOLOGY_ACCESSION1 = "1029";
		ONTOLOGY_ACCESSION2 = "1070";
	}

	@Test
	public void testHashCollapse() throws OntologyServiceException {
		List<OntologyTerm> l = os.searchAll("thymus");
		log.info("List size " + l.size());
		Set<OntologyTerm> h = new HashSet<OntologyTerm>(l);
		log.info("Hash size " + h.size());
		assertEquals(l.size(), h.size());
	}
}
