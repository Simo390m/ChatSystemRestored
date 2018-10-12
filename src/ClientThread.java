import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ClientThread extends ChatServer implements Runnable
{
    private Socket socket;
    private ArrayList<ClientThread> clientThreads;
    private BufferedReader clientIn;

    private PrintWriter clientOut;

    public ClientThread (ArrayList<ClientThread> clientThreads, Socket socket)
    {
        this.clientThreads = clientThreads;
        this.socket = socket;
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
