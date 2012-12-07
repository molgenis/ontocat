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


import org.junit.BeforeClass;

import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;

public class OlsOntologyServiceTest extends AbstractOntologyServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = new OlsOntologyService();
		ONTOLOGY_ACCESSION = "GO";
	}
}
