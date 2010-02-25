package wrappers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="annotations")
@XmlAccessorType(XmlAccessType.FIELD)
public class OntologyTermAnnotationList
{
	public OntologyTermAnnotationList()
	{
		
	}
	
	public OntologyTermAnnotationList(Map<String,List<String>> annotationMap)
	{
		for(String key: annotationMap.keySet())
		{
			for(String value: annotationMap.get(key))
			{
				this.annotations.add(new OntologyTermAnnotation(key, value));
			}
		}
	}

	@XmlElement(name="annotation")
	public List<OntologyTermAnnotation> annotations = new ArrayList<OntologyTermAnnotation>();
	
}
