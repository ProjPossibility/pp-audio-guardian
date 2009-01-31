import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.io.*;

public class Vibrate extends MIDlet implements CommandListener
{
	MainScreen m = new MainScreen();
	ConfigScreen config = new ConfigScreen();
	RecordStore rs;
	
	Command configCommand;
	Command launchCommand;
	Command exitCommand;

	Command backCommand;
	Command saveCommand;

	private Display display;
	
	public Vibrate()
	{
		try
		{
			rs = RecordStore.openRecordStore( "Record", true );
		}
		catch( RecordStoreException e )
		{
			//DISPLAY CANNOT OPEN MSG
		}
		
		display = Display.getDisplay(this);
		configCommand = new Command("Config", Command.OK, 0);
		launchCommand = new Command("Launch", Command.OK, 1);
		exitCommand = new Command("Exit", Command.EXIT, 2);

		backCommand = new Command("Back", Command.BACK, 0);
		saveCommand = new Command("Save", Command.OK, 1);

		config.addCommand(backCommand);
		config.addCommand(saveCommand);
		config.setCommandListener(this);

		m.addCommand(configCommand);
		m.addCommand(launchCommand);
		m.addCommand(exitCommand);
		m.setCommandListener(this);		
	}

	protected void destroyApp(boolean unconditional)
	{
		notifyDestroyed();
	}
	
	public void startApp()
	{
		display.setCurrent(m);
	}

	public void testVibrate()
	{
		Display.getDisplay(this).vibrate(800);
		System.err.println("Worked");
	}
	
	protected void pauseApp()
	{
	}	

	public void commandAction(Command c, Displayable d)
	{
		if( c == launchCommand )
		{
		}
		else if( c == configCommand )
		{
			try
			{
				ByteArrayInputStream bais = new ByteArrayInputStream(rs.getRecord(0));	
			}
			catch( RecordStoreException e)
			{
			}
			DataInputStream inputStream = new DataInputStream(bais);
			config.volumeField.setString(inputStream.toString());
			display.setCurrent(config);
		}
		else if( c == exitCommand )
		{
			destroyApp(true);
		}
		else if( c == saveCommand )
		{
			config.setVolumeLevel();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream outputStream = new DataOutputStream(baos);
			try
			{
				outputStream.writeInt(config.getVolumeLevel());
			}
			catch(IOException e)
			{
			}
			byte[] b = baos.toByteArray();
			try
			{
				rs.addRecord(b, 0, b.length);
			}
			catch(RecordStoreException e)
			{
			}
			display.setCurrent(m);
		}
		else if( c == backCommand )
		{
			display.setCurrent(m);
		}
	}	
}