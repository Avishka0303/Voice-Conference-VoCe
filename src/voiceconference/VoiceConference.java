/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voiceconference;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Predator X21
 */
public class VoiceConference extends Application {
    
    private static GUILaunch guiLaunch;
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("VoCe - Voice Conference");
        stage.sizeToScene();
        stage.setScene(guiLaunch.getScene());
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        guiLaunch = new GUILaunch();
        launch(args);
    }
    
}
