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

import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.virtual.SortedSubsetDecorator;

public class SortedSubsetTest {
	static OntologyService os;

	@SuppressWarnings("serial")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		os = SortedSubsetDecorator.getService(new BioportalOntologyService(),
				new ArrayList<String>() {
			{
				add("HP");
				add("EMAP");
				add("MP");
				add("1136");
				add("1053");
			}
		});
	}

	@Test
	public void testRankedSearch() throws OntologyServiceException {
		List<OntologyTerm> list = os.searchAll("thymus");
		assertNotSame("Empty list returned!", 0, list.size());
		for (OntologyTerm ot : list) {
			System.out.println(ot);
		}
	}

	@Test(expected = NullPointerException.class)
	public void testOutsideScopeSearchOntologyTest()
	throws OntologyServiceException {
		for (OntologyTerm ot : os.searchOntology("CHEBI", "nicotine")) {
			System.out.println(ot);
		}
	}
}
