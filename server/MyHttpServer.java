package mp1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import request.Handler;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        final String PORT = System.getenv().getOrDefault("SERVER_PORT", "8081");;
        ServerSocket serverSocket = new ServerSocket(Integer.valueOf(PORT));
        int id = 0;
        while (true) {
            Handler taskSolver = new Handler(
                serverSocket.accept(),
                id
            );
            id++;
            taskSolver.start();
        }
    }
} 
