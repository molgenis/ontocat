/*
 * 
 */
package uk.ac.ebi.ontocat.file;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLOntology;

import uk.ac.ebi.ontocat.AbstractOntologyService;
import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

// TODO: Auto-generated Javadoc
/**
 * The Class FileOntologyService. This works on both OBO and OWL files.
 */
public final class FileOntologyService extends AbstractOntologyService implements OntologyService
{
	/** The Constant log. */
	private static final Logger log = Logger.getLogger(FileOntologyTerm.class.getName());

	/** The ontology. */
	private final OWLOntology ontology;

	/** The slots. */
	private final FieldDescriptor slots;

	/** The map with classes */
	private Map<String, OWLClass> cache = new TreeMap<String, OWLClass>();

	/**
	 * Instantiates a new file ontology service.
	 * 
	 * @param uriOntology
	 *            where to load the ontology from, can be local file or URL
	 * @param fdesc
	 *            FieldDescriptor describing which annotations hold synonyms,
	 *            definitions and labels
	 */
	public FileOntologyService(URI uriOntology, FieldDescriptor fdesc)
	{
		ontology = new OntologyLoader(uriOntology).getOntology();
		slots = fdesc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getOntologies()
	 */
	@Override
	public List<Ontology> getOntologies() throws OntologyServiceException
	{
		throw new UnsupportedOperationException("Not implemented. Only one ontology available");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getOntology(java.lang.String)
	 */
	@Override
	public Ontology getOntology(String ontologyAccession) throws OntologyServiceException
	{
		throw new UnsupportedOperationException("Not implemented.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getRelations(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, List<String>> getRelations(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		throw new UnsupportedOperationException("Not implemented.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getRootTerms(java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getRootTerms(String ontologyAccession) throws OntologyServiceException
	{
		List<OntologyTerm> rootTerms = new ArrayList<OntologyTerm>();
		for (OWLClass cls : ontology.getReferencedClasses())
		{
			// class without parents, looks like root
			// TODO: add reasoner, otherwise fails for defined classes
			// TODO: test if reasoner works with OBO ontologies
			if (cls.getSuperClasses(ontology).size() == 0)
			{
				rootTerms.add(new FileOntologyTerm(cls, ontology, slots));
			}
		}
		return rootTerms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getSynonyms(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<String> getSynonyms(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		return getAnnotations(ontologyAccession, termAccession).get(slots.synonymSlot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTerm(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public OntologyTerm getTerm(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		return getTerm(termAccession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTerm(java.lang.String)
	 */
	@Override
	public OntologyTerm getTerm(String termAccession) throws OntologyServiceException
	{
		OWLClass theClass = cache.get(termAccession);

		if (theClass == null)
			for (OWLClass cls : ontology.getReferencedClasses())
			{
				if (cls.getURI().toString().endsWith(termAccession))
				{
					cache.put(termAccession, cls);
					return new FileOntologyTerm(cls, ontology, slots);
				}
			}

		throw new OntologyServiceException(termAccession + " was not found in the ontology");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getTermPath(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getTermPath(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{

		ArrayList<OntologyTerm> termPath = new ArrayList<OntologyTerm>();
		int iteration = 0;
		// seed the list with this term
		OntologyTerm term = getTerm(ontologyAccession, termAccession);
		termPath.add(term);
		// iterate its first parents recursively
		List<OntologyTerm> parents = getParents(ontologyAccession, termAccession);
		while (parents.size() != 0)
		{
			term = parents.get(0);
			termPath.add(term);
			parents = getParents(term.getOntologyAccession(), term.getAccession());
			// safety break for circular relations
			if (iteration++ > 100)
			{
				log.error("getTermPath(): TOO MANY ITERATIONS (" + iteration + "x)");
				break;
			}
		}
		// reverse to have root as first
		Collections.reverse(termPath);
		return termPath;
	}

	public List<OntologyTerm> getChildren(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		for (OWLDescription desc : getOwlClass(termAccession).getSubClasses(ontology))
		{
			if (!desc.isAnonymous())
			{
				list.add(new FileOntologyTerm(desc.asOWLClass(), ontology, slots));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#getParents(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> getParents(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		ArrayList<OntologyTerm> list = new ArrayList<OntologyTerm>();
		for (OWLDescription desc : getOwlClass(termAccession).getSuperClasses(ontology))
		{
			if (!desc.isAnonymous())
			{
				list.add(new FileOntologyTerm(desc.asOWLClass(), ontology, slots));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ebi.ontocat.OntologyService#makeLookupHyperlink(java.lang.String)
	 */
	@Override
	public String makeLookupHyperlink(String termAccession)
	{
		try
		{
			return getTerm(termAccession) + termAccession;
		}
		catch (OntologyServiceException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#searchAll(java.lang.String)
	 */
	@Override
	public List<OntologyTerm> searchAll(String keyword) throws OntologyServiceException
	{

		HashSet<OntologyTerm> terms = new HashSet<OntologyTerm>();
		// iterate through all classes annotations
		// TODO: lucene index?
		for (OWLClass cls : ontology.getReferencedClasses())
		{
			for (OWLAnnotation annot : cls.getAnnotations(ontology))
			{
				if (annot.getAnnotationValueAsConstant().getLiteral().contains(keyword))
					terms.add(new FileOntologyTerm(cls, ontology, slots));
			}
		}

		return new ArrayList<OntologyTerm>(terms);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyService#searchOntology(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OntologyTerm> searchOntology(String ontologyAccession, String keyword) throws OntologyServiceException
	{
		throw new UnsupportedOperationException("Not implemented. Only one ontology available. Use searchAll instead.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getDefinitions()
	 */
	@Override
	public List<String> getDefinitions(String ontologyAccession, String termAccession) throws OntologyServiceException
	{
		return getAnnotations(ontologyAccession, termAccession).get(slots.definitionSlot);
	}

	// helper function to manage the cache
	private OWLClass getOwlClass(String termAccession) throws OntologyServiceException
	{
		OWLClass theClass = cache.get(termAccession);

		if (theClass == null)
		{
			for (OWLClass cls : ontology.getReferencedClasses())
			{
				if (cls.getURI().toString().endsWith(termAccession))
				{
					cache.put(termAccession, cls);
					return cls;
				}
			}
			throw new OntologyServiceException(termAccession + " was not found in the ontology");
		}
		else
		{
			return theClass;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ebi.ontocat.OntologyTerm#getAnnotations()
	 */
	@Override
	public Map<String, List<String>> getAnnotations(String ontologyAccession, String termAccession)
			throws OntologyServiceException
	{
		Map<String, List<String>> metadata = new HashMap<String, List<String>>();
		for (OWLAnnotation annot : getOwlClass(termAccession).getAnnotations(ontology))
		{
			String key = getFragment(annot.getAnnotationURI());
			List<String> value = null;
			if (metadata.containsKey(key))
				value = metadata.get(key);
			else
				value = new ArrayList<String>();
			// get an ArrayList from value to add another annottaion
			List<String> arr = value;
			arr.add(annot.getAnnotationValueAsConstant().getLiteral());
			// and convert it back to String[] before putting back in map
			metadata.put(key, arr);
		}
		return metadata;
	}

	/**
	 * Gets the fragment.
	 * 
	 * @param uri
	 *            the uri
	 * 
	 * @return the fragment
	 */
	private String getFragment(URI uri)
	{
		// can't use URI.getFragment() as it's not always delimited with # (see
		// EFO)
		Pattern pattern = Pattern.compile("[^//#]*$");
		Matcher matcher = pattern.matcher(uri.toString());
		if (matcher.find())
			return matcher.group();
		return null;
	}
}
