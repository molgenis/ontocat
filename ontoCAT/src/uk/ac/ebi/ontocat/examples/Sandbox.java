package uk.ac.ebi.ontocat.examples;

import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
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

		OntologyService os = new ReasonedFileOntologyService(
				URI.create("http://www.ebi.ac.uk/efo"), "efo");
		Set<OntologyTerm> organisms = os.getAllChildren("efo", "OBI_0100026");
		log.info("Organism has " + organisms.size()
				+ " children");

		OntologyService bpOS = new BioportalOntologyService();
		for (OntologyTerm t : organisms) {
			if (!t.getAccession().contains("NCBITaxon")) {
				String termLabel = t.getLabel();
				List<OntologyTerm> taxonhits = Collections.emptyList();
				try {
					taxonhits = bpOS.searchOntology("1132",
							termLabel);
				} catch (OntologyServiceException e) {
				}
				if (taxonhits.size() != 0) {
					System.out.println(t + " matched NCBITaxon "
							+ taxonhits.get(0));
				}

			}
		}

	}

	public static String getURI(OntologyTerm term) {
		return "http://purl.org/obo/owl/CL#" + term.getAccession();
		// return ((ConceptBean) term).getFullId();
	}
}
