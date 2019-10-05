package voiceconference;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    
    private static DatagramSocket datagramSocket;
    private static DatagramPacket datagramPacket;
    private byte[] buffer;
    
    public UDPServer(){
        try {
            buffer = new byte[ProgramData.PACKET_SIZE];
            //construct the socket.
            datagramSocket = new DatagramSocket(ProgramData.PORT_NUMBER);
            //create the datagram packet
            datagramPacket = new DatagramPacket(new byte[ProgramData.PACKET_SIZE], ProgramData.PACKET_SIZE);
        } catch (SocketException ex) {
            System.out.println("Socket is used by another program.");
        }
    }
    
    public void receptionHandler(RecordPlayback audioService){
        System.out.println("Your server is online");
        try {
            datagramSocket.receive(datagramPacket);
            VoiceConference.guiLaunch.informIncoming(datagramPacket.getAddress().toString());
            System.out.println("Yeah i am here");
            while(!GUILaunch.isAccept);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Thread t = new Thread( new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        datagramSocket.receive(datagramPacket);    
                        System.out.println("Call from IP address : "+datagramPacket.getAddress()+" ");
                        datagramPacket.setData(buffer);
                        audioService.playVoice(buffer);
                    }catch(Exception e){
                        System.out.println("Error in reception check for that.");
                    }
                }
            }
        });
        t.start();
    }
}
