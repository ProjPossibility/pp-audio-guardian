package tutorial;

import javax.microedition.lcdui.*;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

import tutorial.TutorialMidlet;

public class HelloScreen extends Canvas {
	
	private int width, height;
	private String name;
	private Image img;
	private Player player;
	private TutorialMidlet parentMidlet;
	
	public HelloScreen (TutorialMidlet midlet) {
		parentMidlet = midlet;
		width = getWidth ();
		height = getHeight ();
		name = "unknown";
		// Construct the image from the media file
		try {
			img = Image.createImage("/hello.png");
		} catch (Exception e) {
			e.printStackTrace ();
			img = null;
		}
		
		// Construct the audio player
		try
		{
			player = Manager.createPlayer(parentMidlet.getClass().getResourceAsStream("/hello.wav"), "audio/x-wav");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			player = null;
		}
	}
	
	public void setName (String n) {
		name = n;
	}
	
	public void startPlay()
	{
		try
		{
			player.realize();
			player.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	// Paint the screen based on the name
	protected void paint (Graphics g) {
		g.setColor(0xffffff);
		g.fillRect(0, 0, width, height);
		g.setColor(0x000000);
		
		g.drawImage (img, width / 2, height / 4, Graphics.VCENTER | Graphics.HCENTER);
		
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));
		
		g.drawString (name, width / 2, height * 3/4, Graphics.BASELINE | Graphics.HCENTER);
	}
}