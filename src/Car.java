import java.util.*;
import java.util.function.Consumer;
import java.awt.Color;
import java.lang.*;

public class Car implements Runnable, CarObservable {
	private int carID;
	private int state;
	private int x;
	private int y;
	private int xPrev;
	private int yPrev;
	private int RBEntryPoint;
	private float degOnRB;
	private Color colour;
	private int destX;
	private int destY;
	private boolean blocking[] = new boolean[4];
	private ArrayList<CarObserver> observers = new ArrayList<CarObserver>();
	private static Model theModel;
	private boolean pastRB;
	private static int carIDList;
	private static int tickTime = 50;
	
	public Car(int x, int y, int destX, int destY, Color colour, CarObserver Go, CarObserver To) {
		this.blocking[0] = false;
		this.blocking[1] = false;
		this.blocking[2] = false;
		this.blocking[3] = false;
		this.x = x;
		this.y = y;
		this.destX = destX;
		this.destY = destY;
		this.colour = colour;
		this.registerObservers(Go, To);
		this.pastRB = false;
		this.carID = carIDList++;
		if(Math.signum(x) == -1) {
			RBEntryPoint = 3;	//Left of RB, Road A
		}else if(Math.signum(x) == 1) {
			RBEntryPoint = 1;	//Right of RB, Road C
		}else if(Math.signum(y) == -1) {
			RBEntryPoint = 2;	//Top of RB, Road B
		}else if(Math.signum(y) == 1) {
			RBEntryPoint = 0;	//Bottom of RB, Road D
		}
	}
	
	public static void setModel(Model model) {
		theModel = model;
	}
	
	public static void setCarIDList(int n) {
		carIDList = n;
	}
		
	public void registerObservers(CarObserver Go, CarObserver To) {
		observers.add(Go);
		observers.add(To);
	}
	
	public Color getColour() {
		return this.colour;
	}
	
