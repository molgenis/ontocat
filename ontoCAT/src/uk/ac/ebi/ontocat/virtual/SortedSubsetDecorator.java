package uk.ac.ebi.ontocat.virtual;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;

/**
 * The Class SortedSubsetDecorator. Decorator adding sorting and subsetting
 * capabilities to an OntologyService. Implemented using Java reflection dynamic
 * proxy pattern See the following link for more details {@link http
 * ://www.webreference.com/internet/reflection/3.html}
 * 
 * @author Tomasz Adamusiak
 */
@SuppressWarnings("unchecked")
public class SortedSubsetDecorator implements InvocationHandler {
	// standard invocation
	// see http://www.webreference.com/internet/reflection/4.html
	/** The target. */
	private Object target;

	/** The sort order. */
	private List sortOrder;

	/** The Constant log. */
	private static final Logger log = Logger
			.getLogger(BioportalOntologyService.class.getName());

	/**
	 * Instantiates a new sorted subset decorator.
	 * 
	 * @param obj
	 *            the obj
	 * @param list
	 *            the list
	 */
	private SortedSubsetDecorator(Object obj, List list) {
		target = obj;
		this.sortOrder = list;
		// reverse the list as the first item
		// also has the lowest index
		Collections.reverse(this.sortOrder);
	}

	/**
	 * Creates the proxy.
	 * 
	 * @param obj
	 *            the obj
	 * @param list
	 *            the list
	 * 
	 * @return the object
	 * @throws OntologyServiceException
	 */
	private static Object createProxy(Object obj, List list)
			throws OntologyServiceException {
		// If an exception if thrown here, the OntologyService
		// interface must have changed and you have to modify
		// the <searchAll> string here and in invoke method
		try {
			obj.getClass().getMethod("searchAll", String.class);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		}
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
				.getClass().getInterfaces(), new SortedSubsetDecorator(obj,
				list));
	}

	public static OntologyService getService(OntologyService os, List list)
			throws OntologyServiceException {
		return (OntologyService) SortedSubsetDecorator.createProxy(os, list);
	}

	// here the magic happens
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		try {
			result = method.invoke(target, args);
			// sort the results if the method was serachAll
			if (method.getName().equalsIgnoreCase("searchAll"))
				result = searchAllRanked((List<OntologyTerm>) result, sortOrder);

		} catch (InvocationTargetException e) {
			log.error(method.getName() + " throws " + e.getCause());
		}
		return result;
	}

	/**
	 * Ranked search on a regular OntologyService.searchAll output. All other
	 * methods are passed through. Trims the return list to ontologies supplied
	 * in sortOrder array and then sorts them using a custom
	 * OntologyRankComparator, which compares the position of the
	 * OntologyAccession against the sortOrder
	 * 
	 * @param result
	 *            the result
	 * @param sortOrder
	 *            the sort order
	 * 
	 * @return the list< ontology term>
	 * 
	 * @throws OntologyServiceException
	 *             the ontology service exception
	 */
	private List<OntologyTerm> searchAllRanked(List<OntologyTerm> result,
			List sortOrder) throws OntologyServiceException {
		// trim list	
		for (int i = result.size() - 1; i >= 0; i--) {
			if (!sortOrder.contains(result.get(i).getOntologyAccession()))
				result.remove(i);
		}
		// sort according to sortOrder
		Collections.sort(result, new OntologyRankComparator(sortOrder));
		return result;
	}

	/**
	 * The Class OntologyRankComparator.
	 */
	private class OntologyRankComparator implements Comparator<OntologyTerm> {

		private List sortOrder;

		/**
		 * Instantiates a new ontology rank comparator.
		 * 
		 * @param list
		 *            the list
		 */
		public OntologyRankComparator(List list) {
			sortOrder = list;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(OntologyTerm term0, OntologyTerm term1) {
			try {
				if (sortOrder.indexOf(term0.getOntologyAccession()) > sortOrder
						.indexOf(term1.getOntologyAccession())) {
					return -1;
				} else {
					return 1;
				}
			} catch (OntologyServiceException e) {
				return 0;
			}
		}
	}
}