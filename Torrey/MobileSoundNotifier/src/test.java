
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.RecordControl;
import javax.microedition.media.protocol.DataSource;
import javax.microedition.media.protocol.SourceStream;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class test extends MIDlet{

	public static String getHexString(byte[] b, int offset, int length) throws Exception {
		  String result = "";
		  int end;
		  if( b.length < offset+length)
			  end= b.length;
		  else
			  end= offset+length;
		  
		  
		  
		  for (int i=offset; i < end; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
		}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
		
	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		 try {
			 
			 	Display display = Display.getDisplay(this);
			 	Screen screen = new Screen();
			 	display.setCurrent (screen);
			 
			    // Create a Player that captures live audio.
			    Player p = Manager.createPlayer("capture://audio?encoding=pcm");
			    p.realize();
			    // Get the RecordControl, set the record stream,
			    // start the Player and record for 5 seconds.
			    RecordControl rc = (RecordControl)p.getControl("RecordControl");
			    ByteArrayOutputStream output = new ByteArrayOutputStream();
			   
			    
			    p.start();
			    
			    
			    boolean record = true;
			    while(record)
			    {
			    	rc.setRecordStream(output);
			    	rc.startRecord();
			    	
			    	Thread.currentThread().sleep(10);
			    	
			    	rc.commit();
			    	byte savedRecording[] = output.toByteArray();
			    	
			    	
			    	if( savedRecording.length > 0)
			    	{
			    	//	System.out.println(getHexString(savedRecording,319,8));
			    		screen.drawGraph(savedRecording);
			    	}

			    	//screen.drawGraph(savedRecording);

			    	output.reset();
			    	
			    	
			    }
			    
			    
			    
			    //byte test[] = new byte[10000];

			  
			    
			    
			    
			    
			    
			    
			    
			    
		        //StreamingDataSource data = new StreamingDataSource("capture://audio?encoding=pcm");
		        //SourceStream streams[] = data.getStreams();        //StreamingDataSource.connect();
		 
		        //System.out.println( streams[0].read(test, 0, 5));
		        
		        
			    rc.commit();
			    p.close();
			    
			    //byte test[] = new byte[600000];
			    //ByteArrayInputStream recordedInputStream = new ByteArrayInputStream(test);
         // Player p2 = Manager.createPlayer(recordedInputStream,"audio/x-wav");
         // p2.prefetch();
         // p2.start();
          
          

          
			 } catch (IOException ioe) {
			 } catch (MediaException me) {
			 } catch (InterruptedException ie) { } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		
	}

}
