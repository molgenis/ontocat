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
 import uk.ac.ebi.ontocat.OntologyService;
 import uk.ac.ebi.ontocat.OntologyTerm;
 import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
 import uk.ac.ebi.ontocat.ols.OlsOntologyService;
 import uk.ac.ebi.ontocat.utils.OntologyParser;

 public class OntologyParserTest extends TestCase {
	 private OntologyParser op;


	 @Override
	 protected void setUp() throws Exception {
		 super.setUp();
		 op = new OntologyParser();     //"/Users/nata/IdeaProjects/ontoCAT/ontoCAT-R/inst/extdata/cell.obo"
	 }

	 @Override
	 protected void tearDown() throws Exception {
		 super.tearDown();
		 op = null;
	 }

	 public void testOntologyParser() throws Exception {

		 if (op != null){
			 System.out.println("ONTOLOGY'S RELATION NAMES:");
			 for (String relation : op.getOntologyRelationNames()){
				 System.out.println(relation);
			 }

			 OntologyTerm term = op.getTermById("EFO_0000806");

			 System.out.println("TERM'S \""+ term.getLabel() + "\" RELATION NAMES:");
			 for (String relation : op.getTermRelationNames(term)){
				 System.out.println(relation);
			 }

			 System.out.println("TERM'S \""+ term.getLabel() +"\" RELATIONS (is_location_of_disease):");
			 for (OntologyTerm ot : op.getTermRelations(term,"is_location_of_disease")){
				 System.out.println(ot.getLabel());
			 }

			 System.out.println("TERM'S \""+ term.getLabel() +"\" PARTONOMY:");
			 op.showTermPartonomy(term);

			 OntologyTerm termFail = op.getTermById("AA_0000502");

			 System.out.println("SEARCH TERM:");
			 for (OntologyTerm ot : op.searchTerm("electrically")) {
				 System.out.println(ot);
			 }

			 System.out.println("SEARCH TERM PREFIX:");
			 for (OntologyTerm ot : op.searchTermPrefix("T3")) {
				 System.out.println(ot);
			 }

			 System.out.println("GET ALL TERMS:");
			 for (OntologyTerm ot : op.getAllTerms()) {
				 System.out.println(ot);
			 }

			 System.out.println("GET ALL TERM IDS:");
			 for (String st : op.getAllTermIds()) {
				 System.out.println(st);
			 }

			 System.out.println("GET ROOTS:");
			 for (OntologyTerm ot : op.getRoots()) {
				 System.out.println(ot);
			 }

			 System.out.println("GET ROOT IDS:");
			 for (String st : op.getRootIds()) {
				 System.out.println(st);
			 }

			 System.out.println("GET EFO BRANCH ROOTS:");
			 for (OntologyTerm ot : op.getEFOBranchRoots()) {
				 System.out.println(ot);
			 }

			 System.out.println("GET EFO BRANCH ROOT IDS:");
			 for (String st : op.getEFOBranchRootIds()) {
				 System.out.println(st);
			 }




			 System.out.println("GET TERM CHILDREN:");
			 for (OntologyTerm ot : op.getTermChildrenById(term.getAccession())) {
				 System.out.println(ot);
			 }


			 System.out.println("GET ALL TERM CHILDREN:");
			 for (OntologyTerm ot : op.getAllTermChildrenById(term.getAccession())) {
				 System.out.println(ot);
			 }

			 System.out.println("GET TERM PARENTS:");
			 for (OntologyTerm ot : op.getTermParentsById(term.getAccession())) {
				 System.out.println(ot);
			 }

			 System.out.println("GET ALL TERM PARENTS:");
			 for (OntologyTerm ot : op.getAllTermParentsById(term.getAccession())) {
				 System.out.println(ot);
			 }

			 System.out.println("GET TERM NAME BY ID:");
			 System.out.println(op.getTermNameById("CL_0000502"));
			 System.out.println(op.getTermNameById("AA_0000502"));

			 System.out.println("GET TERM NAME:");
			 System.out.println(op.getTermName(term));
			 System.out.println(op.getTermName(termFail));

			 System.out.println("GET TERM DEFINITIONS BY ID:");
			 for (String st : op.getTermDefinitionsById("CL_0000502")) {
				 System.out.println(st);
			 }
			 for (String st : op.getTermDefinitionsById("AA_0000502")) {
				 System.out.println(st);
			 }

			 System.out.println("GET TERM DEFINITIONS:");
			 for (String st : op.getTermDefinitions(term)) {
				 System.out.println(st);
			 }
			 for (String st : op.getTermDefinitions(termFail)) {
				 System.out.println(st);
			 }

			 System.out.println("GET TERM SYNONYMS BY ID:");
			 for (String st : op.getTermSynonymsById("CL_0000502")) {
				 System.out.println(st);
			 }
			 for (String st : op.getTermSynonymsById("AA_0000502")) {
				 System.out.println(st);
			 }

			 System.out.println("GET TERM SYNONYMS:");
			 for (String st : op.getTermSynonyms(term)) {
				 System.out.println(st);
			 }
			 for (String st : op.getTermSynonyms(termFail)) {
				 System.out.println(st);
			 }

			 System.out.println("CHECK TERM:");
			 System.out.println(op.hasTerm("CL_0000502"));
			 System.out.println(op.hasTerm("AA_0000502"));

			 System.out.println("CHECK ROOT TERM BY ID:");
			 System.out.println(op.isRoot("CL_0000502"));
			 System.out.println(op.isRoot("CL_0000000"));
			 System.out.println(op.isRoot("AA_0000502"));

			 System.out.println("CHECK ROOT TERM:");
			 System.out.println(op.isRoot(term));
			 System.out.println(op.isRoot(termFail));

			 System.out.println("CHECK EFO BRANCH ROOT TERM BY ID:");
			 System.out.println(op.isEFOBranchRoot("CL_0000502"));
			 System.out.println(op.isEFOBranchRoot("CL_0000000"));
			 System.out.println(op.isEFOBranchRoot("AA_0000502"));

			 System.out.println("CHECK EFO BRANCH ROOT TERM:");
			 System.out.println(op.isEFOBranchRoot(term));
			 System.out.println(op.isEFOBranchRoot(termFail));

			 System.out.println("HIERARCHY:");

			 op.showHierarchyDownToTerm("CL_0000502");
			 op.showPathsToTerm("CL_0000502");
			 op.showHierarchyDownToTerm(term);
			 op.showPathsToTerm(term);
			 op.showHierarchyDownToTerm("AA_0000502");
			 op.showHierarchyDownToTerm(termFail);
			 op.showPathsToTerm(termFail);

			 System.out.println("PARSER FOR BIOPORTAL 1158:");
			 OntologyService los = new BioportalOntologyService();
			 OntologyParser bop = new OntologyParser(los, "1158");
			 for (OntologyTerm ot : bop.getRoots()) {
				 System.out.println(ot.getAccession());
			 }
			 System.out.println(bop.getOntologyDescription());

			 System.out.println("PARSER FOR OLS EHDA:");
			 OntologyService los2 = new OlsOntologyService();
			 OntologyParser bop2 = new OntologyParser(los2, "EHDA");
			 for (OntologyTerm ot : bop2.getRoots()) {
				 System.out.println(ot.getAccession());
			 }
			 System.out.println(bop2.getOntologyDescription());

		 }

	 }

 }
