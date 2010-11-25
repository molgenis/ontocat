package uk.ac.ebi.ontocat.special;

import junit.framework.TestCase;
import uk.ac.ebi.ontocat.utils.OntologyBatch;
import uk.ac.ebi.ontocat.utils.OntologyParser;

import java.util.List;

public class OntologyBatchTest extends TestCase {
    private OntologyBatch ob;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ob = new OntologyBatch("/Users/nata/IdeaProjects/ontoCAT/ontoCAT-R/inst/extdata");

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        ob = null;
    }

    public void testOntologyBatch() throws Exception {
        System.out.println("ADD NEW ONTOLOGY (EFO)");
        ob.addEFO();
        System.out.println("ADD NEW ONTOLOGY");
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
        System.out.println(op.getAllTermIds());

        /*System.out.println("SEARCH IN LOADED");

    for (OntologyTerm ot : ob.searchTerm("system"))
        System.out.println(ot);

    System.out.println("SEARCH IN ALL");

    for (OntologyTerm ot : ob.searchTermInAll("system"))
        System.out.println(ot);

    System.out.println("SEARCH IN BIOPORTAL");

    for (OntologyTerm ot : ob.searchTermInBioportal("system"))
        System.out.println(ot);*/

        System.out.println("GET ONTOLOGY PARSER FROM BIOPORTAL:");
        OntologyParser op2 = ob.getOntologyParser("1055");
        System.out.println(op2.getRootIds());

        //Takes too long time
        /*System.out.println("SEARCH IN OLS");

        for (OntologyTerm ot : ob.searchTermInOLS("thymus"))
            System.out.println(ot);

        System.out.println("GET ONTOLOGY PARSER FROM OLS:");
        OntologyParser op3 = ob.getOntologyParser("BTO");
        System.out.println(op3.getAllTermIds()); */


    }
}