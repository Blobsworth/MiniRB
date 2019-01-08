import java.util.*;

public class Model {
	private ArrayList<Car> cars = new ArrayList<Car>();
	protected Road roadA;
	protected Road roadB;
	protected Road roadC;
	protected Road roadD;
	private int roundaboutRadius = 160;
	
	public Model() {
		InitialiseRoads();
	}
	
	public int getRBRadius() {
		return roundaboutRadius;
	}
	
	protected class Road {
		private int farFromRBX;
		private int closeToRBX;
		private int farFromRBY;
		private int closeToRBY;
		
		public Road(int farFromRBX, int farFromRBY, int closeToRBX, int closeToRBY) {
			this.farFromRBX = farFromRBX;
			this.farFromRBY = farFromRBY;
			this.closeToRBX = closeToRBX;
			this.closeToRBY = closeToRBY;
		}
		
		public int getFarFromRBX() {
			return farFromRBX;
		}
		
		public int getFarFromRBY() {
			return farFromRBY;
		}
		
		public int getCloseToRBX() {
			return closeToRBX;
		}
		
		public int getCloseToRBY() {
			return closeToRBY;
		}
	}
	
	private void InitialiseRoads() {
		roadA = new Road(-390, 0, -roundaboutRadius, 0);
		roadB = new Road(0, -250, 0, -roundaboutRadius);
		roadC = new Road(400, 0, roundaboutRadius, 0);
		roadD = new Road(0, 300, 0, roundaboutRadius);
	}
	
	public ArrayList<Car> GetCars() {
		return cars;
	}
	
	public void AddCar(Car car) {
		cars.add(car);
	}
}
