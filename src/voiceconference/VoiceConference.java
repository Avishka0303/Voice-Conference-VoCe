package voiceconference;

import javafx.application.Application;
import javafx.stage.Stage;

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

    public static void main(String[] args) {
        guiLaunch = new GUILaunch();
        guiLaunch.startEngine();
        launch(args);
    }   
}
