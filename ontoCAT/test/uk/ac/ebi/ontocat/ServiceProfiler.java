package uk.ac.ebi.ontocat;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
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

		ArrayList<String> randomStrings = new ArrayList<String>();
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			randomStrings.add(Long.toString(Math.abs(r.nextLong()), 36));
		}

		// ArrayList<String> results = new ArrayList<String>();
		long t1;
		long t2;
		OntologyService osOLS = new OlsOntologyService();
		OntologyService osBP = new BioportalOntologyService();


		for (int i = 0; i < 100; i++) {
			t1 = System.nanoTime();
			osOLS.searchAll(randomStrings.get(i));
			t2 = System.nanoTime();
			logger.info("OLS\t" + ((t2 - t1) * 1e-6) + "\t");

			t1 = System.nanoTime();
			osBP.searchAll(randomStrings.get(i));
			t2 = System.nanoTime();
			logger.info("BP\t" + ((t2 - t1) * 1e-6)
					+ "\t");
		}
	}
}
