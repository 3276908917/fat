package entities;
import core.MasterInfo;

@SuppressWarnings("serial")
public class Boundary extends MasterInfo {
	private int x1, y1, x2, y2;
	public Boundary(int X1, int Y1, int X2, int Y2){x1 = X1; y1 = Y1; x2 = X2; y2 = Y2;}
	public int getX1(){return x1;} public int getY1(){return y1;} public int getX2(){return x2;} public int getY2(){return y2;}
	public String toString() {return "x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2;}
}