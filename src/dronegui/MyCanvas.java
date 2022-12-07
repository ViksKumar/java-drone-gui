package dronegui;
/**
 * Contains class for canvas
 * @author Vikram
 *
 */
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class MyCanvas {
	int xCanvasSize = 512;				// constants for relevant sizes
	int yCanvasSize = 512;
    GraphicsContext gc; 

    /**
     * Constructor sets up relevant Graphics context and size of canvas
     * @param g
     * @param cs
     */
    public MyCanvas(GraphicsContext g, int xcs, int ycs) {
    	gc = g;
    	xCanvasSize = xcs;
    	yCanvasSize = ycs;
    }
   
    /**
     * get x size of canvas
     * @return xsize
     */
    public int getXCanvasSize() {
    	return xCanvasSize;
    }
    
    /**
     * get y size of canvas    
     * @return ysize
     */
    public int getYCanvasSize() {
    	return yCanvasSize;
    }

    /**
     * clear the canvas
     */
    public void clearCanvas() {
		gc.clearRect(0,  0,  xCanvasSize,  yCanvasSize);		
    }
	
	/** 
	 * function to convert char c to actual colour used
	 * @param c
	 * @return Colour
	 */
	Color colFromChar (char c){
		Color ans = Color.BLACK;
		switch (c) {
		case 'y' :	ans = Color.YELLOW;
					break;
		case 'w' :	ans = Color.WHITE;
					break;
		case 'r' :	ans = Color.RED;
					break;
		case 'g' :	ans = Color.GREEN;
					break;
		case 'b' :	ans = Color.BLUE;
					break;
		case 'o' :	ans = Color.ORANGE;
					break;
		}
		return ans;
	}
	
	public void setFillColour (Color c) {
		gc.setFill(c);
	}
	/**
	 * show the drone at position x,y with radius r in colour defined by col
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	public void showCircle(double x, double y, double rad, char col) {
	 	setFillColour(colFromChar(col));									// set the fill colour
		gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);	// fill circle
	}

}