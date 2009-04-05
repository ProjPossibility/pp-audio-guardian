package projectpossibility;

import java.io.ByteArrayOutputStream;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.media.Player;
import javax.microedition.media.control.RecordControl;


/**
 * Records a user-defined sound for later detection
 * 
 */
public class Recorder implements Runnable
{
	private DFTpair[] m_frequencies = new DFTpair[0];
	
	private Player p;
	private COptionsDb db;
	
	private DFT m_dft;
	private CRecordScreen m_screen;
	private CommandListener m_listener;
	private Command m_doneCommand;
	
	private double m_maxFreq = 0.0;
	
	// Change floor at the end to floor level
	public Recorder(Player p, CRecordScreen screen, CommandListener listener, Command doneCommand, COptionsDb db)
	{
		this.p = p;
		this.db = db;
		m_dft = new DFT();
		m_screen = screen;
		m_listener = listener;
		m_doneCommand = doneCommand;
	}
	/**
	 * 
	 * @param Frequencies Array of DFT pairs, holding Frequencies and Amplitudes
	 * @return Average frequency.
	 */
	private double average(DFTpair[] frequencies)
	{
		double avg = 0;
		for(int i = 0; i < frequencies.length; i++)
		{
			avg += frequencies[i].getFreq();
		}
		avg /= frequencies.length;
		return avg;
	}
	/**
	 * Main record loop.
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
	    	ByteArrayOutputStream output;

	    	// Configure record stream destination
	    	//rc.setRecordStream(output);
	    	p.start();
	    	
	    	for(int j=0;j<100;j++)
	    	{	
	    		m_frequencies = new DFTpair[0];
	    		output = new ByteArrayOutputStream();
	    	
		    	rc.setRecordStream(output);
		    	if( j == 0 )
		    	{
		    		for(int i = 5; i > 0; i--)
			    	{
				    	Thread.sleep(600);
				    	m_screen.setText(Integer.toString(i));
			    	}
		    		Thread.sleep(600);
			    	m_screen.setText("Recording in progress");
		    	}
		    	rc.startRecord();
		    	
		    	// sleep 5 seconds
		 		Thread.sleep(1);
		 		
		 		// commit file
			   		
		 		rc.commit();
		   	 	rc.stopRecord();
		   	 	
		   	 	/*
		   	 	 * TODO: Analyze small bits of the data stream
		   	 	 * Extrapolate average DFTpair from that part
		   	 	 * Store that in global array
		   	 	 * Flush output stream and byte array
		   	 	 * Repeat as necessary
		   	 	 */
		   	 	
		   		byte[] recordedSoundArray = output.toByteArray();
		   		if(recordedSoundArray.length>44)
		   		{	
				    // temporary array for stripped sound bytes
				    byte soundonly[] = new byte[recordedSoundArray.length-44];
				    
				    // strip file header
				    for(int i =0; i<soundonly.length; i++)
				    {
				    	soundonly[i]=(byte)(recordedSoundArray[i+44]);
				    }
				    m_frequencies = m_dft.transform(soundonly);
			   		if( m_frequencies.length > 1 )
			   		{
			   			double maxFreq = 0.0;
			   			double maxAmp = 0.0;
			   			
			   			for (int curFreq = 1; curFreq < m_frequencies.length; ++curFreq)
			   			{
			   				DFTpair curPair = m_frequencies[curFreq];
			   				
			   				//System.out.println(curFreq + "[" + curPair.getFreq() + ", " + curPair.getAmp() + "]");
			   				
			   				if(curPair.getAmp() > maxAmp)
			   				{
			   					maxAmp = curPair.getAmp();
			   					maxFreq = curPair.getFreq();
			   				}
			   				if( m_maxFreq < maxFreq )
			   				{
			   					m_maxFreq = maxFreq;
			   				}		
			   			}

			   			//System.out.println("Max Freq: " + maxFreq);
			   			//System.out.println("Amplitude: " + maxAmp);
			   		}
		   		}
		    }
	    	//System.out.println(m_maxFreq);
	    	db.SaveDouble("User Defined Sound", m_maxFreq);
	    	
		    // close streams
		    rc.stopRecord();
		   	p.stop();
		   	p.close();
		   	
		   	System.out.println("Recording finished");
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
