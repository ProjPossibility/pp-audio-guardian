package projectpossibility;

import javax.microedition.lcdui.*;
/**
 * Screen for calibrator
 * @author AG Team
 *
 */
public class CCalibrationScreen extends Form {
	
	private StringItem m_screenText;
	/**
	 * constructor
	 */
	public CCalibrationScreen()
	{
		super("Calibrating");
		m_screenText = new StringItem("", "Calibrating in ... ");
		m_screenText.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));
		m_screenText.setLayout(Item.LAYOUT_VCENTER | Item.LAYOUT_CENTER);
		append(m_screenText);
	}
	
	/**
	 * function for setting text
	 * @param text string to set to
	 */
	public void setText(String text)
	{
		m_screenText.setText(text);
	}
	
}
