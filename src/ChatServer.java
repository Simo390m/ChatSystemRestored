import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class ChatServer
{
    private static int portNumber = 3333;

    private static ArrayList<ClientThread> clients = new ArrayList<>();

    public static void main(String[] args)
    {

        try
        {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while(true)
            {
                try
                {
                    Socket socket = serverSocket.accept();
                    ClientThread client = new ClientThread(clients, socket);
                    Thread thread = new Thread (client);
                    thread.start();
                    clients.add(client);
                }
                catch (IOException e)
                {
                    System.out.println("Accept failed on port: " + portNumber);
                }
            }


        }
        catch(IOException ioException)
        {
            System.out.println("Could not listen on port: " +portNumber);
        }
    }

}
