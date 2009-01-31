import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.*;

public class WelcomeScreen extends Form
{
	public WelcomeScreen()
	{
		super("Mobile Sound Notifier");
		Image welcomeImg;
		try
		{
			welcomeImg = Image.createImage("/welcome.png");

		}
		catch( Exception e )
		{
			e.printStackTrace();
			welcomeImg = null;
		}
		ImageItem imageItem = new ImageItem("", welcomeImg, ImageItem.LAYOUT_CENTER, "Mobile Sound Notifier");
		TextField nameField = new TextField ("Please enter your name", "", 10, TextField.ANY);	
		append (imageItem);	
		append (nameField);
	}
}