package uk.ac.ebi.ontocat.examples;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

public class Sandbox {

	/**
	 * @param args
	 * @throws URISyntaxException
	 * @throws OntologyServiceException
	 */
	public static void main(String[] args) throws URISyntaxException,
			OntologyServiceException {
		OntologyService os = new OlsOntologyService();
		
		
		for (Entry<String, Set<OntologyTerm>> entry : os.getRelations("GO", "GO:0070469").entrySet()){
			System.out.println(entry.getKey());
			for (OntologyTerm ot : entry.getValue()){
				System.out.println("\t" + ot.getLabel());
			}
		}
		// OntologyLoader loader = new OntologyLoader(new URI(
		// "http://www.ebi.ac.uk/efo/efo.owl"));
		// OWLOntology ontology = loader.getOntology();
		//
		// for (OWLNamedIndividual ind : ontology.getIndividualsInSignature()){
		// System.out.println(ind);
		// System.out.println(ind.getAnnotations(ontology));
		// System.out.println(ind.getClassesInSignature());
		// System.out.println(ind.getTypes(ontology));
		// }

		URI uFile = new File(
				"C:\\work\\EFO\\EFO on bar\\ExperimentalFactorOntology\\ExFactorInOWL\\releasecandidate\\efo_release_candidate.owl")
				.toURI();
		//uFile = new File("partonomy.owl").toURI();
		OntologyService os2 = new ReasonedFileOntologyService(uFile, "efo");
		OntologyService os1 = new FileOntologyService(uFile, "efo");

		// for (OntologyTerm ot : os1.getAllTerms("efo")) {
		// if (os1.getParents(ot).size() == 0) {
		// System.out.println("\t" + ot);
		// System.out.println(os2.getParents(ot));
		// }
		// }
		//
		// System.out.println("\n\n\tEFO_0001379");
		// System.out.println(os1.getChildren("efo", "EFO_0001379"));
		// System.out.println(os2.getChildren("efo", "EFO_0001379"));

		// os = new BioportalOntologyService();

		OntologyTerm ot = os2.getTerm("EFO_0000806");
		
		Map<String, Set<OntologyTerm>> result = ((ReasonedFileOntologyService) os2).getRelations(
				ot.getOntologyAccession(), ot.getAccession());
		
		for (Entry<String,Set<OntologyTerm>> entry : result.entrySet()){
			System.out.println("\n\t" + entry.getKey());
			for (OntologyTerm ott : entry.getValue()){
				System.out.println("\t" + ott);
			}
			
		}
		// attachment of spindle microtubules to chromosome

	}
}
