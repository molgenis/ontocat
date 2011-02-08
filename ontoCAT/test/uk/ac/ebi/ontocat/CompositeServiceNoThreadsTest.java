package uk.ac.ebi.ontocat;

import java.net.URI;

import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;
import uk.ac.ebi.ontocat.virtual.CompositeServiceNoThreads;

public class CompositeServiceNoThreadsTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final OntologyService osBP = new BioportalOntologyService();
		final OntologyService osOLS = new OlsOntologyService();
		final FileOntologyService osFile = new FileOntologyService(
				new URI(
						"http://efo.svn.sourceforge.net/svnroot/efo/trunk/src/efoinowl/InferredEFOOWLview/EFO_inferred.owl?revision=142"));
		final FileOntologyService osFile2 = new FileOntologyService(
				new URI(
						"http://diseaseontology.svn.sourceforge.net/svnroot/diseaseontology/trunk/HumanDO.obo"));

		os = CompositeServiceNoThreads.getService(osOLS, osBP, osFile, osFile2);

		// Testing with EFO
		ONTOLOGY_ACCESSION = "http://www.ebi.ac.uk/efo/";
		TERM_ACCESSION = "EFO_0000400";
	}

}
