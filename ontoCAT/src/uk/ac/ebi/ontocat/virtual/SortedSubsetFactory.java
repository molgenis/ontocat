package uk.ac.ebi.ontocat.virtual;

import java.util.List;

import uk.ac.ebi.ontocat.OntologyService;

/**
 * A factory is needed to wrap around the SortedSubsetDecorator class properly
 * 
 * @author Tomasz Adamusiak
 */
public class SortedSubsetFactory {
	
	/**
	 * Gets the service.
	 * 
	 * @param os the os
	 * @param list the list
	 * 
	 * @return the service
	 */
	public static OntologyService getService(OntologyService os, List list) {
		return (OntologyService) SortedSubsetDecorator.createProxy(os,
				list);
	}
}
