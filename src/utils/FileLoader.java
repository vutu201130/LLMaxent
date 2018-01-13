package utils;
/**
 * 
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class FileLoader {
	/**
	 * Opening a resource file and returning a BufferedReader object for reading
	 * data.
	 * 
	 * @param resourceFile
	 *            the name of the resource file; the name should include a path
	 *            to the file so that the system can search it in a folder or in
	 *            the classpath entries
	 * @param streamEncoding
	 *            the encoding of the stream
	 * @return A BufferedReader object for reading data or null if failed to
	 *         open the file
	 */
	public static BufferedReader getBufferredReader(String resourceFile, String streamEncoding) {
		BufferedReader bufferedReader = null;
		String encoding = (streamEncoding == null) ? "UTF8" : streamEncoding;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(resourceFile), encoding));

		} catch (Exception ex) {
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(
						Class.class.getResourceAsStream(resourceFile), encoding));

			} catch (Exception ex1) {
				System.err.println(ex1.toString());
				bufferedReader = null;
			}
		}

		return bufferedReader;
	}

	/**
	 * Opening a resource file and returning a BufferedReader object for reading
	 * data.
	 * 
	 * @param resourceDirectory
	 *            the resource directory
	 * @param resourceFile
	 *            the resource filename
	 * @param streamEncoding
	 *            the encoding, the default is "UTF8"
	 * @return A BufferredReader object for reading data or null if failed to
	 *         open the file
	 */
	public static BufferedReader getBufferredReader(String resourceDirectory, String resourceFile, String streamEncoding) {
		BufferedReader bufferedReader = null;
		String encoding = (streamEncoding == null) ? "UTF8" : streamEncoding;
		String normalizedDirectory = DirFileUtil.normalizeDirectory(resourceDirectory);
		String filename;

		try {
			filename = normalizedDirectory + File.separator + resourceFile;
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding));

		} catch (Exception ex) {
			try {
				filename = normalizedDirectory + "/" + resourceFile;
				bufferedReader = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream(filename),
						encoding));

			} catch (Exception ex1) {
				try {
					filename = normalizedDirectory + File.separator + resourceFile;
					bufferedReader = new BufferedReader(new InputStreamReader(
							Class.class.getResourceAsStream(filename), encoding));

				} catch (Exception ex2) {
					System.err.println(ex2.toString());
					bufferedReader = null;
				}
			}
		}

		return bufferedReader;
	}

	/**
	 * Getting an input stream of a resource file.
	 * 
	 * @param resourceFile
	 *            the name of the resource file
	 * @return The reference to the input stream object corresponding to the
	 *         resource file
	 */
	public static InputStream getInputStream(String resourceFile) {
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(resourceFile);

		} catch (Exception ex) {
			try {
				inputStream = Class.class.getResourceAsStream(resourceFile);

			} catch (Exception ex1) {
				System.err.println(ex1.toString());
				inputStream = null;
			}
		}

		return inputStream;
	}

	/**
	 * Getting an input stream of a resource file.
	 * 
	 * @param resourceDirectory
	 *            the resource directory
	 * @param resourceFile
	 *            the resource file
	 * @return An InputStream object for reading data or null if failed to open
	 *         the file
	 */
	public static InputStream getInputStream(String resourceDirectory, String resourceFile) {
		InputStream inputStream = null;
		String normalizedDirectory = DirFileUtil.normalizeDirectory(resourceDirectory);
		String filename;

		try {
			filename = normalizedDirectory + File.separator + resourceFile;
			inputStream = new FileInputStream(resourceFile);

		} catch (Exception ex) {
			try {
				filename = normalizedDirectory + "/" + resourceFile;
				inputStream = Class.class.getResourceAsStream(filename);

			} catch (Exception ex1) {
				try {
					filename = normalizedDirectory + File.separator + resourceFile;
					inputStream = Class.class.getResourceAsStream(filename);

				} catch (Exception ex2) {
					System.err.println(ex2.toString());
					inputStream = null;
				}
			}
		}

		return inputStream;
	}

	/**
	 * Reading data from an input BufferedReader object.
	 * 
	 * @param bufferedReader
	 *            the BufferedReader object from which the data will be read
	 * @return A list of lines read from the input buffered reader
	 */
	public static List<String> readFromBufferedReader(BufferedReader bufferedReader) {
		List<String> lines = new ArrayList<String>();

		if (bufferedReader == null) {
			return lines;
		}

		try {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}

			bufferedReader.close();

		} catch (IOException e) {
			System.err.println(e.toString());
		}

		return lines;
	}

	/**
	 * Reading a resource file and return a list of lines from that file.
	 * 
	 * @param resourceFile
	 *            the resource filename; this filename needs containing an
	 *            absolute or relative path to the file
	 * @param streamEncoding
	 *            the encoding of the file, default is "UTF8"
	 * @return A list of lines from the file
	 */
	public static List<String> readFile(String resourceFile, String streamEncoding) {
		return readFromBufferedReader(getBufferredReader(resourceFile, streamEncoding));
	}

	/**
	 * Reading a resource file and return a list of lines from that file.
	 * 
	 * @param resourceDirectory
	 *            the directory containing the resource file
	 * @param resourceFile
	 *            the resource filename
	 * @param streamEncoding
	 *            the encoding of the file, default is "UTF8"
	 * @return A list of lines read from the file
	 */
	public static List<String> readFile(String resourceDirectory, String resourceFile, String streamEncoding) {
		return readFromBufferedReader(getBufferredReader(resourceDirectory, resourceFile, streamEncoding));
	}
}
