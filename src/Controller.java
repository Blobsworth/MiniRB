import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Controller implements ActionListener {
	
	Model theModel;
	View theView;
	
	public Controller(Model model, View guiview){
		this.theModel = model;
		this.theView = guiview;
		Car.setModel(model);
		Car.setCarIDList(0);
		
		theView.setButtonsListener((ActionListener)this);
		
		//Refresh gui once all components are initialised in main
		theView.reload();
	}
	
	private void CreateCar(int x, int y, int destX, int destY, Color colour) {
		Car car = new Car(x, y, destX, destY, colour, theView.getGuiComposer(), theView.getTextView());
		theModel.AddCar(car);
		Thread newTask = new Thread(car);
		newTask.start();
		theModel.AddCar(car);
	}
	
	private void TestOne() {
		CreateCar(theModel.roadA.getFarFromRBX(),
				  theModel.roadA.getFarFromRBY(),
				  theModel.roadA.getCloseToRBX(),
				  theModel.roadA.getCloseToRBY(),
				  Color.RED);
		CreateCar(theModel.roadA.getFarFromRBX() - 50,
				  theModel.roadA.getFarFromRBY(),
				  theModel.roadA.getCloseToRBX(),
				  theModel.roadA.getCloseToRBY(),
				  Color.BLUE);
	}
	
	private void TestTwo() {
		CreateCar(theModel.roadA.getFarFromRBX(),
				  theModel.roadA.getFarFromRBY(),
				  theModel.roadD.getFarFromRBX(),
				  theModel.roadD.getFarFromRBY(),
				  generateRandomColour());
		CreateCar(theModel.roadA.getFarFromRBX() - 50,
				  theModel.roadA.getFarFromRBY(),
				  theModel.roadC.getFarFromRBX(),
				  theModel.roadC.getFarFromRBY(),
				  Color.PINK);
	}
	
	private void TestThree() {
		int carCount;
		carCount = (int)(Math.random() * 6) + 3;//between 3 and 8 including.
		int[] carsOnRoad = {0, 0, 0, 0};
		for(int i = 0; i < carCount; i++) {
			int startRoad = (int)(Math.random() * 4);
			int destRoad = (int)(Math.random() * 2) + 1;
			CreateCar(getRoadX(startRoad) + (int)Math.signum(getRoadX(startRoad)) * carsOnRoad[startRoad] * 50,
					  getRoadY(startRoad) + (int)Math.signum(getRoadY(startRoad)) * carsOnRoad[startRoad] * 50, 
					  getRoadX((startRoad + destRoad) % 3),
					  getRoadY((startRoad + destRoad) % 3),
					  generateRandomColour());
			carsOnRoad[startRoad]++;
		}
	}
	
	private void TestFour() {
		int carCount;
		carCount = (int)(Math.random() * 4) + 8;//between 8 and 12 including.
		int[] carsOnRoad = {0, 0, 0, 0};
		for(int i = 0; i < carCount; i++) {
			int startRoad = (int)(Math.random() * 4);
			int destRoad = (int)(Math.random() * 2) + 1;
			CreateCar(getRoadX(startRoad) + (int)Math.signum(getRoadX(startRoad)) * carsOnRoad[startRoad] * 50,
					  getRoadY(startRoad) + (int)Math.signum(getRoadY(startRoad)) * carsOnRoad[startRoad] * 50, 
					  getRoadX((startRoad + destRoad) % 3),
					  getRoadY((startRoad + destRoad) % 3),
					  generateRandomColour());
			carsOnRoad[startRoad]++;
		}
	}
	
	private int getRoadY(int road) {
		switch(road) {
		case 0:
			return theModel.roadD.getFarFromRBY();
		case 1:
			return theModel.roadC.getFarFromRBY();
		case 2:
			return theModel.roadB.getFarFromRBY();
		case 3:
			return theModel.roadA.getFarFromRBY();
		default:
			return theModel.roadA.getFarFromRBY();
		}
	}
	
	private int getRoadX(int road) {
		switch(road) {
		case 0:
			return theModel.roadD.getFarFromRBX();
		case 1:
			return theModel.roadC.getFarFromRBX();
		case 2:
			return theModel.roadB.getFarFromRBX();
		case 3:
			return theModel.roadA.getFarFromRBX();
		default:
			return theModel.roadA.getFarFromRBX();
		}
	}
	
	private Color generateRandomColour() {
		int r = (int)(Math.random() * 16);
		int g = (int)(Math.random() * 16);
		int b = (int)(Math.random() * 16);
		int rgb = (r << 32) + (g << 16) + b;
		return Color.decode("#" + Integer.toString(rgb));
	}

	public void actionPerformed(ActionEvent e) {
		theView.clearTextView();
		theView.buttonContainer.test1Button.setEnabled(false);
		theView.buttonContainer.test2Button.setEnabled(false);
		theView.buttonContainer.test3Button.setEnabled(false);
		theView.buttonContainer.test4Button.setEnabled(false);
		switch(e.getActionCommand()) {
			case "1": TestOne();
			break;
			case "2": TestTwo();
			break;
			case "3": TestThree();
			break;
			case "4": TestFour();
		}
	}
}
