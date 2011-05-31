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
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * The Class SortedSubsetDecorator. Decorator adding sorting and subsetting
 * capabilities to an OntologyService. Implemented using Java reflection dynamic
 * proxy pattern See the following link for more details
 * http://www.webreference.com/internet/reflection/3.html
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
	private List<String> sortOrder;

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(SortedSubsetDecorator.class);

	/**
	 * Instantiates a new sorted subset decorator.
	 * 
	 * @param obj
	 *            the obj
	 * @param sortOrder
	 *            the list of accessions
	 */
	private SortedSubsetDecorator(Object obj, List<String> sortOrder) {
		target = obj;
		this.sortOrder = sortOrder;
		// reverse the list as the first item
		// also has the lowest index
		Collections.reverse(this.sortOrder);
	}

	/**
	 * Creates the proxy.
	 * 
	 * @param obj
	 *            the obj
	 * @param sortOrder
	 *            the list of accessions
	 * 
	 * @return the object
	 * @throws OntologyServiceException
	 */
	private static Object createProxy(Object obj, List<String> sortOrder) throws OntologyServiceException {
		// If an exception if thrown here, the OntologyService
		// interface must have changed and you have to modify
		// the <searchAll> and <searchOntology> strings below
		// and in the invoke method
		try {
			obj.getClass().getMethod("searchAll", String.class, SearchOptions[].class);
			obj.getClass().getMethod("searchOntology", String.class, String.class,
					SearchOptions[].class);
		} catch (Exception e) {
			log.fatal("Signature has changed in proxy pattern!");
			throw new OntologyServiceException(e);
		}
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass()
				.getInterfaces(), new SortedSubsetDecorator(obj, sortOrder));
	}

	public static OntologyService getService(OntologyService os, List<String> sortOrder)
	throws OntologyServiceException {
		return (OntologyService) SortedSubsetDecorator.createProxy(os, sortOrder);
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
		Object result = null;
		try {
			// in case for searching a particular ontology
			// if it's accession is not in the imposed list
			// return nothing
			if (method.getName().equalsIgnoreCase("searchOntology")
					&& !sortOrder.contains(args[0])) {
				log.debug((String) args[0] + " ontology explicitly set outside scope");
				return null;
			}
			result = method.invoke(target, args);
			// sort the results if the method was searchAll
			if (method.getName().equalsIgnoreCase("searchAll")) {
				result = searchAllRanked((List<OntologyTerm>) result);
			}

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
	private List<OntologyTerm> searchAllRanked(List<OntologyTerm> result)
	throws OntologyServiceException {
		// trim list
		for (int i = result.size() - 1; i >= 0; i--) {
			if (!sortOrder.contains(result.get(i).getOntologyAccession())) {
				result.remove(i);
			}
		}
		// sort according to sortOrder
		Collections.sort(result, new OntologyRankComparator());
		// sort according to similarity
		// Collections.sort(result);
		return result;
	}

	/**
	 * The Class OntologyRankComparator.
	 */
	private class OntologyRankComparator implements Comparator<OntologyTerm> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(OntologyTerm term0, OntologyTerm term1) {
			if (sortOrder.indexOf(term0.getOntologyAccession()) > sortOrder.indexOf(term1
					.getOntologyAccession())) {
				return -1;
			}
			if (sortOrder.indexOf(term0.getOntologyAccession()) < sortOrder.indexOf(term1
					.getOntologyAccession())) {
				return 1;
			}
			return 0;
		}
	}
}