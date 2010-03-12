package uk.ac.ebi.ontocat.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FieldDescriptor;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

public class ServiceProfiler {
	private static final Logger logger = Logger.getLogger(ServiceProfiler.class
			.getName());

	/**
	 * @param args
	 * @throws OntologyServiceException
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws OntologyServiceException,
			URISyntaxException {
		System.out.println("OS current temporary directory is "
				+ System.getProperty("java.io.tmpdir"));
		ArrayList<String> randomStrings = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) {
			Random r = new Random();
			randomStrings.add(Long.toString(Math.abs(r.nextLong()), 36));
		}

		// ArrayList<String> results = new ArrayList<String>();
		long t1;
		long t2;
		OntologyService osOLS = new OlsOntologyService();
		OntologyService osBP = new BioportalOntologyService();
		OntologyService osOWL = new FileOntologyService(new URI(
				"http://www.ebi.ac.uk/efo"), new FieldDescriptor(
				"alternative_term", "definition", "label"));

		for (int i = 0; i < 100; i++) {
			t1 = System.nanoTime();
			osOLS.searchAll(randomStrings.get(i));
			t2 = System.nanoTime();
			logger.info("OLS\t" + ((t2 - t1) * 1e-6) + "\t");
			// + ((OlsOntologyService) osOLS).extRequestTime);

			// t1 = System.nanoTime();
			// try {
			// osBP.searchAll(randomStrings.get(i));
			// } catch (Exception e) {
			// }
			// t2 = System.nanoTime();
			// logger.info("BP\t" + ((t2 - t1) * 1e-6) + "\t"
			// + ((BioportalOntologyService) osBP).extRequestTime);

			// t1 = System.nanoTime();
			// osOWL.searchAll(randomStrings.get(i));
			// t2 = System.nanoTime();
			// logger.info("OWL\t" + ((t2 - t1) * 1e-6));
		}
	}
}
