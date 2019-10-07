package voiceconference;

public interface ProgramData {
    public static final int PACKET_SIZE = 500;
    public static final int MEM_SIZE = 16;   //16 slots for store bytes.
    public static final int PORT_NUMBER = 2000;
    public static final int MUL_PORT_NUMBER = 2001;
    public static final String MULTICAST_ADDRESS = "230.0.0.1";     // CLASS D - MULTICAST ADDRESS
}
