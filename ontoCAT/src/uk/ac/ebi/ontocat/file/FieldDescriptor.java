/*
 * 
 */
package uk.ac.ebi.ontocat.file;

// TODO: Auto-generated Javadoc
/**
 * Convenience class used to store URI fragments for synonyms, definitions and
 * labels as these are different between ontologies.
 */
public class FieldDescriptor {
	
	/** The synonym slot. */
	public String synonymSlot;
	
	/** The definition slot. */
	public String definitionSlot;
	
	/** The label slot. */
	public String labelSlot;

	/**
	 * Instantiates a new field descriptor.
	 * 
	 * @param synFragment the syn fragment
	 * @param defFragment the def fragment
	 * @param labelFragment the label fragment
	 */
	public FieldDescriptor(String synFragment, String defFragment,
			String labelFragment) {
		synonymSlot = synFragment;
		definitionSlot = defFragment;
		labelSlot = labelFragment;
	}
}
