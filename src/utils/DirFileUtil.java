/**
 * 
 */

package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class DirFileUtil {
	/**
	 * Listing all files in a directory. It calls the method listDir(dirName,
	 * ext) with ext = "*".
	 * 
	 * @param directory
	 *            the input directory name
	 * @return A list of filenames
	 */
	public static List<String> listDirectory(String directory) {
		return listDirectory(directory, "*");
	}

	/**
	 * Listing all files in a directory.
	 * 
	 * @param directory
	 *            the input directory name
	 * @param fileExtension
	 *            the input file extension
	 * @return A list of filenames
	 */
	public static List<String> listDirectory(String directory, String fileExtension) {
		List<String> res = null;

		File d = new File(directory);
		String[] children = d.list();

		if (children == null) {
			System.out.println("The specified directory does not exist or is not a directory!");
			return res;
		} else {
			res = new ArrayList<String>();

			if (fileExtension.equals("*") || fileExtension.equals(".*")) {
				res.addAll(Arrays.asList(children));
			} else {
				for (int i = 0; i < children.length; i++) {
					if (children[i].endsWith(fileExtension)) {
						res.add(children[i]);
					}
				}
			}
		}

		return res;
	}

	/**
	 * Getting full filenames (including directory) from a directory.
	 * 
	 * @param directory
	 *            the input directory name
	 * @param fileExtension
	 *            the input file extension
	 * @return A list of full filenames (including the directory)
	 */
	public static List<String> getFilenamesFromDirectory(String directory, String fileExtension) {
		List<String> res = null;
		List<String> files = listDirectory(directory, fileExtension);

		if (files.size() > 0) {
			res = new ArrayList<String>();
			for (int i = 0; i < files.size(); i++) {
				res.add(getFullFilename(directory, (String) files.get(i)));
			}
		}

		return res;
	}

	/**
	 * Combining a directory and a filename to return a full filename.
	 * 
	 * @param directory
	 *            the input directory name
	 * @param file
	 *            the input filename
	 * @return A full filename
	 */
	public static String getFullFilename(String directory, String file) {
		return normalizeDirectory(directory) + File.separator + file;
	}

	/**
	 * Normalizing a directory path. If the directory ends with "/" or "\", then
	 * remove it.
	 * 
	 * @param directory
	 *            the directory to be normalized
	 * @return The normalized directory without the path separator at the end
	 */
	public static String normalizeDirectory(String directory) {
		if (directory.endsWith(File.separator) || directory.endsWith("/")) {
			return directory.substring(0, directory.length() - 1);
		} else {
			return directory;
		}
	}
}
