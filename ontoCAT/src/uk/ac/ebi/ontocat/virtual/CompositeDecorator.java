package uk.ac.ebi.ontocat.virtual;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

/**
 * The Class SortedSubsetDecorator. Decorator adding sorting and subsetting
 * capabilities to an OntologyService. Implemented using Java reflection dynamic
 * proxy pattern See the following link for more details {@link http
 * ://www.webreference.com/internet/reflection/3.html}
 * 
 * @author Tomasz Adamusiak
 */
@SuppressWarnings("unchecked")
public class CompositeDecorator implements InvocationHandler {
	// standard invocation
	// see http://www.webreference.com/internet/reflection/4.html
	/** The target. */
	private Object target;

	/** The sort order. */
	private List sortOrder;

	private List<OntologyService> ontoServices;

	/** The Constant log. */
	private static final Logger log = Logger
			.getLogger(CompositeDecorator.class);

	/**
	 * Instantiates a new sorted subset decorator.
	 * 
	 * @param obj
	 *            the obj
	 * @param list
	 *            the list
	 */
	private CompositeDecorator(List<OntologyService> ontoServices) {
		this.ontoServices = ontoServices;
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
		// the <searchAll> strings below and in the invoke method
		try {
			obj.getClass().getMethod("searchAll", new Class[] { String.class });
			obj.getClass().getMethod("getOntologies");
		} catch (Exception e) {
			e.printStackTrace();
			throw new OntologyServiceException(e);
		}
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
				.getClass().getInterfaces(), new CompositeDecorator(list));
	}

	public static OntologyService getService(List list)
			throws OntologyServiceException {
		// instantiate OLSService,
		// it's never used but need an instance of OntologyService interface
		// to properly reflect, could use anything else, or instantiate
		// a private type
		return (OntologyService) CompositeDecorator.createProxy(
				new OlsOntologyService(), list);
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
		Object result;
		ExecutorService ec = Executors.newCachedThreadPool();

		// Create tasks
		Collection<InvokeTask> tasks = new ArrayList<InvokeTask>();
		for (OntologyService os : ontoServices) {
			tasks.add(new InvokeTask(os, method, args));
		}

		// Run tasks in parallel

		if (method.getName().equalsIgnoreCase("searchAll")
				|| method.getName().equalsIgnoreCase("getOntologies")) {
			// searchAll or getOntologies so combine results
			result = new ArrayList();
			for (Future<Object> future : ec.invokeAll(tasks)) {
				((List) result).addAll((Collection) future.get());
			}
		} else {
			// Any valid results will do, don't wait for the others
			result = ec.invokeAny(tasks);
		}
		ec.shutdown();
		return result;
	}

	private class InvokeTask implements Callable<Object> {
		private Object proxy;
		private Method method;
		private Object[] args;

		public InvokeTask(Object proxy, Method method, Object[] args) {
			this.proxy = proxy;
			this.method = method;
			this.args = args;
		}

		@Override
		public Object call() throws Exception {
			Object result = method.invoke(proxy, args);
			if (result == null)
				throw new OntologyServiceException("No results from "
						+ method.getName());
			return result;
		}
	}

}