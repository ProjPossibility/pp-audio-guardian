package projectpossibility;

import javax.microedition.lcdui.*;

public class COptionScreen extends Form {

	private Gauge m_alertThresholdGauge;
	private ChoiceGroup m_soundChoice;
	
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

	public void setAlertThreshold(int val)
	{
		m_alertThresholdGauge.setValue(val);
	}
	
	public int getAlertThreshold()
	{
		return m_alertThresholdGauge.getValue();
	}
	
	public void setSoundChoice(int index, boolean value)
	{
		m_soundChoice.setSelectedIndex(index, value);
	}
	
	public boolean getSoundChoice(int index)
	{
		return m_soundChoice.isSelected(index);
	}
	
}
