package projectpossibility;

import javax.microedition.lcdui.*;

/**
 * A previously recorded sound which can match itself against the incoming audio stream
 * and generate an alert to the user.  This initial implementation uses a simple matching
 * scheme and can only handle constant frequency sounds.  Later revisions will probably
 * include more sophisticated functionality.
 */
public class CMatchableSound {
	
	private String m_name;
	private boolean m_isEnabled;
	private double m_frequency;
	private double m_tolerance;
	private Display m_display;
	
	private static Image m_alertImage = null;
	
	/**
	 * Construct a new matchable sound
	 * @param name The name of the sound
	 * @param display The display object that should be alerted when the sound is detected
	 */
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
	
	/**
	 * Set the dominant frequency of the sound
	 * @param freq The frequency to set
	 */
	public void setFrequency(double freq)
	{
		m_frequency = freq;
	}
	
	/** 
	 * Get the sound's name
	 * @return The name of the sound
	 */
	public String getName()
	{
		return m_name;
	}
	
	/**
	 * Determine whether or not the sound is currently enabled
	 * @return true if the sound is enabled, false otherwise
	 */
	public boolean isEnabled()
	{
		return m_isEnabled;
	}
	
	/**
	 * Set the enabled status of the sound
	 * @param enabled true if the sound should be enabled, false otherwise
	 */
	public void setEnabled(boolean enabled)
	{
		m_isEnabled = enabled;
	}
	
	/**
	 * Analyze the dominant frequency in the current sound stream and generate an alert if it is a match.
	 * @param loudestFreq The loudest frequency in the current waveform
	 * @return true if the sound was matched, false otherwise
	 */
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
