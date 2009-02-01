import java.io.ByteArrayOutputStream;
import javax.microedition.lcdui.Display;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.RecordControl;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class MSN extends MIDlet{
	Display display;
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
		
	}

	protected void startApp() throws MIDletStateChangeException {
		// Waveform chart display
		display = display.getDisplay(this);
		Graph graph = new Graph();
		
		// Audio Loop
		 try {
			    // Create a Player that captures live audio.
			    Player p = Manager.createPlayer("capture://audio");
			    p.realize();
			    
			    // Create record control for player
			    RecordControl rc = (RecordControl)p.getControl("RecordControl");
			    
			    // Create file to write to
			    ByteArrayOutputStream output = new ByteArrayOutputStream();
			    
			    // Configure record stream destination
			    rc.setRecordStream(output);
			    p.start();
			    
			    // Recording Switch
			    boolean record = true;
			    
			    // Start stream
			    while(record){
			    	// Set up recorder
				    rc.setRecordStream(output);
				    rc.startRecord();
				    
				    // Record for 1 ms
				    Thread.currentThread().sleep(1);
				    
				    // Save
				    rc.commit();
				    
				    // Save raw data from capture
				    byte soundfile[] = output.toByteArray();
				    
				    // Display data only when there are sample points taken
				    if(soundfile.length>44){
					    // Discard file header
					    byte soundonly[] = new byte[soundfile.length-44];
					    for(int i =0; i<soundonly.length; i++){
					    	soundonly[i]=soundfile[i+44];
					    }
				    	graph.updateStreamData(soundonly);
				    	display.setCurrent(graph);
					    graph.update();
				    	if(AAnalyzer.samplesAboveThreshold(soundonly, 100)>(soundonly.length-50)/2*1/10)
				    		graph.warning();
				    }
				    // Clear save file
				    output.reset();
			    }
			    
			    p.close();
			 } catch (Exception e) {
				 System.out.println(e.toString());
			 } 
	}
}
