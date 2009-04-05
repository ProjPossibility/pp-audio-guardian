package projectpossibility;

import javax.microedition.media.Manager;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
/**
 * main program
 * @author AG Team
 * 
 */
public class AudioGuardian extends MIDlet implements CommandListener {
	
	private double m_userDefinedSound = -1.0;
	
	private Display m_display;
	private COptionsDb m_optionsDb;
	
	private Monitor m_monitor;
	private Calibrator m_calibrator;
	private Recorder m_recorder;
	private FloorLevel m_floorLevel;
	
	private CStartupScreen m_startupScreen;
	private Command m_startupMenuCommand;
	private Command m_exitCommand;

	private List m_menuList;
	private Command m_menuSelectCommand;
	
	private Graph m_graph;
	private Command m_graphBackCommand;
	
	private COptionScreen m_optionScreen;
	private Command m_optionsBackCommand;
	private Command m_optionsSaveCommand;
	
	private CRecordScreen m_recordScreen;
	private Command m_recordDoneCommand;
	
	private CCalibrationScreen m_calibrationScreen;
	private Command m_calibrationDoneCommand;
	
	private CAboutScreen m_aboutScreen;
	private Command m_aboutBackCommand;
	
	private int m_alertThreshold;
	private CMatchableSound [] m_matchSounds;
	
	private static String[] m_builtinSoundNames = 
	{
		"500 Hz",
		"Fire Alarm",
		"Car Horn",
	};
	
	private double[] m_builtinSoundFreqs =
	{
		512,
		2112,
		992
	};

	/**
	 * constructor
	 */
	public AudioGuardian() 
	{	
		// Load up our options DB
		m_optionsDb = new COptionsDb();
		m_userDefinedSound = m_optionsDb.LoadDouble("User Defined Sound", -1.0);
		System.out.println(m_userDefinedSound);
		
		// Class vars
		m_display = Display.getDisplay(this);
		
		// Build up our matchable sounds
		m_matchSounds = new CMatchableSound[m_builtinSoundNames.length + 1];
		for(int i = 0; i < m_builtinSoundNames.length; ++i)
		{
			CMatchableSound newSound = new CMatchableSound(m_builtinSoundNames[i], m_display); 
			newSound.setFrequency(m_builtinSoundFreqs[i]);
			
			m_matchSounds[i] = newSound;
		}
		
		//Add the user defined sound, if it exists
		if( m_userDefinedSound != -1.0 )
		{
			CMatchableSound newSound = new CMatchableSound("User Defined Sound", m_display);
			newSound.setFrequency(m_userDefinedSound);
			m_matchSounds[m_matchSounds.length-1] = newSound;
		}
		
		
		// Load the options values
		m_alertThreshold = m_optionsDb.LoadInt("alert", 5);
		for(int i = 0; i < m_matchSounds.length - 1; ++i)
		{
			CMatchableSound curSound = m_matchSounds[i];
			
			boolean isEnabled = m_optionsDb.LoadBool(curSound.getName(), false);
			curSound.setEnabled(isEnabled);
		}
		m_floorLevel = new FloorLevel(m_optionsDb.LoadDouble("floor", 50.0));

		// Set up commands & GUI
		// - Startup screen commands
		m_exitCommand = new Command("Exit", Command.EXIT, 0);
		m_startupMenuCommand = new Command("Menu", Command.SCREEN, 1);
		// - Startup screen
		m_startupScreen = new CStartupScreen();
		m_startupScreen.addCommand(m_exitCommand);
		m_startupScreen.addCommand(m_startupMenuCommand);
		m_startupScreen.setCommandListener(this);
		
		// - Menu list commands
		m_menuSelectCommand = new Command("Select", Command.OK, 0);
		// - Menu list
		m_menuList = new List("Main Menu", Choice.IMPLICIT);
		m_menuList.append("Go", null);
		m_menuList.append("Record Sound", null);
		m_menuList.append("Config", null);
		m_menuList.append("Calibrate", null);
		m_menuList.append("About", null);
		m_menuList.addCommand(m_exitCommand);
		m_menuList.addCommand(m_menuSelectCommand);
		m_menuList.setSelectCommand(m_menuSelectCommand);
		m_menuList.setCommandListener(this);
		
		// - Record screen commands
		//m_recordCommand = new Command("Record", Command.OK, 0);
		m_recordDoneCommand = new Command("Done", Command.OK, 1);
		// - Record Screen
		m_recordScreen = new CRecordScreen();
		//m_recordScreen.addCommand(m_recordCommand);
		//m_recordScreen.addCommand(m_recordBackCommand);
		//m_recordScreen.setCommandListener(this);
		
		// - Graph Screen command
		m_graphBackCommand = new Command("Back", Command.BACK, 0);
		// - Graph Screen
		m_graph = new Graph();
		m_graph.addCommand(m_graphBackCommand);
		m_graph.setCommandListener(this);
		
		// - Options Screen commands
		m_optionsBackCommand = new Command("Back", Command.BACK, 1);
		m_optionsSaveCommand = new Command("Save", Command.OK, 1);
		// - Options screen
		m_optionScreen = new COptionScreen(m_builtinSoundNames);
		m_optionScreen.addCommand(m_optionsBackCommand);
		m_optionScreen.addCommand(m_optionsSaveCommand);
		m_optionScreen.setCommandListener(this);
		
		// - Calibration Screen commands
		m_calibrationDoneCommand = new Command("Done", Command.OK, 1);
		// - Calibration screen
		m_calibrationScreen = new CCalibrationScreen();
		
		// - About Screen commands
		m_aboutBackCommand = new Command("Back", Command.BACK, 1);
		// - About Screen
		m_aboutScreen = new CAboutScreen();
		m_aboutScreen.addCommand(m_aboutBackCommand);
		m_aboutScreen.setCommandListener(this);
		
	}

