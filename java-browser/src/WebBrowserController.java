/**
 * TODO LISENSE
 */


import java.io.IOException;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
/**

 */
public class WebBrowserController extends StackPane {
	
	/** The logger. */
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	//------------------------------------------------------------
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Button addTab;
	
	// -------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public WebBrowserController() {
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WebBrowserController.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "", ex);
		}
	}
	
	/**
	 * Called as soon as .fxml is initialized [[SuppressWarningsSpartan]]
	 */
	@FXML
	private void initialize() {
		
		//tabPane
		tabPane.getTabs().clear();
		createAndAddNewTab();
		
		//addTab
		addTab.setOnAction(a -> createAndAddNewTab());
	}
	
	/**
	 * Creates a new tab for the web browser ->Directing to a specific web site [[SuppressWarningsSpartan]]
	 * 
	 * @param webSite
	 */
	public WebBrowserTabController createAndAddNewTab(String... webSite) {
		
		//Create
		WebBrowserTabController webBrowserTab = createNewTab(webSite);
		
		//Add the tab
		tabPane.getTabs().add(webBrowserTab.getTab());
		
		return webBrowserTab;
	}
	
	/**
	 * Creates a new tab for the web browser ->Directing to a specific web site [[SuppressWarningsSpartan]]
	 * 
	 * @param webSite
	 */
	public WebBrowserTabController createNewTab(String... webSite) {
		
		//Create
		Tab tab = new Tab("");
		WebBrowserTabController webBrowserTab = new WebBrowserTabController(this, tab, webSite.length == 0 ? null : webSite[0]);
		tab.setOnClosed(c -> {
			
			//Check the tabs number
			if (tabPane.getTabs().isEmpty())
				createAndAddNewTab();
			
			// Delete cache for navigate back
			webBrowserTab.webEngine.load("about:blank");
			
			//Delete cookies  Experimental!!! 
			//java.net.CookieHandler.setDefault(new java.net.CookieManager())
			
		});
		
		return webBrowserTab;
	}
	
	/**
	 * Closes the tabs to the right of the given Tab
	 * 
	 * @param tab
	 */
	public void closeTabsToTheRight(Tab givenTab) {
		//Return if size <= 1
		if (tabPane.getTabs().size() <= 1)
			return;
		
		//The start
		int start = tabPane.getTabs().indexOf(givenTab);
		
		//Remove the appropriate items
		tabPane.getTabs().stream()
				//filter
				.filter(tab -> tabPane.getTabs().indexOf(tab) > start)
				//Collect the all to a list
				.collect(Collectors.toList()).forEach(this::removeTab);
		
	}
	
	/**
	 * Closes the tabs to the left of the given Tab
	 * 
	 * @param tab
	 */
	public void closeTabsToTheLeft(Tab givenTab) {
		//Return if size <= 1
		if (tabPane.getTabs().size() <= 1)
			return;
		
		//The start
		int start = tabPane.getTabs().indexOf(givenTab);
		
		//Remove the appropriate items
		tabPane.getTabs().stream()
				//filter
				.filter(tab -> tabPane.getTabs().indexOf(tab) < start)
				//Collect the all to a list
				.collect(Collectors.toList()).forEach(this::removeTab);
		
	}
	
	/**
	 * Removes this Tab from the TabPane
	 * 
	 * @param tab
	 */
	public void removeTab(Tab tab) {
		tabPane.getTabs().remove(tab);
		tab.getOnClosed().handle(null);
	}
	
	/**
	 * @return the tabPane
	 */
	public TabPane getTabPane() {
		return tabPane;
	}
	
	/**
	 * This is a list holding all the proposed websites for the user
	 */
	
	public static final SortedSet<String> WEBSITE_PROPOSALS = new TreeSet<>();
	
}
