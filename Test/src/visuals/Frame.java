package visuals;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import core.Game;
import core.MasterInfo;

@SuppressWarnings("serial")
public class Frame extends MasterInfo implements ActionListener, ListSelectionListener {
	private JLabel screen = new JLabel(), section = new JLabel(), date = new JLabel(), weapon = new JLabel(), armor = new JLabel(), augments = new JLabel(), desc = new JLabel(), stats_eff = new JLabel(),
			sI = new JLabel(),  sI_bar = new JLabel(), sI_bar_max = new JLabel(), fuel = new JLabel(), fuel_bar = new JLabel(), fuel_bar_max = new JLabel(), itemDesc = new JLabel(),
			itemStats = new JLabel(), invStats = new JLabel(), invCharSel = new JLabel(), invCatSel = new JLabel(), invItemSel = new JLabel(), tasks = new JLabel(), taskDesc = new JLabel(), 
			taskDescL = new JLabel(), files = new JLabel(), fileL = new JLabel(), file = new JLabel(), statDesc = new JLabel();
	private JComboBox<String> augs = new JComboBox<String>(), effects = new JComboBox<String>(), charSel = new JComboBox<String>(), invCat = new JComboBox<String>(), invItem = new JComboBox<String>(),
			commOrder = new JComboBox<String>(), fileType = new JComboBox<String>(), fileLoc = new JComboBox<String>(), fileOrder = new JComboBox<String>(), mapOrder = new JComboBox<String>();
	private DefaultListModel<String> statMod = new DefaultListModel<String>();
	private ListSelectionModel statListener;
	private JList<String> taskList = new JList<String>(), fileList = new JList<String>(), maps, statList = new JList<String>(statMod);
	private JButton armorB = new JButton(), weaponB = new JButton(), specs = new JButton(), statsB = new JButton(), size = new JButton(), age = new JButton(), mark = new JButton();
	private DecimalFormat form = new DecimalFormat("00");
	private ArrayList<JButton> buttonList = new ArrayList<JButton>(), anatomyButtons = new ArrayList<JButton>(), invButtons = new ArrayList<JButton>();
	private JPanel anatomy = new JPanel();
	private ImagePanel mapBound;
	private Font font = new Font("palatino linotype", Font.PLAIN, 20);
	
