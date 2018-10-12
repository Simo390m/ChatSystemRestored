import java.net.*;
import java.io.*;

public class ClientThread extends ChatServer implements Runnable
{
    private Socket socket;
    private ChatServer chatserver;
    private BufferedReader clientIn;

    private PrintWriter clientOut;

    public ClientThread (ChatServer chatServer, Socket socket)
    {
        this.socket = socket;
        this.chatserver = chatServer;
    }

    @Override
    public void run()
    {
        try
        {
            clientOut = new PrintWriter(socket.getOutputStream(), false);
            clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (!socket.isClosed())
            {
                String input = clientIn.readLine();
                if (input != null)
                {
                    for (ClientThread client : chatserver.getClients())
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
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public PrintWriter getClientOut()
    {
        return clientOut;
    }
}
