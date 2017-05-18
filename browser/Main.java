package browser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import browser.view.BrowserView;
import browser.view.BrowserPresenter;


/**
 *
 * @author Giuseppe Serra
 */
public class Main extends Application{
	
    private WebView web; 
    
    @Override
    public void start(Stage primaryStage){
        
        web = new WebView();
        BrowserView root = new BrowserView(web);
        BrowserPresenter wPres = new BrowserPresenter(web,root);

        Scene scene = new Scene(root,1000,620);
        
        Image tarta = new Image(getClass()
                            .getResourceAsStream("images/TartaAriete.png"));
        primaryStage.getIcons().add(tarta);
        primaryStage.setTitle("TartaAriete!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
    * @param args the command line arguments
    */	
    public static void main(String[] args) { 
            launch(args); 
    }
} 
