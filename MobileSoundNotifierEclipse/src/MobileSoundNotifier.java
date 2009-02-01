import java.io.*;

import javax.microedition.midlet.*;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.lcdui.*;

public class MobileSoundNotifier extends MIDlet implements CommandListener 
{
	public Command configCommand;
	public Command launchCommand;
	public Command exitCommand;
	public Command backCommand;
	public Command saveCommand;
	public Command recordCommand;
	public Command startRecordCommand;
	public Command stopRecordCommand;
	public Command playback;
	private Display display;
	public mainScreen m;
	public ConfigScreen config;
	public RecordStore saveData;
	public RecordScreen r;
	private Recorder recorder;
	
	public Vibrate() 
	{
		m = new mainScreen();
		config = new ConfigScreen();
		r = new RecordScreen();
		
		try
		{
			saveData = RecordStore.openRecordStore("Data", true);
		}
		catch(RecordStoreException e)
		{
		}
		
	    	display = Display.getDisplay(this);
	    	configCommand = new Command("Config", Command.SCREEN,0);
	    	launchCommand = new Command("Go", Command.OK,1);
	    	exitCommand = new Command("Exit", Command.EXIT,2);
		backCommand = new Command("Back", Command.BACK, 0);
		saveCommand = new Command("Save", Command.OK, 1);
		recordCommand = new Command("Record Sound", Command.SCREEN, 2);
		startRecordCommand = new Command("Start Recording", Command.OK, 0);
		stopRecordCommand = new Command("Stop Recording", Command.OK, 0);
		playback = new Command("Playback Sound", Command.EXIT, 1);
	    	m.addCommand(configCommand);
	    	m.addCommand(launchCommand);
	    	m.addCommand(exitCommand);
	    	m.setCommandListener(this);
	    	config.addCommand(backCommand);
	    	config.addCommand(saveCommand);
	    	config.addCommand(recordCommand);
	    	config.setCommandListener(this);
	    	r.addCommand(startRecordCommand);
	    	r.setCommandListener(this);
	    	recorder = new Recorder(); 
	}
	
	protected void destroyApp(boolean unconditional) 
	{
		// TODO Auto-generated method stub
		notifyDestroyed();

	}

	public void startApp() 
	{
		display.setCurrent(m);
	   	// display.vibrate(2000);
	}
	
	public void testVibrate()
	{
		Display.getDisplay(this).vibrate(800);
		System.err.println("Worked");
		
	}

	protected void pauseApp() 
	{
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
			try 
			{
				for(int x = 1; x <= saveData.getNumRecords(); x++)
				{
					if(saveData.getRecordSize(x) > b1.length)
					{
						b1 = new byte[saveData.getRecordSize(x)];
					}
					length = saveData.getRecord(x, b1, 0);
				}
			} 
			catch (RecordStoreNotOpenException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (InvalidRecordIDException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (RecordStoreException e) 
			{
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
		else if(c == recordCommand)
		{
			display.setCurrent(r);
		}
		else if(c == startRecordCommand)
		{
			Thread recordThread = new Thread(recorder);
			recordThread.start();
			r.removeCommand(startRecordCommand);
			r.addCommand(stopRecordCommand);
		}
		
		else if(c == stopRecordCommand)
		{
			recorder.stop();
			r.removeCommand(stopRecordCommand);
			r.addCommand(startRecordCommand);
			if(recorder.count() >= 1)
			{
				r.addCommand(playback);
			}
		}
		else if(c == playback)
		{
			recorder.playbackSound();
		}
	}
}
	



