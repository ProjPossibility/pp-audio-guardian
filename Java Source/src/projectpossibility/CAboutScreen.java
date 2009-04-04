package projectpossibility;

import javax.microedition.lcdui.*;

/**
 * The "about" page which includes credits
 * @author AG Team
 *
 */
public class CAboutScreen extends Form {

	private StringItem m_projectName;
	private StringItem m_teamHeading;
	private StringItem m_teamListing;
	private StringItem m_projectListing;
	
	public CAboutScreen()
	{
		super("About");
		
		// Set up the project name
		m_projectName = new StringItem("", "AudioGuardian v0.1");
		m_projectName.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD | Font.STYLE_UNDERLINED, Font.SIZE_LARGE));
		m_projectName.setLayout(Item.LAYOUT_CENTER);
		append(m_projectName);
		append("\n");
		
		// Set up the team members
		m_teamHeading = new StringItem("", "Team");
		m_teamHeading.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
		m_teamHeading.setLayout(Item.LAYOUT_LEFT);
		append(m_teamHeading);
		append("\n");
		
		// Set up the team listing
		m_teamListing = new StringItem("", "  Eric Hertz\n  Brian Garfinkel\n  Jeff Wang\n  Yin Lok Ho\n  Torrey Umland\n  Jeff Wilhelm (mentor)\n");
		m_teamListing.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
		append(m_teamListing);
		append("\n");
		
		// PP listing trash
		m_projectListing = new StringItem("", "Project Possibility 2009\nhttp://projectpossibility.org");
		m_projectListing.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_MEDIUM));
		append(m_projectListing);
		
	}
	
	
}
