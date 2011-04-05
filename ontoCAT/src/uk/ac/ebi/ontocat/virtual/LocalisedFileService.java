package uk.ac.ebi.ontocat.virtual;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.FileOntologyService;

/**
 * Dynamically swaps all the ontologyAccessions on returned terms to the
 * accession set on instation.
 * 
 * @author Tomasz Adamusiak
 */
@SuppressWarnings("unchecked")
public class LocalisedFileService implements InvocationHandler {
	/** Underlying service */
	private FileOntologyService target;

	/** OntologyAccession to force on the terms */
	private String ontologyAccession;

	/** The Constant log. */
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(LocalisedFileService.class);

	/**
	 * Instantiates the decorator.
	 * 
	 */
	public LocalisedFileService(FileOntologyService fos) {
		this.target = fos;
		this.ontologyAccession = fos.getOntologyUri().toString();
	}

	/**
	 * Creates the proxy.
	 * 
	 * @param fos
	 *            the obj
	 *
	 * 
	 * @return the object
	 * @throws OntologyServiceException
	 */
	private static Object createProxy(FileOntologyService fos) throws OntologyServiceException {
		return Proxy.newProxyInstance(fos.getClass().getClassLoader(), fos.getClass()
				.getInterfaces(), new LocalisedFileService(fos));
	}

	public static OntologyService getService(FileOntologyService fos)
	throws OntologyServiceException {

		return (OntologyService) LocalisedFileService.createProxy(fos);
	}

	// here the magic happens
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = method.invoke(target, args);

		injectAccession(result);

		return result;
	}

	/**
	 * Examples of invalid results for a particular method which in sequential
	 * processing results in trying the next ontologyservice in the queue NOTE:
	 * Where's the above note coming from?
	 */
	private void injectAccession(Object result) {
		// Possible result object involving objects with ontologyAccession
		// These are Ontology and OntologyTerm

		// OntologyTerm
		if (result instanceof OntologyTerm) {
			OntologyTerm ot = (OntologyTerm) result;
			ot.setOntologyAccession(ontologyAccession);

			// Ontology
		} else if (result instanceof Ontology) {
			((Ontology) result).setOntologyAccession(ontologyAccession);

			// List
		} else if (result instanceof List<?>) {
			List<?> list = ((List<?>) result);
			if (list.size() != 0) {
				// List<OntologyTerm>

				if (list.get(0) instanceof OntologyTerm) {
					for (OntologyTerm ot : ((List<OntologyTerm>) result)) {
						ot.setOntologyAccession(ontologyAccession);
					}

					// List<Ontology>
				} else if (list.get(0) instanceof Ontology) {
					for (Ontology o : ((List<Ontology>) result)) {
						o.setOntologyAccession(ontologyAccession);
					}
				}
			}
			// Set<OntologyTerm>
		} else if (result instanceof Set<?>) {
			Set<?> set = ((Set<?>) result);
			if (set.size() != 0) {
				if (set.toArray()[0] instanceof OntologyTerm) {
					for (OntologyTerm ot : ((Set<OntologyTerm>) result)) {
						ot.setOntologyAccession(ontologyAccession);
					}
				}

			}
			// Map<String,Set<OntologyTerm>
		} else if (result instanceof Map<?, ?>) {
			Map<?, ?> map = ((Map<?, ?>) result);
			if (map.size() != 0) {
				for (Object o : map.values()) {
					if (o instanceof Set<?>) {
						Set<?> set = ((Set<?>) o);
						if (set.size() != 0 && set.toArray()[0] instanceof OntologyTerm) {
							for (OntologyTerm ot : ((Set<OntologyTerm>) set)) {
								ot.setOntologyAccession(ontologyAccession);
							}
						}
					}
				}
			}
		}
	}

}