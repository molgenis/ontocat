package uk.ac.ebi.ontocat.mediawiki.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="api")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaWikiResult
{
	public Query query;
	
	public Parse parse;

	public String toString()
	{
		return "MediaWikiResult(query="+query+", parse="+parse+")";
	}
}
