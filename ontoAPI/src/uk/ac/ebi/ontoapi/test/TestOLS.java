package uk.ac.ebi.ontoapi.test;

import java.util.List;
import java.util.Map;

import uk.ac.ebi.ontoapi.Ontology;
import uk.ac.ebi.ontoapi.OntologyService;
import uk.ac.ebi.ontoapi.OntologyServiceException;
import uk.ac.ebi.ontoapi.OntologyTerm;
import uk.ac.ebi.ontoapi.ols.OlsOntologyTerm;
import uk.ac.ebi.ontoapi.ols.OlsOntologyService;


public class TestOLS {
		
	public static void main(String[] args) {
		try {

			OntologyService service = new OlsOntologyService();
			
			System.out.println("\nlist ontologies:");
			System.out.println(service.getOntologies().size());
			printOntology(service.getOntologies());
//		
//			System.out.println("\nlist root items:");
//			printTerm(service.getRootTerms("GO"));
//			
//			System.out.println("\nsearch ontology:");
//			printTerm(service.searchOntology("IMR", "alzheimer"));
//			
//			System.out.println("\nsearch all:");
//			printTerm(service.searchAll("alzheimer"));
//			
//			String ontologyAccession = "IMR";
//			System.out.println("\nget path for IMR:0000361:");
//			printTerm(service.getTermPath(ontologyAccession,"IMR:0000361"));
//			
//			System.out.println("\nget direct child terms for IMR:0000361:");
//			printTerm(service.getChildren(ontologyAccession,"IMR:0000361"));
//			
//			System.out.println("\nget direct parent terms for IMR:0000361:");
//			printTerm(service.getParents(ontologyAccession,"IMR:0000361"));
			
			System.out.println("\nget complete annotations:");
			System.out.println( service.getTerm( "GO:0003674" ).toString());
			System.out.println( service.getTerm( "MI:0362" ).toString());
	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printOntology(List<Ontology> ontologies) throws OntologyServiceException
	{
		for(Ontology t: ontologies) System.out.println(t);
	}
	
	public static void printTerm(List<OntologyTerm> terms) throws OntologyServiceException
	{
		for(OntologyTerm t: terms) System.out.println(t);
	}
}
