package wrappers;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import uk.ac.ebi.ontocat.OntologyTerm;

@XmlRootElement(name="terms")
@XmlAccessorType(XmlAccessType.FIELD)
public class OntologyTermList
{
	@XmlElement(name="term")
	public List<OntologyTerm> terms = new ArrayList<OntologyTerm>();
	
	public OntologyTermList()
	{
		
	}
	
	public OntologyTermList(List<OntologyTerm> terms)
	{
		this.terms = terms;
	}

}
