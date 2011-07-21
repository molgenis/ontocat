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

import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
	 * @throws URISyntaxException
	 * @throws OntologyServiceException
	 * @throws UnknownOptionException
	 * @throws IllegalOptionValueException
	 */
	public static void main(String[] args) throws URISyntaxException,
	OntologyServiceException, IllegalOptionValueException,
	UnknownOptionException {

		URI ont = new File(
		"C:\\work\\EFO\\EFO on bar\\ExperimentalFactorOntology\\ExFactorInOWL\\releasecandidate\\efo_release_candidate.owl")
		.toURI();

		OntologyService os = new FileOntologyService(ont);

		Set<OntologyTerm> terms = os.getAllTerms(null);

		log.info("SIZE IS " + terms.size());

		for (OntologyTerm ot : terms) {
			if (!ot.getAccession().startsWith("EFO_")) {
				log.info(ot.getURI() + "\t"
						+ ot.getLabel());
			}

		}

	}

	private static String printTermList(List<OntologyTerm> list) {
		String output = "";
		for (OntologyTerm term : list) {
			output += " | " + term.getLabel();
		}
		return output;
	}

}
