package uk.ac.ebi.ontocat.special;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

import java.net.URI;
import java.util.*;

/**
 * Methods currently excluded from the OntologyParser and OntologyBatch.
 *
 * Author: Natalja Kurbatova
 * Date: 2010-09-20
 */
public class ExcludedMethods {
    private FileOntologyService os;
    private Ontology ontology;

    public ExcludedMethods(){
        //default is EFO last version
   try{

     // Instantiate a FileOntologyService
     os = new FileOntologyService(new URI(
     "http://efo.svn.sourceforge.net/viewvc/efo/trunk/src/efoinowl/InferredEFOOWLview/EFO_inferred.owl"));

     // Use a non-SKOS annotation for synonyms
     for (Ontology ot :  os.getOntologies()){
       ontology = ot;
      // if (ontology.getOntologyAccession().equals("http://www.ebi.ac.uk/efo/efo.owl"))
        //  ontology.setOntologyAccession("http://www.ebi.ac.uk/efo");
     }


     os.setSynonymSlot("alternative_term");
   }
   catch(Exception e){
     System.out.println("Sorry, OntologyParser for EFO http://efo.svn.sourceforge.net/viewvc/efo/trunk/src/efoinowl/InferredEFOOWLview/EFO_inferred.owl can't be created.");
   }
}


    private void collectTreeDownTo(Iterable<OntologyTerm> nodes, Stack<OntologyTerm> path,
                                List<OntologyTerm> result, int depth) throws OntologyServiceException {

       OntologyTerm next = path.pop();
       for(OntologyTerm n : nodes) {
            result.add(n);
           if(n.equals(next) && !path.empty())
               collectTreeDownTo(os.getChildren(n), path, result, depth + 1);
       }
}
    private void collectSubTree(OntologyTerm currentNode, List<OntologyTerm> result,
                             List<OntologyTerm> pathres, Set<String> allNodes,
                             Set<String> visited, int depth, boolean printing)
     throws OntologyServiceException{
       if(printing && !allNodes.contains(currentNode.getAccession())) {
           printing = false;
       }

       boolean started = false;
       if(!printing && allNodes.contains(currentNode.getAccession()) && !visited.contains(currentNode.getAccession()))
       {
           printing = true;
           pathres = new ArrayList<OntologyTerm>();
           started = true;
       }

       if(printing) {
           pathres.add(currentNode);//newTerm(currentNode, depth)
           visited.add(currentNode.getAccession());
           for (OntologyTerm child : os.getChildren(currentNode))
               collectSubTree(child, result, pathres, allNodes, visited, depth + 1, true);
       } else {
           for (OntologyTerm child : os.getChildren(currentNode))
               collectSubTree(child, result, null, allNodes, visited, 0, false);
       }

       if(started) {
           result.addAll(pathres);
       }
}

    /**
   * Creates flat subtree representation ordered in natural print order,
   * each self-contained sub-tree starts from depth=0
   * @param ids marked IDs
   * @return list of Term's
   */
public List<OntologyTerm> getSubTree(Set<String> ids) throws OntologyServiceException{

       List<OntologyTerm> result = new ArrayList<OntologyTerm>();
       Set<String> visited = new HashSet<String>();

       for(OntologyTerm root : os.getRootTerms(ontology.getOntologyAccession())) {
           collectSubTree(root, result, null, ids, visited, 0, false);
       }

       return result;
}

    /**
   * Creates flat subtree representation of tree "opened" down to specified node,
   * hence displaying all its parents first and then a tree level, containing specified node
   * @param id term id
   * @return list of Term's
   */
public List<OntologyTerm> getListDownTo(String id) throws OntologyServiceException{

       List<OntologyTerm> result = new ArrayList<OntologyTerm>();

       Stack<OntologyTerm> path = new Stack<OntologyTerm>();
       OntologyTerm node = os.getTerm(id);
       while(true) {
           path.push(node);
           if(os.getParents(node).isEmpty())
               break;
           node = os.getParents(node).get(0);
       }
       collectTreeDownTo(os.getRootTerms(ontology), path, result, 0);

       return result;
}


    public void printTreeDownTo(String id) throws Exception {

        List<Stack<OntologyTerm>> pathStack = getClassPathToRoot(os.getTerm(id));

        Iterator paths = pathStack.iterator();
        while (paths.hasNext()) {
            Stack<OntologyTerm> path = (Stack<OntologyTerm>) paths.next();
            System.out.println("Path:" + path.size());
            Stack<OntologyNode> result = new Stack<OntologyNode>();
            collectToPrintTreeDownTo(orderedStack(this.getRoots(), os.getTerm(id)), path, result, 0, os.getTerm(id), false);
            //print Tree
            for (OntologyNode n : result) {
                System.out.println(n);
            }

        }


    }

     private void collectToPrintTreeDownTo(Stack<OntologyTerm> nodes, Stack<OntologyTerm> path,
                                          Stack<OntologyNode> result, int depth, OntologyTerm mainTerm, boolean shortPath) throws OntologyServiceException {

        OntologyTerm next = path.pop();
        OntologyTerm temp = null;
        boolean exclude = false;
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
     * Returns list of root terms
     *
     * @return list of terms
     */
    public List<OntologyTerm> getRoots() throws OntologyServiceException {

        return os.getRootTerms(ontology.getOntologyAccession());

    }

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
     * @return List of paths represented as Stack of OntologyTerms
     */
    private List<Stack<OntologyTerm>> getClassPathToRoot(OntologyTerm term) throws Exception {

        Stack<Stack<OntologyTerm>> tempStack = new Stack<Stack<OntologyTerm>>();
        Stack<Stack<OntologyTerm>> resultsStack = new Stack<Stack<OntologyTerm>>();

        // Seed the queue with first element
        Stack<OntologyTerm> seed = new Stack<OntologyTerm>();
        seed.add(term);
        tempStack.push(seed);
        do {
            // Pop some path from stack
            Stack<OntologyTerm> path = tempStack.pop();
            if (path.size() > 50)
                throw new Exception("Circular path encountered in " + term);
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
     * Returns if term is root ontology term
     *
     * @param accession of term
     * @return if term is root ontology term
     */
    public boolean isRoot(String accession) throws OntologyServiceException {

        boolean result = false;
        OntologyTerm term = os.getTerm(accession);
        if (term == null) return false;
        for (OntologyTerm ot : os.getRootTerms(ontology.getOntologyAccession()))
            if (ot.equals(term))
                result = true;

        return result;
    }

    /**
     * Class to store ontology term and term depth inside of the ontology  *
     */
    private class OntologyNode implements Comparable {
        protected int depth;
        protected OntologyTerm term;

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

