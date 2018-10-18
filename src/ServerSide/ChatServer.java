package ServerSide;

import ClientSide.SendMessages;

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
            System.out.println("Server er startet");
            while(true)
            {
                try
                {
                    System.out.println("Venter på clients");
                    Socket socket = serverSocket.accept();
                    ClientThread clientThread = new ClientThread(clients, socket, serverSocket);
                    Thread thread = new Thread (clientThread);
                    thread.start();
                    clients.add(clientThread);
                    System.out.println("Client tilføjet");
                    System.out.println(clients.get(0));
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

    public static ArrayList<ClientThread> getClients()
    {
        return clients;
    }

}
