package mp1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import request.Handler;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        final String PORT = System.getenv().getOrDefault("SERVER_PORT", "81");
        ServerSocket serverSocket = new ServerSocket(Integer.valueOf(PORT));
        List<Integer> idRegistry = new LinkedList<>();
        int id = 0;
        while (true) {
            Handler taskSolver = new Handler(
                serverSocket.accept(),
                id,idRegistry
            );
            id++;
            taskSolver.start();
        }
    }
} 

