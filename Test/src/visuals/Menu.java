package visuals;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.PrintWriter; DO NOT DELETE
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;
import core.Game;
import core.MasterInfo;

@SuppressWarnings("serial")
public class Menu extends MasterInfo implements ActionListener, KeyListener {
	private ArrayList<JButton> mainButtonList = new ArrayList<JButton>(), loadButtonList = new ArrayList<JButton>(), optionButtonList = new ArrayList<JButton>(), aboutButtonList = new ArrayList<JButton>();
	private JLabel title = new JLabel(), about = new JLabel();
	
	//public static void main(String[] args){menu = new Menu();}
	
	public Menu() {
		try {Game.playSound("sound/music/menu.wav", 10);} catch (Exception e) {}
		frame.setSize(resX, resY);
		frame.setLocationRelativeTo(null);
		sizeX *= (double) resX / (double) DEV_RESX;
		sizeY *= (double) resY / (double) DEV_RESY;
		panel = new Panel();
		panel.setLayout(null);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		createMenuComponents();
		toggleMenuVisibility("Immortal");
		//mask(panel);
		// Initializing the timers responsible for the randomization of attributes of main menu components.
		menuTimerTitle = new Timer(TIMER_SPEED * 5, this);
		menuTimerButton = new Timer(TIMER_SPEED * 6, this);
		menuTimerBackground = new Timer(TIMER_SPEED * 4, this);
		menuTimerTitle.start();
		menuTimerButton.start();
		menuTimerBackground.start();
	}
	
	public String getScreen() {return title.getText();}
	
	private void createMenuComponents() {
		frame.addKeyListener(this);
		createButtons(mainButtonList);
		mainButtonList.get(0).setText("New Game");
		mainButtonList.get(1).setText("Load Game");
		mainButtonList.get(2).setText("Options");
		mainButtonList.get(3).setText("About");
		mainButtonList.get(4).setText("Exit");
		createButtons(aboutButtonList);
		aboutButtonList.get(4).setText("Return");
		for (byte c = 3; c > -1; c--) aboutButtonList.remove(c);
		createButtons(optionButtonList);
		optionButtonList.get(4).setText("Return");
		for (byte c = 3; c > -1; c--) optionButtonList.remove(c);
		scanSaves();
		simpleLabel(title, 9000, 80, 800, 100, Color.WHITE, new Font("trajanpro-regular", Font.PLAIN, 48), "<html><div align=center>Immortal</div></html>");
		Scanner in;
		String aboutText = "<html>" + "<html>";
		try {
			in = new Scanner(new FileReader("text/about.txt"));
			while (in.hasNext()) aboutText += in.nextLine() + "<br>"; 
			in.close();
		} catch (FileNotFoundException e) {}
		simpleLabel(about, 1000, 500, 100, 100, Color.GREEN, new Font("times new roman", Font.PLAIN, 24), aboutText);
	}
	
	private void scanSaves() {
		createButtons(loadButtonList);
		for (byte c = 0; c < SAVE_SLOTS; c++)
			try {
				Scanner in = new Scanner(new FileReader("save/" + c + ".txt"));
				if (in.hasNext()) loadButtonList.get(c).setText(in.nextLine());
				else loadButtonList.get(c).setEnabled(false);
				in.close();
			}
			catch (FileNotFoundException e) {}
		loadButtonList.get(4).setText("Return");
		
	}
	
	private void createButtons(ArrayList<JButton> buttonList) {
		for (byte i = 0; i < 5; i++) {
			buttonList.add(button(new JButton(), resX / 2, resY / 18, resX / 4, (int) (DEV_RESY / 3.8 + 140 * i), Color.ORANGE, Color.BLACK, "courier new", Font.PLAIN, 25, "", true, this));
		}
	}
	
	private void randomizeButtons(ArrayList<JButton> buttonList) {
		for (JButton b : buttonList) {
			byte bfont = (byte) (Math.random() * 10 + 1);
			if (bfont == 9) b.setFont(new Font("times new roman", Font.PLAIN, (int) (Math.random() * 6 + 22 * resY / DEV_RESY)));
			else if (bfont == 10) b.setFont(new Font("helvetica", Font.PLAIN, (int) (Math.random() * 6 + 22 * resY / DEV_RESY)));
			else b.setFont(new Font("courier new", Font.PLAIN, (int) (Math.random() * 6 + 22 * resY / DEV_RESY)));
			byte bcolor = (byte) (Math.random() * 10 + 1);
			if (bcolor == 1) b.setForeground(Color.ORANGE);
			else b.setForeground(Color.BLACK);
		}
	}
	
	private void randomizeButtons2(ArrayList<JButton> buttonList) {
		for (JButton b : buttonList) {
			short newWidth = (short) (Math.random() * 5 + resX / 2 - 5);
			short newHeight = (short) (Math.random() * 5 + resY / 18 - 5);
			b.setSize(newWidth, newHeight);
			short newX = (short) (Math.random() * 5 + resX / 4 - 5);
			b.setLocation(newX, b.getY());
		}
	}
	
