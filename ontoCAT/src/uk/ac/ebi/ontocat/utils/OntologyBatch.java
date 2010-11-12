package uk.ac.ebi.ontocat.utils;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeServiceNoThreads;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Methods to work with group of ontologies from files or URLs and/or with ontologies
 * from OLS or Bioportal.
 * Allows to search for the term within loaded ontologies, BioPortal and/or OLS, list ontologies,
 * create OntologyParser for selected ontology.
 * <p/>
 * Author: Natalja Kurbatova
 * Date: 2010-09-20
 */
public class OntologyBatch {

    private List<OntologyParser> parserList = new ArrayList<OntologyParser>();
    private OntologyService fileService;
    private final Pattern BioportalPattern = Pattern.compile("\\d{4}");
    private final Pattern OLSPattern = Pattern.compile("[A-Z]{3,4}");


    /**
     * Creates instance of OntologyBatch with ontologies from located in directory
     * OWL and OBO formats are supported
     *
     * @param dirPath - path to directory with ontologies
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    @SuppressWarnings("serial")
    public OntologyBatch(String dirPath) throws OntologyServiceException {

        File dir = new File(dirPath);

        if (dir.exists()) {

            for (int i = 0; i < dir.listFiles().length; i++) {

                if (dir.listFiles()[i].isFile()) {
                    String fileName = dir.listFiles()[i].getAbsolutePath();
                    if (fileName.endsWith(".owl") || fileName.endsWith(".obo")) {
                        OntologyParser op = new OntologyParser(fileName);
                        parserList.add(op);
                    }
                }
            }

        } else {
            System.out.println("Sorry, can't create Ontology Batch service: directory " + dirPath + " doesn't exist.");
        }

        if (parserList.size() == 0) {
            System.out.println("Sorry, can't create Ontology Batch service: there are no owl or obo files in directory " + dirPath + ".");
        } else {
            createOntologyService();
        }

    }

    /**
     * Creates instance of Ontology Batch with EFO ontology
     *
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public OntologyBatch() throws OntologyServiceException {
        //add EFO
        OntologyParser op = new OntologyParser();
        parserList.add(op);
        createOntologyService();
    }


    /**
     * Creates ontology service for all loaded ontologies
     *
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    @SuppressWarnings("serial")
    private void createOntologyService() throws OntologyServiceException {

        List<OntologyService> osList = new ArrayList<OntologyService>();

        for (OntologyParser p : parserList)
            osList.add(p.getOntologyService());

        fileService = CompositeServiceNoThreads.getService(osList);
    }

    //* ****************************************************************************************************


    //* METHODS FOR LOADED ONTOLOGIES


    //******************************************************************************************************

    /**
     * Add ontology from file or URL to batch
     *
     * @param pathToOntology - path to ontology (file or URL)
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public void addOntology(String pathToOntology) throws OntologyServiceException {

        OntologyParser op = new OntologyParser(pathToOntology);

        parserList.add(op);

        createOntologyService();
    }

    /**
     * Add EFO ontology to batch
     *
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public void addEFO() throws OntologyServiceException {

        //load EFO
        OntologyParser op = new OntologyParser();

        parserList.add(op);

        createOntologyService();
    }

    /**
     * Returns list of loaded ontologies
     *
     * @return list of loaded ontologies - accessions
     */
    public List<String> listLoadedOntologies() {

        List<String> result = new ArrayList<String>();

        OntologyParser p = parserList.get(1);
        System.out.println(p.getOntologySource());
        // List all available ontologies
        for (OntologyParser op : parserList)
            result.add(op.getOntologyAccession());

        return result;
    }

    /**
     * Returns list of loaded ontologies and sources from where they have been loaded
     *
     * @return list of loaded ontologies - accessions  ans sources
     */
    public List<String> listLoadedOntologiesSources() {

        List<String> result = new ArrayList<String>();

        OntologyParser p = parserList.get(1);
        System.out.println(p.getOntologySource());
        // List all available ontologies
        for (OntologyParser op : parserList)
            result.add("Accession: " + op.getOntologyAccession() + " Source: " + op.getOntologySource());

        return result;
    }

    /**
     * Creates and returns OntologyParser instance
     *
     * @param ontologyAccession - accession of ontology to work with
     * @return instance of OntologyParser to work with particular loaded ontology
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    public OntologyParser getOntologyParser(String ontologyAccession) throws OntologyServiceException {

        if (BioportalPattern.matcher(ontologyAccession).matches()) {
            OntologyService los = new BioportalOntologyService();
            return new OntologyParser(los, ontologyAccession);
        } else if (OLSPattern.matcher(ontologyAccession).matches()) {
            OntologyService los = new OlsOntologyService();
            return new OntologyParser(los, ontologyAccession);
        } else {
            for (OntologyParser op : parserList) {
                if (op.getOntologyAccession().equals(ontologyAccession)) {
                    return op;
                }
            }

        }

        return null;

    }

    //* ****************************************************************************************************


    //* METHODS FOR SEARCH IN OLS and BIOPORTAL. Problems: can't get OntologyParsers for OLS/BioPortal ontologies


    //******************************************************************************************************


    /**
     * Searches for text in OLS (Ontology Lookup Service) ontologies
     *
     * @param text words to search
     * @return list of found terms
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    @SuppressWarnings("serial")
    public List<OntologyTerm> searchTermInOLS(String text) throws OntologyServiceException {
        List<OntologyTerm> result = new ArrayList<OntologyTerm>();
        // Instantiate OLS service
        OntologyService los = new OlsOntologyService();

        for (Ontology o : los.getOntologies()) {
            // Find all terms containing string text
            for (OntologyTerm ot : los.searchOntology(o.getOntologyAccession(), text, OntologyService.SearchOptions.EXACT,
                    OntologyService.SearchOptions.INCLUDE_PROPERTIES))
                result.add(ot);
        }

        return result;
    }


    /**
     * Searches for text in Bioportal  ontologies
     *
     * @param text words to search
     * @return list of found terms
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    @SuppressWarnings("serial")
    public List<OntologyTerm> searchTermInBioportal(String text) throws OntologyServiceException {

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();
        // Instantiate Bioportal service
        OntologyService los = new BioportalOntologyService();

        // Find all terms containing string text
        for (OntologyTerm ot : los.searchAll(text, OntologyService.SearchOptions.EXACT,
                OntologyService.SearchOptions.INCLUDE_PROPERTIES))
            result.add(ot);

        return result;
    }

    /**
     * Searches for text in Bioportal/OLS and loaded ontologies
     *
     * @param text words to search
     * @return list of found terms
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    @SuppressWarnings("serial")
    public List<OntologyTerm> searchTermInAll(String text) throws OntologyServiceException {

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();

        OntologyService fullService = CompositeServiceNoThreads.getService(fileService,
                new BioportalOntologyService(),
                new OlsOntologyService());

        for (OntologyTerm ot : fullService.searchAll(text))
            result.add(ot);

        return result;
    }

    /**
     * Searches for text in loaded ontologies
     *
     * @param text words to search
     * @return list of found terms
     * @throws OntologyServiceException - internal ontoCAT exception
     */
    @SuppressWarnings("serial")
    public List<OntologyTerm> searchTerm(String text) throws OntologyServiceException {

        List<OntologyTerm> result = new ArrayList<OntologyTerm>();

        for (OntologyTerm ot : fileService.searchAll(text))
            result.add(ot);

        return result;
    }


}
