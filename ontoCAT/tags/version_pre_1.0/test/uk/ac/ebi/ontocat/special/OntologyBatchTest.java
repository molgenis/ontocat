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
package uk.ac.ebi.ontocat.special;

 import junit.framework.TestCase;
 import uk.ac.ebi.ontocat.utils.OntologyBatch;
 import uk.ac.ebi.ontocat.utils.OntologyParser;

 public class OntologyBatchTest extends TestCase {
	 private OntologyBatch ob;

	 @Override
	 protected void setUp() throws Exception {
		 super.setUp();
		 ob = new OntologyBatch();

	 }

	 @Override
	 protected void tearDown() throws Exception {
		 super.tearDown();
		 ob = null;
	 }

	 public void testOntologyBatch() throws Exception {
		 //System.out.println("ADD NEW ONTOLOGY (EFO)");
		 //ob.addEFO();
		 //System.out.println(ob.searchTerm("thymus"));
		 //OntologyBatch batch = new OntologyBatch(); //getEFOBatch()
		 //ob.addOntology("/Applications/R/ontoCAT/inst/extdata/cell.obo");
		 //for (OntologyTerm term : ob.searchTerm("cell"))
		 //  System.out.println(term.getAccession());
		 //for (String ont: ob.listBioportalOntologies())
		 //  System.out.println(ont);
		 //OntologyParser op = ob.getOntologyParser("2034");
		 //System.out.println(op.getAllTerms());
		 OntologyParser efo = new OntologyParser();
		 System.out.println(efo.getOntologyDescription());
		 //OLS batch <- getEFOBatch()
		 //searchTermInOLS(batch,"cell")

		 //  batch <- getOntologyBatch("./ontoCAT/extdata/")
		 ///addEFO(batch)

		 //        batch <- getEFOBatch()
		 //addOntology(batch,"./ontoCAT/extdata/cell.obo")
		 //listLoadedOntologies(batch)
		 //searchTermInBatch(batch,"cell")





		 /*System.out.println("ADD NEW ONTOLOGY");
        ob.addOntology("/Users/nata/IdeaProjects/ontoCAT/test/resources/worm.obo");
        System.out.println("LIST LOADED ONTOLOGIES");

        List<String> loadedOntologies = ob.listLoadedOntologies();

        for (String description : loadedOntologies)
            System.out.println(description);

        System.out.println("LIST LOADED ONTOLOGIES WITH SOURCES");

        loadedOntologies = ob.listLoadedOntologiesSources();

        for (String description : loadedOntologies)
            System.out.println(description);

        System.out.println("GET ONTOLOGY PARSER BY ACCESSION");

        OntologyParser op = ob.getOntologyParser("http://who.int/bodysystem.owl");
        System.out.println(op.getAllTermIds());  */

		 /*System.out.println("SEARCH IN LOADED");

    for (OntologyTerm ot : ob.searchTerm("system"))
        System.out.println(ot);

    System.out.println("SEARCH IN ALL");

    for (OntologyTerm ot : ob.searchTermInAll("system"))
        System.out.println(ot);

    System.out.println("SEARCH IN BIOPORTAL");

    for (OntologyTerm ot : ob.searchTermInBioportal("system"))
        System.out.println(ot);*/

		 //System.out.println("GET ONTOLOGY PARSER FROM BIOPORTAL:");
		 //OntologyParser op2 = ob.getOntologyParser("1055");
		 //System.out.println(op2.getRootIds());

		 //Takes too long time
		 /*System.out.println("SEARCH IN OLS");

        for (OntologyTerm ot : ob.searchTermInOLS("thymus"))
            System.out.println(ot);

        System.out.println("GET ONTOLOGY PARSER FROM OLS:");
        OntologyParser op3 = ob.getOntologyParser("BTO");
        System.out.println(op3.getAllTermIds()); */


	 }
 }