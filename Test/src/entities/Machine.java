package entities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import core.Game;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Machine extends Player implements ActionListener {
	private final double STEP_CAP = 25;
	//private Timer stepTimer = new Timer(TIMER_SPEED, this);
	// Following abbreviations: s = structural, i = integrity, m = maximum, L = leg, l = left, r = right, h = head, t = torso, a = arm
	private short fuel = 15, fuelm = 15, flL = 2, frL = 2, fla = 2, fra = 2, fh = 2, ft = 2;
	
	public short get_fuel() {return fuel;}
	public short get_fuelm() {return fuelm;}
	public short get_flL() {return flL;}
	public short get_frL() {return frL;}
	public short get_fla() {return fla;}
	public short get_fra() {return fra;}
	public short get_fh() {return fh;}
	public short get_ft() {return ft;}
	
	public Machine () {rotater = new ImageIcon("sprite/machine/unarmed.png").getImage();
	x = 960 * resX / DEV_RESX; y = 540 * resY / DEV_RESY;}
	
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource().equals(stepTimer)) {
			if (stepCounter == 0) findDirection();
			if (playerDir != 0) {
				if (stepCounter == 0) try {Game.playSound("sound/machine/step" + (int) (Math.random() * 4 + 1) +  ".wav", 0);} catch (Exception E){}
				
				double displacement = moveSpeed * (double) stepCounter * (double) (Math.PI / STEP_CAP);
				double diagDisplacement = diagSpeed * (double) stepCounter * (double) (Math.PI / STEP_CAP);
				switch (playerDir) {
					case 1 : y -= displacement; break;
					case 2 : x -= diagDisplacement; y -= diagDisplacement; break;
					case 3 : x -= displacement; break;
					case 4 : x -= diagDisplacement; y += diagDisplacement; break;
					case 5 : y += displacement; break;
					case 6 : x += diagDisplacement; y += diagDisplacement; break;
					case 7 : x += displacement; break;
					case 8 : x += diagDisplacement; y -= diagDisplacement;
				}		
				stepCounter++;
				if (stepCounter == STEP_CAP){
					if (!pNorth && !pSouth && !pWest && !pEast){
						stepTimer.stop();
						try {Game.playSound("sound/machine/step" + (int) (Math.random() * 4 + 1) +  ".wav", 0);} catch (Exception E) {}
					}
					stepCounter = 0;
				}
			}
		}
	}
}