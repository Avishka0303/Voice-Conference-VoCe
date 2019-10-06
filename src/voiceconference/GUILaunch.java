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
    private static Label incomingIP;
    private RadioButton receiverRadio;
    private RadioButton senderRadio;
    private static Button callBtn;
    private static Button acceptBtn;
    private Scene scene;
    
    private static String hostIP;
    public static boolean isAccept=false;
    
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
        
        receiverRadio =new RadioButton("2 peers");
        receiverRadio.setFont(Font.font(null,FontWeight.MEDIUM,20));
        receiverRadio.setTextFill(Color.WHITE);
        receiverRadio.setSelected(true);
        receiverRadio.setToggleGroup(chooseToggle);
        
        senderRadio = new RadioButton("Multicast");
        senderRadio.setToggleGroup(chooseToggle);
        senderRadio.setFont(Font.font(null,FontWeight.MEDIUM,20));
        senderRadio.setTextFill(Color.WHITE);
        
        incomingIP = new Label("Waiting for incoming call ...");
        incomingIP.setFont(Font.font(null,FontWeight.BOLD,20));
        incomingIP.setTextFill(Color.WHITE);
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(15);
        buttonBox.setPadding(new Insets(15, 15, 15, 0));
        
        callBtn = new Button("Connect");
        callBtn.setFont(Font.font(null,FontWeight.MEDIUM,20));
        callBtn.setTextFill(Color.WHITE);
        callBtn.setStyle("-fx-background-color: green;");
        
        acceptBtn= new Button("Accept");
        acceptBtn.setFont(Font.font(null,FontWeight.MEDIUM,20));
        acceptBtn.setTextFill(Color.WHITE);
        acceptBtn.setStyle("-fx-background-color: green;");
        acceptBtn.setVisible(false);
        
        chooseBox.getChildren().addAll(receiverRadio,senderRadio);
        buttonBox.getChildren().addAll(callBtn,acceptBtn);
        
        leftBar.setAlignment(Pos.CENTER_LEFT);
        leftBar.getChildren().addAll(chooseBox,ipLabel,connectToIP,incomingIP,buttonBox);
     
        return leftBar; 
    }

    void startService() {
        callBtn.setOnAction(ActionEvent->{
            if( isValidIP()){
                client = new UDPClient(hostIP);
                audioService.captureVoice(client);
            }else{
                System.out.println("IP is not valid . Enter a new IP");
            }
        });

        acceptBtn.setOnAction(ActionEvent->{
            client = new UDPClient(hostIP);
            audioService.captureVoice(client);
            isAccept=true;
        });
        
    }
    
    public void startServer(){
        audioService = new RecordPlayback();
        server = new UDPServer(audioService);
        server.start();
    }
    
    public boolean isValidIP() {
        hostIP = connectToIP.getText();
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return hostIP.matches(PATTERN);
    }
    
    public void informIncoming(String ipaddress){
        //acceptBtn.setVisible(true);
        callBtn.setVisible(false);
        ipaddress = ipaddress.replaceAll("\\/", "");
        hostIP=ipaddress;
        client = new UDPClient(hostIP);
        audioService.captureVoice(client);
    }
    
}
