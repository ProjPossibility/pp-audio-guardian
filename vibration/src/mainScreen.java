import javax.microedition.lcdui.*;


public class mainScreen extends Canvas {
	
	private String name;
	private int width, height;
	public mainScreen()
	{
		width = getWidth();
		height = getHeight();
		name = "Mobile Sound Notifier";
		
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(0xffffff);
		g.fillRect(0, 0, width, height);
		g.setColor(0x000000);
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_BOLD,Font.SIZE_LARGE));
		g.drawString (name, width / 2, height * 3/4,Graphics.BASELINE | Graphics.HCENTER);
	}
	
	public void setName(String n)
	{
		name = n;
	}

}
