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
			byte b1[] = new byte[1];
			int length = 0;
			try {
			for(int x = 1; x <= saveData.getNumRecords(); x++)
			{
				if(saveData.getRecordSize(x) > b1.length)
				{
					b1 = new byte[saveData.getRecordSize(x)];
				}
				length = saveData.getRecord(x, b1, 0);
			}
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
			String s1 = new String(b1, 0, length); 
			config.volumeField.setString(s1);
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
			config.setVolumeLevel();
			Integer v = new Integer(config.getVolumeLevel());
			
			byte[] b = v.toString().getBytes();
			
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
	



