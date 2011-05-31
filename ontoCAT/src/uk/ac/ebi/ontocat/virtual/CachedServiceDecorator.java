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
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * The Class CachedServiceDecorator. Decorator adding two caching layers to any
 * requests coming through the OntologyService Layer. All requests are first
 * checked against the first cache with 24h expiry, otherwise the request is
 * passed through to the original provider. If the original provider query
 * fails, the results are returned from the eternal cache if available.
 * <p>
 * Implemented using Java reflection dynamic proxy pattern See the following
 * link for more details http://www.webreference.com/internet/reflection/3.html
 * 
 * @author Tomasz Adamusiak, Niran Abeygunawardena
 */
public class CachedServiceDecorator implements InvocationHandler {
	// standard invocation
	// see http://www.webreference.com/internet/reflection/4.html
	/** The target. */
	private Object target;

	/** The Constant log. */
	private static final Logger log = Logger
	.getLogger(CachedServiceDecorator.class);

	private static Cache ServiceCache;
	private static Cache EternalCache;

	/**
	 * Instantiates a new sorted subset decorator.
	 * 
	 * @param obj
	 *            the obj
	 * @param list
	 *            the list
	 * @throws OntologyServiceException
	 */
	private CachedServiceDecorator(Object obj) throws OntologyServiceException {
		target = obj;
		// Initialise the cache singleton
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");
		CacheManager manager = CacheManager.create(getClass().getResource(
		"ehcache.xml"));
		ServiceCache = manager.getCache("OntologyServiceCache");
		EternalCache = manager.getCache("EternalServiceCache");

		if (ServiceCache == null || EternalCache == null) {
			throw new OntologyServiceException("Could not instantie the caches");
		}
	}

	public static void clearCache() {
		CacheManager.getInstance().clearAll();
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
	private static Object createProxy(Object obj)
	throws OntologyServiceException {
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
				.getClass().getInterfaces(), new CachedServiceDecorator(obj));
	}

	public static OntologyService getService(OntologyService os)
	throws OntologyServiceException {
		return (OntologyService) CachedServiceDecorator.createProxy(os);
	}

	// Cache all the methods via EHCACHE
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
		String cacheKey = method.getName() + ArgsToKey(args);

		// As the very least should return an empty collection
		if (method.getReturnType() == List.class) {
			result = Collections.emptyList();
		}
		if (method.getReturnType() == Map.class) {
			result = Collections.emptyMap();
		}
		if (method.getReturnType() == Set.class) {
			result = Collections.emptySet();
		}

		try {
			// add the result to cache if it's not there already
			if (ServiceCache != null && ServiceCache.get(cacheKey) == null) {
				Element el = new Element(cacheKey, method.invoke(target, args));
				ServiceCache.put(el);
				EternalCache.put(el);
			}
			// get the result from cache
			result = ServiceCache.get(cacheKey).getValue();

		} catch (InvocationTargetException e) {
			if (EternalCache != null && EternalCache.get(cacheKey) != null) {
				result = EternalCache.get(cacheKey).getValue();
				log.warn("Accessing eternal cache for " + cacheKey);
			} else {
				throw new OntologyServiceException(e);
			}
		}
		return result;
	}

	private String ArgsToKey(Object[] args) throws OntologyServiceException {
		String s = "";
		if (args == null) {
			return "";
		}
		for (Object arg : args) {
			if (arg instanceof String) {
				s += "|" + (String) arg;
			} else if (arg instanceof OntologyTerm || arg instanceof Ontology) {
				s += "|" + arg.toString();
			} else if (arg instanceof SearchOptions[]) {
				for (SearchOptions o : (SearchOptions[]) arg) {
					s += "|" + o.toString();
				}
			} else {
				log.fatal("Unrecognised parameter in cached call. Could not create key!");
				throw new OntologyServiceException(new Exception(
				"Unrecognised parameter in cached call."));
			}
		}
		return s;
	}

}