/**
 *
 */
package com.celebros.gateway;

import com.celebros.data.CelebrosExportData;
import com.celebros.data.CelebrosUploadData;


/**
 * Gateway used to export Celebros files to the filesystem and remote location
 */
public interface CelebrosGateway
{
	/**
	 * Saves the Celebros export file to the filesystem
	 *
	 * @param export
	 *           CelebrosExportData describing the file.
	 */
	void saveExport(CelebrosExportData export);

	/**
	 * Compresses and uploads the Celebros file to the remote location
	 *
	 * @param upload
	 *           CelebrosUploadData describing the files and their destination
	 * @return Indication of success
	 */
	boolean zipAndUpload(CelebrosUploadData upload);
}
