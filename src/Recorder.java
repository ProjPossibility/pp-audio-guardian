import javax.microedition.lcdui.*;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.RecordControl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.*;

public class Recorder implements Runnable 
{	
	private boolean doStop = false;
	private int count = 0;
	private  byte[] recordedSoundArray;
	
	public Recorder()
	{
	}
	
	public void stop()
	{
		doStop = true;
		count += 1;
	}
	
	public int count()
	{
		return count;
	}
	
	
	public void run()
	{
		// Audio Loop
	   	// Create a Player that captures live audio.
	    Player p;
		try 
		{
			p = Manager.createPlayer("capture://audio");
			p.realize();
	    
	    	// Create record control for player
	    	RecordControl rc = (RecordControl)p.getControl("RecordControl");
	    
	    	// Create file to write to
	    	ByteArrayOutputStream output = new ByteArrayOutputStream();
	    	output.reset();
	    	output.flush();
	    	// Configure record stream destination
	    	rc.setRecordStream(output);
	    	rc.startRecord();
	    	p.start();
	    
	    	while(!doStop)
	    	{
	    		Thread.sleep(10);
		   		recordedSoundArray = output.toByteArray();
	    	}
	    
	   		p.stop();
	   	 	rc.stopRecord();
	   		rc.commit();
		}
		catch(Exception e)
		{
		}
	}
	
	public void playbackSound()
	{
		ByteArrayInputStream recordedInputStream = new ByteArrayInputStream(recordedSoundArray);
	    Player p2;
		try 
		{
			p2 = Manager.createPlayer(recordedInputStream,"audio/x-wav");
			p2.prefetch();
			p2.start();
		}
		catch(Exception e)
		{
		}
	}
}
