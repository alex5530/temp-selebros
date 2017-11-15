/**
 *
 */
package com.celebros.hmc;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.hmc.attribute.AttributeValueChangeEvent;
import de.hybris.platform.hmc.attribute.SelectLayoutChip;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.google.common.collect.Lists;


public class CelebrosProductTypeSelectEditorChip extends SelectLayoutChip
{

	public CelebrosProductTypeSelectEditorChip(final DisplayState displayState, final Chip parent)
	{
		super(displayState, parent);
	}

	@Override
	protected String toString(final Object obj)
	{
		if (obj instanceof ComposedTypeModel)
		{
			final ComposedTypeModel type = (ComposedTypeModel) obj;
			return type.getCode();
		}
		else
		{
			return "";
		}
	}



	@Override
	protected List getAllValues()
	{

		final ApplicationContext applicationContext = Registry.getGlobalApplicationContext();

		final FlexibleSearchService flexibleSearchService = (FlexibleSearchService) applicationContext
				.getBean("flexibleSearchService");
		final FlexibleSearchQuery query = new FlexibleSearchQuery("Select {pk} from {ComposedType} where {code}='Product'");
		final SearchResult<ComposedTypeModel> result = flexibleSearchService.search(query);
		if (!result.getResult().isEmpty())
		{
			final ComposedTypeModel productType = result.getResult().get(0);
			final List types = Lists.newArrayList();
			types.add(productType);
			types.addAll(productType.getAllSubTypes());
			return types;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setValue(final int pos)
	{
		super.setValue(pos);
	}

	@Override
	public void setValue(final Object value)
	{
		if (!(valuesAreDifferent(this.getValue(), value)))
		{
			return;
		}
		final Object oldValue = this.getValue();

		super.setValue(value);

		notifyAttributeValueChangeListeners(new AttributeValueChangeEvent(oldValue, value, this));
	}

	@Override
	public boolean isSelected(final int pos)
	{
		try
		{
			String test = "test";
			String control = "control";
			final Object testObj = getAllValues().get(pos);
			final Object controlObj = getValue();
			final Method getTestCode = testObj.getClass().getMethod("getCode", null);
			final Method getControlCode = controlObj.getClass().getMethod("getCode", null);
			test = (String) getTestCode.invoke(testObj, null);
			control = (String) getControlCode.invoke(controlObj, null);
			return test.equals(control);
		}
		catch (final Exception e)
		{
			return false;
		}
	}

}
