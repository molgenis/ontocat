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

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;
import uk.ac.ebi.ontocat.virtual.LocalisedFileService;


/**
 * Methods to parse ontology: search terms, get children and parents of term,
 * show hierarchy and paths to term, get info about ontology.
 * <p/>
 * Author: Natalja Kurbatova Date: 2010-09-20
 */
public class OntologyParser {


	private OntologyService os;
	private String ontologySource;
	private String ontologyAccession;
	public boolean status = false;


	//* ****************************************************************************************************


	//* OntologyParser CONSTRUCTORS


	//******************************************************************************************************

	/**
	 * Creates instance of OntologyParser for EFO
	 */
	public OntologyParser() {


		//default is EFO last version
		try {

			this.os = EFOSingleton.getEFO().os;

			ontologySource = "http://www.ebi.ac.uk/efo/efo.owl";

			for (Ontology ot : os.getOntologies()) {
				ontologyAccession = ot.getOntologyAccession();//ontology = ot;
			}

			status = true;

		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't create Ontology object for EFO by using URL \"http://www.ebi.ac.uk/efo/efo.owl\".");
			status = false;
		}

	}

	/**
	 * Creates instance of OntologyParser from provided file or URL OWL and OBO
	 * formats are supported
	 *
	 * @param pathToOntology path to the file or URL for ontology
	 */
	public OntologyParser(String pathToOntology) {

		int correctResource = 0;

		try {

			File file = new File(pathToOntology);
			URI uri = null;

			if (file.exists()) {
				correctResource = 1;
				uri = file.toURI();

			}
			else {

				URL url = new URL(pathToOntology);

				correctResource = 1;

				uri = url.toURI();
			}


			if (correctResource > 0) {
				ontologySource = pathToOntology;

				if (ontologySource.equals("http://www.ebi.ac.uk/efo/efo.owl")) {
					this.os = EFOSingleton.getEFO().os;
				}

				else if (correctResource == 1) {
					os = LocalisedFileService.getService
					(new ReasonedFileOntologyService(uri));
				}


				for (Ontology ot : os.getOntologies()) {
					ontologyAccession = ot.getOntologyAccession();
				}

				status = true;


			}
			else {
				System.out.println(
						"Sorry, can't create Ontology object for file \"" + pathToOntology +
						"\"." +
				"The ontology file doesn't exist.");
			}


		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't create Ontology object. Please, check ontology's URI: " +
					pathToOntology + ".");
			status = false;
		}

	}

	/**
	 * Creates instance of OntologyParser from provided file or URL OWL and OBO
	 * formats are supported. Additional argument allows to switch off reasoning.
	 *
	 * @param pathToOntology path to the file or URL for ontology
	 * @param reasoning      false to switch off reasoning/true the same as
	 *                       previous constructor
	 */
	public OntologyParser(String pathToOntology, String reasoning) {

		int correctResource = 0;

		try {

			File file = new File(pathToOntology);
			URI uri = null;

			if (file.exists()) {
				correctResource = 1;
				if (reasoning.equals("false")) {
					correctResource = 2;
				}
				uri = file.toURI();

			}
			else {

				URL url = new URL(pathToOntology);
				uri = url.toURI();
				correctResource = 1;
				if (reasoning.equals("false")) {
					correctResource = 2;
				}

			}


			if (correctResource > 0) {
				ontologySource = pathToOntology;

				if (ontologySource.equals("http://www.ebi.ac.uk/efo/efo.owl")) {
					this.os = EFOSingleton.getEFO().os;
				}

				else if (correctResource == 1) {
					os = LocalisedFileService.getService
					(new ReasonedFileOntologyService(uri));
				}
				else if (correctResource == 2) {
					os = LocalisedFileService.getService
					(new FileOntologyService(uri));
				}

				for (Ontology ot : os.getOntologies()) {
					ontologyAccession = ot.getOntologyAccession();
				}

				status = true;

			}
			else {
				System.out.println(
						"Sorry, can't create Ontology object for file \"" + pathToOntology +
						"\"." +
				"The ontology file doesn't exist.");
			}


		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't create Ontology object. Please, check ontology's URI: " +
					pathToOntology + ".");
			status = false;
		}

	}

	/**
	 * Creates instance of OntologyParser for OntologyService (OLS Bioportal) and
	 * ontology from these resources defined by ontologyAccession String
	 *
	 * @param os                - ontology service to work with
	 * @param ontologyAccession - ontology accession to work with
	 */
	public OntologyParser(OntologyService os, String ontologyAccession) {
		this.os = os;
		this.ontologyAccession = ontologyAccession;
		try {
			if (!this.os.getRootTerms(this.ontologyAccession).isEmpty()) {
				//
			}
			status = true;
		}
		catch (Exception e) {
			System.out.println("Sorry, can't create Ontology object for ontology \"" +
					ontologyAccession + "\".");
			status = false;
		}
	}

	/**
	 * Returns ontologySource, usually URI
	 *
	 * @return ontologySource
	 */
	public String getOntologySource() {
		return ontologySource;
	}

	/**
	 * Returns ontologyService
	 *
	 * @return ontologyService used in this instance of OntologyParser
	 */
	public OntologyService getOntologyService() {
		return os;
	}


	//* ****************************************************************************************************


	//* SEARCH METHODS


	//******************************************************************************************************

	/**
	 * Searches for term in ontology
	 *
	 * @param text words to search
	 * @return list of OntologyTerm - terms in ontology that have this word in
	 *         label
	 */
	public List<OntologyTerm> searchTerm(String text) {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();

		try {

			for (OntologyTerm ot : os.getAllTerms(ontologyAccession)) {
				if (ot.getLabel().contains(text)) {
					result.add(ot);
				}
				else {
					for (String alt : os.getSynonyms(ot)) {
						if (alt.contains(text)) {
							result.add(ot);
							break;
						}
					}
				}
			}

		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find terms containing \"" + text + "\" in ontology \"" +
					ontologyAccession + "\".");

		}

		return result;
	}

	/**
	 * Searches for term prefix in ontology
	 *
	 * @param prefix prefix to search
	 * @return list of OntologyTerms - terms in ontology that have required prefix
	 *         in label or synonyms
	 */
	public List<OntologyTerm> searchTermPrefix(String prefix) {

		String lowerCasePrefix = prefix.toLowerCase();

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();

		try {
			for (OntologyTerm ot : os.getAllTerms(ontologyAccession)) {
				if (ot.getLabel().toLowerCase().startsWith(lowerCasePrefix) ||
						ot.getAccession().toLowerCase().startsWith(lowerCasePrefix)) {
					result.add(ot);
				}
				else {
					for (String alt : os.getSynonyms(ot)) {
						if (alt.toLowerCase().startsWith(lowerCasePrefix)) {
							result.add(ot);
							break;
						}
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find terms containing prefix \"" + prefix +
					"\" in ontology \"" + ontologyAccession + "\".");

		}
		return result;
	}


	//* ****************************************************************************************************


	//* GET METHODS FOR ALL TERMS


	//******************************************************************************************************

	/**
	 * Returns list of all terms in ontology
	 *
	 * @return list of OntologyTerm - all terms in ontology
	 */
	public List<OntologyTerm> getAllTerms() {
		List<OntologyTerm> result = new ArrayList<OntologyTerm>();

		try {
			for (OntologyTerm ot : os.getAllTerms(ontologyAccession)) {
				result.add(ot);
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't get all terms for ontology " + ontologyAccession + ".");

		}

		return result;


	}

	/**
	 * Returns list of all term accessions in ontology
	 *
	 * @return list of Strings - all term accessions in ontology
	 */
	public List<String> getAllTermIds() {

		List<String> result = new ArrayList<String>();
		try {
			for (OntologyTerm ot : os.getAllTerms(ontologyAccession)) {
				result.add(ot.getAccession());
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't get all terms for ontology \"" + ontologyAccession +
			"\".");

		}
		return result;
	}

	//* ****************************************************************************************************


	//* GET METHODS FOR ROOT TERMS


	//******************************************************************************************************

	/**
	 * Returns list of root node accessions
	 *
	 * @return list of Strings - root node accessions
	 */
	public List<String> getRootIds() {

		List<String> result = new ArrayList<String>();
		try {
			for (OntologyTerm n : os.getRootTerms(ontologyAccession)) {
				result.add(n.getAccession());
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't get root terms for ontology \"" + ontologyAccession +
			"\".");

		}
		return result;
	}

	/**
	 * Returns list of root terms
	 *
	 * @return list of OntologyTerms - root terms
	 */
	public List<OntologyTerm> getRoots() {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();

		try {
			result = os.getRootTerms(ontologyAccession);
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't get root terms for ontology \"" + ontologyAccession +
			"\".");

		}
		return result;

	}

	/**
	 * EFO specific method. Returns list of branch root accessions. Method
	 * specific for EFO ontology
	 *
	 * @return list of Strings - EFO branch root accessions
	 */
	public List<String> getEFOBranchRootIds() {

		List<String> result = new ArrayList<String>();

		try {
			for (OntologyTerm n : os.getAllTerms(ontologyAccession)) {
				if (isEFOBranchRoot(n.getAccession())) {
					result.add(n.getAccession());
				}
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't get EFO branch root terms for ontology \"" +
					ontologyAccession + "\".");

		}
		return result;
	}

	/**
	 * EFO specific method. Returns list of branch root accessions. Method
	 * specific for EFO ontology
	 *
	 * @return list of OntologyTerms - EFO branch root terms
	 */
	public List<OntologyTerm> getEFOBranchRoots() {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		try {
			for (OntologyTerm n : os.getAllTerms(ontologyAccession)) {
				if (isEFOBranchRoot(n.getAccession())) {
					result.add(n);
				}
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't get EFO branch root terms for ontology \"" +
					ontologyAccession + "\".");

		}
		return result;
	}

	//* ****************************************************************************************************


	//* GET METHODS FOR TERM'S CHILDREN AND PARENTS


	//******************************************************************************************************


	/**
	 * Returns list of term's direct children
	 *
	 * @param accession - term accession
	 * @return list of OntologyTerms - direct child terms of the term
	 */
	public List<OntologyTerm> getTermChildren(String accession) {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		try {
			OntologyTerm term = os.getTerm(accession);

			for (OntologyTerm ot : os.getChildren(term)) {
				result.add(ot);
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term '" + accession + "' in ontology \"" +
					ontologyAccession + "\".");


		}
		return result;

	}

	/**
	 * Returns list of term's direct children
	 *
	 * @param term - term of interest
	 * @return list of OntologyTerms - direct child terms of the term
	 */
	public List<OntologyTerm> getTermChildren(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return getTermChildren(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return new ArrayList<OntologyTerm>();
		}
	}

	/**
	 * Returns list of term's direct parents
	 *
	 * @param accession - term accession
	 * @return list of OntologyTerms - direct parent terms of the term
	 */
	public List<OntologyTerm> getTermParents(String accession) {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		try {
			OntologyTerm term = os.getTerm(accession);

			for (OntologyTerm ot : os.getParents(term)) {
				result.add(ot);
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term '" + accession + "' in ontology \"" +
					ontologyAccession + "\".");


		}
		return result;
	}

	/**
	 * Returns list of term's direct parents
	 *
	 * @param term - term of interest
	 * @return list of OntologyTerms - direct parent terms of the term
	 */
	public List<OntologyTerm> getTermParents(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return getTermParents(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return new ArrayList<OntologyTerm>();
		}
	}

	/**
	 * Returns list of term's all children
	 *
	 * @param accession - term accession
	 * @return list of OntologyTerms - all term children
	 */
	public List<OntologyTerm> getAllTermChildren(String accession) {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		try {
			OntologyTerm term = os.getTerm(accession);

			for (OntologyTerm ot : os
					.getAllChildren(ontologyAccession, term.getAccession())) {
				result.add(ot);
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term '" + accession + "' in ontology \"" +
					ontologyAccession + "\".");


		}
		return result;

	}

	/**
	 * Returns list of term's all children
	 *
	 * @param term - term of interest
	 * @return list of OntologyTerms - all term children
	 */
	public List<OntologyTerm> getAllTermChildren(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return getAllTermChildren(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return new ArrayList<OntologyTerm>();
		}

	}

	/**
	 * Returns list of term's all parents
	 *
	 * @param accession - term accession
	 * @return list of OntologyTerms - all term parents
	 */
	public List<OntologyTerm> getAllTermParents(String accession) {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		try {
			OntologyTerm term = os.getTerm(accession);

			for (OntologyTerm ot : os
					.getAllParents(ontologyAccession, term.getAccession())) {
				result.add(ot);
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}
		return result;
	}

	/**
	 * Returns list of term's all parents
	 *
	 * @param term - term of interest
	 * @return list of OntologyTerms - all term parents
	 */
	public List<OntologyTerm> getAllTermParents(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return getAllTermParents(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return new ArrayList<OntologyTerm>();
		}
	}

	/**
	 * Returns list of accessions of node itself and all its children recursively
	 *
	 * @param accession - term accession
	 * @return list of accessions, empty if term is not found
	 */
	public List<OntologyTerm> getTermAndAllChildren(String accession) {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();

		try {

			OntologyTerm term = os.getTerm(accession);
			for (OntologyTerm child : os
					.getAllChildren(ontologyAccession, term.getAccession())) {
				result.add(child);
			}

			result.add(term);
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}

		return result;
	}

	/**
	 * Returns list of accessions of node itself and all its children recursively
	 *
	 * @param term - term of interest
	 * @return list of accessions, empty if term is not found
	 */
	public List<OntologyTerm> getTermAndAllChildren(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return getTermAndAllChildren(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return new ArrayList<OntologyTerm>();
		}
	}


	//* ****************************************************************************************************


	//* GET METHODS FOR TERM: TERM BY ITSELF, NAME, DEFINITIONS, SYNONYMS


	//******************************************************************************************************


	/**
	 * Fetch term by accession
	 *
	 * @param accession - term accession
	 * @return OntologyTerm - external term representation
	 */
	public OntologyTerm getTermById(String accession) {
		OntologyTerm term = null;
		try {
			term = os.getTerm(accession);


		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}
		return term;

	}


	/**
	 * Returns term label by accession
	 *
	 * @param accession - term accession
	 * @return String - term label
	 */
	public String getTermNameById(String accession) {

		String result = "";

		try {
			OntologyTerm term = os.getTerm(accession);

			result = term.getLabel();
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");

		}
		return result;
	}

	/**
	 * Returns list of term's definitions if there are some
	 *
	 * @param accession - term accession
	 * @return list of Strings - term's definitions
	 */
	public List<String> getTermDefinitions(String accession) {

		List<String> result = new ArrayList<String>();
		try {
			OntologyTerm term = os.getTerm(accession);


			for (String definition : os.getDefinitions(term)) {
				result.add(definition);
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}
		return result;
	}

	/**
	 * Returns list of term's synonyms if there are some
	 *
	 * @param accession - term accession
	 * @return list of Strings - term's synonyms
	 */
	public List<String> getTermSynonyms(String accession) {

		List<String> result = new ArrayList<String>();
		try {
			OntologyTerm term = os.getTerm(accession);

			for (String synonym : os.getSynonyms(term)) {
				result.add(synonym);
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}
		return result;
	}


	/**
	 * Returns term label by accession
	 *
	 * @param term - OntologyTerm og interest
	 * @return String - term label
	 */
	public String getTermName(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return getTermNameById(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return "";
		}
	}

	/**
	 * Returns list of term's definitions if there are some
	 *
	 * @param term - OntologyTerm of interest
	 * @return list of Strings - term's definitions
	 */
	public List<String> getTermDefinitions(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return getTermDefinitions(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return new ArrayList<String>();
		}
	}

	/**
	 * Returns list of term's synonyms if there are some
	 *
	 * @param term - OntologyTerm of interest
	 * @return list of Strings - term's synonyms
	 */
	public List<String> getTermSynonyms(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return getTermSynonyms(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return new ArrayList<String>();
		}
	}


	//* ****************************************************************************************************


	//* CHECK METHODS


	//******************************************************************************************************

	/**
	 * Checks if term is in ontology
	 *
	 * @param accession - term accession
	 * @return true if term with provided accession is in ontology
	 */
	public boolean hasTerm(String accession) {

		boolean result = false;
		try {
			OntologyTerm node = os.getTerm(accession);
			result = (node != null);
		}
		catch (Exception e) {
			//Nothing
		}

		return result;
	}


	/**
	 * Returns if term is root ontology term
	 *
	 * @param accession - term accession
	 * @return true if term is root ontology term
	 */
	public boolean isRoot(String accession) {

		boolean result = false;
		try {
			OntologyTerm term = os.getTerm(accession);

			for (OntologyTerm ot : os.getRootTerms(ontologyAccession)) {
				if (ot.equals(term)) {
					result = true;
				}
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}
		return result;
	}

	/**
	 * Returns if term is root ontology term
	 *
	 * @param term - OntologyTerm of interest
	 * @return true if term is root ontology term
	 */
	public boolean isRoot(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return isRoot(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return false;
		}
	}

	/**
	 * EFO specific method. Returns true if term is branch root
	 *
	 * @param accession - term accession
	 * @return true if term is EFO branch root term
	 */
	public boolean isEFOBranchRoot(String accession) {
		boolean result = false;

		try {
			OntologyTerm term = os.getTerm(accession);

			List<String> propBranchClass =
				os.getAnnotations(term).get("branch_class");

			result = propBranchClass != null && propBranchClass.size() > 0
			&& propBranchClass.get(0).equalsIgnoreCase("true");


		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");

		}
		return result;
	}

	/**
	 * EFO specific method. Returns true if term is branch root
	 *
	 * @param term - OntologyTerm of interest
	 * @return true if term is EFO branch root term
	 */
	public boolean isEFOBranchRoot(OntologyTerm term) {
		if (term != null && term.getAccession() != null) {
			return isEFOBranchRoot(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
			return false;
		}


	}


	//* ****************************************************************************************************


	//* REPRESENTATION METHODS FOR HIERARCHY OF ONTOLOGY


	//******************************************************************************************************

	/**
	 * Prints out flat sub-tree representation
	 *
	 * @param accession of term
	 */
	public void showHierarchyDownToTerm(String accession) {

		try {
			OntologyTerm term = os.getTerm(accession);


			List<Stack<OntologyTerm>> pathStack = getClassPathToRoot(term);

			Stack<OntologyNode> result = new Stack<OntologyNode>();
			collectToPrintTreeDownTo(
					orderedStack(this.getRoots(), os.getTerm(accession)), pathStack,
					result, 0, os.getTerm(accession), false, false);

			for (OntologyNode n : result) {
				System.out.println(n);
			}

		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}

	}

	/**
	 * Prints out flat sub-tree representation
	 *
	 * @param term - OntologyTerm of interest
	 */
	public void showHierarchyDownToTerm(OntologyTerm term) {

		if (term == null || term.getAccession() == null) {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
		}
		else {
			showHierarchyDownToTerm(term.getAccession());
		}
	}

	/**
	 * Prints paths to term
	 *
	 * @param accession of term
	 */
	public void showPathsToTerm(String accession) {
		try {
			OntologyTerm term = os.getTerm(accession);


			List<Stack<OntologyTerm>> pathStack = getClassPathToRoot(term);
			Iterator paths = pathStack.iterator();
			int i = 0;
			while (paths.hasNext()) {
				i++;
				Stack<OntologyTerm> path = (Stack<OntologyTerm>) paths.next();
				String result = "Path " + i + ": ";
				for (OntologyTerm n : path) {
					result = result + n.getAccession() + " <- ";
				}
				System.out.println(result.substring(0, result.length() - 4));

			}

		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}
	}

	/**
	 * Prints paths to term
	 *
	 * @param term - OntologyTerm of interest
	 */
	public void showPathsToTerm(OntologyTerm term) {

		if (term == null || term.getAccession() == null) {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");
		}
		else {
			showPathsToTerm(term.getAccession());
		}
	}


	//* ****************************************************************************************************


	//* GET METHODS FOR ONTOLOGY


	//******************************************************************************************************


	/**
	 * Returns parsed ontology description
	 *
	 * @return ontology description: accession, version
	 */
	public String getOntologyDescription() {

		String result = "";
		try {
			Ontology ontology = os.getOntology(ontologyAccession);

			result = "Accession: " +
			(ontology.getOntologyAccession() != null ? ontology
					.getOntologyAccession() : "");

			result = result + (ontology.getVersionNumber() != null ? " Version: " +
					ontology.getVersionNumber() : "");
			result = result +
			(ontology.getLabel() != null ? " Label: " + ontology.getLabel() : "");
			result = result +
			(ontology.getAbbreviation() != null ? " Abbreviation: " +
					ontology.getAbbreviation() : "");
			result = result + (ontology.getDescription() != null ? " Description: " +
					ontology.getDescription() : "");
			result = result +
			(ontology.getDateReleased() != null ? " Release date: " +
					ontology.getDateReleased() : "");
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find description for ontology \"" + ontologyAccession +
			"\".");


		}
		return result;

	}

	/**
	 * Returns ontology accession
	 *
	 * @return ontology accession
	 */
	public String getOntologyAccession() {

		return ontologyAccession;

	}

	//* ****************************************************************************************************


	//* METHODS FOR RELATIONS


	//******************************************************************************************************


	/**
	 * Returns relations used in ontology
	 *
	 * @return list of ontology relations
	 */
	public List<String> getOntologyRelationNames() {

		List<String> result = new ArrayList<String>();

		try {
			OntologyTerm startNode = getRoots().get(0);

			TreeMap<String, Set<OntologyTerm>> relations = new TreeMap();
			relations.putAll(os.getRelations(startNode));

			for (Map.Entry<String, Set<OntologyTerm>> entry : relations.entrySet()) {
				System.out.println("\n\t" + entry.getKey());
				result.add(entry.getKey());
			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find relations in ontology \"" + ontologyAccession +
			"\".");


		}
		return result;

	}

	/**
	 * Returns relations for term
	 *
	 * @param term of interest
	 */
	public List<String> getTermRelationNames(OntologyTerm term) {

		List<String> result = new ArrayList<String>();

		if (term != null && term.getAccession() != null) {
			result = getTermRelationNames(term.getAccession());
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");

		}

		return result;
	}

	/**
	 * Returns relations for term
	 *
	 * @param accession for term
	 */
	public List<String> getTermRelationNames(String accession) {

		List<String> result = new ArrayList<String>();

		try {

			OntologyTerm startNode = os.getTerm(accession);

			TreeMap<String, Set<OntologyTerm>> relations = new TreeMap();
			relations.putAll(os.getRelations(startNode));

			for (Map.Entry<String, Set<OntologyTerm>> entry : relations.entrySet()) {

				if (!entry.getValue().isEmpty()) {
					result.add(entry.getKey());
					//System.out.println("\n\t" + entry.getKey());
				}

			}

		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}

		return result;
	}

	/**
	 * Returns terms that are in specified relation with term of interest
	 *
	 * @param accession term to find relations with
	 * @param relation  that term has to have
	 * @return list of terms
	 */
	public List<OntologyTerm> getTermRelations(String accession,
			String relation) {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();
		OntologyTerm startNode = null;

		try {

			startNode = os.getTerm(accession);
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find term \"" + accession + "\" in ontology \"" +
					ontologyAccession + "\".");


		}

		try {

			Map<String, Set<OntologyTerm>> relations = os.getRelations(startNode);

			for (Map.Entry<String, Set<OntologyTerm>> entry : relations.entrySet()) {

				if (!entry.getValue().isEmpty() && entry.getKey().equals(relation)) {
					for (OntologyTerm ot : entry.getValue()) {
						result.add(ot);
					}
				}

			}
		}
		catch (Exception e) {
			System.out.println(
					"Sorry, can't find relation \"" + relation + "\" for term \"" +
					accession + "\" in ontology \"" + ontologyAccession + "\".");


		}
		return result;

	}

	/**
	 * Returns terms that are in specified relation with term of interest
	 *
	 * @param term     Ontology Term to find relations with
	 * @param relation that term has to have
	 * @return list of terms
	 */
	public List<OntologyTerm> getTermRelations(OntologyTerm term,
			String relation) {

		List<OntologyTerm> result = new ArrayList<OntologyTerm>();

		if (term != null && term.getAccession() != null) {
			result = getTermRelations(term.getAccession(), relation);
		}
		else {
			System.out.println("Sorry, can't find requested term in ontology \"" +
					ontologyAccession + "\".");

		}
		return result;

	}


	/**
	 * Shows term's partonomy
	 *
	 * @param startNode term to show partonomy to
	 */
	public void showTermPartonomy(OntologyTerm startNode) {

		try {


			// Proceed with building the partonomy tree
			// Here focusing on a specific relation makes it quicker
			// but see visualiseAll() for a full tree
			List<OntologyTerm> branch = getAllTermChildren(startNode);
			branch.add(startNode);
			System.out
			.println(
					"\nBuilding exhaustive relations tree, estimated size more than "
					+ branch.size() + " classes\n");

			System.out.println(startNode.getAccession() + " " + startNode.getLabel());
			visualisePartonomy(startNode, "    ");

		}
		catch (Exception e) {
			System.out.println("Sorry, can't visualise partonomy.");


		}
	}

	/**
	 * Shows term all relations
	 *
	 * @param startNode term to show relations to
	 */
	public void showTermAllRelations(OntologyTerm startNode) {

		try {


			// Proceed with building the partonomy tree
			// Here focusing on a specific relation makes it quicker
			// but see visualiseAll() for a full tree
			List<OntologyTerm> branch = getAllTermChildren(startNode);
			branch.add(startNode);
			System.out
			.println(
					"\nBuilding exhaustive relations tree, estimated size more than "
					+ branch.size() + " classes\n");

			System.out.println(startNode.getAccession() + " " + startNode.getLabel());
			visualiseAll(startNode, "    ");
		}
		catch (Exception e) {
			System.out.println("Sorry, can't visualise all relations");


		}

	}


	//* ****************************************************************************************************


	//* PRIVATE METHODS AND CLASSES


	//******************************************************************************************************

	/**
	 * Method to collect all term children and their depths for one path
	 *
	 * @param nodes     - current nodes under inspection
	 * @param path      - path to mainTerm
	 * @param result    - stack with results
	 * @param depth     - current depth in ontology, will be assign to created
	 *                  nodes
	 * @param mainTerm  - term to collect tree down to
	 * @param shortPath - show all neighbour terms to inspected one or not
	 * @throws OntologyServiceException - internal ontoCAT exception
	 */
	private void collectToPrintTreeDownTo(Stack<OntologyTerm> nodes,
			Stack<OntologyTerm> path,
			Stack<OntologyNode> result, int depth,
			OntologyTerm mainTerm,
			boolean shortPath)
	throws OntologyServiceException {

		OntologyTerm next = path.pop();
		OntologyTerm temp = null;
		//boolean exclude = false;
		for (OntologyTerm n : nodes) {
			if (n.equals(next) && !path.empty()) {
				temp = n;
			}
			else if (!shortPath) {
				result.push(new OntologyNode(depth, n));
			}
		}
		//To ensure that requested term is at the bottom of the tree
		if (temp != null) {
			result.push(new OntologyNode(depth, temp));
			collectToPrintTreeDownTo(orderedStack(os.getChildren(temp), mainTerm),
					path, result, depth + 1, mainTerm, shortPath);
		}
		if (temp == null && path.empty() &&
				(!result.contains(new OntologyNode(depth, mainTerm)))) {
			result.push(new OntologyNode(depth, mainTerm));
		}
	}

	/**
	 * Method to collect all term children and their depths for multiple paths
	 *
	 * @param nodes        - current nodes under inspection
	 * @param paths        - paths to mainTerm
	 * @param result       - stack with results
	 * @param depth        - current depth in ontology, will be assign to created
	 *                     nodes
	 * @param mainTerm     - term to collect tree down to
	 * @param shortPath    - show all neighbour terms to inspected one or not
	 * @param stopMergeVal - common tree for all paths has been found or not
	 * @throws OntologyServiceException - internal ontoCAT exception
	 */
	private void collectToPrintTreeDownTo(Stack<OntologyTerm> nodes,
			List<Stack<OntologyTerm>> paths,
			Stack<OntologyNode> result, int depth,
			OntologyTerm mainTerm,
			boolean shortPath, boolean stopMergeVal)
	throws OntologyServiceException {
		boolean stopMerge = stopMergeVal;
		//to find and store the same part of the paths by levels
		HashMap<OntologyTerm, Integer> levelTerms =
			new HashMap<OntologyTerm, Integer>();

		int i = 0;    // path id
		for (Stack<OntologyTerm> path : paths) {
			if (!path.empty() && !stopMerge) {
				OntologyTerm next = path.peek();
				//if in different paths on that level the nodes are the same then will store only one node
				//and size will be 1
				levelTerms.put(next, i);
				i++;
			}
		}
		if (levelTerms.size() != 1)
			//nodes on this level are different in different paths
		{
			stopMerge = true;
		}
		else {
			//take the same part of the paths
			levelTerms.clear();
			i = 0;
			for (Stack<OntologyTerm> path : paths) {
				if (!path.empty() && !stopMerge) {
					OntologyTerm next = path.pop();
					levelTerms.put(next, i);
					i++;
				}
			}
		}

		if (levelTerms.size() == 1 && !stopMerge) {
			//process the same part of the paths
			for (OntologyTerm ontologyTerm : levelTerms.keySet()) {

				int pathNr = levelTerms.get(ontologyTerm);

				//OntologyTerm next = levelTerms.
				OntologyTerm temp = null;
				//boolean exclude = false;
				for (OntologyTerm n : nodes) {
					if (n.equals(ontologyTerm) && !paths.get(pathNr).empty()) {
						temp = n;
					}
					else if (!shortPath) {
						result.push(new OntologyNode(depth, n));
					}
				}
				//To ensure that requested term is at the bottom of the tree
				if (temp != null) {
					result.push(new OntologyNode(depth, temp));
					collectToPrintTreeDownTo(orderedStack(os.getChildren(temp), mainTerm),
							paths, result, depth + 1, mainTerm,
							shortPath, stopMerge);
				}
				if (temp == null && paths.get(pathNr).empty() &&
						(!result.contains(new OntologyNode(depth, mainTerm)))) {
					result.push(new OntologyNode(depth, mainTerm));
				}
			}
		}
		else {
			//process different parts of the paths
			for (Stack<OntologyTerm> path : paths) {
				OntologyTerm next = path.pop();
				OntologyTerm temp = null;
				boolean exclude = false;
				for (OntologyTerm n : nodes) {
					if (n.equals(next) && !path.empty()) {
						temp = n;
					}
					else if (!shortPath) {
						//have to exclude node if it is in other path or already is in results
						for (Stack<OntologyTerm> path2 : paths) {
							if (path2.contains(n)) {
								exclude = true;
							}
						}
						if (!exclude && !result.contains(new OntologyNode(depth, n))) {
							result.push(new OntologyNode(depth, n));
						}
					}
				}
				//To ensure that requested term is at the bottom of the tree
				if (temp != null) {
					result.push(new OntologyNode(depth, temp));
					collectToPrintTreeDownTo(orderedStack(os.getChildren(temp), mainTerm),
							path, result, depth + 1, mainTerm,
							shortPath);
				}
				if (temp == null && path.empty() &&
						(!result.contains(new OntologyNode(depth, mainTerm)))) {
					result.push(new OntologyNode(depth, mainTerm));
				}
			}
		}

	}


	/**
	 * Returns stack with term and its parents at the bottom
	 *
	 * @param list of OntologyTerms to create a stack from
	 * @param term - OntologyTerm to create a path to
	 * @return stack of OntologyTerms with term and its parents at the bottom
	 * @throws OntologyServiceException - internal ontoCAT exception
	 */
	private Stack<OntologyTerm> orderedStack(List<OntologyTerm> list,
			OntologyTerm term)
			throws OntologyServiceException {
		List<OntologyTerm> tempListBottom = new ArrayList<OntologyTerm>();
		List<OntologyTerm> tempListTop = new ArrayList<OntologyTerm>();
		Stack<OntologyTerm> orderedStack = new Stack<OntologyTerm>();
		for (OntologyTerm n : list) {
			if (os.getParents(term).contains(n) || term.equals(n)) {
				tempListBottom.add(n);
			}
			else {
				tempListTop.add(n);
			}
		}
		for (OntologyTerm n : tempListTop) {
			orderedStack.push(n);
		}
		for (OntologyTerm n : tempListBottom) {
			orderedStack.push(n);
		}
		return orderedStack;
	}

	/**
	 * Returns all paths to ontology term
	 *
	 * @param term - OntologyTerm to create a path to
	 * @return list of paths represented as stack of OntologyTerms
	 * @throws OntologyServiceException - internal ontoCAT exception
	 */
	private List<Stack<OntologyTerm>> getClassPathToRoot(OntologyTerm term)
	throws OntologyServiceException {

		Stack<Stack<OntologyTerm>> tempStack = new Stack<Stack<OntologyTerm>>();
		Stack<Stack<OntologyTerm>> resultsStack = new Stack<Stack<OntologyTerm>>();

		// Seed the queue with first element
		Stack<OntologyTerm> seed = new Stack<OntologyTerm>();
		seed.add(term);
		tempStack.push(seed);
		do {
			// Pop some path from stack
			Stack<OntologyTerm> path = tempStack.pop();
			//if (path.size() > 50)
			//  throw new Exception("Circular path encountered in " + term);
			// Go through all the parents on top of the stack
			List<OntologyTerm> parents = os.getParents(path.peek());
			if (parents.size() != 0) {
				for (OntologyTerm parent : parents) {
					// Stop at roots or functional roots
					if (isRoot(parent.getAccession())) {    //isEFOBranchRoot
						path.push(parent);
						path.trimToSize();
						resultsStack.push(path);
					}
					else {
						// Create new path for every parent and add to tempStack
						Stack<OntologyTerm> newPath = (Stack<OntologyTerm>) path.clone();
						newPath.push(parent);
						tempStack.push(newPath);
					}
				}
			}
			// Push the path back if reached root
			else {
				path.trimToSize();
				resultsStack.push(path);
			}
		} while (!tempStack.empty());

		resultsStack.trimToSize();
		return resultsStack;
	}

	/**
	 * Simple recursive visualisation of partonomy starting from the top node
	 *
	 * @param currentNode the current node
	 * @param tab         the tab
	 * @throws OntologyServiceException the ontology service exception
	 */
	private void visualisePartonomy(OntologyTerm currentNode, String tab)
	throws OntologyServiceException {
		String partPadding = pad("has_part", "-");
		String isaPadding = pad("", "-");
		String newTab = tab + pad("", " ");

		List<OntologyTerm> isaChildren = getTermChildren(currentNode);
		List<OntologyTerm> partChildren = getTermRelations(currentNode, "has_part");

		// remove has_part children from the asserted isa set
		isaChildren.removeAll(partChildren);

		for (OntologyTerm term : isaChildren) {
			System.out.println(tab + isaPadding + term.getLabel());
			visualisePartonomy(term, newTab);
		}
		for (OntologyTerm term : partChildren) {
			System.out.println(tab + partPadding + term.getLabel());
			visualisePartonomy(term, newTab);
		}
	}

	/**
	 * Simple recursive visualisation of all relationships from the top node NOTE:
	 * this is rather slow
	 *
	 * @param currentNode the current node
	 * @param tab         the tab
	 * @throws OntologyServiceException the ontology service exception
	 */

	private void visualiseAll(OntologyTerm currentNode, String tab)
	throws OntologyServiceException {
		String isaPadding = pad("", "-");
		String newTab = tab + pad("", " ");

		List<OntologyTerm> isaChildren = getTermChildren(currentNode);
		// Note you could use ReasonedOntologyService.getSpecificRelation
		// to focus only on a specific axis (e.g. has_part)
		Map<String, Set<OntologyTerm>> mOtherChildren = os.getRelations(
				currentNode.getOntologyAccession(), currentNode.getAccession());

		// remove isa children inferred by the reasoner
		// under a structure defined specifically to capture a relationship
		// example skeleton structure, or skeleton disease
		// these will be shown with the original relationship in the next step
		for (Set<OntologyTerm> sOtherChildren : mOtherChildren.values()) {
			isaChildren.removeAll(sOtherChildren);
		}

		// print isa children
		for (OntologyTerm term : isaChildren) {
			System.out.println(tab + isaPadding + term.getLabel());
			visualiseAll(term, newTab);
		}
		// print other children
		for (Map.Entry<String, Set<OntologyTerm>> e : mOtherChildren.entrySet()) {
			for (OntologyTerm ot : e.getValue()) {
				System.out.println(tab + pad(e.getKey(), "-") + ot.getLabel());
				visualiseAll(ot, newTab);
			}
		}
	}

	private static String pad(String str, String padChar) {
		StringBuilder padded = new StringBuilder(padChar + padChar + str);
		while (padded.length() < 15) {
			padded.append(padChar);
		}
		return padded.toString();
	}

	/**
	 * Class to store ontology term and term depth in ontology
	 */
	private class OntologyNode implements Comparable {
		final int depth;
		final OntologyTerm term;

		OntologyNode(int depth, OntologyTerm term) {
			this.depth = depth;
			this.term = term;
		}

		@Override
		public String toString() {
			int i = 0;
			String dashes = "";
			while (i != this.depth) {
				i++;
				dashes = dashes + "-";
			}
			String outTerm = "";

			try {
				outTerm = term.getAccession() + " " + term.getLabel();
			}
			catch (Exception e) {
				//do nothing
			}
			return dashes + outTerm;
		}

		private boolean same(Object o1, Object o2) {
			return o1 == null ? o2 == null : o1.equals(o2);
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof OntologyNode)) {
				return false;
			}
			OntologyNode p = (OntologyNode) obj;
			return same(p.depth, this.depth) && same(p.term, this.term);
		}

		public int compareTo(Object o1) {
			if ((this.term.equals(((OntologyNode) o1).term)) &&
					(this.depth == ((OntologyNode) o1).depth)) {
				return 0;
			}
			else if ((this.depth) > ((OntologyNode) o1).depth) {
				return 1;
			}
			else {
				return -1;
			}
		}

	}

}
