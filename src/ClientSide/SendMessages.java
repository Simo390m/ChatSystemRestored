package ClientSide;

import ClientSide.ClientMain;
import com.sun.tools.javac.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendMessages implements Runnable {

     Socket socket;
     String name;
     Scanner scanner;
     String sendMessage;
     PrintWriter clientOut;
     private ThreadLock threadLock;


    public SendMessages(Socket socket, ThreadLock threadLock) {
        this.socket = socket;
        this.threadLock = threadLock;
    }

    @Override
    public void run()
    {
        try
        {
            clientOut = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(System.in);
            String outputMessage;

            while (!socket.isClosed())
            {
                while(!ClientMain.getIsAccepted())
                {
                    System.out.println("Indtast dit brugernavn: ");
                    String name = scanner.nextLine();
                    send("JOIN " + name + ", " + socket.getInetAddress().toString().substring(1) + ":" + socket.getPort());

                }
                while (ClientMain.getIsAccepted())
                {
                    outputMessage = scanner.nextLine();
                    switch (outputMessage)
                    {
                        case "exit":
                            send("EXIT");
                            System.out.println("Farvel" + name + " du har logget ud");
                            break;

                        case "list":
                            send("LIST");
                            break;

                        default:
                            send("DATA" + name + ": " + outputMessage);
                            break;
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Du har mistet forbindelsen");
           // ClientMain.closeConnection();
        }

    }
    public synchronized void send (String message)
    {
        clientOut.println(message);
    }
}
