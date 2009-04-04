package projectpossibility;

import java.io.ByteArrayOutputStream;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.media.Player;
import javax.microedition.media.control.RecordControl;
/**
 * Monitor controls microphone input.
 * 
 */
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
	/**
	 * @param alertThreshold controls volume required to trigger an alert
	 * @param floor Calibrated for to cut down on background noise.
	 * @param sounds Array of recognizable sounds
	 */
	public Monitor(Player p, Graph g, Display d, int alertThreshold, FloorLevel floor, CMatchableSound [] sounds)
	{
		this.g = g;
		this.p = p;
		this.d = d;
		this.floor = floor;
		
		m_alertThreshold = 100.0 + 3*alertThreshold;
		
		//convert the given sound level to decibel
		//double gain = alertThreshold;
		//gain = gain / 100;
		//float dB = (float) (Float11.log(gain == 0.0 ? 0.0001 :gain) / Float11.log(10.0)*20.0);
		//m_alertThreshold = -10*dB;
		
		
		m_sounds = sounds;
	}
	/**
	 * Stops the record loop.
	 */
	public void stop()
	{
		doStop = true;
	}
	/**
	 * Initiates the main record loop.
	 * This loop samples data, converts it to a byte array and updates the graph.
	 */
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
				    
				    //filter data
				    AAnalyzer.filterAmp(soundonly);
				    
				    
					//filter the frequencies
					for(int i = 0; i < soundonly.length; i++)
					{
						AAnalyzer.doFilter(soundonly[i],.2,0);
						System.out.println(soundonly[i]);
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
