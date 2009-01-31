package tutorial;

import javax.microedition.lcdui.*;

public class WelcomeScreen extends Form {
	
	private TextField nameField;
	
	public WelcomeScreen () {
		super ("Welcome");
		Image img;
		// Construct the image from the media file
		try {
			img = Image.createImage("/welcome.png");
		} catch (Exception e) {
			e.printStackTrace ();
			img = null;
		}
		
		ImageItem imageItem = new ImageItem ("", img, ImageItem.LAYOUT_CENTER, "Welcome");
		nameField = new TextField ("Please enter your name", "", 10, TextField.ANY);
		
		append (imageItem);
		append (nameField);
	}
	
	public void setName (String n) {
		nameField.setString (n);
	}
	
	public String getName () {
		return nameField.getString ();
	}
}