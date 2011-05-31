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
package uk.ac.ebi.ontocat.utils;

import java.net.URI;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;
import uk.ac.ebi.ontocat.virtual.LocalisedFileService;

/**
 * Singleton for EFO OntologyService to prevent more then one EFO instance.
 * <p/>
 * Author: Natalja Kurbatova
 * Date: 2011-03-16
 */
public final class EFOSingleton {

	private static EFOSingleton EFO = null;
	public OntologyService os;

	/**
	 * A private Constructor prevents any other class from instantiating.
	 */
	private EFOSingleton() {
		try {

			//System.out.println("FIRST TRY FROM URL");
			os = LocalisedFileService.getService(new ReasonedFileOntologyService(new URI(
			"http://www.ebi.ac.uk/efo/efo.owl"), "efo"));


		} catch (Exception e) {

			System.out.println("Sorry, can't create Ontology object for EFO by using URL \"http://www.ebi.ac.uk/efo/efo.owl\" or local file.");
			//el.printStackTrace();

		}
	}

	public static synchronized EFOSingleton getEFO() {
		if (EFO == null) {
			EFO = new EFOSingleton();
		}

		return EFO;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
