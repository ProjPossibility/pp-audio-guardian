import java.io.*;

import javax.microedition.media.*;
import javax.microedition.media.control.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;


public class RecordScreen extends Canvas {

	private int width;
	private int height;
	
	public RecordScreen()
	{
		width = getWidth();
		height = getHeight();
	}
			   

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(0xffffff);
		g.fillRect(0, 0, width, height);
		g.setColor(0x000000);
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_BOLD, Font.SIZE_LARGE));
	}
	
}