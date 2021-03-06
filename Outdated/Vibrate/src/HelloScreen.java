
import javax.microedition.lcdui.*;


public class HelloScreen extends Canvas {

  private int width, height;
  private String name;
  private Image img;

  public HelloScreen () {
    width = getWidth ();
    height = getHeight ();
    name = "unknown";

    // Construct the image from the media file
    try {
      img = Image.createImage("/hello.png");
    } catch (Exception e) {
      e.printStackTrace ();
      img = null;
    }
  }

  public void setName (String n) {
    name = n;
  }
  


  // Paint the screen based on the name
  protected void paint (Graphics g) {
    g.setColor(0xffffff);
    g.fillRect(0,  0, width, height);
    g.setColor(0x000000);

    g.drawImage (img, width / 2, height / 4,
            Graphics.VCENTER | Graphics.HCENTER);

    g.setFont(Font.getFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_BOLD,
                Font.SIZE_LARGE));
    g.drawString (name, width / 2, height * 3/4,
            Graphics.BASELINE | Graphics.HCENTER);
  }

}
