import javax.microedition.io.Connector;
import javax.microedition.io.OutputConnection;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.RecordControl;


import com.sun.satsa.pki.FileSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.*;

public class Recorder implements Runnable {
	
	private boolean doStop = false;
	private int count = 0;
	private Player recordPlayer;
	private Player playbackPlayer;
	private RecordControl rc;
	private FileConnection fc;
	private OutputConnection oc;
	private OutputStream os;
	
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
		try {
			
			
			recordPlayer = Manager.createPlayer("capture://audio");
			recordPlayer.realize();
	    
	    // Create record control for player
			oc = (OutputConnection)Connector.open("file://C:/Users/Brian/workspace/vibration/src/Test.wav;append=true", Connector.WRITE);
			os=oc.openOutputStream();
			RecordControl rc = (RecordControl)recordPlayer.getControl("RecordControl");
	    // Create file to write to
	    
	    rc.setRecordStream(os);
	    rc.setRecordSizeLimit(1000000000);
	    rc.startRecord();
	    recordPlayer.start();
	    //while(!doStop)
	    //{
	    	Thread.currentThread().sleep(10);
		    
	   // }
	    
	    rc.stopRecord();
	    rc.commit();
	    recordPlayer.stop();
	    recordPlayer.close();
	    os.close();
	    oc.close();
		}
		
		catch(Exception e)
		{
		}
	}
	
	    public void playbackSound()
	    {
	    	try{
	    	playbackPlayer = Manager.createPlayer("/test.wav");
	    	playbackPlayer.start();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			}
		}
