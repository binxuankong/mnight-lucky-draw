import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.RenderingHints;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.*;

// Class to insert image to the gui by inserting it into a JPanel.
public class InsertImage extends JPanel
{
  // The image.
  private BufferedImage image;

  // The image icon.
  private ImageIcon imageIcon;

  // The image as JLabel.
  private JLabel imageJLabel;

  // Constructor.
  public InsertImage(String fileName)
  {
    try
    {                
      image = ImageIO.read(new File(fileName));
      imageIcon = new ImageIcon(image);
    } // try
    catch (IOException exception)
    {
      System.err.println(exception);
    } // catch
  } // ImagePanel

  // Set the size of the image.
  public void setImageSize(int width, int height)
  {
    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = (Graphics2D) newImage.createGraphics();
    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
    g2d.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
    image = newImage;
    imageIcon = new ImageIcon(image);
  } // setImageSize

  // Return the image as JLabel.
  public JLabel getImage()
  {
    imageJLabel = new JLabel(imageIcon);
    return imageJLabel;
  } // getImage

} // class InsertImage
