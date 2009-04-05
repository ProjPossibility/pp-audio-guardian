package projectpossibility;

import javax.microedition.lcdui.*;

/**
 * The initial splash screen that is displayed when AudioGuardian starts up.  Gives users the option to either
 * start the program or exit.
 */
public class CStartupScreen extends Canvas 
{	
	private int width, height;
	private Image img;

	/**
	 * Construct a new startup screen.
	 */
	public CStartupScreen()
	{
		width = getWidth();
		height = getHeight();
		
		try
		{
			img = Image.createImage("/startup.png");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Paint the startup screen onto the graphics interface.
	 */
	protected void paint(Graphics g) 
	{
		g.setColor(0xffffff);
		g.fillRect(0, 0, width, height);
		g.setColor(0x000000);
		g.drawImage(img, width/2, height/2, Graphics.VCENTER | Graphics.HCENTER);
	}
	
}

