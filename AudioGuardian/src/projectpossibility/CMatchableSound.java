package projectpossibility;

import javax.microedition.lcdui.*;

public class CMatchableSound {
	
	private String m_name;
	private boolean m_isEnabled;
	private double m_frequency;
	private double m_tolerance;
	private Display m_display;
	
	private static Image m_alertImage = null;
	
	public CMatchableSound(String name, Display display)
	{
		if(m_alertImage == null)
		{
			try
			{
				m_alertImage = Image.createImage("/exclamation.png");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		m_name = name;
		m_display = display;
		m_isEnabled = false;
	
		m_frequency = 0.0;
		m_tolerance = 0.01;
	}
	
	public void setFrequency(double freq)
	{
		m_frequency = freq;
	}
	
	public String getName()
	{
		return m_name;
	}
	
	public boolean isEnabled()
	{
		return m_isEnabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		m_isEnabled = enabled;
	}
	
	public boolean checkMatch(double loudestFreq)
	{
		if( Math.abs(loudestFreq - m_frequency) <= m_tolerance )
		{
			// Start vibing
			m_display.vibrate(1000);
			
			// Start flashing
			m_display.flashBacklight(1000);
			
			// Do a popup
			Alert curAlert = new Alert("Sound detected", m_name, m_alertImage, AlertType.ALARM);
			curAlert.setTimeout(3000);
			m_display.setCurrent(curAlert, m_display.getCurrent());
			
			try
			{
				Thread.sleep(curAlert.getTimeout() + 200);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return true;
		}
		
		return false;
		
	}
}
