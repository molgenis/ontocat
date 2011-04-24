package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.OWLClass;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;
import uk.ac.ebi.ontocat.utils.OntologyTermFeature;
import uk.ac.ebi.ontocat.utils.OntologyTermFeature.OntologyTermFeatureType;

/**
 * Example 14
 * 
 * Shows how to compare two versions of the same ontology
 */
public class Example14 {
	private static final Logger log = Logger.getLogger(Example14.class);
	private static Set<OntologyTerm> termsOld;
	private static Set<OntologyTerm> termsNew;
	private static OntologyService osOld;
	private static OntologyService osNew;

	public static void main(String[] args) throws Exception {
		// These point to two versions of EFO
		// we're going to compare in this example
		URI uriOld = new URI(
				"http://rest.bioontology.org/bioportal/ontologies/download/45486");
		URI uriNew = new URI(
				"http://rest.bioontology.org/bioportal/ontologies/download/45574");

		// Instantiate the ontologies and load terms
		log.info("Loading ontologies");
		osOld = new FileOntologyService(uriOld);
		osNew = new FileOntologyService(uriNew);
		termsOld = osOld.getAllTerms("");
		termsNew = osNew.getAllTerms("");
		log.info("Old version contains " + termsOld.size() + " terms");
		log.info("New version contains " + termsNew.size() + " terms");
		log.info("The difference in size is "
				+ (termsNew.size() - termsOld.size()) + " terms");

		getChangedTerms();
		getChangedFeatures();
	}

	private static void getChangedTerms() throws OntologyServiceException {
		log.info("\tCHANGED TERMS");

		for (OntologyTerm term : termsNew) {
			if (!termsOld.contains(term)) {
				String id = term.getLabel() + " (" + term.getAccession() + ")";
				OntologyTerm termOld = osOld.getTerm(term.getAccession());
				if (termOld == null) {
					log.info("ADDED TERM " + id);
				}
			}
		}

		for (OntologyTerm term : termsOld) {
			if (!termsNew.contains(term)) {
				String id = term.getLabel() + " (" + term.getAccession() + ")";
				OntologyTerm termNew = osNew.getTerm(term.getAccession());
				if (termNew == null) {
					log.info("REMOVED TERM " + id);
				} else {
					String idnew = termNew.getLabel() + " ("
							+ termNew.getAccession() + ")";
					log.info("RENAMED TERM " + id + " -> " + idnew);
				}

			}
		}
	}

	private static void getChangedFeatures() throws OntologyServiceException {
		log.info("\tCHANGED FEATURES");

		for (OntologyTerm termNew : termsNew) {
			// skip if the term was deleted in the new version
			if (!termsOld.contains(termNew)) {
				continue;
			}

			Set<OntologyTermFeature> featuresNew = mapTermToFeatures(termNew,
					osNew);
			// since the term exists, can reuse to grab annots
			Set<OntologyTermFeature> featuresOld = mapTermToFeatures(termNew,
					osOld);

			for (OntologyTermFeature f : featuresNew) {
				if (!featuresOld.contains(f)) {
					String id = termNew.getLabel() + " ("
							+ termNew.getAccession() + ")";
					log.info(id + ": ADDED " + f);
				}
			}

			for (OntologyTermFeature f : featuresOld) {
				if (!featuresNew.contains(f)) {
					String id = termNew.getLabel() + " ("
							+ termNew.getAccession() + ")";
					log.info(id + ": REMOVED " + f);
				}
			}

		}
	}

	// Converts an OntologyTerm into a set of typed features
	private static Set<OntologyTermFeature> mapTermToFeatures(
			OntologyTerm term, OntologyService os)
			throws OntologyServiceException {
		Set<OntologyTermFeature> result = new HashSet<OntologyTermFeature>();

		// annotations
		for (Entry<String, List<String>> e : os.getAnnotations(term).entrySet()) {
			// ignore provenance annotatation
			if (e.getKey().equalsIgnoreCase("bioportal_provenance")) {
				continue;
			}
			for (String val : e.getValue()) {
				String annotation = e.getKey() + " - " + val;
				result.add(new OntologyTermFeature(annotation,
						OntologyTermFeatureType.ANNOTATION));
			}
		}

		// parents
		for (OntologyTerm parent : os.getParents(term)) {
			String id = parent.getLabel() + " (" + parent.getAccession() + ")";
			result.add(new OntologyTermFeature(id,
					OntologyTermFeatureType.PARENT));
		}

		return result;
	}

}