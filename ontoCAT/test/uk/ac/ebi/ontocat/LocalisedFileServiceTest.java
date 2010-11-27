package uk.ac.ebi.ontocat;

import static org.junit.Assert.assertNotSame;

import java.net.URI;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;
import uk.ac.ebi.ontocat.virtual.LocalisedFileService;

public class LocalisedFileServiceTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		URI uri = new URI(
				"http://efo.svn.sourceforge.net/viewvc/efo/trunk/src/efoinowl/InferredEFOOWLview/EFO_inferred.owl");

		os = LocalisedFileService.getService(new FileOntologyService(uri));

		ONTOLOGY_ACCESSION = uri.toString();
		TERM_ACCESSION = "EFO_0000318";
	}

	@Test
	public final void testGetAllTerms() throws OntologyServiceException {
		printCurrentTest();
		Set<OntologyTerm> set = os.getAllTerms(ONTOLOGY_ACCESSION);
		assertNotSame("Empty set returned!", 0, set.size());
		println(set.size());
	}
}
