/**
 * Copyright (c) 2010 - 2011 European Molecular Biology Laboratory and University of Groningen
 *
 * Contact: ontocat-users@lists.sourceforge.net
 * 
 * This file is part of OntoCAT
 * 
 * OntoCAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * OntoCAT is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along
 * with OntoCAT. If not, see <http://www.gnu.org/licenses/>.
 */
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
