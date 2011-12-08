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


 import java.util.Collections;
 import java.util.List;
 import java.util.Set;

 import org.junit.Assert;
 import org.junit.BeforeClass;
 import org.junit.Ignore;
 import org.junit.Test;

 import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
 import uk.ac.ebi.ontocat.special.AbstractOntologyServiceTest;

 public class BioportalServiceTest extends AbstractOntologyServiceTest {

	 @BeforeClass
	 public static void setUpBeforeClass() throws Exception {
		 os = new BioportalOntologyService();
		 // GO accession, alternatively use 1506
		 ONTOLOGY_ACCESSION = "1070";
	 }

	 @Test
	// Marco's use case, searching in EFO's subtree
	 public void testSearchSubtree() throws Exception {
		 BioportalOntologyService bos = new BioportalOntologyService();
		 List<OntologyTerm> list1 = bos
		 .searchSubtree("1136", "efo:EFO_0003740", "EST");
		 Assert.assertNotSame("Subtree search list empty!", Collections.EMPTY_LIST, list1);

		 for (OntologyTerm ot : list1) {
			 System.out.println(ot);
		 }

		 // Test the number of returned terms
		 List<OntologyTerm> list2 = bos.searchOntology("1136", "EST");
		 log.info(list1.size() + " < " + list2.size());
		 Assert.assertTrue("Incorrect list size returned!", list1.size() <= list2.size());
		 
	 }

	 @Test
	 @Ignore("Takes too long to test usually")
	 public void testGetAllTerms() throws Exception {
		 // Fetch all terms for EFO
		 Set<OntologyTerm> set = os.getAllTerms("1136");
		 log.info(set.size() + " terms returned.");
		 Assert.assertTrue("Less than expected terms returned", set.size() > 2600);
	 }
 }
