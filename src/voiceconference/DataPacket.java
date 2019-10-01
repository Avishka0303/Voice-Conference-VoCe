package voiceconference;

import java.io.Serializable;

public class DataPacket implements Serializable{
    int packetNo;
    byte voice_buffer[];
    
    public DataPacket(int count , byte data[]){
        voice_buffer= new byte[ProgramData.PACKET_SIZE];
        voice_buffer = data;
        this.packetNo = count;
    }
}
