package ServerSide;

import ClientSide.SendMessages;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ClientThread extends ChatServer implements Runnable
{
    private ServerSocket serverSocket;
    private Socket socket;
    private ArrayList<ClientThread> clientThreads;
    private BufferedReader clientIn;
    private PrintWriter clientOut;
    private boolean isAccepted = false;
    private String input;
    private String username;
    private SendMessages sendMessages;

    public ClientThread (ArrayList<ClientThread> clientThreads, Socket socket, ServerSocket serverSocket)
    {
        this.clientThreads = clientThreads;
        this.socket = socket;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run()
    {

        try
        {
            clientOut = new PrintWriter(socket.getOutputStream(), true);
            clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true)
            {
                while(!isAccepted)
                {
                    input = clientIn.readLine();
                    System.out.println(input);


                    switch (input.substring(0,4))
                    {
                        case "JOIN":
                            String[] messageString = input.split(", ");
                            String attemptedUsername = messageString[0].split(" ")[1];

                            if (validator(attemptedUsername))
                            {
                                String[] socketInformation = messageString[1].split(":");

                                if (InetAddress.getLocalHost().toString().split("/")[1].equals(socketInformation[0]) ||
                                        InetAddress.getLoopbackAddress().toString().split("/")[1].equals(socketInformation[0]) &&
                                                Integer.parseInt(socketInformation[1]) == serverSocket.getLocalPort())
                                {
                                    for (ClientThread clientThread : ChatServer.getClients())
                                    {
                                        if (clientThread.username == attemptedUsername && clientThread != this)
                                        {
                                            send("J_ER1: Navnet du har skrevet er i brug");
                                            break;
                                        }

                                    }
                                    this.username = attemptedUsername;
                                    isAccepted = true;
                                    send("J_OK");
                                } else
                                {
                                    send("J_ER2 Fejl i Socket eller port");
                                }
                            } else
                            {
                                send("J_ER3 Navnet du har skrevet er i et forkert Format");
                            }
                            break;
                        default:
                            send("J_ER4 Du er ikke accepteret");
                            break;

                    }

                }
                while (isAccepted)
                {
                    String input = clientIn.readLine();
                    if (input != null)
                    {
                        for (ClientThread client : clientThreads)
                        {
                            PrintWriter clientOut = client.getClientOut();
                            if (clientOut != null)
                            {
                                clientOut.write(input + "\n");
                                clientOut.flush();
                            }
                        }
                    }
                }
            }
        }


        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
    public static boolean validator(String attemptedUsername)
    {
        String regex = "[a-zA-Z]+" ;
        if(attemptedUsername.matches(regex));
        {
            if(attemptedUsername.length() > 1 && attemptedUsername.length() < 12 )
            {
                return true;
            }


        }
        return false;
    }

    public PrintWriter getClientOut()
    {
        return clientOut;
    }
    public synchronized void send (String message)
    {
        clientOut.println(message);
    }
}
