import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
// Graph
// Canvas used for graphing audio data
public class Graph extends Canvas{
	
	// byte array for raw sound data
	byte sounddata[] = new byte[0];
	
	// frequencies/amplitude double pairs
	double frequencies[] = new double[0];
	
	// background color
	int bgRGB[] = {0,0,0};
	
	// width and height
	int width,height;
	
	// constructor
	public Graph(){
		width = this.getWidth();
		height = this.getHeight();
	}
	protected void paint(Graphics arg0) {
		// TODO Auto-generated method stub
		
		//dim background
		arg0.setColor(bgRGB[0],bgRGB[1],bgRGB[2]);
		arg0.fillRect(0,0,500,500);
		
		//draw frequency bars
		for(int i = 2; i<frequencies.length-1&&i<width*4; i+=4){
			arg0.setColor(0xFFFFFF);
			arg0.fillRect(i-2,height-(int)frequencies[i+1]*4,2,(int)frequencies[i+1]*4);
			if((i-2)%40==0){
			arg0.setColor(0xAAAAAA);
			arg0.drawString(""+(int)(frequencies[i]),i-10, 10, 0);
			arg0.drawLine(i,40, i,height);
			}
		}
		
		//draw graph
		arg0.setColor(0xFF0000);
		
		//index set at 50 to skip format header
		for(int i = 2; i<sounddata.length&&i-100<width;i+=2){
			arg0.drawLine((i-2),(sounddata[i-2])/2+150,i, (sounddata[i])/2+150);
		}
		
		//dim background
		for(int i = 0; i<3; i++){
			if(bgRGB[i]>0)
				bgRGB[i]-=1;
		}
	}
	
	// update sampled stream data
	public void updateStreamData(byte[] array){
		sounddata = array;
		updateFrequencies();
	}
	
	// allows other objects to call repaint
	public void update(){
		repaint();
	}
	
	// function for applying DFT to signal
	public void updateFrequencies(){
	    // Cxfft instance
	    DFT dft = new DFT();
		
		// FFT processing
		frequencies = dft.transform(sounddata);
		
		//output highest intensity freq
		double hif =0;
		double amp =0;
		for(int i = 2; i< frequencies.length-1; i+=2){
			if(frequencies[i+1]>amp){
				hif = frequencies[i];
				amp = frequencies[i+1];
			}
		}
		//uncomment for checking highest frequency
		//System.out.println("High Frequency: "+hif);
	}
	
	// change background color as warning
	public void warning(){
		bgRGB[0]=100;
		bgRGB[1]=100;
		bgRGB[2]=100;
	}
}
