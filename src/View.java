import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;

public class View extends JFrame {
	protected ButtonPanel buttonContainer;
	protected TextView theTextView;
	private static GUIComposer guiComposer;
	
	public View() {
		//Define JFrame
		super("Mini Roundabout");
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    this.setSize( 825, 665 ); // set frame size
	    this.setVisible( true ); // display frame
	    this.setResizable(false);
	    
	    //Define Main Panel and add to JFrame
	    JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new BorderLayout());
	    this.getContentPane().add(mainPanel);
	    mainPanel.setBackground(Color.DARK_GRAY);
	    
	    //Add Button Panel
	    buttonContainer = new ButtonPanel();
		mainPanel.add(buttonContainer, BorderLayout.WEST);
		
		//Add Console Panel
		theTextView = new TextView();
		mainPanel.add(theTextView, BorderLayout.SOUTH);
		
		//Add Background Panel
		guiComposer = new GUIComposer();
		mainPanel.add(guiComposer, BorderLayout.CENTER);
	}
	
	public GUIComposer getGuiComposer() {
		return guiComposer;
	}
	
	public TextView getTextView() {
		return theTextView;
	}
	
	public void setButtonsListener(ActionListener listener) {
		buttonContainer.setButtonsListener(listener);
	}
	
	public void reload() {
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void clearTextView() {
		theTextView.clearTextView();
	}
}
