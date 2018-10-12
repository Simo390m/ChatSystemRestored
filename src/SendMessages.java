import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendMessages implements Runnable {

    Socket socket;
    String name;
    Scanner scanner;
    String sendMessage;


    public SendMessages(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run()
    {
        try
        {
            PrintWriter clientOut = new PrintWriter(socket.getOutputStream(), false);
            scanner = new Scanner(System.in);

            while (!socket.isClosed())
            {
                if (scanner.hasNextLine())
                {
                    sendMessage = scanner.nextLine();
                    clientOut.println(sendMessage);
                    clientOut.flush();
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
