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
package uk.ac.ebi.ontocat.examples;

import java.io.File;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

public class Sandbox {

	private static final Logger log = Logger.getLogger(Sandbox.class);

	/**
	 * @param args
	 * @throws OntologyServiceException
	 */
	public static void main(String[] args) throws OntologyServiceException {

		OntologyService os = new FileOntologyService(
				new File(
						"C:\\Users\\tadamusiak\\Dropbox\\docs\\papers\\2012-AMIA_loinc\\loinc_condor_inferred.owl")
						.toURI());
		// "C:\\Users\\tadamusiak\\Dropbox\\docs\\papers\\2012-AMIA_loinc\\data\\loinc_inferred.owl")


		// gluose | urine
		OntologyTerm ot = os.getTerm("OBS_LP44799-2");
		Set<OntologyTerm> set = os.getAllChildren(ot.getOntologyAccession(),
				ot.getAccession());
		System.out.println(set.size());

	}
}
