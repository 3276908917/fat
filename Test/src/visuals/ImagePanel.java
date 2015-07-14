package visuals;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import core.MasterInfo;

@SuppressWarnings("serial")
public class ImagePanel extends MasterInfo implements MouseWheelListener, MouseListener, MouseMotionListener {
    private BufferedImage image;
    private double szX = 1, szY = 1;
    private short dragX = 0, dragY = 0;
    private double offX = 0, offY = 0;
    private final short ZOOM_CAP = 1600;

    public ImagePanel(BufferedImage bi) {
    	image = bi;
    	addMouseListener(this);
    	addMouseWheelListener(this);
    	addMouseMotionListener(this);
    }

    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2D = (Graphics2D) g;
		AffineTransform at = AffineTransform.getRotateInstance(0, 0, 0);
		at.translate(offX, offY);
		at.scale(szX, szY);
		g2D.drawImage(image, at, this);
    }
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {dragX = (short) e.getX(); dragY = (short) e.getY();}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// Negative wheel rotation represents zooming in. Positive, zooming out.
		if ((e.getPreciseWheelRotation() < 0 && image.getWidth() * szX < ZOOM_CAP && image.getHeight() * szY < ZOOM_CAP) ||
				(e.getPreciseWheelRotation() > 0 && image.getWidth() * szX > getWidth() && image.getHeight() * szY > getHeight())) {
			double tempX = szX; double tempY = szY;
			if (e.getPreciseWheelRotation() > 0 && image.getWidth() * szX - 40 < getWidth()) szX = (double) getWidth() / image.getWidth();
			else szX -= e.getPreciseWheelRotation() * 0.05;
			if (e.getPreciseWheelRotation() > 0 && image.getHeight() * szY - 40 < getHeight()) szY = (double) getHeight() / image.getHeight();
				else szY -= e.getPreciseWheelRotation() * 0.05;
			offX += (tempX - szX) * image.getWidth() / 2;
			offY += (tempY - szY) * image.getHeight() / 2;
			if (mapW > offX + image.getWidth() * szX) offX = mapW - image.getWidth() * szX;
			else if (offX > 0) offX = 0;
			if (mapH > offY + image.getHeight() * szY) offY = mapH - image.getHeight() * szY;
			else if (offY > 0) offY = 0;
			System.out.println("Width by size: " + image.getWidth() * szX + " Height by size: " + image.getHeight() * szY);
			System.out.println("getWidth: " + getWidth() + " getHeight: " + getHeight());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if ((dragX - e.getX() > 0 && mapW < offX + image.getWidth() * szX) || (dragX - e.getX() < 0 && offX < 0)) offX -= dragX - e.getX();
		if ((dragY - e.getY() > 0 && mapH < offY + image.getHeight() * szY) || (dragY - e.getY() < 0 && offY < 0)) offY -= dragY - e.getY();
		dragX = (short) e.getX(); dragY = (short) e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}