	public void returnToMain() {
		time.stop();
		gameTimer.stop();
//		switch (saveSlot) {
//		case 0 : loadButtonList.get(0).setText(inventory.getDate()); break;
//		case 1 : loadButtonList.get(1).setText(inventory.getDate()); break;
//		case 2 : loadButtonList.get(2).setText(inventory.getDate()); break;
//		case 3 : loadButtonList.get(3).setText(inventory.getDate()); break;
//		}
//		try {
//			PrintWriter save = new PrintWriter("save/" + saveSlot + ".txt");
//			save.println(inventory.getDate());
//			save.println(second);
//			save.println(minute);
//			save.println(hour);
//			save.println(dayOfWeek);
//			save.println(dayOfMonth);
//			save.println(month);
//			save.println(year);
//			save.println(area);
//			save.println(offsetX);
//			save.println(offsetY);
//			save.close();
//		} catch (FileNotFoundException e) {e.printStackTrace();}
		Game.stopAllSound();
		//try {Game.playSound("sound/music/menu.wav", 10);} catch (Exception e) {}
		frame.removeKeyListener(game);
		frame.addKeyListener(this);
		title.setVisible(true);
		toggleMenuVisibility("Immortal");
		title.setText("Immortal");
		game = null;
	}
	
	private void newGame(byte S) {
		saveSlot = S;
		frame.removeKeyListener(this);
		Game.stopAllSound();
		panel.remove(title);
		game = new Game();
		frame.addKeyListener(game);
		toggleMenuVisibility("");
		//try {Game.playSound("sound/music/alone.wav", 10);} catch (Exception E) {}
		game = new Game();
	}
	
	private void toggleMenuVisibility(String menu) {
		panel.removeAll();
		if (!menu.equals("")) panel.add(title);
		title.setText(menu);
		if (menu.equals("Immortal")) {for (JButton b : mainButtonList) panel.add(b);}
		else if (menu.equals("Load") || menu.equals("Select File")) {
			for (JButton b : loadButtonList) panel.add(b);
			if (menu.equals("Load")) {for (JButton b : loadButtonList) if (b.getText().equals("")) b.setEnabled(false);}
			else if (menu.equals("Select File")) for (JButton b : loadButtonList) b.setEnabled(true);
		}
		else if (menu.equals("Options")) for (JButton b : optionButtonList) panel.add(b);
		else if (menu.equals("About")) {
			for (JButton b : aboutButtonList) panel.add(b);
			panel.add(about);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menuTimerTitle)) {
			short newX = (short) ((Math.random() * 25) + 810 * resX / DEV_RESX);
			short newY = (short) ((Math.random() * 25) + 88 * resY / DEV_RESY);
			title.setLocation(newX, newY);
			
			byte font = (byte) (Math.random() * 10 + 1);
			if (font == 9) title.setFont(new Font("times new roman", Font.PLAIN, (int) (Math.random() * 8 + 44 * resY / DEV_RESY)));
			else if (font == 10) title.setFont(new Font("Courier", Font.PLAIN, (int) (Math.random() * 8 + 44 * resY / DEV_RESY)));
			else title.setFont(new Font("trajanpro-regular", Font.PLAIN, (int) (Math.random() * 8 + 44 * resY / DEV_RESY)));

			byte color = (byte) (Math.random() * 10 + 1);
			if (color == 1) title.setForeground(Color.BLACK);
			else if (color == 2) title.setForeground(Color.RED);
			else title.setForeground(Color.WHITE);
			return;
		}
		if (e.getSource().equals(menuTimerButton)) {
			randomizeButtons(mainButtonList);
			randomizeButtons(loadButtonList);
			randomizeButtons(optionButtonList);
			randomizeButtons(aboutButtonList);
			return;
		}
		else if (e.getSource().equals(menuTimerBackground)) {
			short rnd = (short) (Math.random() * 65 + 15);
			Color rndGrey = new Color(rnd, rnd, rnd);
			panel.repaint(rndGrey);
			randomizeButtons2(mainButtonList);
			randomizeButtons2(loadButtonList);
			randomizeButtons2(optionButtonList);
			randomizeButtons2(aboutButtonList);
			return;
		}
		try {Game.playSound("sound/ui/button_press.wav", 0);} catch (Exception E){}
		try {if (((JButton) (e.getSource())).getText().equals("Return")) toggleMenuVisibility("Immortal");} catch (ClassCastException E) {}
		if (e.getSource().equals(mainButtonList.get(0))) toggleMenuVisibility("Select File");
		else if (e.getSource().equals(loadButtonList.get(0)) && loadButtonList.get(0).getText().equals("")) newGame((byte) 0);
		else if (e.getSource().equals(loadButtonList.get(1)) && loadButtonList.get(1).getText().equals("")) newGame((byte) 1);
		else if (e.getSource().equals(loadButtonList.get(2)) && loadButtonList.get(2).getText().equals("")) newGame((byte) 2);
		else if (e.getSource().equals(loadButtonList.get(3)) && loadButtonList.get(3).getText().equals("")) newGame((byte) 3);
		else if (e.getSource().equals(mainButtonList.get(1))) toggleMenuVisibility("Load");
		else if (e.getSource().equals(mainButtonList.get(2))) toggleMenuVisibility("Options");
		else if (e.getSource().equals(mainButtonList.get(3))) toggleMenuVisibility("About");
		else if (e.getSource().equals(mainButtonList.get(4))) System.exit(0);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE) {
			if (title.getText().equals("Immortal")) System.exit(0);
			else if (title.getText().equals("Load")) loadButtonList.get(4).doClick();
			else if (title.getText().equals("Options")) optionButtonList.get(0).doClick();
			else if (title.getText().equals("About")) aboutButtonList.get(0).doClick();
		}
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}