package uk.ac.ebi.ontocat.virtual;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.Ontology;
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
public class CachedServiceDecorator implements InvocationHandler {
	// standard invocation
	// see http://www.webreference.com/internet/reflection/4.html
	/** The target. */
	private Object target;

	/** The sort order. */
	private List sortOrder;

	/** The Constant log. */
	private static final Logger log = Logger
			.getLogger(BioportalOntologyService.class.getName());

	private static Cache ServiceCache;
	private static CacheManager manager;

	/**
	 * Instantiates a new sorted subset decorator.
	 * 
	 * @param obj
	 *            the obj
	 * @param list
	 *            the list
	 */
	private CachedServiceDecorator(Object obj) {
		target = obj;
		// Initialise the cache
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");
		manager = new CacheManager(getClass().getResource("ehcache.xml"));
		ServiceCache = manager.getCache("OntologyServiceCache");
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
		try {

			String cacheKey = method.getName() + ArgsToKey(args);

			// add the result to cache if it's not there already
			if (ServiceCache != null && ServiceCache.get(cacheKey) == null) {
				ServiceCache.put(new Element(cacheKey, method.invoke(target,
						args)));
			}
			// get the result from cache
			result = ServiceCache.get(cacheKey).getValue();

		} catch (InvocationTargetException e) {
			log.error(method.getName() + " throws " + e.getCause());
		}
		return result;
	}

	private String ArgsToKey(Object[] args) throws OntologyServiceException {
		String s = "";
		if (args == null)
			return "";
		for (Object arg : args) {
			if (arg instanceof String) {
				s += "|" + (String) arg;
			} else if (arg instanceof OntologyTerm || arg instanceof Ontology) {
				s += "|" + arg.toString();
			} else {
				throw new OntologyServiceException(new Exception(
						"UNRECOGNISED PARAMETER IN CACHED CALL"));
			}
		}
		return s;
	}

}