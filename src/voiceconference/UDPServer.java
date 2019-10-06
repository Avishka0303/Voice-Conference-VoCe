package voiceconference;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer extends Thread{
    
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    
    private RecordPlayback audioService;
    
    private byte[] buffer;
    
    private byte reArrangeBuffer[][]; 
    
    public UDPServer(RecordPlayback playback){
        
        try {
            
            this.audioService = playback;
            
            //buffer for read input data
            buffer = new byte[ProgramData.PACKET_SIZE * 4];
            
            //buffer for rearranging the packets
            this.reArrangeBuffer = new byte[ProgramData.MEM_SIZE][ProgramData.PACKET_SIZE];
            
            //construct the socket.
            datagramSocket = new DatagramSocket(ProgramData.PORT_NUMBER);
           
            //create the datagram packet
            datagramPacket = new DatagramPacket( buffer , ProgramData.PACKET_SIZE * 4 );
            
        } catch (IOException ex1) {
            System.out.println("Socket is used by another program.");
            System.exit(1);
        }
        
    }
    

    @Override
    public void run() {
            
        System.out.println("Server is online");
        
        try { 
            datagramSocket.receive(datagramPacket);
            VoiceConference.guiLaunch.informIncoming(datagramPacket.getAddress().toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        while(true){

            try{

                //-------------------- Recieve byte array from datagram socket --------------
                datagramSocket.receive(datagramPacket);    
                System.out.println("Call from IP address : "+datagramPacket.getAddress()+" ");

                //--------------------- Deseriaize the object --------------------------------
                ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
                ObjectInputStream inputObject = new ObjectInputStream(inputStream);
                DataPacket packet = (DataPacket)inputObject.readObject();

                System.out.println("Packet index "+packet.packetNo);

                //--------------------- Send to audio output  --------------------------------
                audioService.playVoice(packet.voice_buffer);

            }catch(IOException e){
                System.out.println("Error in reception check for that.");
                e.printStackTrace();
            }catch(ClassNotFoundException ex){
                System.out.println("Error in deserialization part.");
                ex.printStackTrace();
            }
            
        }
        
    }
}

