import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient
{

    private static final String host = "localhost";
    private static final int portNumber = 3333;

    private String userName;
    private String serverHost;
    private int serverPort;
    private Scanner userInputScanner;


    public static void main(String[] args)

    {
        String readName = null;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input username: " );
        while (readName == null || readName.trim().equals("")){
            readName = scan.nextLine();
            if(readName.trim().equals("")){
                System.out.println("Invalid. Please enter again:");
            }
        }
        ChatClient client = new ChatClient(readName, host, portNumber);
        client.startClient(scan);
    }


    private ChatClient(String userName, String host, int portNumber){
        this.userName = userName;
        this.serverHost = host;
        this.serverPort = portNumber;
    }

    private void startClient (Scanner scan){
        try{
            Socket socket = new Socket(serverHost, serverPort);
            Thread.sleep(1000);

            ServerThread serverThread = new ServerThread(socket, userName);
            Thread serverAccessThread = new Thread(serverThread);
            Thread HeartbeatThread = new Thread(serverThread);
            serverAccessThread.start();

            while (serverAccessThread.isAlive()){
                if (scan.hasNextLine()){
                    serverThread.addNextMessage(scan.nextLine());
                }

            }
            while (serverAccessThread.isAlive())
            {

            }

        }
        catch (IOException ex){
            System.err.println("Fatal connection error");
            ex.printStackTrace();
        }
        catch (InterruptedException ex){
            System.out.println("Interrupted ");
        }
    }
}
