package uk.ac.ebi.ontocat.special;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ResponseCodesTest {


	protected static final Logger log = Logger.getLogger(AbstractOntologyServiceTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCodes() throws IOException {
		URL url = new URL(
				"http://rest.bioontology.org/bioportal/virtual/children/1070/GO:0043227?email=example@example.org");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		log.info(conn.getResponseCode());
		log.info(conn.getResponseMessage());

	}

}
