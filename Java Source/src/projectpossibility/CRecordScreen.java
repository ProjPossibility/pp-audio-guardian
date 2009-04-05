package projectpossibility;

import javax.microedition.lcdui.*;
/**
 * Displays info for recording user-defined detection sounds.
 * 
 */
public class CRecordScreen extends Form
{
private StringItem m_screenText;
	
	public CRecordScreen()
	{
		super("Recording");
		m_screenText = new StringItem("", "Recording in ... ");
		m_screenText.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));
		m_screenText.setLayout(Item.LAYOUT_VCENTER | Item.LAYOUT_CENTER);
		append(m_screenText);
	}
	/**
	 * Allows changing the display text.
	 * @param text Text to change it to.
	 */
	public void setText(String text)
	{
		m_screenText.setText(text);
	}
}

