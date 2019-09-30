package voiceconference;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    
    private static DatagramSocket datagramSocket;
    private static DatagramPacket datagramPacket;
    private byte[] buffer;
    
    public UDPServer(){
        try {
            buffer = new byte[Data.PACKET_SIZE];
            //construct the socket.
            datagramSocket = new DatagramSocket(Data.PORT_NUMBER);
            //create the datagram packet
            datagramPacket = new DatagramPacket(new byte[Data.PACKET_SIZE], Data.PACKET_SIZE);
        } catch (SocketException ex) {
            System.out.println("Socket is used by another program.");
        }
    }
    
    public void receptionHandler(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        datagramSocket.receive(datagramPacket);
                        System.out.println("IP address : "+datagramPacket.getAddress()+" ");
                        datagramPacket.setData(buffer);
                        
                        //give to the record playback.
                    }catch(Exception e){
                        System.out.println("Error in reception check for that.");
                    }
                }
            }
        });
        t.start();
    }
}
