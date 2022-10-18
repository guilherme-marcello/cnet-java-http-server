import java.io.IOException;
import java.util.Scanner;

/**
 * This is a class meant to test the client and server
 * applications to be developed within the mini-project MP1
 * of the course RC-LEI 22-23s.
 *
 * @author Salvatore Signorello
 *
 **/
public class TestMP1 {

	// Array to store the menu's options
    private static String[] menuOptions = {"0- Exit",
    		"1- GET request for an existing webpage",
    		"2- GET request for a non-existing webpage",
    		"3- POST request",
            "4- Request with an unimplemented method",
            "5- Malformed GET request",
    };
    
    // Auxiliary method used to print the menu on stdout
    public static void printMenu(String[] options){
    	
        for (String option : options){
        	System.out.println(option);
        }
        
        System.out.print("Choose your option : ");
    
    }
    
	public static void main(String[] args) {

		// if wrong number of arguments is provided, then
		// print the Usage message and terminate the client
		if (args.length != 2) {
			
			System.err.println("Usage: java EchoClient <host name> <port number>");
			System.exit(1);
		
		}

		// Parse the provided arguments
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);

		MyHttpClient myClient;
		
		try {
			
			// Create a client and open a connection to the server
			myClient = new MyHttpClient(hostName, portNumber);
			System.out.println("Connected to " + hostName + ":" + portNumber);
			
			// Integer to store the option selected from the menu
			int option = 0;
			
			while(true) {

				// Prepare to read from the standard input and print the menu
				Scanner stdin = new Scanner(System.in);
				printMenu(menuOptions);
				option = stdin.nextInt();

				switch(option) {

				case 1: 
					myClient.getResource("index.html"); // this tests the Server's ability to reply with an ok code and with the required content
					break;

				case 2:
					myClient.getResource("nonExisting.html"); // this tests the Server's ability to reply with a content not found message
					break;

				case 3: 
					String[] formDAta = {"StudentName: Tim Berners-Lee","StudentID: fcXXXXX"};
					myClient.postData(formDAta); // this should reply with, guess what? :)
					break;

				case 4:
					myClient.sendUnimplementedMethod("SEARCH"); // ask for anything except GET and POST and you should get a 5xx error message
					break;

				case 5:
					System.out.print("Enter the type of request 1 or 2 or 3: ");
					int typeOfRequest = stdin.nextInt();
					myClient.malformedRequest(typeOfRequest); // send a malformed GET request of the selected type
					break;

				case 0:
					// free the resources and terminates the client
					myClient.close();
					stdin.close();
					System.exit(0);


				}
			}
			
		} catch (IOException e) {
			
			// something went wrong went connecting to the server or during a method call
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
			
		}

	}

}