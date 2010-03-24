package uk.ac.ebi.ontocat.test;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

public class CompositeDecoratorTest extends OntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final OntologyService osBP = new BioportalOntologyService();
		final OntologyService osOLS = new OlsOntologyService();
		final FileOntologyService osFile = new FileOntologyService(new URI(
				"http://www.ebi.ac.uk/efo"));
		osFile.setSynonymSlot("alternative_term");

		os = CompositeDecorator.getService(new ArrayList<OntologyService>() {
			{
				add(osOLS);
				add(osBP);
				add(osFile);
			}
		});
		ONTOLOGY_ACCESSION1 = "1029";
		ONTOLOGY_ACCESSION2 = "1070";
	}

	@Test
	@Ignore
	public void testHashCollapse() throws OntologyServiceException {
		List<OntologyTerm> l = os.searchAll("thymus");
		log.info("List size " + l.size());
		Set<OntologyTerm> h = new HashSet<OntologyTerm>(l);
		log.info("Hash size " + h.size());
		assertEquals(l.size(), h.size());
		// for (OntologyTerm o : l) log.info(o);
	}
}
