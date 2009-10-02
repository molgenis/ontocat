package uk.ac.ebi.efo.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyAnnotationAxiom;
import org.semanticweb.owl.model.OWLOntologyChangeException;

import plugin.OntologyBrowser.OntologyEntity;
import plugin.OntologyBrowser.OntologyServiceException;
import plugin.OntologyBrowser.OntologyTermExt;
import uk.ac.ebi.efo.api.WrapperOntology.AnnotationFragmentNotFoundException;
import uk.ac.ebi.efo.bioportal.BioportalService;
import uk.ac.ebi.efo.bioportal.BioportalService.BioportalMapping;
import uk.ac.ebi.efo.bioportal.xmlbeans.OntologyBean;

/**
 * Imports annotations from source ontology into target ontology. Walks all
 * classes in the target ontology looking for source ontology ids in linking
 * annotations. It then follows the ids into source ontology and imports all the
 * specified annotations under specific annotation in target ontology class the
 * link was followed from.
 * <p>
 * Source ontology classes are copied into a hashtable for easier lookup in step
 * 2 of the import process {@link WrapperOntology#getHashed}
 * <p>
 * Linking id is being inferred from source ontology automatically
 * 
 * @author $Id: BioportalImporter.java 9060 2009-09-24 12:41:48Z tomasz $
 * @see ScriptMain
 */
public class BioportalImporter {
	private static final Logger _log = Logger.getLogger(BioportalImporter.class.getName());
	private final OWLOntology _ontTarget;
	private final URI _uriOutputOntology;
	private final ProgressCounter[] counters = new ProgressCounter[3]; // used
	// for
	// validity
	// checking,
	private final URI uriAlternativeTerm;
	private final URI uriDefinition;
	private final URI uriEditor;
	private final URI uriDefinitionCitation;
	private final BioportalService bw;
	private final WrapperOntology ontTool;

	/**
	 * Default constructor, loads all the parameters
	 * 
	 * @param uriTargetOntology
	 * @param uriOutputOntology
	 * @throws AnnotationFragmentNotFoundException
	 */
	public BioportalImporter(URI uriTargetOntology, URI uriOutputOntology, String email)
			throws AnnotationFragmentNotFoundException {
		_uriOutputOntology = uriOutputOntology;
		bw = new BioportalService(email);

		// Initialise counters
		counters[0] = new ProgressCounter(); // syns
		counters[1] = new ProgressCounter(); // defs
		counters[2] = new ProgressCounter(); // xrefs

		_log.info("Loading target ontology " + uriTargetOntology);
		_ontTarget = new OntologyLoader(uriTargetOntology).getOntology();

		uriAlternativeTerm = new WrapperOntology(_ontTarget).getUriFromFrag("alternative_term");
		uriDefinition = new WrapperOntology(_ontTarget).getUriFromFrag("definition");
		uriEditor = new WrapperOntology(_ontTarget).getUriFromFrag("definition_editor");
		uriDefinitionCitation = new WrapperOntology(_ontTarget).getUriFromFrag("definition_citation");
		ontTool = new WrapperOntology(_ontTarget);
	}

	/**
	 * Main process method. Return value used for checking if import was
	 * successful.
	 * 
	 * @return number of axioms added in the import process
	 * @throws OntologyServiceException
	 * @throws AnnotationFragmentNotFoundException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws OWLOntologyChangeException
	 * @throws InterruptedException
	 * @throws OntologyServiceException
	 * @throws OWLOntologyChangeException
	 */
	public int processImport() throws OntologyServiceException, OWLOntologyChangeException {
		_log.info("SPECIAL CASES");
		addBrendaMappings();
		removeMPmappings();

		_log.info("UPDATING ONTOLOGY METADATA\n");
		updateOntologyMetadata();
		_log.info("CHECKING ALL CLASSES FOR MAPPINGS\n");
		walkTargetOntology();

		_log.info("Saving target ontology " + _uriOutputOntology);
		new OntologySaver(_ontTarget).saveTo(_uriOutputOntology);

		return counters[0].getTotalAdded() + counters[1].getTotalAdded() + counters[2].getTotalAdded();
	}

