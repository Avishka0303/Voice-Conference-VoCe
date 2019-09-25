/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voiceconference;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Predator X21
 */
class GUILaunch {
    
    private Text title;
    private Scene scene;
    
    public GUILaunch(){
        createGUI();
    }
    
    public Scene getScene(){
        return scene;
    }

    void startConversation() {
        
    }

    private void createGUI() {
        
        BorderPane rootPane = new BorderPane();
        
        //create the titlebar 
        title = new Text("VoCe - Voice Conference");
        title.setFont(Font.font(null, FontWeight.BOLD,40));
        title.setFill(Color.WHITE);
        
        VBox topBar = new VBox(5);
        topBar.setAlignment(Pos.CENTER);
        topBar.getChildren().add(title);
        
        rootPane.setTop(topBar);
        
        //set with the scene
        scene = new Scene(rootPane,900,700);
        scene.setFill(Color.DARKMAGENTA);
        
    }
    
    private VBox createLeftBar(){
        
        VBox leftBar = new VBox(5);
        Text ipText = new Text("This is your IP : ");
        leftBar.setAlignment(Pos.CENTER);
        return leftBar;
    }
    
    
    
}
