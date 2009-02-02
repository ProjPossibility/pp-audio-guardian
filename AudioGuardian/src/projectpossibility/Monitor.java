package projectpossibility;

import java.io.ByteArrayOutputStream;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.media.Player;
import javax.microedition.media.control.RecordControl;

public class Monitor implements Runnable 
{	
	private boolean doStop = false;
	private Graph g;
	private Player p;
	private Display d;
	private FloorLevel floor;
	
	private double m_alertThreshold;
	private CMatchableSound [] m_sounds;
	
	//constructor
	public Monitor(Player p, Graph g, Display d, int alertThreshold, FloorLevel floor, CMatchableSound [] sounds)
	{
		this.g = g;
		this.p = p;
		this.d = d;
		this.floor = floor;
		
		m_alertThreshold = 100.0 + 2*alertThreshold;
		m_sounds = sounds;
	}
	
	public void stop()
	{
		doStop = true;
	}

	public void run()
	{
		// Audio Loop
		try 
		{
			p.realize();
	    
	    	// Create record control for player
	    	RecordControl rc = (RecordControl)p.getControl("RecordControl");
	    
	    	// Create file to write to
	    	ByteArrayOutputStream output = new ByteArrayOutputStream();

	    	// Configure record stream destination
	    	rc.setRecordStream(output);
	    	p.start();
	    	
	    	while(!doStop)
	    	{
		    	rc.setRecordStream(output);
		    	rc.startRecord();
		    	
		    	// sleep 1 milisecond
	    		Thread.sleep(1);
	    		
	    		// commit file
		   		rc.commit();
		   	 	rc.stopRecord();
		   	 	
		   		byte[] recordedSoundArray = output.toByteArray();
		   		if(recordedSoundArray.length>44){
			    	
				    // temporary array for stripped sound bytes
				    byte soundonly[] = new byte[recordedSoundArray.length-44];
				    
				    // strip file header
				    for(int i =0; i<soundonly.length; i++){
				    	soundonly[i]=(byte)(recordedSoundArray[i+44]-floor.getLevel());
				    }
				    
				    // update stream array in graph
			    	g.updateStreamData(soundonly);
			    	
			    	// set current display on graph
			    	//display.setCurrent(this);
			    	
			    	// update graph plot
				    g.update();
				    
				    // warn if threshold reached
			    	if(AAnalyzer.samplesAboveThreshold(soundonly, m_alertThreshold)>(soundonly.length-50)/2*1/10){
			    		
			    		double loudestFrequency = g.getLoudestFrequency();
			    	
			    		// Scan through the match candidates and alert on a match
			    		boolean foundSpecificMatch = false;
			    		for(int j = 0; j < m_sounds.length; j++)
			    		{
			    			if(m_sounds[j].checkMatch(loudestFrequency))
			    			{
			    				foundSpecificMatch = true;
			    			}
			    		}
			    		
			    		if(!foundSpecificMatch && loudestFrequency>100){
			    			
			    			d.vibrate(2000);
			    			
			    			// Do a popup
			    			Alert curAlert = new Alert("Sound detected", "Unknown sound - " + Double.toString(loudestFrequency), null, AlertType.ALARM);
			    			curAlert.setTimeout(3000);
			    			d.setCurrent(curAlert, d.getCurrent());
			    			
			    			Thread.sleep(curAlert.getTimeout()+200);
			    			System.out.println("frequency hit: "+ loudestFrequency);
			    		}
			    	}
			    }
		   		else{
		   		}
		    	output.reset();
	    	}
	    	
	    	// close streams
	    	rc.stopRecord();
	   		p.stop();
	   		p.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
