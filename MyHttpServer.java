package mp1;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import request.Handler;
import request.Util;
import java.util.LinkedList;
import java.util.List;

/**
 *this class runs a httpServer
 */
public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        //number of the port to access
        final String PORT = System.getenv().getOrDefault("SERVER_PORT", "8080");;
        ServerSocket serverSocket = new ServerSocket(Integer.valueOf(PORT));
        //threads running
        List<Integer> idRegistry = new LinkedList<>();
        int id = 0;
        //hashMap with the files stored in the server
        HashMap<String, String> contentDirectory = Util.getContentDirectory("directory");
        while (true) {
            Handler taskSolver = new Handler(serverSocket.accept(), id, contentDirectory, idRegistry);
            id++;
            taskSolver.start();
        }
    }

}