	@SuppressWarnings("unchecked")
	private void addBrendaMappings() throws OWLOntologyChangeException {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("temp/EFO2Brenda_mappings.txt"));
			while (scanner.hasNext()) {
				String EFOid = scanner.next();
				String BTOid = scanner.next();
				for (OWLClass cls : _ontTarget.getReferencedClasses()) {
					if (cls.getURI().toString().endsWith(EFOid)) {
						_log.info("ADD " + EFOid + " " + BTOid);
						ontTool.addAnnotation(cls, uriDefinitionCitation, BTOid);
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void checkForDuplicatesOnNewSubmission() throws OWLOntologyChangeException {
		Scanner scanner;
		Map<String, OWLClass> citations = new HashMap();
		for (OWLClass cls : _ontTarget.getReferencedClasses()) {
			for (OWLAnnotation annot : cls.getAnnotations(_ontTarget, uriDefinitionCitation)) {
				if (!ontTool.getClassLabel(cls).contains("obsolete")) {
					String annotText = new WrapperAnnotation(annot).getValue();
					if (!annotText.contains("GeneRIF") && citations.containsKey(annotText)) {
						_log.warn("\t" + annotText + "\t" + cls + "(" + ontTool.getClassLabel(cls) + ")\t"
								+ citations.get(annotText) + "(" + ontTool.getClassLabel(citations.get(annotText))
								+ ")");
					}
					citations.put(annotText, cls);
				}

			}
		}

		try {
			scanner = new Scanner(new File("temp/cell_types.txt"));
			scanner.useDelimiter("\t");
			while (scanner.hasNext()) {
				String s = scanner.next();
				if (!s.equals("")) {
					for (OWLClass cls : _ontTarget.getReferencedClasses()) {
						for (OWLAnnotation annot : cls.getAnnotations(_ontTarget, uriDefinitionCitation)) {
							if (new WrapperAnnotation(annot).getValue().equals(s))
								_log.warn("DUPLICATE for " + s + " IN " + cls + " " + ontTool.getClassLabel(cls));
						}
					}
				}

			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void removeMPmappings() throws OWLOntologyChangeException {
		for (OWLClass cls : _ontTarget.getReferencedClasses()) {
			boolean isAnatomyTerm = false;
			boolean containsMP = false;
			for (OWLAnnotation annot : cls.getAnnotations(_ontTarget, uriDefinitionCitation)) {
				String annotText = new WrapperAnnotation(annot).getValue();
				if (annotText.startsWith("FMAID:") || annotText.startsWith("MAT:"))
					isAnatomyTerm = true;
				if (annotText.startsWith("MP:"))
					containsMP = true;
			}
			if (isAnatomyTerm && containsMP) {
				_log.info("MP conflict detected " + cls + " " + ontTool.getClassLabel(cls));
				for (OWLAnnotation annot : cls.getAnnotations(_ontTarget, uriDefinitionCitation)) {
					String annotText = new WrapperAnnotation(annot).getValue();
					if (annotText.startsWith("MP:")) {
						_log.info("DEL definition_citation " + annot);
						ontTool.removeAnnotation(cls, annot);
					}
				}
				for (OWLAnnotation annot : cls.getAnnotations(_ontTarget, uriAlternativeTerm)) {
					String Source = new WrapperAnnotation(annot).getSource();
					if (Source.contains("MP:")) {
						_log.debug("DEL MP alternative_term " + annot);
						ontTool.removeAnnotation(cls, annot);
					}
				}
				for (OWLAnnotation annot : cls.getAnnotations(_ontTarget, uriDefinition)) {
					String Source = new WrapperAnnotation(annot).getSource();
					if (Source.contains("MP:")) {
						_log.debug("DEL MP definition " + annot);
						ontTool.removeAnnotation(cls, annot);
					}
				}
			} else if (containsMP) {
				_log.warn("Possible MP conflict " + cls + " " + ontTool.getClassLabel(cls));
			}
		}
	}

	private void updateOntologyMetadata() throws OntologyServiceException, OWLOntologyChangeException {
		// load mapped ontologies into hash
		ArrayList<BioportalMapping> mappings = BioportalService.getMappings();
		ArrayList<OWLAxiom> axiomsToRemove = new ArrayList<OWLAxiom>();
		ArrayList<OntologyEntity> ontsMapped = new ArrayList<OntologyEntity>();

		for (BioportalMapping BPmap : mappings) {
			ontsMapped.add(bw.getOntologyDescription(BPmap.getOntID()));
		}

		// remove outdated
		for (OWLOntologyAnnotationAxiom axa : _ontTarget.getAnnotations(_ontTarget)) {
			OWLAnnotation annot = axa.getAnnotation();
			String annotText = annot.getAnnotationValueAsConstant().toString();
			if (annot.getAnnotationURI().toString().equals("http://www.w3.org/2000/01/rdf-schema#comment")) {
				for (OntologyEntity bean : ontsMapped) {
					if (annotText.toLowerCase().contains(bean.getDisplayLabel().toLowerCase())) {
						_log.info("DEL " + annotText);
						axiomsToRemove.add(axa);
					}
				}
			}
		}
		for (OWLAxiom axa : axiomsToRemove) {
			ontTool.removeAxiom(axa);
		}

		// Add new
		for (OntologyEntity bean : ontsMapped) {
			_log.info("ADD " + ((OntologyBean) bean).getMetaAnnotation());
			ontTool.addCommentAnnotation(((OntologyBean) bean).getMetaAnnotation());
		}
	}

	/**
	 * Walk specified annotations in target ontology looking for ids from source
	 * ontology.
	 * 
	 * @throws OWLOntologyChangeException
	 */
	@SuppressWarnings("unchecked")
	private void walkTargetOntology() throws OWLOntologyChangeException {
		// Walk the classes of target ontology
		Integer cProgress = 0;
		for (OWLClass cls : _ontTarget.getReferencedClasses()) {
			if (++cProgress % 10 == 0) {
				_log.info("Processed " + cProgress + " classes (imported total syns:" + counters[0].getTotalAdded()
						+ " defs:" + counters[1].getTotalAdded() + ")");
			}
			for (OWLAnnotation annot : cls.getAnnotations(_ontTarget, uriDefinitionCitation)) {
				if (annot.isAnnotationByConstant()) {
					// Obtain a reference to the corresponding class in source
					try {
						pullExtInfo(cls, annot);
					} catch (OntologyServiceException e) {
						if (!e.getMessage().equals("java.lang.Exception: TERM NOT MAPPABLE")) {
							_log.warn("IMPORT FAILED FOR " + annot);
						}
					}
				} else {
					_log.warn("Annotation is not a constant value " + annot);
				}
			}
			ontTool.removeDuplicateAnnotations(cls, uriAlternativeTerm, counters[0]);
			ontTool.removeDuplicateAnnotations(cls, uriDefinition, counters[1]);
		}
		_log.info("Imported synonyms: " + counters[0].getTotalAdded() + " (new: " + counters[0].getTotalNew() + ")");
		_log.info("Imported definitions: " + counters[1].getTotalAdded() + " (new: " + counters[1].getTotalNew() + ")");
		_log.info("Imported xrefs: " + counters[2].getTotalAdded() + " (new: " + counters[2].getTotalNew() + ")");
	}

	/**
	 * Step through specified annotations in source ontology and import them
	 * into target ontology.
	 * <p>
	 * A automatic definition_editor is added as well with a text
	 * 'OntologyMappingImporter'
	 * 
	 * @throws OWLOntologyChangeException
	 * @throws OntologyServiceException
	 */
	private void pullExtInfo(OWLClass clsTarget, OWLAnnotation linkAnnot) throws OWLOntologyChangeException,
			OntologyServiceException {

		String externalID = new WrapperAnnotation(linkAnnot).getValue();

		OntologyTermExt cb = bw.getTerm(externalID);
		_log.debug(clsTarget + " -> " + linkAnnot);
		// walk matched class annotations
		String source = "[accessedResource: " + externalID + "]" + "[accessDate: "
				+ bw.getSuccessBean().getAccessDate() + "]";

		preProcessing(clsTarget, externalID, bw.getSuccessBean().getAccessedResource());

		if (cb.getSynonyms() != null) {
			for (String synonym : cb.getSynonyms()) {
				counters[0].incrementAdded();
				ontTool.addAnnotation(clsTarget, uriAlternativeTerm, synonym + source);
			}
			if (counters[0].getCurrentNew() < 0) {
				_log.warn("Synonyms removed for " + clsTarget + " -> " + linkAnnot);
			}
		}

		if (cb.getDefinitions() != null) {
			for (String definition : cb.getDefinitions()) {
				counters[1].incrementAdded();
				ontTool.addAnnotation(clsTarget, uriDefinition, definition + source);
			}
			if (counters[1].getCurrentNew() < 0) {
				_log.warn("Definitions removed for " + clsTarget + " -> " + linkAnnot);
			}
		}
		if ((cb.getSynonyms() != null) || (cb.getDefinitions() != null))
			postProcessing(clsTarget);
	}

	private void preProcessing(OWLClass cls, String source1, String source2) throws OWLOntologyChangeException {
		// reset current counters
		counters[0].resetCurrent();
		counters[1].resetCurrent();
		// source2 is legacy, to be removed at next iteration
		Pattern pattern = Pattern.compile(".*/(.*)$");
		Matcher matcher = pattern.matcher(source2);
		if (matcher.find())
			source2 = matcher.group(1);
		ontTool.removeAllAnnotationsBySource(cls, uriAlternativeTerm, source1, counters[0]);
		ontTool.removeAllAnnotationsBySource(cls, uriAlternativeTerm, source2, counters[0]);
		ontTool.removeAllAnnotationsBySource(cls, uriDefinition, source1, counters[1]);
		ontTool.removeAllAnnotationsBySource(cls, uriDefinition, source2, counters[1]);
	}

	private void postProcessing(OWLClass cls) throws OWLOntologyChangeException {
		ontTool.removeEditor(cls, "BioportalImporter");
		ontTool.addEditor(cls);
	}
}
