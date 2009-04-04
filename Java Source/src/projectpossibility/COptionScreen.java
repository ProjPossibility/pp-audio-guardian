package projectpossibility;

import javax.microedition.lcdui.*;

/**
 * The primary options screen for adjusting AudioGuardian settings.  Implements functionality to
 * set the alert threshold and enable/disable detection of specific sounds.
 */
public class COptionScreen extends Form {

	private Gauge m_alertThresholdGauge;
	private ChoiceGroup m_soundChoice;
	
	/**
	 * Construct a new COptionsScreen with the specified set of builtin sounds
	 * @param builtinSounds The list of sounds to display in the enable/disable dialog
	 */
	public COptionScreen(String [] builtinSounds)
	{
		super("Options");
		
		m_alertThresholdGauge = new Gauge("Alert Threshold", true, 10, 0);
		m_alertThresholdGauge.setLayout(Item.LAYOUT_EXPAND);
		append(m_alertThresholdGauge);
		append("\n");
		
		if(builtinSounds.length > 0 )
		{
			m_soundChoice = new ChoiceGroup("Sound Identification", Choice.MULTIPLE);
			
			for(int i = 0; i < builtinSounds.length; ++i)
			{
				m_soundChoice.append(builtinSounds[i], null);
			}
			
			append(m_soundChoice);
			append("\n");
		}
	}

	/**
	 * Set the threshold and which an alert should occur - should be between 0 and 10
	 * @param val The threshold at which to alert
	 */
	public void setAlertThreshold(int val)
	{
		m_alertThresholdGauge.setValue(val);
	}
	
	/**
	 * Retrieve the user's preferred alert threshold.
	 * @return The alert treshold (a value between 0 and 10)
	 */
	public int getAlertThreshold()
	{
		return m_alertThresholdGauge.getValue();
	}
	
	/**
	 * Set the enable/disable status of a specific alert sound.
	 * @param index The index of the sound to alert on
	 * @param value true if the sound should be enabled, false otherwise
	 */
	public void setSoundChoice(int index, boolean value)
	{
		m_soundChoice.setSelectedIndex(index, value);
	}
	
	/**
	 * Determine whether the user has enabled or disabled a specific sound.
	 * @param index The index of the sound to query
	 * @return true if the sound should be enabled, false otherwise
	 */
	public boolean getSoundChoice(int index)
	{
		return m_soundChoice.isSelected(index);
	}
	
}
