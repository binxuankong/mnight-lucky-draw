import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.util.Random;
import javax.swing.Timer;

// Program for Manchester Malaysian Night 2017 Lucky Draw
public class LuckyDraw extends JFrame implements ActionListener
{
  // Text area to show the lucky winner
  private final JEditorPane displayJEditorPane = new JEditorPane();

  // JButton
  private JButton startJButton;
  private JButton luckyJButton;

  // Is timer running?
  private boolean running = false;

  // The first winner, prevent duplication
  private char firstWinnerRow;
  private int firstWinnerSeat;
  private boolean firstWinnerDone = false;

  // Random generator
  private Random rand = new Random();

  // Constructor
  public LuckyDraw()
  {
    setTitle("Manchester Malaysian Night 2017 Lucky Draw");

    Container contents = getContentPane();
    contents.setLayout(new BorderLayout());

    JPanel titlePanel = new JPanel();
    contents.add(titlePanel, BorderLayout.NORTH);
    // Insert image for header.
    InsertImage header = new InsertImage("images/header.jpg");
    header.setImageSize(828, 315);
    titlePanel.add(header.getImage());

    contents.add(displayJEditorPane, BorderLayout.CENTER);
    displayJEditorPane.setPreferredSize(new Dimension(828, 315));
    displayJEditorPane.setEditable(false);
    HTMLEditorKit editorKit = new HTMLEditorKit();
    displayJEditorPane.setEditorKit(editorKit);
    displayJEditorPane.setText("<b><center><font face=\"Lucida Console\" size=\"50\">The winners of Manchester Malaysian Night 2017 Lucky Draw are </center></font></b>");

    // The buttons go at the bottom.
    JPanel buttonJPanel = new JPanel();
    contents.add(buttonJPanel, BorderLayout.SOUTH);
    buttonJPanel.setLayout(new GridLayout(1, 2));

    // Images for buttons
    ImageIcon button1 = new ImageIcon("images/button1.jpg");
    ImageIcon button2 = new ImageIcon("images/button2.jpg");

    startJButton = new JButton(button1);
    buttonJPanel.add(startJButton);
    startJButton.addActionListener(this);

    luckyJButton = new JButton(button2);
    buttonJPanel.add(luckyJButton);
    luckyJButton.addActionListener(this);
    luckyJButton.setEnabled(false);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();
  } // LuckDraw

  // Check seat validity, return TRUE if it is valid.
  public boolean checkSeat(char row, int seat)
  {
    switch(row) {
      case 'A': if (seat < 3 || seat > 22)
                  return false;
                else
                  return true;
      case 'B': if (seat < 2 || seat == 17 || seat > 23)
                  return false;
                else
                  return true;
      case 'F': if (seat < 8 || seat > 17)
                  return false;
                else
                  return true;
      case 'G': if (seat < 3 || seat > 22)
                  return false;
                else
                  return true;
      case 'I': return false;
      case 'H':      case 'J':      case 'K':      case 'L':      case 'M':      case 'N':
      case 'O':      case 'P':      case 'Q':      case 'R':      case 'S':
      case 'T': if (seat < 2 || seat > 23)
                  return false;
                else
                  return true;
      case 'U': if (seat < 3 || seat > 7 || seat < 18 || seat > 22)
                  return false;
                else
                  return true;
      default: return true;
    } // switch
  } // checkSeat

  // Timer to change text rapidly
  final Timer updater = new Timer(50, new ActionListener()
  {
    public void actionPerformed(ActionEvent event)
    {
      // Get ASCII value of characters from A to U
      char randRow = (char) (rand.nextInt(19) + 65);
      int randSeat = rand.nextInt(24) + 1;

      // Check validity, get new row and seat if it is not valid
      while (!checkSeat(randRow, randSeat) || randRow == firstWinnerRow && randSeat == firstWinnerSeat)
      {
        randRow = (char) (rand.nextInt(19) + 65);
        randSeat = rand.nextInt(24) + 1;
      } // while

      // Display random seats
      if (firstWinnerDone)
      {
        displayJEditorPane.setText("<b><center><font face=\"Lucida Console\" size=\"60\">The winners of Manchester Malaysian Night 2017 Lucky Draw are <br><br><br> </font></center> <font face=\"Lucida Console\" size=\"80\" color=\"blue\"><center>Row " + firstWinnerRow + "\tSeat " + firstWinnerSeat + "</center></font><br><br><font face=\"Lucida Console\" size=\"60\" color=\"red\"><center>Row " + randRow + "\tSeat " + randSeat + "</center></font></b>");
      } // if
      else
      {
        displayJEditorPane.setText("<b><center><font face=\"Lucida Console\" size=\"60\">The winners of Manchester Malaysian Night 2017 Lucky Draw are <br><br><br> </font></center> <font face=\"Lucida Console\" size=\"80\" color=\"blue\"><center>Row " + randRow + "\tSeat " + randSeat + "</center></font></b>\n");
      } // else
    } // actionPerformed
  }); // updater

  // Act upon the button being pressed.
  public void actionPerformed(ActionEvent event)
  {
    // Start button
    if (event.getSource() == startJButton)
    {
      updater.start();
      startJButton.setEnabled(false);
      luckyJButton.setEnabled(true);
    } // startJButton

    // Lucky button
    else if (event.getSource() == luckyJButton)
    {
      // Get ASCII value of characters from A to U
      char randRow = (char) (rand.nextInt(19) + 65);
      int randSeat = rand.nextInt(24) + 1;

      // Check validity, get new row and seat if it is not valid
      while (!checkSeat(randRow, randSeat) || randRow == firstWinnerRow && randSeat == firstWinnerSeat)
      {
        randRow = (char) (rand.nextInt(19) + 65);
        randSeat = rand.nextInt(24) + 1;
      } // while

      // Display winner. Save it as first winner if there is not one in the first place.
      if (firstWinnerDone)
      {
        updater.stop();
        running = false;
        firstWinnerDone = false;
        startJButton.setEnabled(true);
        luckyJButton.setEnabled(false);

        displayJEditorPane.setText("<b><center><font face=\"Lucida Console\" size=\"60\">The winners of Manchester Malaysian Night 2017 Lucky Draw are <br><br><br> </font></center> <font face=\"Lucida Console\" size=\"60\" color=\"blue\"><center>Row " + firstWinnerRow + "\tSeat " + firstWinnerSeat + "</center></font><br><br><font face=\"Lucida Console\" size=\"80\" color=\"red\"><center>Row " + randRow + "\tSeat " + randSeat + "</center></font></b>");
      } // if
      else
      {
        updater.stop();
        running = false;
        startJButton.setEnabled(true);
        luckyJButton.setEnabled(false);

        // Save the winner
        firstWinnerRow = randRow;
        firstWinnerSeat = randSeat;
        firstWinnerDone = true;
        displayJEditorPane.setText("<b><center><font face=\"Lucida Console\" size=\"60\">The winners of Manchester Malaysian Night 2017 Lucky Draw are <br><br><br> </font></center> <font face=\"Lucida Console\" size=\"80\" color=\"blue\"><center>Row " + randRow + "\tSeat " + randSeat + "</center></font></b>\n");
      } // else
    } // luckyJButton
  } // actionPerformed


  public static void main(String[] args)
  {
    new LuckyDraw().setVisible(true);
  } // main

} // LuckyDraw
