package voiceconference;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer extends Thread{
    
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    
    private RecordPlayback audioService;
    
    private byte[] buffer;
    
    
    public UDPServer(RecordPlayback playback){
        
        try {
            
            this.audioService = playback;
            
            //buffer for read input data
            buffer = new byte[ProgramData.PACKET_SIZE];
            
            //construct the socket.
            datagramSocket = new DatagramSocket(ProgramData.PORT_NUMBER);
           
            //create the datagram packet
            datagramPacket = new DatagramPacket(new byte[ProgramData.PACKET_SIZE], ProgramData.PACKET_SIZE);
            
        } catch (IOException ex1) {
            System.out.println("Socket is used by another program.");
            System.exit(1);
        }
        
    }
    

    @Override
    public void run() {

        System.out.println("Your server is online");
        try {
            datagramSocket.receive(datagramPacket);
            GUILaunch.informIncoming(datagramPacket.getAddress().toString());
            System.out.println("Yeah i am here");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        while(!GUILaunch.isAccept);

        while(true){

            try{

                //-------------------- Recieve byte array from datagram socket --------------
                datagramSocket.receive(datagramPacket);    
                System.out.println("Call from IP address : "+datagramPacket.getAddress()+" ");
                datagramPacket.setData(buffer);

                //--------------------- Deseriaize the object --------------------------------
                ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
                ObjectInput inputObject = null;
                inputObject = new ObjectInputStream(inputStream);
                DataPacket packet = (DataPacket)inputObject.readObject();

                System.out.println("Packet seq "+packet.packetNo);

                //--------------------- Send to audio output  --------------------------------
                audioService.playVoice(buffer);

            }catch(IOException e){
                System.out.println("Error in reception check for that.");
            }catch(ClassNotFoundException ex){
                System.out.println("Error in deserialization part.");
            }
            
        }
        
    }

}

