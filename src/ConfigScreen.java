import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.*;
import javax.microedition.rms.*;
import java.lang.*;

public class ConfigScreen extends Form
{
	private Image img;
	int volLvl;

	TextField volumeField;

	public ConfigScreen()
	{
		super("Configuration");
		//name = "Configuration";
		volumeField  = new TextField("Enter the volume level: ", "", 3, TextField.ANY);
		append(volumeField);
		volLvl = 0;
	}

	public void setVolumeLevel()
	{
		volLvl = Integer.parseInt(volumeField.getString());
	}

	public int getVolumeLevel()
	{
		return volLvl;
	}
}