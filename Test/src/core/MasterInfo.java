package core;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import visuals.Frame;
import visuals.Menu;
import visuals.Panel;
import entities.Boundary;
import entities.Human;
import entities.Machine;
import entities.Player;

@SuppressWarnings("serial")
public class MasterInfo extends JPanel {
	public static JFrame frame;
	public static Panel panel;
	
	public static Menu menu;
	public static Game game;
	public static Machine machine;
	public static Human human;
	public static Player player;
	public static Frame tab;
	// 'area' variable changes as player moves from room to room. The digit increases to 9 before resetting to 0, with the letter changing to the next.
	public static String area = "a0";
	public static ArrayList<Boundary> bounds = new ArrayList<Boundary>();
	public static int money = 0;
	public static byte saveSlot;
	// The variables 'resX' and 'resY' contain the horizontal and vertical resolutions that the user has selected.
	public static short resX = 1920, resY = 1080, mapW = 562, mapH = 400;
	public static float pureRatio = 1;
	/* The constants 'DEV_RESX' and 'DEV_RESY' represent the horizontal and vertical resolutions at which the UI is developed.
	 * 'DEV_RESX' and 'DEV_RESY' are used to adjust the values of visual components so that they appear appropriately for the selected resolution. */
	public static final short DEV_RESX = 1920, DEV_RESY = 1080;
	// Multiples of the constant 'TIMER_SPEED' are used to set the rate of any timer so that the speed of the entire program may be adjusted from one point.
	public static final byte TIMER_SPEED = 20, SAVE_SLOTS = 4;
	/* 'menuTimerTitle', 'menuTimerButton', and 'menuTimerBackground' are used for the randomization of various attributes of components on the main menu.
	 * 'time' controls date, hours, minutes, seconds. 'gameTimer' controls all other events (e.g. entity movement). */
	public static Timer menuTimerTitle, menuTimerButton, menuTimerBackground, gameTimer, time;
	// Image resources
	public static Image enviro = new ImageIcon("sprite/area/env1.png").getImage();
	public static Image icon = new ImageIcon("sprite/icon.png").getImage();
	public static BufferedImage map = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
	// Rotation and scaling variables
	public static double imageOrientation, sizeX = 0.15, sizeY = 0.15, 	rotX, rotY;
	// Date and time variables
	public static byte dayOfMonth = 5, month = 6, hour = 12, minute = 6, second = 34;
	public static short year = 2045;
	public static String dayOfWeek = "Tuesday";
	
	public JLabel simpleLabel(JLabel Label, int sizeX, int sizeY, int locX, int locY, Color foreground, Font font, String text) {
		Label.setSize(sizeX * resX / DEV_RESX, sizeY * resY / DEV_RESY);
		Label.setLocation(locX * resX / DEV_RESX, locY * resY / DEV_RESY);
		Label.setOpaque(false);
		Label.setForeground(foreground);
		Label.setFont(font.deriveFont(font.getSize() * pureRatio));
		Label.setText(text);
		Label.setVisible(true);
		return Label;
	}
	
	public JLabel label(JLabel Label, int sizeX, int sizeY, int locX, int locY, boolean opaque, Color background, Color foreground,
			Border border, String font, int format, int fontSize, String text) {
		Label.setSize(sizeX * resX / DEV_RESX, sizeY * resY / DEV_RESY);
		Label.setLocation(locX * resX / DEV_RESX, locY * resY / DEV_RESY);
		Label.setOpaque(opaque);
		Label.setBackground(background);
		Label.setForeground(foreground);
		Label.setBorder(border);
		// Volatile
		Label.setFont(new Font(font, format, (int) (fontSize * pureRatio)));
		Label.setText(text);
		Label.setVisible(true);
		return Label;
	}
	
	public JButton simpleButton(JButton button, int sizeX, int sizeY, int locX, int locY, Color background, ActionListener aL) {
		button.setSize(sizeX * resX / DEV_RESX, sizeY * resY / DEV_RESY);
		button.setLocation(locX * resX / DEV_RESX, locY * resY / DEV_RESY);
		button.setBackground(background);
		button.setEnabled(true);
		button.addActionListener(aL);
		button.setFocusable(false);
		button.setVisible(true);
		return button;
	}
	
	public JButton button(JButton button, int sizeX, int sizeY, int locX, int locY, Color background, Color foreground, String font,
			int format, int fontSize, String text, boolean enabled, ActionListener aL) {
		button.setSize(sizeX * resX / DEV_RESX, sizeY * resY / DEV_RESY);
		button.setLocation(locX * resX / DEV_RESX, locY * resY / DEV_RESY);
		button.setBackground(background);
		button.setForeground(foreground);
		// Volatile
		button.setFont(new Font(font, format, (int) (fontSize * pureRatio)));
		button.setText(text);
		button.setEnabled(enabled);
		button.addActionListener(aL);
		button.setFocusable(false);
		button.setVisible(true);
		return button;
	}
	
	public JComboBox<String> dropdown(JComboBox<String> dropdown, int sizeX, int sizeY, int locX, int locY, Font font, ActionListener aL) {
		dropdown.setSize(sizeX * resX / DEV_RESX, sizeY * resY / DEV_RESY);
		dropdown.setLocation(locX * resX / DEV_RESX, locY * resY / DEV_RESY);
		// Volatile
		dropdown.setFont(font.deriveFont(font.getSize() * pureRatio));
		dropdown.addActionListener(aL);
		dropdown.setVisible(true);
		dropdown.setEnabled(true);
		dropdown.setFocusable(false);
		return dropdown;
	}
} 