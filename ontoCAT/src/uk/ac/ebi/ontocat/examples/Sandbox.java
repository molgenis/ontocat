package uk.ac.ebi.ontocat.examples;

import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;

public class Sandbox {

	private static final Logger log = Logger.getLogger(Sandbox.class);

	/**
	 * @param args
	 * @throws URISyntaxException
	 * @throws OntologyServiceException
	 * @throws UnknownOptionException
	 * @throws IllegalOptionValueException
	 */
	public static void main(String[] args) throws URISyntaxException,
	OntologyServiceException, IllegalOptionValueException,
	UnknownOptionException {

		URI uri = new File(
		"C:\\Documents and Settings\\tomasz\\Desktop\\gene_ontology_ext_slim.obo")
		.toURI();
		OntologyService os = new ReasonedFileOntologyService(uri, "go");
		log.info("Loaded GO with " + os.getAllTerms(null).size()
				+ " classes");

		OntologyTerm root = os.getRootTerms("go").get(0);
		OntologyTerm term = os.getTerm("GO_0000977");
		log.info("root is " + root);
		log.info("term is " + term);
		log.info(os.getRelations(term));

	}

	public static String getURI(OntologyTerm term) {
		return "http://purl.org/obo/owl/CL#" + term.getAccession();
		// return ((ConceptBean) term).getFullId();
	}
}
