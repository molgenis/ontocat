package uk.ac.ebi.ontocat.examples;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 */
	public static void main(String[] args) throws URISyntaxException,
	OntologyServiceException {

		// URI rc = new File(
		// "C:\\work\\EFO\\EFO on bar\\ExperimentalFactorOntology\\ExFactorInOWL\\releasecandidate\\efo_release_candidate.owl")
		// .toURI();
		URI rc = URI.create("http://www.ebi.ac.uk/efo/efo.owl");
		OntologyService os = new ReasonedFileOntologyService(rc, "efo");

		Map<String, Set<OntologyTerm>> synonyms = new HashMap<String, Set<OntologyTerm>>();

		// iterate thr all terms
		for (OntologyTerm t : os.getAllTerms("efo")) {
			// skip obsolete
			if (t.getLabel().startsWith("obsolete_")) {
				continue;
			}
			// iterate thr all annotations
			Set<String> annots = new HashSet<String>();
			annots.addAll(os.getSynonyms(t));
			annots.add(t.getLabel());
			for (String syn : annots) {
				// if accession list doesn't exist
				// create one
				if (syn.length() <= 2) {
					log.warn("short annotation detected <" + syn
							+ "> in "
							+ t);
					continue; // skip it
				}
				if (syn.length() == 0) {
					log.warn("skipping empty synonym <" + syn
							+ "> in "
							+ t);
					continue; // skip it
				}
				if (!synonyms.containsKey(syn)) {
					synonyms.put(syn, new HashSet<OntologyTerm>());
				}
				synonyms.get(syn).add(t);
			}

		}

		log.info("Loaded " + synonyms.size()
				+ " synonyms");

		// Cross check
		Set<Set<OntologyTerm>> seenSets = new HashSet<Set<OntologyTerm>>();
		Integer counter = 0;

		// related branches
		Set<OntologyTerm> relatedBranches = new HashSet<OntologyTerm>();
		relatedBranches.add(os.getTerm("CHEBI_51086")); // chemical role
		relatedBranches.add(os.getTerm("CHEBI:37577")); // chemical compound
		relatedBranches.add(os.getTerm("EFO_0000635")); // organism part
		relatedBranches.add(os.getTerm("EFO_0000408")); // disease
		relatedBranches.add(os.getTerm("EFO_0000324")); // cell type
		relatedBranches.add(os.getTerm("EFO_0000322")); // cell line
		relatedBranches.add(os.getTerm("OBI_0100026")); // organism

		for (String syn1 : synonyms.keySet()) {
			for (String syn2 : synonyms.keySet()) {
				// same synonym
				if (syn1.equalsIgnoreCase(syn2)) {
					continue;
				}
				// same term(s)
				if (synonyms.get(syn1).equals(synonyms.get(syn2))) {
					continue;
				}
				// synonym exists in substring word boundary
				Pattern pattern = Pattern.compile("\\b" + Pattern.quote(syn1)
						+ "\\b", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(syn2);

				if (matcher.find()) {
					Set<OntologyTerm> terms1 = new HashSet<OntologyTerm>(
							synonyms.get(syn1));
					Set<OntologyTerm> terms2 = new HashSet<OntologyTerm>(
							synonyms.get(syn2));

					// this checks if it's one of the related branches
					Boolean isRelated = true;

					// proximity metric
					for (OntologyTerm term1 : synonyms.get(syn1)) {
						Set<OntologyTerm> parents1 = os.getAllParents(
								term1.getOntologyAccession(),
								term1.getAccession());
						for (OntologyTerm term2 : synonyms.get(syn2)) {
							Set<OntologyTerm> parents2 = os.getAllParents(
									term2.getOntologyAccession(),
									term2.getAccession());

							// related branch by intersection
							Set<OntologyTerm> relatedness = new HashSet<OntologyTerm>(
									parents1);
							relatedness.retainAll(relatedBranches);
							if (relatedness.size() == 0) {
								isRelated = false;
							}

							relatedness = new HashSet<OntologyTerm>(parents2);
							relatedness.retainAll(relatedBranches);
							if (relatedness.size() == 0) {
								isRelated = false;
							}

							// proximity by intersection
							parents2.retainAll(parents1);

							// drop term1 if shares more than 2 parents with
							// term2
							if (parents2.size() > 2) {
								terms1.remove(term1);
							}
						}
					}

					// on related branches skip
					if (isRelated) {
						continue;
					}

					// share parents skip this one
					if (terms1.size() == 0) {
						continue;
					}

					// this combination of terms1 terms2
					// have been seen already for other syns
					Set<OntologyTerm> comb = new HashSet<OntologyTerm>();
					comb.addAll(terms1);
					comb.addAll(terms2);
					if (!seenSets.add(comb)) {
						continue;
					}

					counter++;
					log.info("ANNOTATION <" + syn1
							+ "> IS SUBSTRING OF <"
							+ syn2
							+ ">");
					log.info("\tsource classes: " + printSet(terms1));
					log.info("\ttarget classes: " + printSet(terms2));
				}
			}
		}
		log.info("Identified " + counter
				+ " possible conflicts");
	}

	private static String printSet(Set<OntologyTerm> input) {
		String result = "";
		for (OntologyTerm t : input) {
			result += "<" + t.getLabel()
			+ "> ("
			+ t.getAccession()
			+ "), ";
		}
		return result;
	}
}

// // is terms1 a parent term in context
// for (OntologyTerm parent : synonyms.get(syn1)) {
// for (OntologyTerm child : synonyms.get(syn2)) {
// Set<OntologyTerm> trueParents = os.getAllParents(
// child.getOntologyAccession(),
// child.getAccession());
// // is the same context branch
// if (trueParents.contains(parent)) {
// terms1.remove(parent);
// }
// }
// }
//
// // or both share the same parent
// // see asian
// for (OntologyTerm t1 : synonyms.get(syn1)) {
// for (OntologyTerm t2 : synonyms.get(syn2)) {
// for (OntologyTerm parent1 : os.getParents(t1)) {
// for (OntologyTerm parent2 : os.getParents(t2)) {
// if (parent1.equals(parent2)) {
// terms1.remove(t1);
// }
// }
// }
// }
// }

