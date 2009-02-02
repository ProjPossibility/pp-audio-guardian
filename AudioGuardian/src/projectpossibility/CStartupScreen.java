package projectpossibility;

import javax.microedition.lcdui.*;

public class CStartupScreen extends Canvas 
{	
	private int width, height;
	private Image img;

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

	protected void paint(Graphics g) 
	{
		// TODO Auto-generated method stub
		g.setColor(0xffffff);
		g.fillRect(0, 0, width, height);
		g.setColor(0x000000);
		g.drawImage(img, width/2, height/2, Graphics.VCENTER | Graphics.HCENTER);
	}
	
}

