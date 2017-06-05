package browser.view;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import java.util.LinkedList;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

/**
 *
 * @author Giuseppe Serra
 */
public class BrowserPresenter {
    
        private final BrowserView viewWeb;
        private final WebView wView;
        
    
    
    public BrowserPresenter(WebView webView,BrowserView viewWeb){
      this.viewWeb=viewWeb;
      this.wView =webView;
      attachEvents();
    }

    
    /**
    * Associo gli eventi ai vari componenti la GUI.
    */
    private void attachEvents(){
        WebEngine wEngine = wView.getEngine();
        WebHistory history= wEngine.getHistory();
        ObservableList<WebHistory.Entry> hList= history.getEntries();
        
        wEngine.load("http://www.google.com/");
       
        //definisco le azioni del visualizatore,TextFiled.
        viewWeb.url.setOnAction(e -> {
            String uri = viewWeb.url.getText();
            String regex ="http\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?";
            String regex1="w{2}[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?";
            if(uri.matches(regex))wEngine.load(uri);
            else if(uri.matches(regex1)){
                           wEngine.load(String.format("http://%s", uri)); 
        }
        String [] splits =uri.split("\\s");
        LinkedList<String> strings = new LinkedList<>();
        for(String split:splits){
            strings.add(split);
        }
        String query= String.join("+", strings);
            wEngine.load(String.format(
        "https://www.google.it/#q=%s"
            ,query));
        });
        //definisco le azioni dei pulsanti del Browser,aggiungendo i menu contestuali.
        viewWeb.home.setOnAction(e-> wEngine.load("http://www.google.com"));
        viewWeb.back.setOnAction(e -> history.go(-1));
        viewWeb.back.disableProperty().bind(history.currentIndexProperty()
                                           .isEqualTo(0));
        viewWeb.forth.setOnAction(e ->history.go(1));
        viewWeb.forth.disableProperty().bind(Bindings.createBooleanBinding(
        () -> history.getCurrentIndex() >= hList.size() - 1,//interface Callable.
              history.currentIndexProperty()));
        viewWeb.back.setOnContextMenuRequested(e -> {
            int curr = history.getCurrentIndex();
            if(curr<1) return;
            ContextMenu cm = new ContextMenu();
            for(int i=curr-1;i>=0;i--){
                int offset=curr-1;
                MenuItem mi=new MenuItem(hList.get(i).getUrl());
                mi.setOnAction(v -> history.go(offset));
                cm.getItems().add(mi);
            }
            cm.setAutoHide(true);
            cm.show(viewWeb.back.getScene().getWindow(), e.getScreenX(),
                                                 e.getScreenY());
        });	
        viewWeb.forth.setOnContextMenuRequested(e -> {
        int curr = history.getCurrentIndex();
        if (curr >= history.getEntries().size() - 1) return;
        ContextMenu cm = new ContextMenu();
        for (int i = curr + 1; i < hList.size(); i++) {
            int offset = i - curr;
            MenuItem mi = new MenuItem(hList.get(i).getUrl());
            mi.setOnAction(v -> history.go(offset));
            cm.getItems().add(mi);
        }
        cm.setAutoHide(true);
        cm.show(viewWeb.forth.getScene().getWindow(), e.getScreenX(), 
                                              e.getScreenY());
        });
        //carico il Worker su WebEngine per visualizzare URI corretto.
        wEngine.getLoadWorker().stateProperty().addListener((o, ov, nv) -> {
        if (nv == Worker.State.SUCCEEDED) {
            viewWeb.url.setText(wEngine.getLocation());
        } else if (nv == Worker.State.FAILED || nv == Worker.State.CANCELLED) { }
        });
    }
}

