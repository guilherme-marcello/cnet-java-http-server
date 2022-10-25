package request;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * this class provides useful utilities 
 */
public class Util {
	/**
	 * string with the carriage return and line-feed
	 */
	public static final String CRLF = "\r\n";
	
	/**
	 * returns a hashMap where the key are the names of the files in a 
	 * given directory and the values are the files content
	 * @param directory the name of the directory to map
	 * @return hashMap of the file in a given directory
	 * @throws IOException
	 */
	public static HashMap<String, String> getContentDirectory (String directory) throws IOException {
		HashMap<String, String> contentDirectory = new HashMap<String, String>();
   
        Path directoryPath = FileSystems.getDefault().getPath(directory);    
        File folder = new File(directoryPath.toString());
        File[] files = folder.listFiles();
        for (File file : files) {
        	contentDirectory.put("/" + file.getName(), Files.readString(Path.of(file.toString())));
		}
		
		return contentDirectory;	
	}
}
