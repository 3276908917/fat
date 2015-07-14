package entities;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import core.MasterInfo;

@SuppressWarnings("serial")
public class Player extends MasterInfo implements ActionListener{
	protected short silL = 1, silLm = 1, sirL = 1, sirLm = 1, sila = 1, silam = 1, sira = 1, siram = 1,  sih = 1, sihm = 1, sit = 1, sitm = 1, mass = 0, massm = 100;
	protected Timer stepTimer = new Timer(TIMER_SPEED, this);
	protected byte volume = 0, volumem = 50, stepCounter = 0, playerDir = 0, accuracy, agility, dexterity, endurance, fitness, survival, strength;
	protected float moveSpeed = (float) 0.75;
	protected boolean lDisabled, rDisabled, dDisabled, uDisabled, pNorth, pSouth, pWest, pEast;
	protected Image rotater;
	protected double diagSpeed = Math.sqrt(Math.pow(moveSpeed, 2) / 2), x, y;
	protected boolean sprint = false;
	
	public Image getRotater() {return rotater;}
	public short get_silL() {return silL;}
	public short get_silLm() {return silLm;}
	public short get_sirL() {return sirL;}
	public short get_sirLm() {return sirLm;}
	public short get_sila() {return sila;}
	public short get_silam() {return silam;}
	public short get_sira() {return sira;}
	public short get_siram() {return siram;}
	public short get_sih() {return sih;}
	public short get_sihm() {return sihm;}
	public short get_sit() {return sit;}
	public short get_sitm() {return sitm;}
	public byte getAccuracy() {return accuracy;}
	public byte getAgility() {return agility;}
	public byte getDexterity() {return dexterity;}
	public byte getEndurance() {return endurance;}
	public byte getFitness() {return fitness;}
	public byte getStrength() {return strength;}
	public byte getSurvival() {return survival;}
	public byte get_volume() {return volume;}
	public byte get_volumem() {return volumem;}
	public boolean getL() {return lDisabled;}
	public boolean getR() {return rDisabled;}
	public boolean getU() {return uDisabled;}
	public boolean getD() {return dDisabled;}
	public short get_mass() {return mass;}
	public short get_massm() {return massm;}
	public double getXPos() {return x;}
	public double getYPos() {return y;}
	public void setNorth(boolean is) {pNorth = is;}
	public void setSouth(boolean is) {pSouth = is;}
	public void setWest(boolean is) {pWest = is;}
	public void setEast(boolean is) {pEast = is;}
	public void setSprint(boolean is) {sprint = is;}
	
	public void startTimer() {stepTimer.start();}
	
	void findDirection() {
		if (lDisabled) pWest = false;
		if (uDisabled) pNorth = false;
		if (dDisabled) pSouth = false;
		if (rDisabled) pEast = false;
		if (pNorth && !pWest && !pEast && !pSouth) playerDir = 1; // North
		else if (pNorth && pWest && !pEast && !pSouth) playerDir = 2; // Northwest
		else if (!pNorth && pWest && !pEast && !pSouth) playerDir = 3; // West
		else if (!pNorth && pWest && !pEast && pSouth) playerDir = 4; // Southwest
		else if (!pNorth && !pWest && !pEast && pSouth) playerDir = 5; // South
		else if (!pNorth && !pWest	&& pEast && pSouth)	playerDir = 6; // Southeast
		else if (!pNorth && !pWest	&& pEast && !pSouth) playerDir = 7; // East
		else if (pNorth && !pWest && pEast && !pSouth)playerDir = 8; // Northeast
		else playerDir = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			Rectangle playerRectL = new Rectangle((int) (rotX - rotater.getWidth(null) * sizeX / 2) - 1, (int) (rotY - rotater.getHeight(null) * sizeY), 1, (int) (rotater.getHeight(null) * sizeY));
			Rectangle playerRectR = new Rectangle((int) (rotX + rotater.getWidth(null) * sizeX / 2) + 1, (int) (rotY - rotater.getHeight(null) * sizeY), 1, (int) (rotater.getHeight(null) * sizeY));
			Rectangle playerRectA = new Rectangle((int) (rotX - rotater.getWidth(null) * sizeX / 2), (int) (rotY - rotater.getHeight(null) * sizeY) - 1, (int) (rotater.getWidth(null) * sizeX), 1);
			Rectangle playerRectB = new Rectangle((int) (rotX - rotater.getWidth(null) * sizeX / 2), (int) (rotY) + 1, (int) (rotater.getWidth(null) * sizeX), 1);
			for (Boundary b: bounds) {
				if (playerRectL.intersectsLine(b.getX1(), b.getY1(), b.getX2(), b.getY2()) && (playerDir > 1 && playerDir < 5)) {
					lDisabled = true; playerDir = 0; stepCounter = 0;}
				if (playerRectR.intersectsLine(b.getX1(), b.getY1(), b.getX2(), b.getY2()) && playerDir > 5) {
					rDisabled = true; playerDir = 0; stepCounter = 0;}
				if (playerRectA.intersectsLine(b.getX1(), b.getY1(), b.getX2(), b.getY2()) && (playerDir < 3 || playerDir > 7)) {
					uDisabled = true; playerDir = 0; stepCounter = 0;}
				if (playerRectB.intersectsLine(b.getX1(), b.getY1(), b.getX2(), b.getY2()) && (playerDir > 3 && playerDir < 7)) {
					dDisabled = true; playerDir = 0; stepCounter = 0;}
				if (playerDir == 0) return;
			}
			lDisabled = rDisabled = dDisabled = uDisabled = false;
		}
}
