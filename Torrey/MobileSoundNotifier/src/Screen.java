import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


public class Screen extends Canvas{

	private int width, height;
	private String name;
	private Image img;	
	private byte[] sounddata;
	
	public Screen()
	{
		width = getWidth ();
		height = getHeight ();
	}
	
	
	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, width, height);
		
		
		g.setColor(0xFF0000);
		for(int i = 50; i<sounddata.length;i+=2){
			g.drawLine((i-2)-100,(sounddata[i-2])/2+150,i-100, (sounddata[i])/2+150);
			}
	}
	public void drawGraph(byte[] b)
	{
		sounddata = b;
		repaint();
	}
	
	
	
}
