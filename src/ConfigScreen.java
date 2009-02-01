import javax.microedition.lcdui.*;
public class ConfigScreen extends Form
{
	public TextField volumeField;
	private int volLvl;
	
	public ConfigScreen() 
	{
		// TODO Auto-generated constructor stub
		super("Config");
		volumeField = new TextField("Enter the Volume Level:","", 3, TextField.NUMERIC);
		append (volumeField);
	}
	
	public void setVolumeLevel()
	{
		volLvl = Integer.parseInt(volumeField.getString());
	}
	
	public int getVolumeLevel()
	{
		return volLvl;
	}
	
	/*protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(0xffffff);
		g.fillRect(0, 0, width, height);
		g.setColor(0x000000);
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_BOLD,Font.SIZE_LARGE));
		g.drawString (name, width / 2, height * 3/4,Graphics.BASELINE | Graphics.HCENTER);
	}*/
}
