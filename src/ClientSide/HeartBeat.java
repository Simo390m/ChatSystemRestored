package ClientSide;



public class HeartBeat implements Runnable
{
    private SendMessages send;

    public HeartBeat(SendMessages send)
    {
        this.send = send;
    }

    @Override
    public void run()
    {
        while (true)
        {
            while(ClientMain.getBool())
            {
                synchronized (this)
                {
                    try
                    {
                        wait(60*1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                send.send("IMAV");
            }
        }


    }
}