	/**
	 * destructor
	 */
	protected void destroyApp(boolean unconditional) {
		notifyDestroyed();
	}

	/**
	 * pause app
	 */
	protected void pauseApp() {
	}

	/**
	 * start app
	 */
	protected void startApp() throws MIDletStateChangeException {
		
		m_display.setCurrent(m_startupScreen);
		
	}
	
	/**
	 * @param c Command object
	 * @param d Displayable object
	 */
	public void commandAction(Command c, Displayable d)
	{
		// Open config
		if( c == m_startupMenuCommand)
		{
			m_display.setCurrent(m_menuList);
		}
		
		// Back from config
		else if(c == m_optionsBackCommand)
		{
			m_display.setCurrent(m_menuList);
		}
		
		// Save from config
		else if(c == m_optionsSaveCommand)
		{
			m_alertThreshold = m_optionScreen.getAlertThreshold();
			m_optionsDb.SaveInt("alert", m_alertThreshold);
			
			for(int i = 0; i < m_matchSounds.length - 1; ++i)
			{
				CMatchableSound curSound = m_matchSounds[i];
				
				curSound.setEnabled(m_optionScreen.getSoundChoice(i));
				m_optionsDb.SaveBool(curSound.getName(), curSound.isEnabled());
			}
			
			
			m_display.setCurrent(m_menuList);
		}
		
		// Done calibrating
		else if (c == m_calibrationDoneCommand)
		{
			m_optionsDb.SaveDouble("floor", m_floorLevel.getLevel());	
			m_display.setCurrent(m_menuList);
		}
		
		// Back from about
		else if (c == m_aboutBackCommand)
		{
			m_display.setCurrent(m_menuList);
		}
		
		// Back from record 
		else if(c == m_recordDoneCommand)
		{
			m_display.setCurrent(m_menuList);
			m_userDefinedSound = m_optionsDb.LoadDouble("User Defined Sound", -1);
			
			//reinitialize the user defined sound
			CMatchableSound newSound = new CMatchableSound("User Defined Sound", m_display);
			newSound.setFrequency(m_userDefinedSound);
			newSound.setEnabled(true);
			m_matchSounds[m_matchSounds.length-1] = newSound;
		}
		
		// Menu selection
		else if (c == m_menuSelectCommand)
		{
			switch(m_menuList.getSelectedIndex())
			{
			// Go to the graph
			case 0:
		    	
				try
				{
					m_monitor = new Monitor(Manager.createPlayer("capture://audio"), 
											m_graph, 
											m_display, 
											m_alertThreshold,
											m_floorLevel,
											m_matchSounds);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

				Thread monitorThread = new Thread(m_monitor);
				monitorThread.start();
				
				m_display.setCurrent(m_graph);
				break;
				
			// Record your own sound	
			case 1:
				// TODO: Implement recording functionality
				m_display.setCurrent(m_recordScreen);
				try
				{
					m_recorder = new Recorder(Manager.createPlayer("capture://audio"), 
												  m_recordScreen, 
												  this,
												  m_recordDoneCommand,
												  m_optionsDb);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				Thread recordingThread = new Thread(m_recorder);
				recordingThread.start();
				break;
			// Go to options!
			case 2:
				m_optionScreen.setAlertThreshold(m_alertThreshold);
				for(int i = 0; i < m_matchSounds.length - 1; ++i)
				{
					m_optionScreen.setSoundChoice(i, m_matchSounds[i].isEnabled());
				}
				
				m_display.setCurrent(m_optionScreen);
				break;
				
			// Calibrate
			case 3:
				m_display.setCurrent(m_calibrationScreen);
				try
				{
					m_calibrator = new Calibrator(Manager.createPlayer("capture://audio"), 
												  m_graph, 
												  m_floorLevel, 
												  m_calibrationScreen, 
												  this,
												  m_calibrationDoneCommand);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				Thread calibrationThread = new Thread(m_calibrator);
				calibrationThread.start();
				break;
				
			// Go to about
			case 4:
				m_display.setCurrent(m_aboutScreen);
				break;
			}
		}
		
		// Back from graph
		else if (c == m_graphBackCommand)
		{
			m_monitor.stop();
			m_display.setCurrent(m_menuList);
		}
		
		// Exiting
		else if (c == m_exitCommand)
		{
			destroyApp(true);
		}
	}

}
