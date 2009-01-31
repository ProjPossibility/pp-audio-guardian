import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.*;
import javax.microedition.rms.*;

public class MainScreen extends Canvas
{
	private int width, height;
	private String name;
	private Image img;

	public MainScreen()
	{
		width = getWidth();
		height = getHeight();
		name = "Mobile Sound Notifier";
		try
		{
			img = Image.createImage("/welcome.png");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			img = null;
		}
	}

	public void setName(String n)
	{
		name = n;
	}

	protected void paint(Graphics g)
	{
		g.setColor(0xffffff);
		g.fillRect(0, 0, width, height);
		g.setColor(0x000000);
		g.drawImage (img, width / 2, height / 4,Graphics.VCENTER |Graphics.HCENTER);
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_BOLD,Font.SIZE_LARGE));
		g.drawString (name, width / 2, height * 3/4,Graphics.BASELINE | Graphics.HCENTER);		
	}
}