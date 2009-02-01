
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class TutorialMidlet extends MIDlet
                          implements CommandListener {

  Display display;
  Command greetCommand;
  Command exitCommand;
  Command clearCommand;
  Command backCommand;
  WelcomeScreen welcomeScreen;
  HelloScreen helloScreen; 
  IAlert vibrate;
  // instantiate the internal variables
  public TutorialMidlet () {
    display = Display.getDisplay(this);
    
	vibrate = new Vibrate(200, 3, display);
    greetCommand =
        new Command ("Greet", Command.OK, 0);
    exitCommand =
        new Command ("Exit", Command.EXIT, 0);
    clearCommand =
        new Command ("Clear", Command.CANCEL, 1);
    backCommand =
        new Command ("Back", Command.SCREEN, 1);

    welcomeScreen = new WelcomeScreen ();
    welcomeScreen.addCommand (greetCommand);
    welcomeScreen.addCommand (clearCommand);
    welcomeScreen.setCommandListener (this);

    helloScreen = new HelloScreen ();
    helloScreen.addCommand (exitCommand);
    helloScreen.addCommand (backCommand);
    helloScreen.setCommandListener (this);
  }

  // Called when the MIDlet is started by the AMS
  protected void startApp () {
    display.setCurrent (welcomeScreen);
  }

  protected void pauseApp () {
    // Do nothing
  }

  protected void destroyApp (boolean unconditional) {
    notifyDestroyed ();
  }

  public void commandAction (Command c, Displayable d) {
    if (c == greetCommand) {
      String name = welcomeScreen.getName ();
      helloScreen.setName(name);
      display.setCurrent (helloScreen);
      vibrate.trigger();

    } else if (c == clearCommand) {
      welcomeScreen.setName("");
      display.setCurrent(welcomeScreen);

    } else if (c == backCommand) {
      display.setCurrent (welcomeScreen);

    } else if (c == exitCommand) {
      destroyApp (true);
    }
    
  }

}