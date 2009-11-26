package uk.ac.ebi.ontoapi.ols;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontoapi.OntologyServiceException;
import uk.ac.ebi.ontoapi.OntologyTerm;
import uk.ac.ebi.ook.web.services.Query;

public class OlsOntologyTerm implements OntologyTerm {
	Logger logger = Logger.getLogger(OlsOntologyTerm.class);
	// handle to the service to lazy load more properties
	private final OlsOntologyService ols;

	private String termAccession;
	private String[] definitions;
	private String label;
	private Map<String, String[]> annotations;
	private final String ontologyAccession;
	private String[] synonyms;
	private List<OntologyTerm> parents;
	private List<OntologyTerm> children;

	@Override
	public String toString() {
		try {
			String metadataString = "";
			if (getAnnotations() != null)
				for (String key : getAnnotations().keySet()) {
					metadataString += "\n\tannotation[" + key + "]=" + toString(getAnnotations().get(key));
				}

			String relationString = "";
			if (getRelations() != null)
				for (String key : getRelations().keySet()) {
					relationString += "\n\trelation[" + key + "]=" + toString(getRelations().get(key));
				}

			String parentsString = "";
			for (int i = 0; i < getParents().size(); i++) {
				parentsString += (i > 0 ? "," : "") + getParents().get(i).getAccession();
			}

			String childrenString = "";
			for (int i = 0; i < getChildren().size(); i++) {
				childrenString += (i > 0 ? "," : "") + getChildren().get(i).getAccession();
			}

			return String.format("OlsOntologyTerm(accession=%s, label=%s, ontologyAccession=%s, " + "\n\tsynonyms=%s"
					+ "\n\tdefinitions=%s,\n\tparents=%s,\n\tchildren=%s,%s,%s)", getAccession(), getLabel(),
					getOntologyAccession(), toString(getSynonyms()), toString(getDefinitions()), parentsString,
					childrenString, metadataString, relationString);
		} catch (OntologyServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public OlsOntologyTerm(OlsOntologyService ols, String ontologyAccession, String termAccession, String termLabel) {
		this.ols = ols;
		this.ontologyAccession = ontologyAccession;
		this.termAccession = termAccession;
		this.label = termLabel;

	}

	@Override
	public String getAccession() {
		return termAccession;
	}

	@Override
	public String getOntologyAccession() {
		return ontologyAccession;
	}

	@Override
	public String[] getDefinitions() throws OntologyServiceException {
		if (definitions == null)
			definitions = getAnnotations().get("definition");
		return definitions;
	}

	@Override
	public String getLabel() throws OntologyServiceException {
		// get the label if not yet loaded
		// logger.debug("retrieving label '" + label + "' for " +
		// this.getAccession());
		if (label == null) {
			logger.debug("retrieving label for " + this.getAccession());
			try {
				label = ols.getQuery().getTermById(termAccession, ontologyAccession);
			} catch (RemoteException e) {
				throw new OntologyServiceException(e);
			}
		}
		return label;
	}

	@Override
	public Map<String, String[]> getAnnotations() throws OntologyServiceException {
		if (annotations == null) {
			try {
				Map result = ols.getQuery().getTermMetadata(this.getAccession(), this.getOntologyAccession());
				// clean out the String from String[]
				for (Object key : result.keySet()) {
					if (result.get(key) instanceof String) {
						result.put(key, new String[] {
							(String) result.get(key)
						});
					}
				}
				// assign
				annotations = result;
			} catch (RemoteException e) {
				throw new OntologyServiceException(e);
			}
		}
		return annotations;
	}

	@Override
	public String[] getSynonyms() throws OntologyServiceException {
		if (synonyms == null)
			synonyms = getAnnotations().get("exact_synonym");
		return synonyms;
	}

	private String toString(String[] array) {
		String result = null;
		if (array != null) {
			result = "";
			for (int i = 0; i < array.length; i++) {
				result += (i == 0 ? "" : ",") + array[i];
			}
		}
		return result;
	}

	@Override
	public Map<String, String[]> getRelations() throws OntologyServiceException {
		Query qs = ols.getQuery();

		Map<String, List<String>> temp = new LinkedHashMap<String, List<String>>();
		try {
			// xrefs
			Map<String, String> xrefs;

			xrefs = qs.getTermXrefs(getAccession(), getOntologyAccession());

			for (Entry e : xrefs.entrySet()) {
				if (temp.get(e.getKey()) == null)
					temp.put((String) e.getKey(), new ArrayList<String>());
				temp.get(e.getKey()).add((String) e.getValue());
			}

			// relations
			List<OntologyTerm> relations = ols.convert(qs.getTermRelations(getAccession(), getOntologyAccession()));
			for (OntologyTerm r : relations) {
				if (temp.get(r.getLabel()) == null)
					temp.put(r.getLabel(), new ArrayList<String>());
				temp.get(r.getLabel()).add(r.getAccession());
			}

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			throw new OntologyServiceException(e1);
		}
		Map<String, String[]> result = new LinkedHashMap<String, String[]>();
		for (String key : temp.keySet())
			result.put(key, temp.get(key).toArray(new String[temp.get(key).size()]));
		return result;
	}

	@Override
	public List<OntologyTerm> getChildren() throws OntologyServiceException {
		if (children == null) {
			try {
				children = ols.convert(ols.getQuery().getTermChildren(termAccession, ontologyAccession, 1, null));
			} catch (RemoteException e) {
				throw new OntologyServiceException(e);
			}
		}
		return children;
	}

	@Override
	public List<OntologyTerm> getParents() throws OntologyServiceException {
		if (parents == null) {
			try {
				parents = ols.convert(ols.getQuery().getTermParents(termAccession, ontologyAccession));
			} catch (RemoteException e) {
				throw new OntologyServiceException(e);
			}
		}
		return parents;
	}

	@Override
	public List<OntologyTerm> getTermPath() throws OntologyServiceException {
		List<OntologyTerm> path = new ArrayList<OntologyTerm>();

		// include searched acc in path
		path.add(this);

		boolean done = false;
		int iteration = 0;
		boolean parentFound = true;
		while (parentFound) {
			List<OntologyTerm> possibleParents = this.getParents();

			if (possibleParents.size() == 1) {
				path.add(possibleParents.get(0));
				termAccession = possibleParents.get(0).getAccession();
			} else {
				parentFound = false;
				for (OntologyTerm tryParent : possibleParents) {
					List<OntologyTerm> possibleChildren = this.getChildren();
					for (OntologyTerm tryChild : possibleChildren) {
						if (tryChild.getAccession().equals(termAccession)) {
							path.add(tryParent);
							// recurse
							termAccession = tryParent.getAccession();
							parentFound = true;
						}
					}
				}
			}

			// safety break for circluar relations
			iteration++;
			if (iteration > 100) {
				logger.error("findSearchTermPath(): TOO MANY ITERATIONS (" + iteration + "x)");
				done = true;
			}
		}

		// reverse order
		Collections.reverse(path);
		return path;
	}
}
