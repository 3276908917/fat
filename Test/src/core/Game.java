package core;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import visuals.Frame;
import entities.Boundary;
import entities.Human;
import entities.Machine;

@SuppressWarnings("serial")
public class Game extends MasterInfo implements ActionListener, KeyListener{
	// All sounds to be played are added to this ArrayList and removed once played.
	private static ArrayList<Clip> soundStack = new ArrayList<Clip>();
	
	// The Game constructor loads the area, creates the timers, and sets-up player objects. 
	public Game() {
		loadMap();
		// Create the timers. 'time' controls date, hours, minutes, seconds. 'gameTimer' controls all other events (e.g. entity movement).
		time = new Timer(1000, this);
		time.start();
		gameTimer = new Timer(TIMER_SPEED, this);
		gameTimer.start();
		/* Set-up player objects. At any given moment, the player can either be playing as the main character or their machine. Objects for both the machine and the human exist at all times,
		 * and the 'player' object is simply reassigned to either the 'machine' or the 'human' object whenever the player switches.*/
		machine = new Machine();
		human = new Human();
		player = human;
		tab = new Frame();
		panel.add(tab);
	}
	
	// For every change in area, loadMap() will establish boundaries, objects, enemies, etc. It will also clear those from the previous area. Currently, it only works with boundaries.
	private void loadMap() {
	bounds.clear();
		try {
			Scanner in = new Scanner(new FileReader("area/" + area + ".txt"));
			while (in.hasNext()) {
				String line = in.nextLine();
				// bounds.add() takes two coordinate pairs for a total of four arguments to construct a line boundary.
				bounds.add(new Boundary(
						Integer.parseInt(line.substring(0, line.indexOf(","))) * resX / DEV_RESX, Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(",", line.indexOf(",") + 1))) * resX / DEV_RESX,
						Integer.parseInt(line.substring(line.indexOf(",", line.indexOf(",") + 1) + 1, line.indexOf(",", line.indexOf(",", line.indexOf(",") + 1) + 1))) * resY / DEV_RESY, 
						Integer.parseInt(line.substring(line.indexOf(",", line.indexOf(",", line.indexOf(",") + 1) + 1) + 1)) * resY / DEV_RESY
						));
			}
			in.close();
		} catch (FileNotFoundException e) {}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(gameTimer)) {
			// Rotating the player image to face the mouse:
			rotX = player.getRotater().getWidth(null) * sizeX / 2 + player.getXPos();
			rotY = player.getRotater().getHeight(null) * sizeY + player.getYPos();
			double mouseX = MouseInfo.getPointerInfo().getLocation().getX() - frame.getLocationOnScreen().getX();
			double mouseY = MouseInfo.getPointerInfo().getLocation().getY() - frame.getLocationOnScreen().getY();
			double cos = mouseX - rotX, sin = mouseY - rotY;
			cos /= Math.hypot(cos, sin);
			imageOrientation = Math.copySign(Math.acos(cos), sin) + .5 * Math.PI;
			panel.repaint();
		}
		else if (e.getSource().equals(time)) {
			// Changing the time and date by incrementing the 'second' variable:
			second++;
			if (second == 60) {
				second = 0;
				minute++;
				if (minute == 60) {
					minute = 0;
					hour++;
					if (hour == 24) {
						hour = 0;
						dayOfMonth++;
						try {
							Scanner in = new Scanner(new FileReader("text/days.txt"));
							while(in.hasNext()) {
								String line = in.nextLine();
								if (line.equals(dayOfWeek)) {if (in.hasNext()) dayOfWeek = in.nextLine(); else {in = new Scanner(new FileReader("text/days.txt")); dayOfWeek = in.nextLine();}}
							}
							in.close();
						} catch (FileNotFoundException E) {}
						if (dayOfMonth > 28) {
							byte dayLimit = 0;
							try {
								Scanner in = new Scanner(new FileReader("text/daysPerMonth.txt"));
								while(in.hasNext()) {
									String line = in.nextLine();
									if (Integer.parseInt(line.substring(0, line.indexOf(":"))) == month) dayLimit = (byte) Integer.parseInt(line.substring(line.indexOf(":") + 1));
								}
								in.close();
							} catch (FileNotFoundException E) {}
							if (dayOfMonth > dayLimit) {
								dayOfMonth = 1;
								month++;
								if (month == 13) {month = 1; year++;}
							}
						}
					}
				}
			}
			tab.updateTime();
		}
	}
	
	// Currently, Java's built-in libraries are used to produce sound. I plan on migrating to a more robust and customizable system in the future.
	public static void playSound(String fileLoc, int loopCount) throws Exception {
    	File soundFile = new File(fileLoc);
    	Clip clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
        clip.open(ais);
        soundStack.add(clip);
        clip.loop(loopCount);
        SwingUtilities.invokeLater(new Runnable(){public void run(){}});
    }
	
	/* Three methods are used to end any sounds that may be playing at the moment: stop(), flush(), and close(). It is most likely unnecessary to use all three of them.
	 * However, I do not know enough about AudioInputStream to design a more reliable system. */
	public static void stopAllSound() {
		for (byte c = (byte) (soundStack.size() - 1); c > -1; c--){
			soundStack.get(c).stop();
			soundStack.get(c).flush();
			soundStack.get(c).close();
			soundStack.remove(c);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_COMMA && !player.getU()){
			player.startTimer();
			player.setNorth(true);}
		if (key == KeyEvent.VK_A && !player.getL()){
			player.startTimer();
			player.setWest(true);}
		if (key == KeyEvent.VK_O && !player.getD()){
			player.startTimer();
			player.setSouth(true);}
		if (key == KeyEvent.VK_E && !player.getR()){
			player.startTimer();
			player.setEast(true);}
		if (key == KeyEvent.VK_SHIFT)
			player.setSprint(true);
		if (key == KeyEvent.VK_ESCAPE) menu.returnToMain();
	}
	
	public void keyReleased(KeyEvent e)	{
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_COMMA) player.setNorth(false);
		if (key == KeyEvent.VK_A) player.setWest(false);
		if (key == KeyEvent.VK_O) player.setSouth(false);
		if (key == KeyEvent.VK_E) player.setEast(false);
		if (key == KeyEvent.VK_SHIFT)
			player.setSprint(false);
		if (key == KeyEvent.VK_TAB) tab.toggle();
		if (key == KeyEvent.VK_Z) {player = machine; tab.toggle(); tab.toggle();}
		if (key == KeyEvent.VK_X) {player = human; tab.toggle(); tab.toggle();}
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public String getArea() {return area;}
}