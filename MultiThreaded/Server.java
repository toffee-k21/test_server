import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer() {
        return new Consumer<Socket> () {
            @Override
            public void accept (Socket clientSocket){
                try {
                    PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                    toClient.println("Hello from the server");
                    toClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
    public static void main(String[] args) throws IOException{
        Server server = new Server();
        int port = 8010;
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
        try {
            while (true) { 
                System.out.println("Server is listening to port" + port);
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
