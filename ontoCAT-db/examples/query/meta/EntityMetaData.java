package plugins.query.meta;

import java.util.List;

public interface EntityMetaData {

	List<FieldMetaData> getFields();

	String getName();

	List<FieldMetaData> getAllFields();

	List<EntityMetaData> getExtends();

	FieldMetaData getFieldMetaData(String fieldName, boolean includeExtended);

	FieldMetaData getPrimaryKey();
	 
	MolgenisMetaData getDatabaseMetaData();

}
