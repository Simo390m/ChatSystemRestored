package ClientSide;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendMessages implements Runnable {

     Socket socket;
     String username;
     Scanner scanner;
     PrintWriter clientOut;



    public SendMessages(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public synchronized void run()
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

                        try
                        {
                            wait();
                        }
                    catch (InterruptedException interruptedException)
                    {
                        interruptedException.printStackTrace();
                    }

                }
                while (ClientMain.getIsAccepted())
                {
                    System.out.println("SKRIV NOGET NU");
                    outputMessage = scanner.nextLine();
                    switch (outputMessage)
                    {
                        case "exit":
                            send("EXIT");
                            System.out.println("Farvel " + username + " du har logget ud");
                            break;

                        case "list":
                            send("LIST");
                            break;

                        default:
                            send("DATA" + username + ": " + outputMessage);
                            break;
                    }
                }
            }
        }
        catch (IOException e )
        {
            System.out.println("Du har mistet forbindelsen");
           // ClientMain.closeConnection();
        }
    }

    public synchronized void send (String message)
    {
        clientOut.println(message);
    }

    public void resumeThread() {
        synchronized(this) {
            this.notify();
        }
    }
    public synchronized void closeOut(){
        clientOut.close();
    }
}
