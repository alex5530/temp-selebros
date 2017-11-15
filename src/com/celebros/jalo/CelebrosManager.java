package com.celebros.jalo;

import com.celebros.constants.CelebrosConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.apache.log4j.Logger;

@SuppressWarnings("PMD")
public class CelebrosManager extends GeneratedCelebrosManager
{
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger( CelebrosManager.class.getName() );
	
	public static final CelebrosManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (CelebrosManager) em.getExtension(CelebrosConstants.EXTENSIONNAME);
	}
	
}