	public int getState() {
		return state;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getDestX() {
		return this.destX;
	}
	
	public int getDestY() {
		return this.destY;
	}
	
	public int getID() {
		return this.carID;
	}
	
	public boolean[] getBlocking() {
		return blocking;
	}
	
	public int getXPrev() {
		return xPrev;
	}
	
	private boolean getPastRB() {
		return pastRB;
	}
	
	public int getYPrev() {
		return yPrev;
	}
	
	private boolean onRoad() {
		if(Math.abs(x) > theModel.getRBRadius() || Math.abs(y) > theModel.getRBRadius()){
			return true;
		}else {
			return false;
		}
	}
	
	private int[] nextLocation() {
		int coordsDir[] = new int[4];
		//0 x next location
		//1 y next location
		//2 x direction
		//3 y direction
		if(pastRB) {
			coordsDir[0] = (int) (x + Math.signum(x));
			coordsDir[1] = (int) (y + Math.signum(y));
			coordsDir[2] = (int) Math.signum(x);
			coordsDir[3] = (int) Math.signum(y);
		}else {
			coordsDir[0] = (int) (x - Math.signum(x));
			coordsDir[1] = (int) (y - Math.signum(y));
			coordsDir[2] = (int) -Math.signum(x);
			coordsDir[3] = (int) -Math.signum(y);
		}
		return coordsDir;
	}
	
	public void updateObservers(int state) {
		for(CarObserver o : observers) {
			o.update(this, state);
		}
	}

	private void move(int[] coordsDir) {
		if(Math.abs(y) > 0) {
			y = coordsDir[1];
		} else if(Math.abs(x) > 0){
			x = coordsDir[0];
		}
	}
	
	private boolean spaceFree(int[] coordsDir) {
		for(Car car : theModel.GetCars()) {
			if(carID != car.getID()) {
				if(car.getX() == x + (coordsDir[2] * 25) &&
				   car.getY() == y &&
				   car.getPastRB() == this.pastRB) {
					return false;
				}
				if(car.getY() == y + (coordsDir[3] * 25) &&
				   car.getX() == x &&
				   car.getPastRB() == this.pastRB) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean RBEntranceFree(int entrance) {
		for(Car car : theModel.GetCars()) {
			if(car.getBlocking()[entrance] == true) {
				return false;
			}
		}
		return true;
	}
	
	private void enterRoundabout(int entryPoint) {
		//entry point 0, 1, 2, or 3
		for(int i = 0; i < 43; i++) {
			move(nextLocation());
			try {
				Thread.sleep(tickTime);
			} catch(InterruptedException iex) {
				System.out.println("Exception: " + iex.getMessage() + ". Thread stopped");
			}
		}
		if(!pastRB) {
			degOnRB = (90 * entryPoint) - 10 - 360;
			setPointOnRB();
		}
	}
	
	private void goToDestRoad() {
		if(destX == 0) {
			if(Math.signum(destY) > 0) {
				x = 0;
				y = theModel.roadD.getCloseToRBY();
			}else {
				x = 0;
				y = theModel.roadB.getCloseToRBY();
			}
		}
		if(destY == 0){
			if(Math.signum(destX) > 0) {
				x = theModel.roadC.getCloseToRBX();
				y = 0;
			}else {
				x = theModel.roadA.getCloseToRBX();
				y = 0;
			}
		}
		pastRB = true;
		blocking[0] = false;
		blocking[1] = false;
		blocking[2] = false;
		blocking[3] = false;
		move(nextLocation());
	}
	
	private void setPointOnRB() {
		x = (int) ((theModel.getRBRadius()-40)*(Math.sin(degOnRB * (Math.PI/180))));
		y = (int) ((theModel.getRBRadius()-40)*(Math.cos(degOnRB * (Math.PI/180))));
		int degBlock = (int) (degOnRB + 20);
		
		blocking[0] = false;
		blocking[1] = false;
		blocking[2] = false;
		blocking[3] = false;
		
		if(degBlock < -270) {
			blocking[0] = true;
		}else if(degBlock < -180) {
			blocking[1] = true;
		}else if(degBlock < -90) {
			blocking[2] = true;
		}else if(degBlock < 0) {
			blocking[3] = true;
		}
	}
	
	private boolean atDestRoad() {
		if(destX == x && Math.signum(y) == Math.signum(destY)) {
			return true;
		}
		if(destY == y && Math.signum(x) == Math.signum(destX)) {
			return true;
		}
		return false;
	}
	
	public void run() {
		updateObservers(0);//Created
		while(destX != x || destY != y) {
			if(this.onRoad()) {
				if(spaceFree(nextLocation())) {
					xPrev = x;
					yPrev = y;
					move(nextLocation());
					state = 1;//Moving
					updateObservers(state);
				} else {
					state = 2;//stopped
					updateObservers(state);
				}
			}else {
				if(RBEntranceFree(RBEntryPoint)) {
					enterRoundabout(RBEntryPoint);
					state = 4;//Joined RB
					updateObservers(state);
					while(!pastRB) {
						degOnRB-= 0.5;
						degOnRB = degOnRB % 360;
						setPointOnRB();
						if(atDestRoad()) {
							pastRB = true;
							goToDestRoad();
							state = 5;//Exited RB
						}
						try {
							Thread.sleep(tickTime);
						} catch(InterruptedException iex) {
							System.out.println("Exception: " + iex.getMessage() + ". Thread stopped");
						}
					}
				} else {
					state = 2;//Stopped
					updateObservers(state);
				}
			}
			if(pastRB) {
				if(x < theModel.roadA.getFarFromRBX() + 1 ||
				   x > theModel.roadC.getFarFromRBX() - 1 ||
				   y < theModel.roadB.getFarFromRBY() - 1 ||
				   y > theModel.roadD.getFarFromRBY() + 1) {
					state = 3;//At Destination
					updateObservers(state);
					theModel.GetCars().remove(this);
				}
			}
			try {
				Thread.sleep(tickTime);
			} catch(InterruptedException iex) {
				System.out.println("Exception: " + iex.getMessage() + ". Thread stopped");
			}
		}
		theModel.GetCars().remove(this);
	}
}
