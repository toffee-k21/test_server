import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Server {
    public void run() throws IOException {
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);
        socket.setSoTimeout(10000);
        Path path = Paths.get("../static/read.txt");
        List <String> lines = Files.readAllLines(path);
        while (true) { 
            try {
                System.out.println("Server is listening to port" + port);
                Socket acceptedConnection = socket.accept();
                System.out.println("Connection accepted from client"+acceptedConnection.getRemoteSocketAddress());
                PrintWriter toClient =  new PrintWriter(acceptedConnection.getOutputStream());
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
                toClient.println("Hello from the server ! -> sending each lines one by one && here we go...");
                for(String line : lines){
                    toClient.println(line);
                }
                toClient.close();
                fromClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}