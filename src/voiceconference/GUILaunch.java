package voiceconference;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;



class GUILaunch {
    
    private Text title;
    private TextField connectToIP;
    private RadioButton receiverRadio;
    private RadioButton senderRadio;
    private Button callBtn;
    private Scene scene;
    
    private String hostIP;
    
    private static UDPClient client;
    private UDPServer server;
    private RecordPlayback audioService;
    
    public GUILaunch(){
        createGUI();
    }
    
    public Scene getScene(){
        return scene;
    }

    private void createGUI() {
        
        BorderPane rootPane = new BorderPane();
        rootPane.setPadding(new Insets(15, 15, 15, 15));
        
        //create the titlebar 
        title = new Text("VoCe - Voice Conference");
        title.setFont(Font.font(null, FontWeight.BOLD,40));
        title.setFill(Color.WHITE);
        
        VBox topBar = new VBox(5);
        topBar.setAlignment(Pos.CENTER);
        topBar.getChildren().add(title);
        
        rootPane.setTop(topBar);
        rootPane.setLeft(createLeftBar());
        rootPane.setStyle("-fx-background-color: darkmagenta;");
        
        //set with the scene
        scene = new Scene(rootPane,600,500);
        scene.setFill(Color.DARKMAGENTA);
        
    }
    
    private VBox createLeftBar(){
        
        VBox leftBar = new VBox(5);
        leftBar.setPadding(new Insets(15, 15, 15, 15));
        leftBar.setSpacing(5);
        
        Label ipLabel = new Label("Enter IP of host : ");
        ipLabel.setFont(Font.font(null,FontWeight.BOLD,20));
        ipLabel.setTextFill(Color.WHITE);
        
        connectToIP = new TextField();
        connectToIP.setPromptText("Enter reciever ip");
        connectToIP.setFont(Font.font(null,FontWeight.MEDIUM,20));
        
        HBox chooseBox = new HBox();
        chooseBox.setSpacing(15);
        chooseBox.setPadding(new Insets(15, 15, 15, 0));
        
        ToggleGroup chooseToggle = new ToggleGroup();
        
        receiverRadio =new RadioButton("Receiver");
        receiverRadio.setFont(Font.font(null,FontWeight.MEDIUM,20));
        receiverRadio.setTextFill(Color.WHITE);
        receiverRadio.setSelected(true);
        receiverRadio.setToggleGroup(chooseToggle);
        
        senderRadio = new RadioButton("Sender");
        senderRadio.setToggleGroup(chooseToggle);
        senderRadio.setFont(Font.font(null,FontWeight.MEDIUM,20));
        senderRadio.setTextFill(Color.WHITE);
        
        callBtn = new Button("Connect");
        callBtn.setFont(Font.font(null,FontWeight.MEDIUM,20));
        callBtn.setTextFill(Color.WHITE);
        callBtn.setStyle("-fx-background-color: green;");
        
        chooseBox.getChildren().addAll(receiverRadio,senderRadio);
       
        leftBar.setAlignment(Pos.CENTER_LEFT);
        leftBar.getChildren().addAll(ipLabel,connectToIP,chooseBox,callBtn);
     
        return leftBar; 
    }

    void startService() {
        callBtn.setOnAction(ActionEvent->{
            if( isValidIP() && client==null ){
                client = new UDPClient(hostIP);
                audioService.captureVoice(client);
            }else{
                System.out.println("IP is not valid . Enter a new IP");
            }
        });
    }
    
    public void startServer(){
        server = new UDPServer();
        audioService = new RecordPlayback();
        server.receptionHandler(audioService);
    }

    private boolean isValidIP() {
        hostIP = connectToIP.getText();
        if(hostIP.equals(""))
            return false;
        else
            return true;
    }
}
