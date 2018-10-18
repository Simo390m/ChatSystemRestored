package ClientSide;

import ServerSide.ClientThread;

import javax.sound.sampled.ReverbType;
import java.io.IOException;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ClientMain {



    private static boolean isAccepted;
    private SendMessages sendMessages;
    private RecieveMessages recieveMessages;
    private Socket socket;
    private static ThreadLock threadLock;

    public static void main(String[] args)

    {
        try
        {
            Socket socket = new Socket("127.0.0.1", 3333);

            SendMessages sendMessages = new SendMessages(socket, threadLock);
            RecieveMessages recieveMessages = new RecieveMessages(socket, threadLock);

            Thread send = new Thread(sendMessages);
            Thread recieve = new Thread(recieveMessages);

            send.start();
            recieve.start();
        }
        catch(IOException e)
        {
            System.out.println("Fatal Connection error");
        }

    }
    //ON THE MOVE
/*
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
*/
    public synchronized void closeConnection()
    {


    }

    public synchronized static boolean getIsAccepted()
    {
        return isAccepted;
    }

    public synchronized static void changeIsAccepted(Boolean value)
    {
        isAccepted = value;
    }


}
