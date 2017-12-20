package file.share.controller;

import file.share.model.FileShared;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Class controller of the actions of the user.
 * @author Gustavo Henrique
 */
public class Controller {
    
    private static Controller controller; // Statement controller.
    
    private final String ipServer = "127.0.0.1";
    
    private final int TIMEOUT = 3;
    private final int PORTSERVER = 55600;
    private final int LENGTHCODEPROTOCOL = 2;
    private final int LENGTHCODEPROTOCOLSERVER = 4;
    private final String TOKENSEPARATOR = "!=";
    private DatagramSocket serverSocket;
    
     /* Design Pattern Singleton */
    
    /**
     * The constructor is private for use the singleton
     */
    private Controller(){}
    
    /**
     * Return the instance of controller.
     * @return controller - An instance.
     */
    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }
    
    /**
     * Reset the controller.
     */
    public static void resetController(){
        controller = null;
    }
                                                /* End Singleton */
    /**
     * Register the file on server.
     * @param file - File to share.
     * @throws SocketException - If the communication could not be started.
     * @throws UnknownHostException - If the host is inaccessible.
     * @throws IOException - If could not send a packet.
     */
    public void registerFile(FileShared file) throws SocketException, UnknownHostException, IOException{
        DatagramPacket packet;
        byte[] dataByte = new byte[1024];
        String data;
        serverSocket = new DatagramSocket();
        
        data = "01" + file.getWay() + TOKENSEPARATOR + file.getName() + TOKENSEPARATOR + file.getExtension() + TOKENSEPARATOR 
                + file.getDate() + TOKENSEPARATOR + file.getSize() + "01";
        sendDatagramPacket(data, InetAddress.getByName(ipServer), PORTSERVER);
        System.out.println(receive());
    }
    
    /**
     * Send an data string for a client with ip and a port.
     * @param data - Data to send.
     * @param ip - Ip of destiny.
     * @param port - Port of destiny.
     */
    private void sendDatagramPacket(String data, InetAddress ip, int port) {
        try {
            DatagramPacket sendPacket;
            byte[] sendData = data.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
            serverSocket.send(sendPacket);
            System.out.println("Enviando: " + data);
        } catch (IOException ex) {
            System.out.println("ERROR: Não foi possível enviar um pacote.");
        }
    }
    
    /**
     * Return the reply of server.
     * @return string - Reply of server.
     * @throws java.io.IOException - If the communication could not be started.
     */
    public String receive() throws IOException {
        byte[] dataReceive;
        DatagramPacket packetReceive;
        String data;
        String codeProtocol, lastCodeProtocol;
        int indexLastCode;
        
        dataReceive = new byte[1024];
        packetReceive = new DatagramPacket(dataReceive, dataReceive.length);
        serverSocket.setSoTimeout(TIMEOUT*1000);
        serverSocket.receive(packetReceive);//To await the reply of server.
        data = new String(packetReceive.getData());// Convert the data bytes for char.
        System.out.println("Chegou: " + data);
        if (data.trim().isEmpty() || data.length() < LENGTHCODEPROTOCOL * 2) {
            return "";
        }
        codeProtocol = data.substring(0, LENGTHCODEPROTOCOLSERVER);// Get the code of protocol.
        data = data.substring(LENGTHCODEPROTOCOLSERVER);// Cut the first code.
        indexLastCode = data.lastIndexOf(codeProtocol);// Get the last index of code.
        lastCodeProtocol = data.substring(indexLastCode, indexLastCode + LENGTHCODEPROTOCOLSERVER);// Get the last code.
        data = data.substring(0, indexLastCode);// Now data have only the data
        if (!codeProtocol.equals(lastCodeProtocol)) {// If the protocol is wrong the data is discarded.
            data = null;
        }
        return data;
    }
}
