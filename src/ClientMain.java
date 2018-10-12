import javax.sound.sampled.ReverbType;
import java.io.IOException;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args)

    {
        try
        {
            Socket socket = new Socket("127.0.0.1", 3333);

            String readName = null;
            Scanner scan = new Scanner(System.in);
            System.out.println("Please input username: " );
            while (readName == null || readName.trim().equals("")){
                readName = scan.nextLine();
                if(readName.trim().equals("")){
                    System.out.println("Invalid. Please enter again:");
                }
            }
            SendMessages sendMessages = new SendMessages(socket, readName);
            RecieveMessages recieveMessages = new RecieveMessages(socket, readName);

            Thread send = new Thread(sendMessages);
            Thread recieve = new Thread(recieveMessages);

            send.start();
            recieve.start();
        }
        catch(IOException e)
        {
            System.out.println("Fatal Connection error");
        }

        //TODO: Make a RegularExpression for Validation
    }
}
