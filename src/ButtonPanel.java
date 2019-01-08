import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{
	JButton test1Button = new JButton();
	JButton test2Button = new JButton();
	JButton test3Button = new JButton();
	JButton test4Button = new JButton();
	
	public ButtonPanel() {
		this.setLayout(new GridLayout(0, 1));
			    
		test1Button.setText("Test One");
		test2Button.setText("Test Two");
		test3Button.setText("Test Three");
		test4Button.setText("Test Four");
			    
		test1Button.setActionCommand("1");
		test2Button.setActionCommand("2");
		test3Button.setActionCommand("3");
		test4Button.setActionCommand("4");
			    
		this.add(test1Button);
		this.add(test2Button);
		this.add(test3Button);
		this.add(test4Button);
	}
	
	public void setButtonsListener(ActionListener listener) {
		test1Button.addActionListener(listener);
		test2Button.addActionListener(listener);
		test3Button.addActionListener(listener);
		test4Button.addActionListener(listener);
	}
}
