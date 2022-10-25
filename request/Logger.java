package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * this class provides methods to print useful information
 */
public class Logger {
	//a way of identifying in which thread the Logger Object is running
	private String prefix = "";
	/**
	 * Creates a Logger object with an id
	 * @param id identifier of the thread in which the Logger is running
	 */
	public Logger(int id) {
		this.prefix = String.format("[Thread-%d]", id);
	}
	
	/**
	 * Creates a Logger object without the need for an id
	 */
	public Logger() {			
	}
	
	/**
	 * Prints information in the format [Thread-id] [time of printing] |type of information| - message
	 * @param level type of information
	 * @param content message to print
	 * @requires this.id != null
	 */
	private void printWithLevel(String level, String content) {
		System.out.println(
				String.format("%s[%s] |%s| - %s", this.prefix, new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()), level, content)
		);
	}
	
	/**
	 * prints a given string in the format described in printWithLevel() of the type "INFO"
	 * @param content message to print
	 */
	public void info(String content) {
		this.printWithLevel("INFO", content);
	}
	
	/**
	 * prints a given string in the format described in printWithLevel() of the type "ERROR"
	 * @param content message to print
	 */
	public void error(String content) {
		this.printWithLevel("ERROR", content);
	}
	
	/**
	 * Print the content passed through the given BufferedReader
	 * @param in the BufferedReader which information is going to be printed
	 * @throws IOException
	 * @requires BufferedReader != null
	 */
	public void answer(BufferedReader in) throws IOException {
		System.out.println(
				String.format("[%s] |%s|", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()), "Server Answer")
				);
		String s = in.readLine();
		while (s != null) { 		
			System.out.println(s);
			if(in.ready()) {
				s = in.readLine();
			}
			else {
				return;
			}
		}
	}
}

