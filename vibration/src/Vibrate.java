import java.io.*;

import javax.microedition.midlet.*;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.lcdui.*;

public class Vibrate extends MIDlet implements CommandListener {

	Command configCommand;
	Command launchCommand;
	Command exitCommand;
	Command backCommand;
	Command saveCommand;
	private Display display; 
	mainScreen m = new mainScreen();
	ConfigScreen config = new ConfigScreen();
	RecordStore saveData;
	
	public Vibrate() {
		
		try
		{
			saveData = RecordStore.openRecordStore("Data", true);
		}
		catch(RecordStoreException e)
		{
		}
		
	    display = Display.getDisplay(this);
	    configCommand = new Command("Config", Command.OK,0);
	    launchCommand = new Command("Go", Command.OK,1);
	    exitCommand = new Command("Exit", Command.EXIT,2);
		backCommand = new Command("Back", Command.BACK, 0);
		saveCommand = new Command("Save", Command.OK, 1);
	    m.addCommand(configCommand);
	    m.addCommand(launchCommand);
	    m.addCommand(exitCommand);
	    config.addCommand(backCommand);
	    config.addCommand(saveCommand);	   
	    m.setCommandListener(this);
	    config.setCommandListener(this);
	    
	  }
	
	protected void destroyApp(boolean unconditional) {
		// TODO Auto-generated method stub
		notifyDestroyed();

	}

	  public void startApp() {
	    display.setCurrent(m);
	   // display.vibrate(2000);
	  }
	
	public void testVibrate()
	{
		Display.getDisplay(this).vibrate(800);
		System.err.println("Worked");
		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}
	
	public void commandAction(Command c, Displayable d)
	{
		if(c == launchCommand)
		{
			
		}
		else if(c == configCommand)
		{
			ByteArrayInputStream bais;
			try {
				bais = new ByteArrayInputStream(saveData.getRecord(0));
			} catch (RecordStoreNotOpenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidRecordIDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RecordStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataInputStream inputStream = new DataInputStream(bais);
			config.volumeField.setString(inputStream.toString());
			display.setCurrent(config);
		}
		else if(c == exitCommand)
		{
			destroyApp (true);
		}
		else if(c == backCommand)
		{
			display.setCurrent(m);
		}
		else if(c == saveCommand)
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream outputStream = new DataOutputStream(baos);
			config.setVolumeLevel();
			
			try 
			{
				outputStream.writeInt(config.getVolumeLevel());
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
			}
			
			byte[] b = baos.toByteArray();
			try
			{
			saveData.addRecord(b,0,b.length);
			}
			catch(RecordStoreException e)
			{
				//DISPLAY FULL MSG
			}
			display.setCurrent(m);
		}
	}
}
	



