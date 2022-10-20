package mp1;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import request.Handler;
import request.Util;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        final String PORT = System.getenv().getOrDefault("SERVER_PORT", "8080");;
        ServerSocket serverSocket = new ServerSocket(Integer.valueOf(PORT));
        int id = 0;
        
        HashMap<String, String> contentDirectory = Util.getContentDirectory("directory");
    
        while (true) {
            Handler taskSolver = new Handler(serverSocket.accept(), id, contentDirectory);
            id++;
            taskSolver.start();
        }
    }

}
