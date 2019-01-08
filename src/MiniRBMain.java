
public class MiniRBMain {
	public static void main(String[] args) {
		Model theModel = new Model();
		View theView = new View();
		Controller theController = new Controller(theModel, theView);
	}
}
