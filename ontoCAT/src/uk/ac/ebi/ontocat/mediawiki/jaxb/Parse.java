package uk.ac.ebi.ontocat.mediawiki.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Parse
{
	public String text;
	
	public String toString()
	{
		return "Parse(text="+text+")";
	}
}
