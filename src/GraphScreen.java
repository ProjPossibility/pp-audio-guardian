import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextField;


public class GraphScreen extends Canvas{

	private int width, height;
	private TextField name;
	private Image img;	
	private byte[] sounddata;
	private double[] fft;
	
	public GraphScreen()
	{
		width = getWidth ();
		height = getHeight ();
	}
	
	protected void paint(Graphics g) 
	{
		// TODO Auto-generated method stub
		g.setColor(0x000000);
		g.fillRect(0, 0, width, height);
		g.setColor(0xFFFFFF);
		
		//super("Graph");
		//name = new TextField()
		//append(name);
		
		g.setColor(0x0000FF);
		if(sounddata.length > 44)
		{
			
			//create wave parameters
			int x1 = 0,y1 =0,x2=0 ,y2=0;
			for(int i = 44; i<sounddata.length && x2 < width && y2 < height;i+=2)
			{
				x1=(i-2)-100;
				y1=(sounddata[i-2])/2+150;
				x2=i-100;
				y2=(sounddata[i])/2+150;
				g.drawLine(x1,y1,x2,y2);
			}
			//for each wave data point
			//check to make sure its on the screen
		for(int i = 44; i<sounddata.length && x2 < width && y2 < height;i+=2)
		{
			//set wave parameters
			x1=(i-2)-100;
			y1=(sounddata[i-2])/2+150;
			x2=i-100;
			y2=(sounddata[i])/2+150;
			//draw a piece of the wave
			g.drawLine(x1,y1,x2,y2);
		}
			for(int i=1; i < fft.length && i < width; i+=1)
			{
				g.drawLine((i-1),height/4-(int)(fft[i-1])/100,i,height/4-(int)(fft[i])/100);
			}

		for(int i=1; i < fft.length && i < width; i+=1)
		{
			g.drawLine((i-1),height/4-(int)(fft[i-1])/100,i,height/4-(int)(fft[i])/100);
		}
	}
}
	public void drawGraph(byte[] b)
	{
		sounddata = b;
		repaint();
	}
/*	
	public void drawFFT(double[] f)
	{
		fft = f;
		repaint();
	}
	*/
}
