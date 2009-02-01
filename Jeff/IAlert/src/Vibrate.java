import javax.microedition.lcdui.Display;

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

		for(int x = 0; x < nPulses; x++)
		{
			Vibration.vibrate(time);
			Vibration.flashBacklight(time);
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