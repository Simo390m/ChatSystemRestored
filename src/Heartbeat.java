import java.util.Timer;
import java.util.TimerTask;

public class Heartbeat extends TimerTask {

    @Override
    public void run() {
        heartbeat();
        System.out.println("Hearbeat alive");
    }

    private void heartbeat() {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        TimerTask hB = new Heartbeat();
        //running timer task as daemon thread
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(hB, 0, 5*1000);


        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}