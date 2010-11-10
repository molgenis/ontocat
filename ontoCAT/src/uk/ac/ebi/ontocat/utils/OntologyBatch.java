package uk.ac.ebi.ontocat.utils;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Methods to work with group of ontologies.
 *  
 * Author: Natalja Kurbatova
 * Date: 2010-09-20
 */
public class OntologyBatch {

    List<OntologyService> osList = new ArrayList<OntologyService>();

    public OntologyBatch(String dirPath) throws OntologyServiceException {

        File dir = new File(dirPath);
        List<File> listOfFiles = new ArrayList<File>();
        String fileName;

        for (int i = 0; i < dir.listFiles().length; i++){

            if (dir.listFiles()[i].isFile()){
                fileName = dir.listFiles()[i].getAbsolutePath();
                if (fileName.endsWith(".owl") || fileName.endsWith(".obo"))
                    listOfFiles.add(new File(fileName));

            }
        }

        for (File file : listOfFiles){

            FileOntologyService os = new FileOntologyService(file.toURI());
            os.setSynonymSlot("alternative_term");
            osList.add(os);
        }

    }



  @SuppressWarnings("serial")
  public Collection<OntologyTerm> searchTermInAll(String text) throws OntologyServiceException, URISyntaxException {

     List<OntologyTerm> result = new ArrayList<OntologyTerm>();
	   // Instantiate a composite service

	   OntologyService los = CompositeDecorator.getService(osList);

	   // Run a search across them all
	   for (OntologyTerm ot : los.searchAll(text))
	    result.add(ot);

    return result;
  }

  public Collection<OntologyTerm> searchTermInBioportal (String text) throws OntologyServiceException {
    List<OntologyTerm> result = new ArrayList<OntologyTerm>();
    // Instantiate Bioportal service
	  OntologyService los = new BioportalOntologyService();

	  // List all available ontologies
    //System.out.println("Available Bioportal ontologies:");
	  for (Ontology o : los.getOntologies()) {
	    //System.out.println(o);
	  }

	  // Find all terms containing string "text"
	  for (OntologyTerm ot : los.searchAll(text, OntologyService.SearchOptions.EXACT,
	                                OntologyService.SearchOptions.INCLUDE_PROPERTIES))
	    result.add(ot);

    return result;
  }

  public Collection<OntologyTerm> searchTermInOLS (String text) throws OntologyServiceException {
    List<OntologyTerm> result = new ArrayList<OntologyTerm>();
    // Instantiate OLS service
	  OntologyService los = new OlsOntologyService();

    //System.out.println("Available OLS ontologies:");
	  // List all available ontologies
	  for (Ontology o : los.getOntologies()) {
	    System.out.println(o);
	  }

	  // Find all terms containing string thymus
	  for (OntologyTerm ot : los.searchAll(text, OntologyService.SearchOptions.EXACT,
	                                OntologyService.SearchOptions.INCLUDE_PROPERTIES))
	    result.add(ot);

    return result;
  }

  public Set<String> listOntologies () throws OntologyServiceException {
    HashSet<String> result = new HashSet<String>();

    //System.out.println("Available OLS ontologies:");
	  // List all available ontologies
	  for (OntologyService os : osList) {
          for (Ontology o: os.getOntologies())
	        result.add("Accession: "+o.getOntologyAccession()+" Description:"+o.getDescription()+" Abbreviation:"+o.getAbbreviation()
            +" Version:"+o.getVersionNumber()+" Release date:"+o.getDateReleased());
	  }

    return result;
  }

  public Set<String> listAccessions () throws OntologyServiceException {
    HashSet<String> result = new HashSet<String>();

    //System.out.println("Available OLS ontologies:");
	  // List all available ontologies
	  for (OntologyService os : osList) {
          for (Ontology o: os.getOntologies())
	        result.add(o.getOntologyAccession());
	  }

    return result;
  }


  public OntologyParser getOntologyParser(String ontologyAccession) throws OntologyServiceException, URISyntaxException {

    for (OntologyService fos: osList){
       Ontology ontology = fos.getOntology(ontologyAccession);
//        System.out.println(ontology.getOntologyAccession());
       if (ontology != null){
           System.out.println("Got:" + ontology.getOntologyAccession());
            return new OntologyParser(); //(FileOntologyService)fos
       }

    }

    return null;

  }

  public Set<String> listOLS () throws OntologyServiceException {
    HashSet<String> result = new HashSet<String>();
    // Instantiate OLS service
	  OntologyService los = new OlsOntologyService();

    //System.out.println("Available OLS ontologies:");
	  // List all available ontologies
	  for (Ontology o : los.getOntologies()) {
	    result.add("Label:"+o.getLabel()+" Abbreviation:"+o.getAbbreviation()
        +" Version:"+o.getVersionNumber()+" Release date:"+o.getDateReleased());
	  }

    return result;
  }

  public Set<String> listBioportal () throws OntologyServiceException {
    HashSet<String> result = new HashSet<String>();
    // Instantiate OLS service
	  OntologyService bos = new BioportalOntologyService();

    //System.out.println("Available OLS ontologies:");
	  // List all available ontologies
	  for (Ontology o : bos.getOntologies()) {
	    result.add("Label:"+o.getLabel()+" Abbreviation:"+o.getAbbreviation()
        +" Version:"+o.getVersionNumber()+" Release date:"+o.getDateReleased());
	  }

    return result;
  }

  /**
    * Searches for text in OLS (Ontology Lookup Service) ontologies
    * @param text words to search
    * @return collection of terms
    */
 /*public Set<String> searchTermInOLS (String text) throws OntologyServiceException {
   Set<String> result = new HashSet<String>();
   // Instantiate OLS service
   OntologyService los = new OlsOntologyService();

   // Find all terms containing string text
   for (OntologyTerm ot : los.searchAll(text, SearchOptions.EXACT,
                                 SearchOptions.INCLUDE_PROPERTIES))
     result.add(ot.getAccession());

   return result;
 }          */

 /**
    * Searches for text in Bioportal  ontologies
    * @param text words to search
    * @return collection of terms
    */
/* public Set<String> searchTermInBioportal (String text) throws OntologyServiceException {

   Set<String> result = new HashSet<String>();
   // Instantiate Bioportal service
   OntologyService los = new BioportalOntologyService();

   // Find all terms containing string text
   for (OntologyTerm ot : los.searchAll(text, SearchOptions.EXACT,
                                 SearchOptions.INCLUDE_PROPERTIES))
     result.add(ot.getAccession());

   return result;
 }*/  
}
