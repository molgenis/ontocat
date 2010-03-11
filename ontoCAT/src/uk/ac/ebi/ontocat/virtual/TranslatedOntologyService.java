package uk.ac.ebi.ontocat.virtual;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyIdTranslator;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * The Class MappingDecorator. Decorater providing mappings from one ontology
 * namespace to another. Intercepts both ontologyAccessions and termAccessions
 * and translates them from the request namespace to the underlying resource
 * namespace. Translations are provided via the OntologyIdMapping interface.
 * 
 * @author Tomasz Adamusiak, Morris Swertz
 */

public class TranslatedOntologyService extends AbstractOntologyService {

	private OntologyService os;
	private OntologyIdTranslator translator;
	/** The Constant log. */
	private static final Logger log = Logger
			.getLogger(TranslatedOntologyService.class);

	/**
	 * @param os
	 * @param translator
	 */
	public TranslatedOntologyService(OntologyService os,
			OntologyIdTranslator translator) {
		this.os = os;
		this.translator = translator;
	}

	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return os.getAnnotations(translator
				.getTranslatedOntologyAccession(ontologyAccession), translator
				.getTranslatedTermAccession(termAccession));
	}

	@Override
	public List<OntologyTerm> getChildren(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return os.getChildren(translator
				.getTranslatedOntologyAccession(ontologyAccession), translator
				.getTranslatedTermAccession(termAccession));
	}

	@Override
	public List<String> getDefinitions(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return os.getDefinitions(translator
				.getTranslatedOntologyAccession(ontologyAccession), translator
				.getTranslatedTermAccession(termAccession));
	}

	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException {
		return os.getOntologies();
	}

	@Override
	public Ontology getOntology(String ontologyAccession)
			throws OntologyServiceException {
		return os.getOntology(translator
				.getTranslatedOntologyAccession(ontologyAccession));
	}

	@Override
	public List<OntologyTerm> getParents(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return os.getParents(translator
				.getTranslatedOntologyAccession(ontologyAccession), translator
				.getTranslatedTermAccession(termAccession));
	}

	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return os.getRelations(translator
				.getTranslatedOntologyAccession(ontologyAccession), translator
				.getTranslatedTermAccession(termAccession));
	}

	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession)
			throws OntologyServiceException {
		return os.getRootTerms(translator
				.getTranslatedOntologyAccession(ontologyAccession));
	}

	@Override
	public List<String> getSynonyms(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return os.getSynonyms(translator
				.getTranslatedOntologyAccession(ontologyAccession), translator
				.getTranslatedTermAccession(termAccession));
	}

	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession)
			throws OntologyServiceException {
		return os.getTerm(translator
				.getTranslatedOntologyAccession(ontologyAccession), translator
				.getTranslatedTermAccession(termAccession));
	}

	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession,
			String termAccession) throws OntologyServiceException {
		return os.getTermPath(translator
				.getTranslatedOntologyAccession(ontologyAccession), translator
				.getTranslatedTermAccession(termAccession));
	}

	@Override
	public List<OntologyTerm> searchAll(String keywords)
			throws OntologyServiceException {
		return os.searchAll(keywords);
	}

	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession,
			String keywords) throws OntologyServiceException {
		return os.searchOntology(translator
				.getTranslatedOntologyAccession(ontologyAccession), keywords);
	}

	@Override
	public OntologyTerm getTerm(String termAccession)
			throws OntologyServiceException {
		return os.getTerm(translator.getTranslatedTermAccession(termAccession));
	}

	@Override
	public String makeLookupHyperlink(String termAccession) {
		try {
			return os.makeLookupHyperlink(translator
					.getTranslatedTermAccession(termAccession));
		} catch (OntologyServiceException e) {
			e.printStackTrace();
			log.error("Making lookup hyperlink failed for " + termAccession);
			return null;
		}
	}

}