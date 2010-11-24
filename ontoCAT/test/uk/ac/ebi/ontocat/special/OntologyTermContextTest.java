package uk.ac.ebi.ontocat.special;

import java.net.URI;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

public class OntologyTermContextTest {
	protected static OntologyService os;
	protected static final Logger log = Logger.getLogger(OntologyTermContextTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OntologyService ols = new OlsOntologyService();
		OntologyService bioportal = new BioportalOntologyService();
		OntologyService efoowl = new FileOntologyService(new URI("http://www.ebi.ac.uk/efo"));

		os = CompositeDecorator.getService(ols, bioportal, efoowl);
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetContext() throws OntologyServiceException {
		for (OntologyTerm ot : os.searchAll("thymus", SearchOptions.INCLUDE_PROPERTIES
				)) {
			log.info(ot);
			log.info(ot.getContext());
		}
	}
}
