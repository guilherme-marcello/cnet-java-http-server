package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private String prefix;
	public Logger(int id) {
		this.prefix = String.format("[Thread-%d]", id);
	}

	public Logger() {			
	}

	private void printWithLevel(String level, String content) {
		System.out.println(
				String.format("%s[%s] |%s| - %s", this.prefix, new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()), level, content)
				);
	}

	public void info(String content) {
		this.printWithLevel("INFO", content);
	}

	public void error(String content) {
		this.printWithLevel("ERROR", content);
	}

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

