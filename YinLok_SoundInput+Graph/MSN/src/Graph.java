import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;


public class Graph extends Canvas{
	byte sounddata[] = new byte[0];
	public Graph(){
		
	}
	protected void paint(Graphics arg0) {
		// TODO Auto-generated method stub
		arg0.setColor(0x000000);
		arg0.fillRect(0,0,500,500);
		//draw graph
		arg0.setColor(0xFF0000);
		
		//index set at 50 to skip format header
		for(int i = 50; i<sounddata.length;i+=2){
			arg0.drawLine((i-2)-100,(sounddata[i-2])/2+150,i-100, (sounddata[i])/2+150);
		}
	}
	public void drawGraph(byte[] array){
		sounddata = array;
		repaint();
	}
}
