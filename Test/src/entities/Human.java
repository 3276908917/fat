package entities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import core.Game;

@SuppressWarnings("serial")
public class Human extends Player implements ActionListener {
	private byte stamina = 127;
	private double stepCap = 25;
	
	
	public Human () {rotater = new ImageIcon("sprite/human/unarmedh.png").getImage();
	x = 960 * resX / DEV_RESX; y = 540 * resY / DEV_RESY;
	moveSpeed = (float) 1.5; diagSpeed = Math.sqrt(Math.pow(moveSpeed, 2) / 2);
	accuracy = agility = dexterity = endurance = fitness = strength = survival = 5;}
	
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		System.out.println(stamina);
		if (sprint && stamina > 0) {stepCap = 10; moveSpeed = (float) 5; diagSpeed = Math.sqrt(Math.pow(moveSpeed, 2) / 2); stamina--;}
		else {stepCap = 25; moveSpeed = (float) 1.5; diagSpeed = Math.sqrt(Math.pow(moveSpeed, 2) / 2); if (stamina < 127) stamina++;}
		if (e.getSource().equals(stepTimer)) {
			findDirection();
			switch (playerDir) {
			case 1 : y -= moveSpeed; break;
			case 2 : x -= diagSpeed; y -= diagSpeed; break;
			case 3 : x -= moveSpeed; break;
			case 4 : x -= diagSpeed; y += diagSpeed; break;
			case 5 : y += moveSpeed; break;
			case 6 : x += diagSpeed; y += diagSpeed; break;
			case 7 : x += moveSpeed; break;
			case 8 : x += diagSpeed; y -= diagSpeed;
		}
			if (playerDir == 0) stepCounter = 0;
			if (playerDir != 0) {
				if (stepCounter == 0) try {Game.playSound("sound/human/step" + (int) (Math.random() * 4 + 1) +  ".wav", 0);} catch (Exception E){}		
				stepCounter++;
				if (stepCounter == stepCap){
					if (!pNorth && !pSouth && !pWest && !pEast){
						stepTimer.stop();
						try {Game.playSound("sound/human/step" + (int) (Math.random() * 4 + 1) +  ".wav", 0);} catch (Exception E) {}
					}
					stepCounter = 0;
				}
			}
		}
	}
}