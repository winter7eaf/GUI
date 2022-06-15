import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException {
        //Open the server socket
        ServerSocket serverSocket = new ServerSocket(Credentials.PORT);
        System.out.println("Server is running");

        //Create a Database object and check the connection with establishDBConnection():
        Database database = new Database();
        Boolean checkConnection = database.establishDBConnection();
        //If the db connection fails, print:
        if (checkConnection.equals(false)) {
            System.out.println("DB connection fail, stopping.");
        } else {
            System.out.println("Server is now connected to DB");
        }

        //Increment clientId
        int clientId = 0;
        //Continuously listen for client requests
        while (true) {
            //Accept new connection and create the client socket
            Socket clientSocket = serverSocket.accept();
            //The clientId is not reassigned once used.
            clientId++;
            //Display clientId and IP address:
            System.out.println("Client " + clientId + " connected with IP " + clientSocket.getInetAddress().getHostAddress());
            //Create a new client handler object and start the thread
            ClientHandler clientHandler = new ClientHandler(clientSocket, clientId, database);
            new Thread(clientHandler).start();
        }
    }
}

