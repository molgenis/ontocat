package uk.ac.ebi.ontoapi.test;

import org.junit.BeforeClass;

import uk.ac.ebi.efo.bioportal.EFOIDTranslator;
import uk.ac.ebi.ontoapi.bioportal.BioportalOntologyService;

public class BioportalServiceTest extends OntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new BioportalOntologyService("ontoapi-svn@lists.sourceforge.net", new EFOIDTranslator());
		ONTOLOGY_ACCESSION1 = "1029";
		ONTOLOGY_ACCESSION2 = "1070";
	}
}
