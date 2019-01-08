import java.awt.*;
import javax.swing.*;

public class TextView extends JPanel implements CarObserver{
	JTextArea console = new JTextArea(10, 0);
	JScrollPane wrappedConsole = new JScrollPane(console);
	
	public TextView() {
		this.setLayout(new BorderLayout());
		this.add(wrappedConsole);
	}
	
	public void printLine(String line) {
		System.out.println(line);
		console.setText(line + "\n" + console.getText());
	}
	
	public void clearTextView() {
		console.setText("");
	}

	public void update(Car car, int state) {
		String line = "";
		switch(state) {
		case 0:
			line = "Car " + car.getID() + " Created At:\n\t" + car.getX() + ", " + car.getY() + "\n";
			break;
		case 2:
			line = "Car " + car.getID() + " Stopped At\n\t" + car.getX() + ", " + car.getY() + "\n";
			break;
		case 3:
			line = "Car " + car.getID() + " Reached Destination At:\n\t" + car.getX() + ", " + car.getY() + "\n";
			break;
		case 4:
			line = "Car " + car.getID() + " Joined the Roundabout";
			break;
		case 5:
			line = "Car " + car.getID() + " Exited the Roundabout";
			break;
		}
		if(line != "") {
			printLine(line);
		}
	}
}
