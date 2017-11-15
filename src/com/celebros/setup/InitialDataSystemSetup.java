/**
 *
 */
package com.celebros.setup;

import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetup.Process;
import de.hybris.platform.core.initialization.SystemSetup.Type;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;

import java.util.ArrayList;
import java.util.List;

import com.celebros.constants.CelebrosConstants;


/**
 * SystemSetup for Celebros Add-On
 */
@SystemSetup(extension = CelebrosConstants.EXTENSIONNAME)
public class InitialDataSystemSetup extends AbstractSystemSetup
{

	@Override
	public List<SystemSetupParameter> getInitializationOptions()
	{
		return new ArrayList<SystemSetupParameter>();
	}

	/**
	 * Create Essential data for Celebros Add-On
	 */
	@SystemSetup(type = Type.ESSENTIAL, process = Process.ALL)
	public void createProjectData(final SystemSetupContext context)
	{
		importImpexFile(context, "/celebros/import/essential/cms-slots.impex", true);
	}

}
