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
package uk.ac.ebi.ontocat.bioportal.xmlbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.ontocat.OntologyTerm;

/**
 * @author Tomasz Adamusiak
 * 
 */
@SuppressWarnings("unused")
public class EntryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The string. */
	private ArrayList<String> strings;

	/** The list. */
	@SuppressWarnings("rawtypes")
	private ArrayList list;

	/** The Unmodifiable collection. */
	@SuppressWarnings({ "rawtypes" })
	private ArrayList UnmodifiableCollection;

	/** The counter. */
	private Integer counter;

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return strings.get(0);
	}

	public String getPath() {
		return strings.get(1);
	}

	/**
	 * Gets the list.
	 * 
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<String> getList() {
		try {
			if (list != null && list.size() > 0 && list.get(0) instanceof String) {
				return list;
			} else {
				return null;
			}
		} catch (ArrayStoreException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<OntologyTerm> getConceptBeans() {
		if (list.get(0) instanceof ConceptBean) {
			return list;
		} else {
			return null;
		}
	}
}
