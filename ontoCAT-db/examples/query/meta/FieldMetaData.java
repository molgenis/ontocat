package plugins.query.meta;


public interface FieldMetaData {

	EntityMetaData getEntityMetaData();

	String getXRefEntityName();

	String getXRefFieldName();
	
	String getName();

	boolean isXref();

	EntityMetaData getXRefEntity();

}