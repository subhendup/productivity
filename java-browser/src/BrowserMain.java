


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * From here you start the application
 * 

 /
 */
public class BrowserMain extends Application {
	
	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		
		//Root
		BorderPane root = new BorderPane();
		root.setCenter(new WebBrowserController());
		
		//Scene
		Scene scene = new Scene(root, getVisualScreenWidth() / 1.2, getVisualScreenHeight() / 1.2);
		
		//Prepare the Stage
		primaryStage.setTitle("Subhendu's WebBrowser");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	/**
	 * Gets the visual screen width.
	 *
	 * @return The screen <b>Width</b> based on the <b>visual bounds</b> of the
	 *         Screen.These bounds account for objects in the native windowing
	 *         system such as task bars and menu bars. These bounds are
	 *         contained by Screen.bounds.
	 */
	public static double getVisualScreenWidth() {
		return Screen.getPrimary().getVisualBounds().getWidth();
	}
	
	/**
	 * Gets the visual screen height.
	 *
	 * @return The screen <b>Height</b> based on the <b>visual bounds</b> of the
	 *         Screen.These bounds account for objects in the native windowing
	 *         system such as task bars and menu bars. These bounds are
	 *         contained by Screen.bounds.
	 */
	public static double getVisualScreenHeight() {
		return Screen.getPrimary().getVisualBounds().getHeight();
	}
	
	/**
	 * BrowserMain Method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