	public Frame() {
		setLayout(null);
		setFocusable(false);
		setSize(960 * resX / DEV_RESX, 540 * resY / DEV_RESY);
		setLocation(480 * resX / DEV_RESX, 270 * resY / DEV_RESY);
		setBackground(Color.darkGray);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.ORANGE), BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), 
				BorderFactory.createLineBorder(Color.ORANGE))));
		addNavButtons();
		simpleLabel(screen, 1000, 40, 15, 13, Color.ORANGE, font.deriveFont((float) 36), "");
		setVisible(false);
		setUpStatus();
		status();
		setUpInv();
		setUpMaps();
		setUpComm();
		setUpInfo();
	}
	
	private void addNavButtons() {
		for (byte i = 0; i < 5; i++) buttonList.add(new JButton());
		byte c = 5;
		for (JButton b : buttonList) {
			b = simpleButton(b, 50, 50, c * 100, 10, null, this);
			c++;
		}
		buttonList.get(1).setIcon(new ImageIcon("sprite/ui/inv.png"));
		buttonList.get(2).setIcon(new ImageIcon("sprite/ui/map.png"));
		buttonList.get(3).setIcon(new ImageIcon("sprite/ui/work.png"));
		buttonList.get(4).setIcon(new ImageIcon("sprite/ui/files.png"));
	}
	
	private void setUpStatus() {
		dropdown(charSel, 200, 30, 25, 65, font.deriveFont((float) 16), this);
		simpleLabel(section, 1000, 30, 245, 105, Color.ORANGE, font, "Summary");
		simpleLabel(sI, 1000, 30, 245, 140, Color.ORANGE, font, "Structural Integrity");
		label(sI_bar, player.get_sih() + player.get_sit() + player.get_silL() + player.get_sirL() + player.get_sila() + player.get_sira(),
				30, 245, 170, true, Color.GREEN, Color.BLACK, null, "courier", Font.PLAIN, 12, "");
		label(sI_bar_max, player.get_sihm() + player.get_sitm() + player.get_silLm() + player.get_sirLm() + player.get_silam() + player.get_siram(), 30, 245, 170, true, Color.BLACK, null, null, null, 0, 0, "");
		simpleLabel(fuel, 1000, 30, 245, 210, Color.ORANGE, font, "Fuel");
		label(fuel_bar, machine.get_fuel(), 30, 245, 240, true, new Color(166,143,50), Color.BLACK, null, "courier", Font.PLAIN, 12, "");
		label(fuel_bar_max, machine.get_fuelm(), 30, 245, 240, true, Color.BLACK, null, null, null, 0, 0, "");
		simpleLabel(armor, 1000, 30, 245, 300, Color.ORANGE, font, "Apparel");
		simpleButton(armorB, 70, 70, 245, 330, Color.BLACK, this);
		simpleLabel(weapon, 1000, 30, 245, 420, Color.ORANGE, font, "Weapon");
		simpleButton(weaponB, 70, 70, 245, 450, Color.BLACK, this);
		button(specs, 200, 40, 610, 400, Color.DARK_GRAY, Color.ORANGE, "palatino linotype", Font.PLAIN, 16, "Statistics", true, this);
		label(desc, 440, 150, 500, 185, true, Color.GRAY, Color.BLACK, BorderFactory.createLoweredBevelBorder(), "times new roman", Font.PLAIN, 16, 
				"<html>If you have any status effects in the list above, you can click on them to learn more about them.</html>");
		simpleLabel(augments, 1000, 30, 765, 105, Color.ORANGE, font, "Augmentations");
		dropdown(augs, 200, 30, 740, 145, font.deriveFont((float) 16), this);
		simpleLabel(stats_eff, 1000, 30, 535, 105, Color.ORANGE, font, "Status Effects");
		dropdown(effects, 200, 30, 500, 145, font.deriveFont((float) 16), this);
		addAnatomyButtons();
		statMod.addElement("Accuracy");
		statMod.addElement("Agility");
		statMod.addElement("Dexterity");
		statMod.addElement("Endurance");
		statMod.addElement("Fitness");
		statMod.addElement("Survival");
		statMod.addElement("Strength");
		statList.setSize(275, 360);
		statList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		statList.setFont(new Font("times new roman", Font.PLAIN, 16));
		statList.setLocation(340, 140);
		statList.setBackground(Color.GRAY);
		statList.setBorder(BorderFactory.createLoweredBevelBorder());
		statList.setVisible(false);
		statListener = statList.getSelectionModel();
        statListener.addListSelectionListener(this);
		button(statsB, 200, 40, 50, 200, Color.DARK_GRAY, Color.ORANGE, "palatino linotype", Font.PLAIN, 16, "Attributes", true, this);
		statsB.setVisible(false);
		label(statDesc, 275, 360, 645, 140, true, Color.GRAY, Color.BLACK, BorderFactory.createLoweredBevelBorder(), "times new roman", Font.PLAIN, 16, "<html>Click on a statistic to learn more about it.</html>");
		statDesc.setVisible(false);
	}	
	
	private void addAnatomyButtons() {
		anatomyButtons.add(simpleButton(new JButton(), 65, 65, 70, 20, Color.BLACK, this));
		anatomyButtons.add(simpleButton(new JButton(), 105, 150, 50, 65, Color.BLACK, this));
		anatomyButtons.add(simpleButton(new JButton(), 30, 200, 20, 65, Color.BLACK, this));
		anatomyButtons.add(simpleButton(new JButton(), 30, 200, 155, 65, Color.BLACK, this));
		anatomyButtons.add(simpleButton(new JButton(), 50, 180, 50, 215, Color.BLACK, this));
		anatomyButtons.add(simpleButton(new JButton(), 50, 180, 105, 215, Color.BLACK, this));
		for (JButton b: anatomyButtons) anatomy.add(b);
		anatomy.setLayout(null);
		anatomy.setFocusable(false);
		anatomy.setSize(205 * resX / DEV_RESX, 415 * resY / DEV_RESY);
		anatomy.setLocation(25 * resX / DEV_RESX, 105 * resY / DEV_RESY);
		anatomy.setBackground(Color.WHITE);
		anatomy.setVisible(true);
		anatomy.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), 
				BorderFactory.createLineBorder(Color.BLACK))));
	}
	
	private void setUpInv() {
		simpleLabel(invCharSel, 1000, 30, 50, 90, Color.ORANGE, font, "Character");
		simpleLabel(invCatSel, 1000, 30, 50, 165, Color.ORANGE, font, "Category");
		dropdown(invCat, 200, 30, 50, 200, font.deriveFont((float) 16), this);
		invCat.addItem("Sustainment"); invCat.addItem("Apparel"); invCat.addItem("Weapons"); invCat.addItem("Ammunition"); invCat.addItem("Miscellaneous");
		simpleLabel(invItemSel, 1000, 30, 50, 240, Color.ORANGE, font, "Item");
		dropdown(invItem, 200, 30, 50, 275, font.deriveFont((float) 16), this);
		label(itemDesc, 400, 150, 50, 355, true, Color.GRAY, Color.BLACK, BorderFactory.createLoweredBevelBorder(), "times new roman", Font.PLAIN, 16, 
				"<html>" + "Select an item to learn more about it." + "</html>");
		label(itemStats, 400, 150, 500, 355, true, Color.GRAY, Color.BLACK, BorderFactory.createLoweredBevelBorder(), "times new roman", Font.PLAIN, 16, "");
		label(invStats, 150, 175, 750, 125, true, Color.GRAY, Color.BLACK, BorderFactory.createLoweredBevelBorder(), "times new roman", Font.PLAIN, 16, "");
		addInvButtons();
	}
	
	private void addInvButtons() {
		for (byte x = 0, y = 0; x < 2; y++) {
			invButtons.add(button(new JButton(), 130, 40, 330 + 160 * x, 125 + 80 * y, Color.ORANGE, Color.BLACK, "palatino linotype", Font.PLAIN, 18, "", true, this));
			if (y == 1) {y = -1; x++;}
		}
		invButtons.get(0).setText("Implement");
		invButtons.get(1).setText("Combine");
		invButtons.get(2).setText("Transfer");
		invButtons.get(3).setText("Abandon");
	}
	
	private void setUpMaps() {
		dropdown(mapOrder, 200, 30, 50, 100, font.deriveFont((float) 16), this);
		mapOrder.addItem("Newest to Oldest"); mapOrder.addItem("Oldest to Newest"); mapOrder.addItem("Alphabetical Order");
		String[] mapData = {"New Bristol"};
		maps = new JList<String>(mapData);
		maps.setSelectedIndex(0);
		maps.setFont(new Font("times new roman", Font.PLAIN, (int) (16 * pureRatio)));
		maps.setSize(275 * resX / DEV_RESX, 360 * resY / DEV_RESY);
		maps.setLocation(50 * resX / DEV_RESX, 140 * resY / DEV_RESY);
		maps.setBackground(Color.GRAY);
		maps.setBorder(BorderFactory.createLoweredBevelBorder());
		map = imageToBuffered(map, "a0");
		mapBound = new ImagePanel(map);
		mapBound.setLayout(null);
		mapBound.setFocusable(false);
		mapBound.setSize(mapW, mapH);
		mapBound.setLocation(350 * resX / DEV_RESX, 100 * resY / DEV_RESY);
		mapBound.setBackground(Color.ORANGE);
		mapBound.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), 
				BorderFactory.createLineBorder(Color.BLACK))));
		
	}
	
	private BufferedImage imageToBuffered(BufferedImage bi, String map) {
		Graphics g = bi.getGraphics();
		g.drawImage(new ImageIcon("sprite/map/" + map + ".png").getImage(), 0, 0, null);
		g.dispose();
		return bi;
	}
	
	private void setUpComm() {
		simpleLabel(date, 1000, 30, 15, 100, Color.ORANGE, font, "");
		dropdown(commOrder, 200, 30, 75, 175, font.deriveFont((float) 16), this);
		commOrder.addItem("Newest to Oldest"); commOrder.addItem("Oldest to Newest"); commOrder.addItem("Alphabetical Order"); 
		button(age, 225, 40, 62, 250, Color.DARK_GRAY, Color.ORANGE, "palatino linotype", Font.PLAIN, 16, "Incomplete Commissions", true, this);
		button(size, 225, 40, 62, 325, Color.DARK_GRAY, Color.ORANGE, "palatino linotype", Font.PLAIN, 16, "Large Commissions", true, this);
		simpleLabel(tasks, 1000, 30, 340, 100, Color.ORANGE, font, "Commissions");
		taskList.setSize(275 * resX / DEV_RESX, 360 * resY / DEV_RESY);
		taskList.setLocation(340  * resX / DEV_RESX, 140 * resY / DEV_RESY);
		taskList.setBackground(Color.GRAY);
		taskList.setBorder(BorderFactory.createLoweredBevelBorder());
		simpleLabel(taskDescL, 1000, 30, 645, 100, Color.ORANGE, font, "Description");
		label(taskDesc, 275, 360, 645, 140, true, Color.GRAY, Color.BLACK, BorderFactory.createLoweredBevelBorder(),
				"times new roman", Font.PLAIN, 16, "<html><div align=\"center\">Select a commission to view constituent objectives.</div></html>");
	}
	
	private void setUpInfo() {
		dropdown(fileType, 200, 30, 75, 140, font.deriveFont((float) 16), this);
		fileType.addItem("Emails"); fileType.addItem("Documents"); fileType.addItem("Images"); fileType.addItem("Conversations"); 
		dropdown(fileLoc, 200, 30, 75, 215, font.deriveFont((float) 16), this);
		fileLoc.addItem("New Bristol");
		dropdown(fileOrder, 200, 30, 75, 290, font.deriveFont((float) 16), this);
		fileOrder.addItem("Newest to Oldest"); fileOrder.addItem("Oldest to Newest"); fileOrder.addItem("Alphabetical Order"); 
		button(mark, 200, 40, 75, 365, Color.DARK_GRAY, Color.ORANGE, "palatino linotype", Font.PLAIN, 16, "Mark as Read", true, this);
		simpleLabel(files, 1000, 30, 340, 100, Color.ORANGE, font, "Files");
		fileList.setSize(275 * resX / DEV_RESX, 360 * resY / DEV_RESY);
		fileList.setLocation(340  * resX / DEV_RESX, 140 * resY / DEV_RESY);
		fileList.setBackground(Color.GRAY);
		fileList.setBorder(BorderFactory.createLoweredBevelBorder());
		simpleLabel(fileL, 1000, 30, 645, 100, Color.ORANGE, font, "File Viewer");
		label(file, 275, 360, 645, 140, true, Color.GRAY, Color.BLACK, BorderFactory.createLoweredBevelBorder(), "times new roman", Font.PLAIN, 16, "<html>Select a file to view it.</html>");
	}
	
	public void toggle() {
		if (isVisible() == false)
			try {Game.playSound("sound/ui/frame_open.wav", 0);} catch (Exception e){}
		else try {Game.playSound("sound/ui/frame_close.wav", 0);} catch (Exception e){}
		setVisible(!isVisible());
		refresh();
	}
	
	private void refresh() {
		if (buttonList.get(0).isEnabled() == false) status();
		else if (buttonList.get(1).isEnabled() == false) inventory();
		else if (buttonList.get(2).isEnabled() == false) cartographs();
		else if (buttonList.get(3).isEnabled() == false) commissions();
		else if (buttonList.get(4).isEnabled() == false) information();
	}
	
	private void Hide(String title, byte buttonToDisable) {
		removeAll();
		add(screen);
		screen.setText(title);
		for (JButton b : buttonList) {add(b); b.setEnabled(true);}
		buttonList.get(buttonToDisable).setEnabled(false);
		if (player.equals(machine)) buttonList.get(0).setIcon(new ImageIcon("sprite/ui/stats_machine.png")); else buttonList.get(0).setIcon(new ImageIcon("sprite/ui/stats_human.png"));
		try {Game.playSound("sound/ui/frame_switch.wav", 0);} catch (Exception e) {}
	}
	
	private void status() {
		Hide("Status", (byte) 0);
		add(charSel);
		charSel.setLocation(25 * resX / DEV_RESX, 65 * resY / DEV_RESY);
		add(anatomy); add(section); add(sI);
		if (player.equals(machine)) {
			sI.setText("Structural Integrity"); add(fuel); add(fuel_bar); add(augments); add(augs);
			boolean barVisible = true; for (JButton b: anatomyButtons) if (b.getBackground() == Color.BLUE) barVisible = false; if (barVisible) add(fuel_bar_max);}
		else sI.setText("Physical Condition");
		add(sI_bar); add(sI_bar_max); add(stats_eff); add(effects); add(desc); add(specs); add(statList); add(statsB); add(statDesc);
		for (JButton b: anatomyButtons) {
			if (b.getBackground().equals(Color.BLUE)) {
				add(armor); add(armorB);
				if ((b.equals(anatomyButtons.get(2))) || (b.equals(anatomyButtons.get(3)))) {add(weapon); add(weaponB);}
			}
		}
		charSel.removeAllItems();
		if (player.equals(machine)) charSel.addItem("Machine"); else charSel.addItem("Self");
	}
	
	private void statistics() {
		statList.setVisible(!statList.isVisible());
		statsB.setVisible(!statsB.isVisible());
		statDesc.setVisible(!statDesc.isVisible());
		buttonList.get(0).setVisible(!buttonList.get(0).isVisible());
		buttonList.get(1).setVisible(!buttonList.get(1).isVisible());
		buttonList.get(2).setVisible(!buttonList.get(2).isVisible());
		buttonList.get(3).setVisible(!buttonList.get(3).isVisible());
		buttonList.get(4).setVisible(!buttonList.get(4).isVisible());
		charSel.setVisible(!charSel.isVisible());
		anatomy.setVisible(!anatomy.isVisible());
		section.setVisible(!section.isVisible());
		sI.setVisible(!sI.isVisible());
		sI_bar.setVisible(!sI_bar.isVisible());
		sI_bar_max.setVisible(!sI_bar_max.isVisible());
		fuel.setVisible(!fuel.isVisible());
		fuel_bar.setVisible(!fuel_bar.isVisible());
		augments.setVisible(!augments.isVisible());
		augs.setVisible(!augs.isVisible());
		stats_eff.setVisible(!stats_eff.isVisible());
		effects.setVisible(!effects.isVisible());
		desc.setVisible(!desc.isVisible());
		armor.setVisible(!armor.isVisible());
		armorB.setVisible(!armorB.isVisible());
		weapon.setVisible(!weapon.isVisible());
		weaponB.setVisible(!weaponB.isVisible());
		if (sI.isVisible()) screen.setText("Status"); else screen.setText("Statistics");
	}
	
	private void inventory() {
		Hide("Inventory", (byte) 1);
		add(invCharSel); add(charSel);
		charSel.setLocation(50 * resX / DEV_RESX, 125 * resY / DEV_RESY);
		add(invCatSel); add(invCat); add(invItemSel); add(invItem); add(itemDesc); add(itemStats); add(invStats);
		for (JButton b: invButtons) add(b);
		charSel.removeAllItems();
		if (player.equals(machine)) charSel.addItem("Machine"); else charSel.addItem("Self");
	}
	
	private void cartographs() {
		Hide("Cartographs", (byte) 2);
		add(mapOrder); add(maps); add(mapBound);
	}
	
	private void commissions() {
		Hide("Commissions", (byte) 3);
		add(date);
		updateTime();
		add(commOrder); add(age); add(size); add(tasks); add(taskList); add(taskDescL); add(taskDesc);
	}
	
	private void information() {
		Hide("Information", (byte) 4);
		add(fileType); add(fileLoc); add(fileOrder); add(mark);	add(files);	add(fileList); add(file); add(fileL);
	}
	
	public void updateTime() {
		if (!buttonList.get(3).isEnabled()) {
			String Month = "";
			try {
				Scanner in = new Scanner(new FileReader("text/months.txt"));
				for (byte c = 1; c < 13; c++) {
					if (c == month) Month = in.nextLine();
					else in.nextLine();
				}
				in.close();
			} catch (FileNotFoundException e) {}
			date.setText(dayOfWeek + ", " + Month + " " + dayOfMonth + ", " + year + " @ " + form.format(hour) + ":" + form.format(minute) + ":" + form.format(second));
		}
	}
	
	public String getDate() {
		String Month = "";
		try {
			Scanner in = new Scanner(new FileReader("text/months.txt"));
			for (byte c = 1; c < 13; c++) {
				if (c == month) Month = in.nextLine();
				else in.nextLine();
			}
			in.close();
		} catch (FileNotFoundException e) {}
		return dayOfWeek + ", " + Month + " " + dayOfMonth + ", " + year + " @ " + form.format(hour) + ":" + form.format(minute) + ":" + form.format(second);
	}
	
	private void bodyPart(String part) {
		try {Game.playSound("sound/ui/status_anatomy.wav", 0);} catch (Exception e) {}
		short sI = 0, sIm = 0, fl = 0;
		if (section.getText().equals(part)) {
			if (player.equals(machine)) add(fuel_bar_max);
			fuel_bar.setOpaque(true);
			fuel_bar.setForeground(Color.BLACK);
			fuel_bar.setSize(machine.get_fuel(), fuel_bar.getHeight());
  			fuel_bar.setText(machine.get_fuel() + "/" + machine.get_fuelm());
			for (JButton b: anatomyButtons) b.setBackground(Color.BLACK);
			sI = (short) (player.get_sih() + player.get_sit() + player.get_silL() + player.get_sirL() + player.get_sila() + player.get_sira()); 
			sIm = (short) (player.get_sihm() + player.get_sitm() + player.get_silLm() + player.get_sirLm() + player.get_silam() + player.get_siram());
			remove(armorB); remove(armor); remove(weaponB); remove(weapon);
			section.setText("Summary");
		} else {
			switch (part) {
			case "Head": sI = player.get_sih(); sIm = player.get_sihm(); fl = machine.get_fh(); section.setText(part); remove(weaponB); remove(weapon); break;
			case "Torso": sI = player.get_sit(); sIm = player.get_sitm(); fl = machine.get_ft(); section.setText(part); remove(weaponB); remove(weapon); break;
			case "Left Arm": sI = player.get_sila(); sIm = player.get_silam(); fl = machine.get_fla(); section.setText(part); add(weapon); add(weaponB); break;
			case "Right Arm": sI = player.get_sira(); sIm = player.get_siram(); fl = machine.get_fra(); section.setText(part); add(weapon); add(weaponB); break;
			case "Left Leg": sI = player.get_silL(); sIm = player.get_silLm(); fl = machine.get_flL(); section.setText(part); remove(weaponB); remove(weapon); break;
			case "Right Leg": sI = player.get_sirL(); sIm = player.get_sirLm(); fl = machine.get_frL(); section.setText(part); remove(weaponB); remove(weapon); break;
			}
			add(armor); add(armorB);
			remove(fuel_bar_max);
			fuel_bar.setOpaque(false);
			fuel_bar.setSize(1000, fuel_bar.getHeight());
			switch (fl) {
			case 2 : fuel_bar.setText("Stable Connection"); fuel_bar.setForeground(Color.GREEN); break;
			case 1 : fuel_bar.setText("Faulty Connection"); fuel_bar.setForeground(Color.ORANGE); break;
			default : fuel_bar.setText("Connection Failure"); fuel_bar.setForeground(Color.RED); break;
			}
		}
		sI_bar_max.setSize(sIm, sI_bar_max.getHeight());
		sI_bar.setSize(sI, sI_bar.getHeight());
		sI_bar.setText(sI + "/" + sIm);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(buttonList.get(0))) status();
		else if (e.getSource().equals(specs)) {statistics(); if (sI.isVisible()) {specs.setText("Statistics"); specs.setLocation(610 * resX / DEV_RESX, 400 * resY / DEV_RESY);}
			else {specs.setText("Return"); specs.setLocation(50 * resX / DEV_RESX, 300 * resY / DEV_RESY);}}
		else if (e.getSource().equals(statsB)) {
			if (statsB.getText().equals("Attributes")) {statsB.setText("Requisites"); statMod.removeAllElements();
			statMod.addElement("Hydration");
			statMod.addElement("Calories");
			statMod.addElement("Carbohydrates");
			statMod.addElement("Vitamin A");
			statMod.addElement("Vitamin C");
			statMod.addElement("Temperature");}
			else {statsB.setText("Attributes"); statMod.removeAllElements();
			statMod.addElement("Accuracy");
			statMod.addElement("Agility");
			statMod.addElement("Dexterity");
			statMod.addElement("Endurance");
			statMod.addElement("Fitness");
			statMod.addElement("Survival");
			statMod.addElement("Strength");}}
		else if (e.getSource().equals(buttonList.get(1))) inventory();
		else if (e.getSource().equals(buttonList.get(2))) cartographs();
		else if (e.getSource().equals(buttonList.get(3))) commissions();
		else if (e.getSource().equals(buttonList.get(4))) information();
		else if (e.getSource().equals(anatomyButtons.get(0))) {for (JButton b: anatomyButtons) b.setBackground(Color.BLACK); anatomyButtons.get(0).setBackground(Color.BLUE); bodyPart("Head");}
		else if (e.getSource().equals(anatomyButtons.get(1))) {for (JButton b: anatomyButtons) b.setBackground(Color.BLACK); anatomyButtons.get(1).setBackground(Color.BLUE); bodyPart("Torso");}
		else if (e.getSource().equals(anatomyButtons.get(2))) {for (JButton b: anatomyButtons) b.setBackground(Color.BLACK); anatomyButtons.get(2).setBackground(Color.BLUE); bodyPart("Left Arm");}
		else if (e.getSource().equals(anatomyButtons.get(3))) {for (JButton b: anatomyButtons) b.setBackground(Color.BLACK); anatomyButtons.get(3).setBackground(Color.BLUE); bodyPart("Right Arm");}
		else if (e.getSource().equals(anatomyButtons.get(4))) {for (JButton b: anatomyButtons) b.setBackground(Color.BLACK); anatomyButtons.get(4).setBackground(Color.BLUE); bodyPart("Left Leg");}
		else if (e.getSource().equals(anatomyButtons.get(5))) {for (JButton b: anatomyButtons) b.setBackground(Color.BLACK); anatomyButtons.get(5).setBackground(Color.BLUE); bodyPart("Right Leg");}
		else if (e.getSource().equals(charSel)) {invStats.setText("Money: $" + money); if ((charSel).getSelectedIndex() == 0) invStats.setText("<html>Money: $" + money + 
				"<br><br>Total Mass of Items: <br>" + player.get_mass() + "/" + player.get_massm() + " kg<br><br>Total Volume of Items: <br>" + player.get_volume() + "/" + 
				player.get_volumem() + " cm^3</html>");}
		else if (e.getSource().equals(age)) {if (age.getText().equals("Incomplete Commissions")) age.setText("Complete Commissions"); else age.setText("Incomplete Commissions");}
		else if (e.getSource().equals(size)) {if (size.getText().equals("Large Commissions")) size.setText("Small Commissions"); else size.setText("Large Commissions");}
	}

	public void valueChanged(ListSelectionEvent e) {
		if (statList.getSelectedValue() != null) {
			if (statsB.getText().equals("Attributes")) {
				if (statList.getSelectedValue().equals("Accuracy")) statDesc.setText("<html><div align=\"center\">Level: " + player.getAccuracy() 
						+ "<br><br>Accuracy determines one's chances of hitting a harget.<div></html>");
				else if (statList.getSelectedValue().equals("Agility")) statDesc.setText("<html><div align=\"center\">Level: " + player.getAgility() 
						+ "<br><br>Agility determines one's movement speed and rate of attack.<div></html>");
				else if (statList.getSelectedValue().equals("Dexterity")) statDesc.setText("<html><div align=\"center\">Level: " + player.getDexterity()
						+ "<br><br>Dexterity contributes to melee damage and resistance to disarm attempts.<div></html>");
				else if (statList.getSelectedValue().equals("Endurance")) statDesc.setText("<html><div align=\"center\">Level: " + player.getEndurance()
						+ "<br><br>Endurance increases damage resistance.<div></html>");
				else if (statList.getSelectedValue().equals("Fitness")) statDesc.setText("<html><div align=\"center\">Level: " + player.getFitness()
						+ "<br><br>Fitness determines the magnitude of one's stamina supply.<div></html>");
				else if (statList.getSelectedValue().equals("Strength")) statDesc.setText("<html><div align=\"center\">Level: " + player.getStrength()
						+ "<br><br>Stength contributes to melee damage and the maximum weight that one may carry.<div></html>");
				else if (statList.getSelectedValue().equals("Survival")) statDesc.setText("<html><div align=\"center\">Level: " + player.getSurvival()
						+ "<br><br>Survival extends the amount of time that one can survive without vital nutrients and slumber.<div></html>");
			};
		} else statDesc.setText("");
	}
}