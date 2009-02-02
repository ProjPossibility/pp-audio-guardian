import javax.microedition.lcdui.Display;
import javax.microedition.media.Manager;

public class Vibrate implements IAlert
{
	private int time;
	private int nPulses;
	private Display Vibration;
	public Vibrate (int t, int n, Display disp)
	{
		time = t;
		nPulses = n;
		Vibration = disp;
	}
	public void trigger()
	{
		/* try {
		   for (int i = 68; i < 88; i++) {
		       
		        Manager.playTone(120, 5000, 100);
		        Thread.sleep(300);

		      }

		    } catch (Exception me) {
		      System.err.println(me);
		    }*/

		for(int x = 0; x < nPulses; x++)
		{
			Vibration.vibrate(time);
			Vibration.flashBacklight(5*time);
			try{
				Thread.sleep(6*time); // to keep the vibrations from running over one another 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
			
			
	}
	

	
}