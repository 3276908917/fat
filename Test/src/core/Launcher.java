package core;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import visuals.Menu;

@SuppressWarnings("serial")
public class Launcher extends MasterInfo implements ActionListener {
	private JLabel title = new JLabel(), expl = new JLabel();
	private JComboBox<String> ratio = new JComboBox<String>(), resolution = new JComboBox<String>();
	private JButton launch = new JButton();
	private JPanel contentPane = new JPanel();
	
	public Launcher () {
		setUpFrame();
		setUpComponents();
		addResolutions("4-3");
	}
	
	public static void main(String args[]) {new Launcher();}
	
	private void setUpFrame() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setTitle("Immortal");
		frame.setIconImage(icon);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusTraversalKeysEnabled(false);
		frame.getContentPane().add(contentPane);
		contentPane.setLayout(null);
		contentPane.setFocusable(false);
		contentPane.setSize(frame.getWidth(), frame.getHeight());
		contentPane.setLocation(0, 0);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setVisible(true);
	}
	
	private void setUpComponents() {
		contentPane.add(title);
		simpleLabel(title, 265, 100, 187, 25, Color.RED,  new Font("trajanpro-regular", Font.PLAIN, 48), "<html><div align=center>Immortal</div></html>");
		contentPane.add(expl);
		simpleLabel(expl, 195, 93, 50, 175, Color.ORANGE,  new Font("palatino linotype", Font.PLAIN, 24),
				"<html><div align=center>Aspect Ratio:<br><br>Screen Resolution:</div></html>");
		contentPane.add(ratio);
		dropdown(ratio, 200, 30, 400, 175, new Font("times new roman", Font.PLAIN, 16), this);
		try {Scanner in = new Scanner(new FileReader("text/aspects.txt"));
		while (in.hasNext()) ratio.addItem(in.nextLine()); in.close();} catch (FileNotFoundException e) {}
		contentPane.add(resolution);
		dropdown(resolution, 200, 30, 400, 250, new Font("times new roman", Font.PLAIN, 16), this);
		contentPane.add(launch);
		button(launch, 150, 45, 250, 350, Color.ORANGE, Color.DARK_GRAY, "palatino linotype", Font.PLAIN, 24, "Launch", true, this);
	}
	
	private void addResolutions(String aR) {
		resolution.removeAllItems();
		try {
			Scanner in = new Scanner(new FileReader("text/" + aR + ".txt"));
			while (in.hasNext()) resolution.addItem(in.nextLine());
			in.close();
		}
		catch (FileNotFoundException e) {}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(launch)) {
			String res = resolution.getSelectedItem().toString();
			resX = Short.parseShort(res.substring(0, res.indexOf("x")));
			resY = Short.parseShort(res.substring(res.indexOf("x") + 1));
			pureRatio =  (float) resY / (float) DEV_RESY;
			System.out.println(pureRatio);
			mapW *= (double) resX / DEV_RESX;
			mapH *= (double) resY / DEV_RESY;
			System.out.println(mapW + " " + mapH);
			menu = new Menu();
		}
		else if (e.getSource().equals(ratio)) addResolutions((String) ratio.getSelectedItem());
	}
}
