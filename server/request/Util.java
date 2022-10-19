package request;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Util {
	public static final String CRLF = "\r\n";
	
	public static HashMap<String, String> getContentDirectory () throws IOException {
		HashMap<String, String> contentDirectory = new HashMap<String, String>();
   
        Path directoryPath = FileSystems.getDefault().getPath("directory");    
        File folder = new File(directoryPath.toString());
        File[] files = folder.listFiles();
        for (File file : files) {
        	contentDirectory.put(file.toString(), Files.readString(Path.of(file.toString())));
		}
		
		return contentDirectory;	
	}
}
