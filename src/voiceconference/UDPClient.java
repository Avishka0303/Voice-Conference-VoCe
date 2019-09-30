package voiceconference;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClient {
    
    private InetAddress hostAddress;
    private DatagramSocket udpSocket;
    
    public UDPClient(String peerIPAddress){
        try {
            this.hostAddress = InetAddress.getByName(peerIPAddress);
            this.udpSocket   = new DatagramSocket();
        } catch (UnknownHostException ex) {
            System.out.println("This host is unknown.");
        }catch(SocketException ex1){
            System.out.println("This socket cannot be created.");
        }
    }
    
    public void UDPSendPacket(byte[] data){
        DatagramPacket dataPacket = new DatagramPacket(data,data.length,hostAddress,Data.PORT_NUMBER);
        try{
            udpSocket.send(dataPacket);
        }catch(IOException ex){
            System.out.println("Error in packet sending protocol.");
        }
    }
    
}
