package voiceconference;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient {
    
    private MulticastSocket multicastSocket;
    private InetAddress multicastAddress;
    private RecordPlayback audioService;
    private byte[] buffer;
    
    public static boolean multicastOnline = true ;
    
    public MulticastClient(){
        try {           
            //initiaize the multicast Socket.
            multicastSocket = new MulticastSocket(ProgramData.MUL_PORT_NUMBER);
            //initialize the multicast
            multicastAddress =InetAddress.getByName(ProgramData.MULTICAST_ADDRESS);
            //join the group
            multicastSocket.joinGroup(multicastAddress);
        }catch (IOException ex1){
            System.out.println("IO Exception has been generate");
        }
    }
    
    public void sendDataPacket(byte[] data){ 
        try {
            DatagramPacket dataPacket = new DatagramPacket(data,data.length,multicastAddress,ProgramData.MUL_PORT_NUMBER);
            multicastSocket.send(dataPacket);
        } catch (IOException ex) {
            System.out.println("Error in multicast packet sending.");
        }
    }
    
    public void endTransmission(){
        try {
            multicastSocket.leaveGroup(multicastAddress);
            multicastSocket.close();
        } catch (IOException ex) {
            System.out.println("Error when leaving the group.");
        }
    }
}
