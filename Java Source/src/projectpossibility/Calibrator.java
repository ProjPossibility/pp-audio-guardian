package projectpossibility;


//Passes floor level, determines mic offset level
import java.io.ByteArrayOutputStream;

import javax.microedition.lcdui.*;
import javax.microedition.media.Player;
import javax.microedition.media.control.RecordControl;
/**
 * Provides Calibration/Noise level analysis (* still needs a correct implementation)
 * @author AG Team
 *
 */
public class Calibrator implements Runnable 
{	
	private Graph g;
	private Player p;
	private double sum;
	private int samples;
	private FloorLevel floor;
	
	private CCalibrationScreen m_screen;
	private CommandListener m_listener;
	private Command m_doneCommand;
	
	/**
	 * 
	 * @param p player
	 * @param g graph
	 * @param floor noise level/sound base level
	 * @param screen screen for calibrator
	 * @param listener command listener
	 * @param doneCommand command for changing pages after finishing
	 */
	public Calibrator(Player p, Graph g, FloorLevel floor, CCalibrationScreen screen, CommandListener listener, Command doneCommand)
	{
		this.g = g;
		this.p = p;
		this.floor = floor;
		m_screen = screen;
		m_listener = listener;
		m_doneCommand = doneCommand;
	}
	
	/**
	 * start calibration
	 */
	public void run()
	{
		// Audio Loop
	   	// Create a Player that captures live audio.
		try 
		{
			// fetch resources for 
			p.realize();
	    
	    	// Create record control for player
	    	RecordControl rc = (RecordControl)p.getControl("RecordControl");
	    
	    	// Create file to write to
	    	ByteArrayOutputStream output = new ByteArrayOutputStream();

	    	// Configure record stream destination
	    	rc.setRecordStream(output);
	    	p.start();
	    	
	    	for(int i = 5; i > 0; i--)
	    	{
		    	Thread.sleep(600);
		    	m_screen.setText(Integer.toString(i));
	    	}
	    	
	    	Thread.sleep(600);
	    	m_screen.setText("Calibration in progress");
	    	
	    	for(int j=0;j<100;j++){
	    		
	    		
	    		//System.out.println(j);
	    	
	    	rc.setRecordStream(output);
	    	rc.startRecord();
	    	
	    	
	    	// sleep 5 seconds
	 		Thread.sleep(1);
	 		
	 		// commit file
		   		
	 		rc.commit();
	   	 	rc.stopRecord();
	   	 	
	   		byte[] recordedSoundArray = output.toByteArray();
	   		if(recordedSoundArray.length>44){
			    // add to sum
			    for(int i=0; i<recordedSoundArray.length-44; i+=2){
			    	sum+=recordedSoundArray[i+44];
			    	System.out.println(recordedSoundArray[i+44]);
			    }
				    
			    // add to number of total samples
			    samples+=(recordedSoundArray.length-44)/2;
			    
			    // temporary array for stripped sound bytes
			    byte soundonly[] = new byte[recordedSoundArray.length-44];
			    
			    // strip file header
			    for(int i =0; i<soundonly.length; i++){
			    	soundonly[i]=(byte)(recordedSoundArray[i+44]-floor.getLevel());
			    }
			    
			    
			    // update stream array in graph
		    	g.updateStreamData(soundonly);
			    	
		    	// update graph plot
			    //g.update();
				    
		    }
	   		else{
	   		}
	   			output.reset();
	    	}
	    // close streams
	    rc.stopRecord();
	   	p.stop();
	   	p.close();
	   	
	   	System.out.println("calibration finished");
	   	
	   	//return floor
	   	floor.setLevel((200-sum/samples));
	
	   	System.out.println("floor ="+floor.getLevel());
	   	
		
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			m_listener.commandAction(m_doneCommand, m_screen);
		}
	}
}
