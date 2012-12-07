/**
 * Copyright (c) 2010 - 2011 European Molecular Biology Laboratory and University of Groningen
 *
 * Contact: ontocat-users@lists.sourceforge.net
 * 
 * This file is part of OntoCAT
 * 
 * OntoCAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * OntoCAT is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along
 * with OntoCAT. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.ontocat.file;

 import java.net.URI;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;

 import org.apache.log4j.Logger;
 import org.semanticweb.HermiT.Reasoner;
 import org.semanticweb.owlapi.model.OWLClass;
 import org.semanticweb.owlapi.model.OWLClassExpression;
 import org.semanticweb.owlapi.model.OWLDataFactory;
 import org.semanticweb.owlapi.model.OWLEntity;
 import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
 import org.semanticweb.owlapi.model.OWLObjectProperty;
 import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
 import org.semanticweb.owlapi.reasoner.OWLReasoner;
 import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

 import uk.ac.ebi.ontocat.OntologyService;
 import uk.ac.ebi.ontocat.OntologyServiceException;
 import uk.ac.ebi.ontocat.OntologyTerm;

 // TODO: Auto-generated Javadoc
 /**
  * The Class ReasonedFileOntologyService.
  * 
  * @author Tomasz Adamusiak
  */
 public class ReasonedFileOntologyService extends FileOntologyService implements
 OntologyService {

	 /** The reasoner. */
	 private OWLReasoner reasoner;

	 /** The cache inverse property. */
	 private Map<OWLObjectProperty, OWLObjectProperty> cacheInverseProperty = new HashMap<OWLObjectProperty, OWLObjectProperty>();

	 /** The cache property label. */
	 private Map<OWLObjectProperty, String> cachePropertyLabel = new HashMap<OWLObjectProperty, String>();

	 /** The cache property by label. */
	 private Map<String, OWLObjectProperty> cachePropertyByLabel = new HashMap<String, OWLObjectProperty>();

	 /** The factory. */
	 private OWLDataFactory factory;

	 /** The Constant log. */
	 private static final Logger log = Logger
	 .getLogger(ReasonedFileOntologyService.class);

	 /**
	  * Instantiates a new reasoned file ontology service.
	  * 
	  * @param uriOntology
	  *            the uri ontology
	  * @throws OntologyServiceException
	  *             the ontology service exception
	  */
	 public ReasonedFileOntologyService(URI uriOntology)
	 throws OntologyServiceException {
		 super(uriOntology);
		 runCommonInstantiationCode(uriOntology);
	 }

	 /**
	  * Instantiates a new reasoned file ontology service.
	  * 
	  * @param uriOntology
	  *            the uri ontology
	  * @param ontologyAccession
	  *            the ontology accession
	  * @throws OntologyServiceException
	  *             the ontology service exception
	  */
	 public ReasonedFileOntologyService(URI uriOntology, String ontologyAccession)
	 throws OntologyServiceException {
		 super(uriOntology, ontologyAccession);
		 runCommonInstantiationCode(uriOntology);
	 }

	 private void runCommonInstantiationCode(URI uriOntology)
	 throws OntologyServiceException {
		 fixPropertiesURIs();
		 injectInverseObjectProperties();
		 instantiateReasoner(uriOntology);
	 }

	 // FIXME: fix for OWL API 3.2.2 object property bug
	 // which duplicates object properties by introducing
	 // incorrect URIs in OBOConsumer
	 // this goes through all the properties and removes
	 // the ones that are not used anywhere (duplicates)
	 private void fixPropertiesURIs() {
		 for (OWLObjectProperty prop : ontology.getObjectPropertiesInSignature()) {
			 if (prop.getReferencingAxioms(ontology).size() == 1) {
				 loader.getManager().removeAxiom(
						 ontology,
						 loader.getManager().getOWLDataFactory()
						 .getOWLDeclarationAxiom(prop));
			 }
		 }
	 }

	 private void injectInverseObjectProperties()
	 throws OntologyServiceException {
		 factory = loader.getManager().getOWLDataFactory();
		 Map<String, OWLObjectProperty> propertyCache = new HashMap<String, OWLObjectProperty>();

		 // cache the properties
		 for (OWLObjectProperty rel : ontology.getObjectPropertiesInSignature()) {
			 propertyCache.put(getLabel(rel), rel);
		 }

		 // manually define the corresponding inverses
		 // based on the relation ontology
		 Map<String, String> ROinverses = new HashMap<String, String>();
		 ROinverses.put("part_of", "has_part");
		 ROinverses.put("has_part", "part_of");
		 ROinverses.put("integral_part_of", "has_integral_part");
		 ROinverses.put("has_integral_part", "integral_part_of");
		 ROinverses.put("proper_part_of", "has_proper_part");
		 ROinverses.put("has_proper_part", "proper_part_of");
		 ROinverses.put("located_in", "location_of");
		 ROinverses.put("location_of", "located_in");
		 ROinverses.put("improper_part_of", "has_improper_part");
		 ROinverses.put("has_improper_part", "improper_part_of");
		 ROinverses.put("agent_in", "has_agent");
		 ROinverses.put("has_agent", "agent_in");
		 ROinverses.put("participates_in", "has_participant");
		 ROinverses.put("has_participant", "participates_in");
		 ROinverses.put("precedes", "preceded_by");
		 ROinverses.put("preceded_by", "precedes");
		 ROinverses.put("derived_into", "derives_from");
		 ROinverses.put("derives_from", "derived_into");
		 ROinverses.put("transformation_of", "transformed_into");
		 ROinverses.put("transformed_into", "transformation_of");
		 ROinverses.put("contained_in", "contains");
		 ROinverses.put("contains", "contained_in");

		 // get inverse axioms
		 Set<OWLInverseObjectPropertiesAxiom> axiomsToAdd = new HashSet<OWLInverseObjectPropertiesAxiom>();
		 for (Entry<String, OWLObjectProperty> e : propertyCache.entrySet()) {
			 OWLObjectProperty forward = e.getValue();
			 // make sure we have an inverse defined for it
			 if (ROinverses.containsKey(e.getKey())) {
				 String inverseLabel = ROinverses.get(e.getKey());
				 // and the inverse property exists in the ontology
				 if (propertyCache.containsKey(inverseLabel)) {
					 OWLObjectProperty inverse = propertyCache.get(inverseLabel);
					 OWLInverseObjectPropertiesAxiom inverseAxiom = factory
					 .getOWLInverseObjectPropertiesAxiom(forward,
							 inverse);
					 axiomsToAdd.add(inverseAxiom);
				 } else {
					 log.warn("The inverse object property " + inverseLabel
							 + " for "
							 + e.getKey()
							 + " was not found in the ontology");
				 }

			 }
		 }
		 // add all the inverse axioms
		 loader.getManager().addAxioms(ontology, axiomsToAdd);

	 }

	 /**
	  * Instantiate reasoner.
	  * 
	  * @param uriOntology
	  *            the uri ontology
	  * @throws OntologyServiceException
	  *             the ontology service exception
	  */
	 private void instantiateReasoner(URI uriOntology)
	 throws OntologyServiceException {
		 // Create a reasoner factory.
		 OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();

		 // Create a console progress monitor. This will print the reasoner
		 // progress out to the console.
		 // ConsoleProgressMonitor progressMonitor = new
		 // ConsoleProgressMonitor();
		 // OWLReasonerConfiguration config = new SimpleConfiguration(
		 // progressMonitor);

		 // Create a HermiT OWLReasoner
		 reasoner = reasonerFactory.createReasoner(ontology);

		 // Ask the reasoner to do all the necessary work
		 reasoner.precomputeInferences();

		 // Throw an exception if the ontology is inconsistent
		 if (!reasoner.isConsistent()) {
			 throw new OntologyServiceException(
					 "Inconsistent ontology according to HermiT reasoner - " + uriOntology
					 .toString());
		 }
		 log.info("Classified the ontology " + uriOntology.toString());

		 // Inject inverses if they're missing

		 // Cache the properties for relations
		 factory = loader.getManager().getOWLDataFactory();
		 for (OWLObjectProperty prop : ontology
				 .getObjectPropertiesInSignature(false)) {
			 OWLObjectProperty inverse = findInverseObjectProperty(prop);
			 cacheInverseProperty.put(prop, inverse);
			 cachePropertyLabel.put(prop, getLabel(prop));
			 cachePropertyByLabel.put(getLabel(prop), prop);
		 }

	 }

	 /*
	  * (non-Javadoc)
	  * 
	  * @see
	  * uk.ac.ebi.ontocat.file.FileOntologyService#getRelations(java.lang.String,
	  * java.lang.String)
	  */
	 @Override
	 public Map<String, Set<OntologyTerm>> getRelations(
			 String ontologyAccession, String termAccession)
			 throws OntologyServiceException {
		 // this is only works for classes now (not individuals)
		 OWLEntity ent = getOwlEntity(termAccession);
		 if (!ent.isOWLClass()) {
			 return Collections.emptyMap();
		 }

		 // initialise
		 Map<String, Set<OntologyTerm>> result = new HashMap<String, Set<OntologyTerm>>();

		 // iterate through properties
		 for (OWLObjectProperty prop : cacheInverseProperty.keySet()) {

			 Set<OntologyTerm> relatedTerms = getSpecificRelation(
					 ent.asOWLClass(), prop);
			 if (relatedTerms != null) { // null if no inverse found
				 result.put(cachePropertyLabel.get(prop), relatedTerms);
			 }
		 }

		 return result;
	 }

	 /**
	  * Gets the specific relation for a term. Works by asking the reasoner for
	  * children of an anonymous class defined as inverse(ObjectProperty) some
	  * classInQuestion
	  * 
	  * @param clsQueried
	  *            the cls queried
	  * @param prop
	  *            the prop
	  * @return the specific relation
	  * @throws OntologyServiceException
	  *             the ontology service exception
	  */
	 private Set<OntologyTerm> getSpecificRelation(OWLClass clsQueried,
			 OWLObjectProperty prop) throws OntologyServiceException {
		 OWLObjectProperty inverse = cacheInverseProperty.get(prop);
		 // this really only works for inversable properties
		 // so ignore other
		 if (inverse == null) {
			 return null;
		 }

		 // create class expression
		 OWLClassExpression relationSomeObject = factory
		 .getOWLObjectSomeValuesFrom(inverse, clsQueried);
		 // and find all subclasses that fulfil it
		 Set<OWLClass> sRelatedClasses = new HashSet<OWLClass>();
		 sRelatedClasses.addAll(reasoner.getSubClasses(relationSomeObject, true)
				 .getFlattened());

		 // filter out anonymous classes
		 Set<OntologyTerm> sFilteredResult = new HashSet<OntologyTerm>();
		 for (OWLClass cls : sRelatedClasses) {
			 if (cls.isBuiltIn()) {
				 continue;
			 }
			 sFilteredResult.add(getTerm(cls));
		 }

		 return sFilteredResult;
	 }

	 /**
	  * Calls the previous method, but identifies the property by its name rather
	  * then OWLObjectProperty
	  * 
	  * @param ontologyAccession
	  *            the ontology accession
	  * @param termAccession
	  *            the term accession
	  * @param relation
	  *            the relation
	  * @return the specific relation
	  * @throws OntologyServiceException
	  *             the ontology service exception
	  */
	 public Set<OntologyTerm> getSpecificRelation(String ontologyAccession,
			 String termAccession, String relation)
			 throws OntologyServiceException {
		 // this is only works for classes now (not individuals)
		 OWLEntity ent = getOwlEntity(termAccession);
		 if (!ent.isOWLClass()) {
			 return Collections.emptySet();
		 }

		 // find the relation
		 OWLObjectProperty prop = cachePropertyByLabel.get(relation);
		 if (prop == null) { // no such relation found
			 return Collections.emptySet();
		 }

		 Set<OntologyTerm> relatedTerms = getSpecificRelation(ent.asOWLClass(),
				 prop);
		 if (relatedTerms != null) { // null if no inverse found
			 return relatedTerms;
		 }

		 return Collections.emptySet();
	 }

	 /**
	  * Find inverse object property. This could potentially be a set, but most
	  * of the time only a single element one, so just return the first.
	  * 
	  * @param prop
	  *            the property to find inverse of
	  * @return the inverse of the object property passed in as parameter
	  */
	 private OWLObjectProperty findInverseObjectProperty(OWLObjectProperty prop) {
		 Set<OWLObjectPropertyExpression> inverse = reasoner
		 .getInverseObjectProperties(prop).getEntities();
		 for (OWLObjectPropertyExpression pe : inverse) {
			 if (pe.isAnonymous()) {
				 continue;
			 }
			 // only need the first one
			 return pe.asOWLObjectProperty();
		 }
		 return null;
	 }

	 /*
	  * (non-Javadoc)
	  * 
	  * @see
	  * uk.ac.ebi.ontocat.file.FileOntologyService#getChildren(java.lang.String,
	  * java.lang.String)
	  */
	 @Override
	 public List<OntologyTerm> getChildren(String ontologyAccession,
			 String termAccession) throws OntologyServiceException {
		 if (!ontoAccessions.contains(ontologyAccession)) {
			 return Collections.emptyList();
		 }
		 ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		 OWLEntity ent = getOwlEntity(termAccession);
		 if (ent == null) {
			 return Collections.emptyList();
		 }
		 if (ent.isOWLClass()) {
			 for (OWLClass cls : reasoner.getSubClasses(ent.asOWLClass(), true)
					 .getFlattened()) {
				 if (cls.isBuiltIn()) {
					 continue;
				 }
				 list.add(getTerm(cls));
			 }
		 }
		 return list;
	 }

	 /*
	  * (non-Javadoc)
	  * 
	  * @see
	  * uk.ac.ebi.ontocat.file.FileOntologyService#getParents(java.lang.String,
	  * java.lang.String)
	  */
	 @Override
	 public List<OntologyTerm> getParents(String ontologyAccession,
			 String termAccession) throws OntologyServiceException {
		 if (!ontoAccessions.contains(ontologyAccession)) {
			 return Collections.emptyList();
		 }
		 ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		 OWLEntity ent = getOwlEntity(termAccession);
		 if (ent == null) {
			 return Collections.emptyList();
		 }
		 if (ent.isOWLClass()) {
			 for (OWLClass cls : reasoner
					 .getSuperClasses(ent.asOWLClass(), true).getFlattened()) {
				 if (cls.isBuiltIn()) {
					 continue;
				 }
				 list.add(getTerm(cls));
			 }
		 }
		 if (ent.isOWLNamedIndividual()) {
			 for (OWLClass cls : reasoner.getTypes(ent.asOWLNamedIndividual(),
					 true).getFlattened()) {
				 list.add(getTerm(cls));
			 }
		 }
		 return list;
	 }
 }
