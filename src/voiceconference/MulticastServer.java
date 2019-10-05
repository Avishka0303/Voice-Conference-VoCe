package voiceconference;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class MulticastServer {
    
    private DatagramPacket datagramPacket;
    private MulticastSocket multicastSocket;
    private RecordPlayback audioService;
    
    private byte[] buffer;
    
    public MulticastServer(RecordPlayback playback){
        
        try {
            
            this.audioService = playback;
            
            buffer = new byte[ProgramData.PACKET_SIZE];
            //initiaize the multicast Socket.
            multicastSocket = new MulticastSocket(ProgramData.MUL_PORT_NUMBER);
            //create the datagram packet
            datagramPacket = new DatagramPacket(new byte[ProgramData.PACKET_SIZE], ProgramData.PACKET_SIZE);
            
        }catch (IOException ex1){
            System.out.println("IO Exception has been generate");
        }
        
    }
    
    public void run(){ 

        while(true){
            
            try{
                multicastSocket.receive(datagramPacket);
                datagramPacket.setData(buffer);
                audioService.playVoice(buffer);
            }catch(IOException ex){
                System.out.println("Error in multicast recieve.");
            }
            
        }
        
    }
    
}
