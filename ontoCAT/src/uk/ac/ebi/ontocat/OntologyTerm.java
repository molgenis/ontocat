package uk.ac.ebi.ontocat;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "term")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class OntologyTerm implements Serializable
{
	private String accession;
	private String label;
	private String ontologyAccession;

	public OntologyTerm() {
	}

	public OntologyTerm(String accession, String label, String ontologyAccession)
	{
		this.setAccession(accession);
		this.setLabel(label);
		this.setOntologyAccession(ontologyAccession);
	}
	
	/** Copy constructor 
	 * @throws OntologyServiceException */
	public OntologyTerm(OntologyTerm term) throws OntologyServiceException
	{
		this.setAccession(term.getAccession());
		this.setLabel(term.getLabel());
		this.setOntologyAccession(term.getOntologyAccession());
	}

	public String getAccession() throws OntologyServiceException
	{
		return accession;
	}

	public void setAccession(String accession)
	{
		this.accession = accession;
	}

	// public String[] getDefinitions() throws OntologyServiceException
	// {
	// return definitions;
	// }
	// public void setDefinitions(String[] definitions)
	// {
	// this.definitions = definitions;
	// }
	public String getLabel() throws OntologyServiceException
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getOntologyAccession() throws OntologyServiceException
	{
		return ontologyAccession;
	}

	public void setOntologyAccession(String ontologyAccession)
	{
		this.ontologyAccession = ontologyAccession;
	}

	// public String[] getSynonyms() throws OntologyServiceException
	// {
	// return synonyms;
	// }
	// public void setSynonyms(String[] synonyms)
	// {
	// this.synonyms = synonyms;
	// }
	// public Map<String, String[]> getAnnotations() throws
	// OntologyServiceException
	// {
	// return annotations;
	// }
	// public void setAnnotations(Map<String, String[]> annotations)
	// {
	// this.annotations = annotations;
	// }

	@Override
	public String toString()
	{
		try
		{
			return String.format("OntologyTerm(accession=%s, label=%s, ontologyAccession=%s)", getAccession(),
					getLabel(), getOntologyAccession());
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accession == null) ? 0 : accession.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime
				* result
				+ ((ontologyAccession == null) ? 0 : ontologyAccession
						.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OntologyTerm other = (OntologyTerm) obj;
		if (accession == null) {
			if (other.accession != null)
				return false;
		} else if (!accession.equals(other.accession))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (ontologyAccession == null) {
			if (other.ontologyAccession != null)
				return false;
		} else if (!ontologyAccession.equals(other.ontologyAccession))
			return false;
		return true;
	}

//	private String toString(String[] array)
//	{
//		String result = null;
//		if (array != null)
//		{
//			result = "";
//			for (int i = 0; i < array.length; i++)
//			{
//				result += (i == 0 ? "" : ",") + array[i];
//			}
//		}
//		return result;
//	}

}
