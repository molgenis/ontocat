package uk.ac.ebi.ontocat.utils;

import org.apache.commons.validator.UrlValidator;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.virtual.LocalisedFileService;

import java.io.File;
import java.net.URI;
import java.util.*;


/**
 * Methods to parse ontology: search terms, get children and parents of term,
 * show hierarchy and paths to term, get info about ontology.
 *
 * Author: Natalja Kurbatova
 * Date: 2010-09-20
 */
public class OntologyParser {


    private OntologyService os;
    private String ontologySource;
    private String ontologyAccession;


    //* ****************************************************************************************************


    //* OntologyParser CONSTRUCTORS


    //******************************************************************************************************

    /**
     * Creates instance of OntologyParser for EFO
     */
    public OntologyParser() {

        //default is EFO last version
        try {

            // Instantiate a FileOntologyService
            os = LocalisedFileService.getService(new FileOntologyService(new URI(
                    "http://efo.svn.sourceforge.net/viewvc/efo/trunk/src/efoinowl/InferredEFOOWLview/EFO_inferred.owl")));

            ontologySource = "http://efo.svn.sourceforge.net/viewvc/efo/trunk/src/efoinowl/InferredEFOOWLview/EFO_inferred.owl";

            // Use a non-SKOS annotation for synonyms
            for (Ontology ot : os.getOntologies())
                ontologyAccession = ot.getOntologyAccession();//ontology = ot;

            ((FileOntologyService) os).setSynonymSlot("alternative_term");
        }
        catch (Exception e) {
            System.out.println("Sorry, OntologyParser for EFO http://efo.svn.sourceforge.net/viewvc/efo/trunk/src/efoinowl/InferredEFOOWLview/EFO_inferred.owl can't be created.");
        }

    }

    /**
     * Creates instance of OntologyParser from provided file or URL
     * OWL and OBO formats are supported
     *
     * @param pathToOntology words to search
     */
    public OntologyParser(String pathToOntology) {

        boolean correctResourse = false;

        try {

            File file = new File(pathToOntology);
            URI uri = null;

            if (file.exists()) {
                uri = file.toURI();
                correctResourse = true;
            } else {
                UrlValidator urlValidator = new UrlValidator();
                if (urlValidator.isValid(pathToOntology)) {
                    uri = new URI(pathToOntology);
                    correctResourse = true;
                }
            }

            if (correctResourse) {
                ontologySource = pathToOntology;

                // Instantiate a FileOntologyService
                os = LocalisedFileService.getService(new FileOntologyService(uri));
                // Use a non-SKOS annotation for synonyms

                for (Ontology ot : os.getOntologies()) {
                    ontologyAccession = ot.getOntologyAccession();//ontology = ot;
                }

                ((FileOntologyService) os).setSynonymSlot("alternative_term");

            } else
                System.out.println("Sorry, OntologyParser for " + pathToOntology + " can't be created. " +
                        "The ontology file doesn't exist.");


        }
        catch (Exception e) {
            System.out.println("Sorry, OntologyParser for " + pathToOntology + " can't be created.");
        }

    }

