package uk.ac.ebi.ontocat.test.special;

import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import junit.framework.TestCase;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import uk.ac.ebi.ontocat.virtual.CompositeDecorator;

/**
 * Tests retrieval of ontology terms using ontocat
 *
 * @author Tony Burdett
 * @date 16-Mar-2010
 */
public class ZoomaTest extends TestCase {
  private OntologyService compositeService;

  private String query;

  private URI efoURI;

  protected void setUp() throws Exception {
    super.setUp();

    try {
      OntologyService ols = new OlsOntologyService();
      OntologyService bioportal = new BioportalOntologyService();

      List<OntologyService> services = new ArrayList<OntologyService>();
			// services.add(ols);
      services.add(bioportal);

      this.compositeService = CompositeDecorator.getService(services);
    }
    catch (OntologyServiceException e) {
      e.printStackTrace();
      fail();
    }

    this.query = "cyclosporin A";

    this.efoURI = URI.create("http://www.ebi.ac.uk/efo/");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    this.compositeService = null;
  }

  public void testFetchTerms() {
    long start = System.currentTimeMillis();

    // aggregate resulting terms
    try {
      List<OntologyTerm> terms = retrieveTermsUsingService(compositeService);

      long end = System.currentTimeMillis();
      String total =
          new DecimalFormat("#.##").format(((float) (end - start)) / 1000);
      System.out.println(
          "Fetched " + terms.size() + " terms in " + total + "s. from OntoCAT");
    }
    catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  public void testFetchOntology() {
    String ontologyAccession = "";
    try {
      Map<OntologyService, List<OntologyTerm>> serviceToTerms =
          new HashMap<OntologyService, List<OntologyTerm>>();
      serviceToTerms
          .put(compositeService, retrieveTermsUsingService(compositeService));
      for (OntologyService os : serviceToTerms.keySet()) {
        for (OntologyTerm ot : serviceToTerms.get(os)) {
          System.out.println("Next term: " + ot);
          ontologyAccession = ot.getOntologyAccession();
          Ontology ontology = null;
          try {
            ontology =
                retrieveOntologyUsingService(compositeService,
                                             ontologyAccession);
          }
          catch (OntologyServiceException e) {
            e.printStackTrace();
            fail("Failed to get ontology from composite service for " + ot +
                " (from " + os + ")");
          }
          assertNotNull("Ontology is null", ontology);
        }
      }
    }
    catch (OntologyServiceException e) {
      e.printStackTrace();
      fail("Unable to find ontology '" + ontologyAccession +
          "' using OntoCAT");
    }
  }

  public void testGetOntology() {
    try {
      List<OntologyTerm> terms = retrieveTermsUsingService(compositeService);
      for (OntologyTerm term : terms) {
        Ontology ontology = term.getOntology();
				assertEquals("Ontology doesn't match accession",
                   term.getOntologyAccession(),
                   ontology.getOntologyAccession());
      }
    }
    catch (OntologyServiceException e) {
      e.printStackTrace();
      fail();
    }
  }

  public void testFetchInParallel() {
    ExecutorService service = Executors.newFixedThreadPool(8);

    List<Future<OntologyService>> tasks =
        new ArrayList<Future<OntologyService>>();
    for (int i = 0; i < 8; i++) {
      tasks.add(service.submit(new Callable<OntologyService>() {

        public OntologyService call() throws Exception {
          return new FileOntologyService(efoURI);
        }
      }));
    }

    for (Future<OntologyService> task : tasks) {
      try {
        OntologyService os = task.get();
        assertNotNull("Ontology Service is null", os);

        for (Ontology ont : os.getOntologies()) {
          System.out.println("Next ontology: " + ont.getOntologyAccession());
        }
      }
      catch (Exception e) {
        e.printStackTrace();
        fail();
      }
    }
  }

  public void testFetchForTerm() {
    try {


      System.out.println("Creating service...");
      OntologyService efoService = new FileOntologyService(efoURI);
      System.out.println("Service acquired!");

      List<OntologyTerm> results = new ArrayList<OntologyTerm>();
      System.out.println("Searching...");
      List<OntologyTerm> serviceTerms = efoService.searchAll("metastasis");
      System.out.println("Got terms ok");

      // iterate over fetched terms
      if (serviceTerms != null) {
        for (OntologyTerm serviceTerm : serviceTerms) {
          // create an OLS context object for each term


          // and, add the term to our set of results
          results.add(serviceTerm);
        }
      }

      for (OntologyTerm term : results) {
        System.out.println("Next term: " + term);
      }
    }
    catch (OntologyServiceException e) {
      e.printStackTrace();
      fail();
    }
  }

  private List<OntologyTerm> retrieveTermsUsingService(OntologyService service)
      throws OntologyServiceException {
    List<OntologyTerm> results = new ArrayList<OntologyTerm>();
    List<OntologyTerm> serviceTerms =
        service.searchAll(query, OntologyService.SearchOptions.EXACT);
    // iterate over fetched terms
    if (serviceTerms != null) {
      for (OntologyTerm serviceTerm : serviceTerms) {
        results.add(serviceTerm);
      }
    }
    else {
      System.out.println("No terms!");
    }

    return results;
  }

  private Ontology retrieveOntologyUsingService(OntologyService service,
                                                String ontologyAccession)
      throws OntologyServiceException {
    System.out.println("Fetching ontology by accession " + ontologyAccession);
    long start = System.currentTimeMillis();
    Ontology ontology = service.getOntology(ontologyAccession);
    long end = System.currentTimeMillis();
    String total =
        new DecimalFormat("#.##").format(((float) (end - start)) / 1000);
    System.out.println(
        "Acquired ontology " + ontology.getLabel() + " in " + total + "s.");
    return ontology;
  }
}
