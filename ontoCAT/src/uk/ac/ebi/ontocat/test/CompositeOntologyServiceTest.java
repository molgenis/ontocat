package uk.ac.ebi.ontocat.test;

import java.util.ArrayList;

import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.OntologyService;
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
}
