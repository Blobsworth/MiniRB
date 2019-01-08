import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GUIComposer extends JPanel implements CarObserver {
	private BufferedImage bg;
	private ArrayList<Car> cars;
	private int transX; //These are used to make the 0,0 position the centre of the roundabout
	private int transY; //This makes determining the direction of the car easier
							  //if sign of x < 0 car left of the roundabout, if y < 0 car above roundabout, etc.
	public GUIComposer() {
		transX = 345;
		transY = 225;
		cars = new ArrayList<Car>();
		try{
			bg = ImageIO.read(new File("src/assets/BG.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(bg, 0, 0, this);
	    
	    repaint();
	    drawCars(g);
	    //Math.sin(90 * (Math.PI/180);
	}
	
	private void drawCars(Graphics g) {
		for(Car car : cars) {
			int xOff = 0;
			int yOff = 0;
			int dir;
	    	g.setColor(car.getColour());
	    	
	    	if(car.getState() != 4) {
		    	dir = (int) Math.signum((car.getXPrev() - car.getX()) + (car.getY() - car.getYPrev()));
		    	if(car.getX() == 0) {
		    		xOff = 20 * dir;
		    	}
		    	if(car.getY() == 0) {
		    		yOff = 20 * dir;
		    	}
	    	}
	    	g.fillRect(Math.round(car.getX() + transX + xOff), Math.round(car.getY() + transY + yOff), 20, 20);
	    }
	}
	
	public void update(Car car, int state) {
		if(state == 0) {
			cars.add(car);
		}
		repaint();
	}
	
	public Dimension getPreferredSize() {
	    return new Dimension(bg.getWidth(), bg.getHeight());
	}
}
