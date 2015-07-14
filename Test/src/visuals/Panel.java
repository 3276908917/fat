package visuals;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import core.MasterInfo;

@SuppressWarnings("serial")
public class Panel extends MasterInfo {
	private Color rndGrey;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(rndGrey);
		((Graphics2D) g).scale(.5, .5);
		if (menu.getScreen().equals("")){
			Graphics2D g2D = (Graphics2D) g;
			AffineTransform enviroRot = AffineTransform.getRotateInstance(0, enviro.getWidth(null), enviro.getHeight(null));
			enviroRot.translate(0, 0);
			enviroRot.scale(1 * (double) resX / (double) DEV_RESX, 1 * (double) resY / (double) DEV_RESY);
			g2D.drawImage(enviro, enviroRot, this);
			AffineTransform playerRot = AffineTransform.getRotateInstance(imageOrientation,
					player.getRotater().getWidth(null) * sizeX / 2 + player.getXPos(), player.getRotater().getHeight(null) * sizeY / 2 + player.getYPos());
			playerRot.translate(player.getXPos(), player.getYPos());
			playerRot.scale(sizeX, sizeY);
			g2D.drawImage(player.getRotater(), playerRot, this);
			g.setColor(Color.RED);
		} else {g.fillRect(0, 0, resX, resY);}
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void repaint(Color grey){
		rndGrey = grey;
		super.repaint();
	}
}