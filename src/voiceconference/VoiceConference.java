package voiceconference;

import javafx.application.Application;
import javafx.stage.Stage;

public class VoiceConference extends Application {
    
    public static GUILaunch guiLaunch;
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("VoCe - Voice Conference");
        stage.sizeToScene();
        stage.setScene(guiLaunch.getScene());
        stage.setResizable(false);
        stage.show();
        guiLaunch.startServer();
        guiLaunch.startService();
    }

    public static void main(String[] args) {
        guiLaunch = new GUILaunch();
        launch(args);
    }   
    
}
