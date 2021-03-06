
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.*;
import javax.microedition.media.*;




public class WelcomeScreen extends Form {

  private TextField nameField;

  public WelcomeScreen () {
    super ("Welcome");
    Image img;
    // Construct the image from the media file
    try {
    	//playsnd();
    	
      img = Image.createImage("/welcome.png");
    } catch (Exception e) {
      e.printStackTrace ();
      img = null;
    }
    ImageItem imageItem =
        new ImageItem ("", img,
              ImageItem.LAYOUT_CENTER, "Welcome");

    nameField =
        new TextField ("Please enter your name", "",
                       10, TextField.ANY);

    append (imageItem);
    append (nameField);
  }

  public void playsnd() {
	  try {
	  InputStream is = getClass().getResourceAsStream("/res/moo.wav");
	  Player p = Manager.createPlayer(is, "audio/x-wav");
	  p.prefetch();
	  p.start();
	  }
	  catch (IOException ex) {}
	  catch (MediaException ex) {}

	  }
  public void setName (String n) {
    nameField.setString (n);
  }

  public String getName () {
    return nameField.getString ();
  }


}