    /**
     * Creates instance of OntologyParser for OntologyService (OLS Bioportal) and ontology from
     * these resources defined by ontologyAccession String
     *
     * @param os                - ontology service to work with
     * @param ontologyAccession - ontology accession to work with
     */
    public OntologyParser(OntologyService os, String ontologyAccession) {
        this.os = os;
        this.ontologyAccession = ontologyAccession;
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
     * @return list of OntologyTerm - terms in ontology that have this word in label
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<OntologyTerm> searchTerm(String text) throws OntologyServiceException {

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();

        for (OntologyTerm ot : os.searchAll(text))
            result.add(ot);

        return result;
    }

    /**
     * Searches for term prefix in ontology
     *
     * @param prefix prefix to search
     * @return list of OntologyTerms - terms in ontology that have required prefix in label
     *         or synonyms
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<OntologyTerm> searchTermPrefix(String prefix) throws OntologyServiceException {

        String lprefix = prefix.toLowerCase();

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();

        for (OntologyTerm ot : os.getAllTerms(ontologyAccession)) {
            if (ot.getLabel().toLowerCase().startsWith(lprefix) || ot.getAccession().toLowerCase().startsWith(lprefix)) {
                result.add(ot);
            } else for (String alt : os.getSynonyms(ot))
                if (alt.toLowerCase().startsWith(lprefix)) {
                    result.add(ot);
                    break;
                }
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
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<OntologyTerm> getAllTerms() throws OntologyServiceException {
        List<OntologyTerm> result = new ArrayList<OntologyTerm>();


        for (OntologyTerm ot : os.getAllTerms(ontologyAccession))
            result.add(ot);

        return result;


    }

    /**
     * Returns list of all term accessions in ontology
     *
     * @return list of Strings - all term accessions in ontology
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<String> getAllTermIds() throws OntologyServiceException {

        List<String> result = new ArrayList<String>();

        for (OntologyTerm ot : os.getAllTerms(ontologyAccession))
            result.add(ot.getAccession());

        return result;
    }

    //* ****************************************************************************************************


    //* GET METHODS FOR ROOT TERMS


    //******************************************************************************************************

    /**
     * Returns list of root node accessions
     *
     * @return list of Strings - root node accessions
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<String> getRootIds() throws OntologyServiceException {

        List<String> result = new ArrayList<String>();

        for (OntologyTerm n : os.getRootTerms(ontologyAccession)) {
            result.add(n.getAccession());
        }
        return result;
    }

    /**
     * Returns list of root terms
     *
     * @return list of OntologyTerms - root terms
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<OntologyTerm> getRoots() throws OntologyServiceException {

        return os.getRootTerms(ontologyAccession);

    }

    /**
     * EFO specific method. Returns list of branch root accessions. Method specific for EFO ontology
     *
     * @return list of Strings - EFO branch root accessions
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<String> getEFOBranchRootIds() throws OntologyServiceException {

        List<String> result = new ArrayList<String>();

        for (OntologyTerm n : os.getAllTerms(ontologyAccession)) {
            if (isEFOBranchRoot(n.getAccession()))
                result.add(n.getAccession());
        }
        return result;
    }

    /**
     * EFO specific method. Returns list of branch root accessions. Method specific for EFO ontology
     *
     * @return list of OntologyTerms - EFO branch root terms
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<OntologyTerm> getEFOBranchRoots() throws OntologyServiceException {

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();

        for (OntologyTerm n : os.getAllTerms(ontologyAccession)) {
            if (isEFOBranchRoot(n.getAccession()))
                result.add(n);
        }
        return result;
    }

    //* ****************************************************************************************************


    //* GET METHODS FOR TERM CHILDREN AND PARENTS


    //******************************************************************************************************


    /**
     * Returns list of term's direct children
     *
     * @param accession - term accession
     * @return list of OntologyTerms - direct child terms of the term
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<OntologyTerm> getTermChildren(String accession) throws OntologyServiceException {

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();

        OntologyTerm term = os.getTerm(accession);
        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
            return result;
        }

        for (OntologyTerm ot : os.getChildren(term))
            result.add(ot);

        return result;

    }

    /**
     * Returns list of term's direct parents
     *
     * @param accession - term accession
     * @return list of OntologyTerms - direct parent terms of the term
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<OntologyTerm> getTermParents(String accession) throws OntologyServiceException {

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();

        OntologyTerm term = os.getTerm(accession);
        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
            return result;
        }
        for (OntologyTerm ot : os.getParents(term))
            result.add(ot);

        return result;
    }

    /**
     * Returns list of term's all children
     *
     * @param accession - term accession
     * @return list of OntologyTerms - all term children
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<OntologyTerm> getAllTermChildren(String accession) throws OntologyServiceException {

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();

        OntologyTerm term = os.getTerm(accession);
        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in ontology.");
            return result;
        }
        for (OntologyTerm ot : os.getAllChildren(ontologyAccession, term.getAccession()))
            result.add(ot);
        return result;

    }

    /**
     * Returns list of term's all parents
     *
     * @param accession - term accession
     * @return list of OntologyTerms - all term parents
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<OntologyTerm> getAllTermParents(String accession) throws OntologyServiceException {

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();

        OntologyTerm term = os.getTerm(accession);
        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
            return result;
        }
        for (OntologyTerm ot : os.getAllParents(ontologyAccession, term.getAccession()))
            result.add(ot);
        return result;
    }

    /**
     * Returns list of accessions of node itself and all its children recursively
     *
     * @param accession - term accession
     * @return list of accessions, empty if term is not found
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<String> getTermAndAllChildrenIds(String accession) throws OntologyServiceException {
        OntologyTerm term = os.getTerm(accession);
        List<String> result = new ArrayList<String>();

        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
            return result;
        }
        //List<String> ids = new ArrayList<String>(term == null ? 0 : os.getChildren(term).size());


        for (OntologyTerm child : os.getAllChildren(ontologyAccession, term.getAccession()))
            result.add(child.getAccession());

        result.add(term.getAccession());


        return result;
    }


    //* ****************************************************************************************************


    //* GET METHODS FOR TERM: TERM BY ITSELF, NAME, DEFINITIONS, SYNONYMS


    //******************************************************************************************************


    /**
     * Fetch term by accession
     *
     * @param accession - term accession
     * @return OntologyTerm - external term representation
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public OntologyTerm getTermById(String accession) throws OntologyServiceException {

        OntologyTerm term = os.getTerm(accession);

        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");

        }

        return term;

    }


    /**
     * Returns term label by accession
     *
     * @param accession - term accession
     * @return String - term label
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public String getTermNameById(String accession) throws OntologyServiceException {

        String result = "";

        OntologyTerm term = os.getTerm(accession);
        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
            return result;
        }

        result = term.getLabel();

        return result;
    }

    /**
     * Returns list of term's definitions if there are some
     *
     * @param accession - term accession
     * @return list of Strings - term's definitions
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<String> getTermDefinitionsById(String accession) throws OntologyServiceException {

        List<String> result = new ArrayList<String>();

        OntologyTerm term = os.getTerm(accession);
        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
            return result;
        }

        for (String definition : os.getDefinitions(term))
            result.add(definition);

        return result;
    }

    /**
     * Returns list of term's synonyms if there are some
     *
     * @param accession - term accession
     * @return list of Strings - term's synonyms
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<String> getTermSynonymsById(String accession) throws OntologyServiceException {

        List<String> result = new ArrayList<String>();

        OntologyTerm term = os.getTerm(accession);
        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
            return result;
        }

        for (String synonym : os.getSynonyms(term))
            result.add(synonym);

        return result;
    }


    /**
     * Returns term label by accession
     *
     * @param term - OntologyTerm og interest
     * @return String - term label
     */
    public String getTermName(OntologyTerm term) {

        String result = "";

        if (term == null) {
            System.out.println("Sorry, term is not found in the ontology.");
            return result;
        }

        result = term.getLabel();

        return result;
    }

    /**
     * Returns list of term's definitions if there are some
     *
     * @param term - OntologyTerm of interest
     * @return list of Strings - term's definitions
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<String> getTermDefinitions(OntologyTerm term) throws OntologyServiceException {

        List<String> result = new ArrayList<String>();

        if (term == null) {
            System.out.println("Sorry, term is not found in the ontology.");
            return result;
        }

        for (String definition : os.getDefinitions(term))
            result.add(definition);

        return result;
    }

    /**
     * Returns list of term's synonyms if there are some
     *
     * @param term - OntologyTerm of interest
     * @return list of Strings - term's synonyms
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public List<String> getTermSynonyms(OntologyTerm term) throws OntologyServiceException {

        List<String> result = new ArrayList<String>();

        if (term == null) {
            System.out.println("Sorry, term is not found in the ontology.");
            return result;
        }

        for (String synonym : os.getSynonyms(term))
            result.add(synonym);

        return result;
    }


    //* ****************************************************************************************************


    //* CHECK METHODS


    //******************************************************************************************************

    /**
     * Checks if term is in ontology
     *
     * @param accession - term accession
     * @return true if term with provided accession is in ontology
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public boolean hasTerm(String accession) throws OntologyServiceException {

        OntologyTerm node = os.getTerm(accession);

        return node != null;
    }


    /**
     * Returns if term is root ontology term
     *
     * @param accession - term accession
     * @return true if term is root ontology term
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public boolean isRoot(String accession) throws OntologyServiceException {

        boolean result = false;
        OntologyTerm term = os.getTerm(accession);
        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
            return false;
        }
        for (OntologyTerm ot : os.getRootTerms(ontologyAccession))
            if (ot.equals(term))
                result = true;

        return result;
    }

    /**
     * Returns if term is root ontology term
     *
     * @param term - OntologyTerm of interest
     * @return true if term is root ontology term
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public boolean isRoot(OntologyTerm term) throws OntologyServiceException {
        if (term != null && term.getAccession() != null)
            return isRoot(term.getAccession());
        else {
            System.out.println("Sorry, term is not found in the ontology.");
            return false;
        }
    }

    /**
     * EFO specific method. Returns true if term is branch root
     *
     * @param accession - term accession
     * @return true if term is EFO branch root term
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public boolean isEFOBranchRoot(String accession) throws OntologyServiceException {

        OntologyTerm term = os.getTerm(accession);
        if (term == null) {
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
            return false;
        } else {
            List<String> propBranchClass = os.getAnnotations(term).get("branch_class");

            return propBranchClass != null && propBranchClass.size() > 0
                    && propBranchClass.get(0).equalsIgnoreCase("true");

        }

    }

    /**
     * EFO specific method. Returns true if term is branch root
     *
     * @param term - OntologyTerm of interest
     * @return true if term is EFO branch root term
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public boolean isEFOBranchRoot(OntologyTerm term) throws OntologyServiceException {

        if (term != null && term.getAccession() != null)
            return isEFOBranchRoot(term.getAccession());
        else {
            System.out.println("Sorry, term is not found in the ontology.");
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
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public void showHierarchyDownToTerm(String accession) throws OntologyServiceException {

        OntologyTerm term = os.getTerm(accession);

        if (term == null)
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
        else {
            List<Stack<OntologyTerm>> pathStack = getClassPathToRoot(term);

            Stack<OntologyNode> result = new Stack<OntologyNode>();
            collectToPrintTreeDownTo(orderedStack(this.getRoots(), os.getTerm(accession)), pathStack, result, 0, os.getTerm(accession), false, false);

            for (OntologyNode n : result) {
                System.out.println(n);
            }
        }
    }

    /**
     * Prints out flat sub-tree representation
     *
     * @param term - OntologyTerm of interest
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public void showHierarchyDownToTerm(OntologyTerm term) throws Exception {

        if (term == null || term.getAccession() == null)
            System.out.println("Sorry, term is not found in the ontology.");
        else {
            showHierarchyDownToTerm(term.getAccession());
        }
    }

    /**
     * Prints paths to term
     *
     * @param accession of term
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public void showPathsToTerm(String accession) throws OntologyServiceException {
        OntologyTerm term = os.getTerm(accession);

        if (term == null)
            System.out.println("Sorry, term '" + accession + "' is not found in the ontology.");
        else {
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
    }

    /**
     * Prints paths to term
     *
     * @param term - OntologyTerm of interest
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public void showPathsToTerm(OntologyTerm term) throws OntologyServiceException {

        if (term == null || term.getAccession() == null)
            System.out.println("Sorry, term is not found in the ontology.");
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
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public String getOntologyDescription() throws OntologyServiceException {

        String result;
        Ontology ontology = os.getOntology(ontologyAccession);

        result = "Accession: " + ontology.getOntologyAccession();
        result = result + " Version: " + ontology.getVersionNumber();
        result = result + " Label: " + ontology.getLabel();
        result = result + " Abbreviation: " + ontology.getAbbreviation();
        result = result + " Description: " + ontology.getDescription();
        result = result + " Release date: " + ontology.getDateReleased();

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


    //* PRIVATE METHODS AND CLASSES


    //******************************************************************************************************

    /**
     * Method to collect all term children and their depths for one path
     *
     * @param nodes     - current nodes under inspection
     * @param path      - path to mainTerm
     * @param result    - stack with results
     * @param depth     - current depth in ontology, will be assign to created nodes
     * @param mainTerm  - term to collect tree down to
     * @param shortPath - show all neighbour terms to inspected one or not
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    private void collectToPrintTreeDownTo(Stack<OntologyTerm> nodes, Stack<OntologyTerm> path,
                                          Stack<OntologyNode> result, int depth, OntologyTerm mainTerm, boolean shortPath) throws OntologyServiceException {

        OntologyTerm next = path.pop();
        OntologyTerm temp = null;
        //boolean exclude = false;
        for (OntologyTerm n : nodes) {
            if (n.equals(next) && !path.empty())
                temp = n;
            else if (!shortPath)
                result.push(new OntologyNode(depth, n));
        }
        //To ensure that requested term is at the bottom of the tree
        if (temp != null) {
            result.push(new OntologyNode(depth, temp));
            collectToPrintTreeDownTo(orderedStack(os.getChildren(temp), mainTerm), path, result, depth + 1, mainTerm, shortPath);
        }
        if (temp == null && path.empty() && (!result.contains(new OntologyNode(depth, mainTerm))))
            result.push(new OntologyNode(depth, mainTerm));
    }

    /**
     * Method to collect all term children and their depths for multiple paths
     *
     * @param nodes        - current nodes under inspection
     * @param paths        - paths to mainTerm
     * @param result       - stack with results
     * @param depth        - current depth in ontology, will be assign to created nodes
     * @param mainTerm     - term to collect tree down to
     * @param shortPath    - show all neighbour terms to inspected one or not
     * @param stopMergeVal - common tree for all paths has been found or not
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    private void collectToPrintTreeDownTo(Stack<OntologyTerm> nodes, List<Stack<OntologyTerm>> paths,
                                          Stack<OntologyNode> result, int depth, OntologyTerm mainTerm, boolean shortPath, boolean stopMergeVal) throws OntologyServiceException {
        boolean stopMerge = stopMergeVal;
        //to find and store the same part of the paths by levels
        HashMap<OntologyTerm, Integer> levelTerms = new HashMap<OntologyTerm, Integer>();

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
            stopMerge = true;
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
                    if (n.equals(ontologyTerm) && !paths.get(pathNr).empty())
                        temp = n;
                    else if (!shortPath)
                        result.push(new OntologyNode(depth, n));
                }
                //To ensure that requested term is at the bottom of the tree
                if (temp != null) {
                    result.push(new OntologyNode(depth, temp));
                    collectToPrintTreeDownTo(orderedStack(os.getChildren(temp), mainTerm), paths, result, depth + 1, mainTerm, shortPath, stopMerge);
                }
                if (temp == null && paths.get(pathNr).empty() && (!result.contains(new OntologyNode(depth, mainTerm))))
                    result.push(new OntologyNode(depth, mainTerm));
            }
        } else {
            //process different parts of the paths
            for (Stack<OntologyTerm> path : paths) {
                OntologyTerm next = path.pop();
                OntologyTerm temp = null;
                boolean exclude = false;
                for (OntologyTerm n : nodes) {
                    if (n.equals(next) && !path.empty())
                        temp = n;
                    else if (!shortPath) {
                        //have to exclude node if it is in other path or already is in results
                        for (Stack<OntologyTerm> path2 : paths) {
                            if (path2.contains(n)) exclude = true;
                        }
                        if (!exclude && !result.contains(new OntologyNode(depth, n)))
                            result.push(new OntologyNode(depth, n));
                    }
                }
                //To ensure that requested term is at the bottom of the tree
                if (temp != null) {
                    result.push(new OntologyNode(depth, temp));
                    collectToPrintTreeDownTo(orderedStack(os.getChildren(temp), mainTerm), path, result, depth + 1, mainTerm, shortPath);
                }
                if (temp == null && path.empty() && (!result.contains(new OntologyNode(depth, mainTerm))))
                    result.push(new OntologyNode(depth, mainTerm));
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
    private Stack<OntologyTerm> orderedStack(List<OntologyTerm> list, OntologyTerm term) throws OntologyServiceException {
        List<OntologyTerm> tempListBottom = new ArrayList<OntologyTerm>();
        List<OntologyTerm> tempListTop = new ArrayList<OntologyTerm>();
        Stack<OntologyTerm> orderedStack = new Stack<OntologyTerm>();
        for (OntologyTerm n : list) {
            if (os.getParents(term).contains(n) || term.equals(n))
                tempListBottom.add(n);
            else
                tempListTop.add(n);
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
    private List<Stack<OntologyTerm>> getClassPathToRoot(OntologyTerm term) throws OntologyServiceException {

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
                    } else {
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
     * Class to store ontology term and term depth in ontology
     */
    private class OntologyNode implements Comparable {
        final int depth;
        final OntologyTerm term;

        OntologyNode(int depth, OntologyTerm term) {
            this.depth = depth;
            this.term = term;
        }

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

        public boolean equals(Object obj) {
            if (!(obj instanceof OntologyNode))
                return false;
            OntologyNode p = (OntologyNode) obj;
            return same(p.depth, this.depth) && same(p.term, this.term);
        }

        public int compareTo(Object o1) {
            if ((this.term.equals(((OntologyNode) o1).term)) && (this.depth == ((OntologyNode) o1).depth))
                return 0;
            else if ((this.depth) > ((OntologyNode) o1).depth)
                return 1;
            else
                return -1;
        }

    }

}
