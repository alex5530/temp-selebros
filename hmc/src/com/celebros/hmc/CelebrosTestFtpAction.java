/**
 *
 */
package com.celebros.hmc;

import de.hybris.platform.hmc.util.action.ActionEvent;
import de.hybris.platform.hmc.util.action.ActionResult;
import de.hybris.platform.hmc.util.action.ItemAction;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

import com.celebros.jalo.CelebrosExportCronjob;


/**
 * @author jamiebartlett
 *
 */
public class CelebrosTestFtpAction extends ItemAction
{
	public final static String successString = "Ftp Connection tested successfully";
	public final static String failString = "Error in FTP Connection, check logs";

	@Override
	public ActionResult perform(final ActionEvent event) throws JaloBusinessException
	{
		final Item item = getItem(event);
		if (!(item instanceof CelebrosExportCronjob))
		{
			throw new JaloBusinessException("Not allowed data type " + item);
		}
		final CelebrosExportCronjob cronjob = (CelebrosExportCronjob) item;


		final int result = testFtp(cronjob);
		if (result == ActionResult.OK)
		{
			return new ActionResult(result, successString, false, true);
		}
		else
		{
			return new ActionResult(result, failString, false, true);
		}
	}

	private int testFtp(final CelebrosExportCronjob cronjob)
	{
		int success = ActionResult.FAILED;
		final FTPClient ftpClient = new FTPClient();
		try
		{
			ftpClient.connect(cronjob.getRemoteHost(), cronjob.getRemotePort().intValue());
			ftpClient.login(cronjob.getUsername(), cronjob.getPassword());
			ftpClient.changeWorkingDirectory(cronjob.getRemoteLocation());
			if (ftpClient.getReplyCode() < 400)
			{
				success = ActionResult.OK;
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ftpClient.isConnected())
				{
					ftpClient.logout();
					ftpClient.disconnect();
				}
			}
			catch (final IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return success;
	}
}
