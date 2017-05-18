package browser.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;



/**
 *
 * @author Giuseppe Serra
 */
public class BrowserView extends BorderPane{
    Button  back=new Button();
    Button  forth = new Button();    
    Button  home=new Button();
    
    TextField url= new TextField();
   
    private final WebView wView;
     

    public BrowserView(WebView wView){
        super();
        this.wView =wView;
        VBox top = new VBox();
        VBox centerWeb = new VBox();
        HBox control = new HBox();
        
        Image back_=new Image("browser/images/back.png",30,30,false,false);
        Image forth_=new Image("browser/images/forth.png",30,30,false,false);
        Image home_=new Image("browser/images/home.png",30,30,false,false);
        back.setGraphic(new ImageView(back_));
        forth.setGraphic(new ImageView(forth_));
        home.setGraphic(new ImageView(home_));
        
        
        control.getChildren().addAll(home,back,forth,url);
        control.setAlignment(Pos.CENTER);
        top.getChildren().add(control);
        
        HBox.setHgrow(url,Priority.ALWAYS);
        
        centerWeb.getChildren().add(wView);
        VBox.setVgrow(centerWeb, Priority.ALWAYS);
        
        
        this.setPadding(new Insets(5,0,5,0));
        this.setTop(top);
        this.setCenter(centerWeb);
    }   
}
