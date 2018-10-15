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

            String attemptedUsername;
            String username = null;
            Scanner scan = new Scanner(System.in);

            System.out.println("Please input username: " );

            while (username == null || username.trim().equals("")){
                 attemptedUsername = scan.nextLine();
                if (validator(attemptedUsername)){
                    username = attemptedUsername;
                }
                if(username.trim().equals("")){
                    System.out.println("Invalid. Please enter again:");
                }
            }
            SendMessages sendMessages = new SendMessages(socket, username);
            RecieveMessages recieveMessages = new RecieveMessages(socket, username);

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


}
