/**
 *
 */
package com.celebros.visitor;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;


/**
 *
 * Interface to visit all of a certain ComposedType and perform actions against each
 *
 * @param <T>
 */
public interface CelebrosVisitor<T extends ItemModel>
{
	/**
	 * visit each item and action it
	 *
	 * @param item
	 *           The item to action
	 * @throws FieldValueProviderException
	 */
	public void visit(T item) throws FieldValueProviderException;
}
