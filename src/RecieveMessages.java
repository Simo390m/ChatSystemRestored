import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RecieveMessages implements Runnable {

    Socket socket;
    String username;
    String recievedMessage;
    BufferedReader bufferedReader;

    public RecieveMessages(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    @Override
    public void run()

    {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((recievedMessage = bufferedReader.readLine()) != null)
            {
                System.out.println(recievedMessage);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
