package plugins.query.meta;

import java.util.List;

public interface MolgenisMetaData {

	FieldMetaData getFieldMetaData(String f) throws MetaDataException;

	EntityMetaData getEntityMetaData(String e) throws MetaDataException;

	List<EntityMetaData> getEntities();

}