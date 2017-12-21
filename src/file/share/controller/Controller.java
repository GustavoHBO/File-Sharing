package file.share.controller;

import file.share.model.FileShared;
import file.share.model.FileSharedProperty;
import file.share.util.RemoteInterface;
import file.share.util.RemoteInterfaceImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Iterator;

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
    
    private ArrayList<FileShared> listFiles;
    
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
     * @return 0 - If the file was not register, 1 - If the file was register.
     * @throws SocketTimeoutException - If the answer don't coming.
     * @throws SocketException - If the communication could not be started.
     * @throws UnknownHostException - If the host is inaccessible.
     * @throws IOException - If could not send a packet.
     */
    public int registerFile(FileShared file) throws SocketTimeoutException, SocketException, UnknownHostException, IOException{
        DatagramPacket packet;
        byte[] dataByte = new byte[1024];
        String data;
        serverSocket = new DatagramSocket();
        
        data = "01" + file.getIpHost()+ TOKENSEPARATOR + file.getWay() + TOKENSEPARATOR + file.getName() + TOKENSEPARATOR + file.getExtension() + TOKENSEPARATOR 
                + file.getDate() + TOKENSEPARATOR + file.getSize() + "01";
        sendDatagramPacket(data, InetAddress.getByName(ipServer), PORTSERVER);
        return Integer.parseInt(receive());
    }
    
    /**
     * Remove the file on server with @param.
     * @param hash
     * @return 0 - If the file wasn't removed, 1 - If the file was removed.
     * @throws SocketTimeoutException - If the answer don't coming.
     * @throws UnknownHostException - If the server is Unknown.
     * @throws IOException - If the communication could not be performed.
     */
    public int removeFile(String hash) throws SocketTimeoutException, UnknownHostException, IOException{
        String data;
        listFiles = new ArrayList<>();
        data = "02" + hash + "02";
        sendDatagramPacket(data, InetAddress.getByName(ipServer), PORTSERVER);
        return Integer.parseInt(receive());
    }
    
    /**
     * Get the files in server using the identifier received.
     *
     * @param identifier - Identifier the file.
     * @return 0 - If not exist anything file, 1 - If exist at least one.
     * @throws SocketTimeoutException - If the answer don't coming.
     * @throws UnknownHostException - If the server is Unknown.
     * @throws IOException - If the communication could not be performed.
     */
    public int findFileServer(String identifier) throws SocketTimeoutException, UnknownHostException, IOException {
        String data, received;
        data = "03" + identifier + "03";
        sendDatagramPacket(data, InetAddress.getByName(ipServer), PORTSERVER);
        received = receive();
        if (received.trim().isEmpty()) {
            listFiles = new ArrayList<>();
            return 0;
        } else {
            String[] stringSplited = received.split(TOKENSEPARATOR);
            FileShared fileShared;
            listFiles = new ArrayList<>();
            System.out.println(stringSplited.length);
            for(int i = 0; i < stringSplited.length; i+=6){
                fileShared = new FileShared(stringSplited[i], stringSplited[i+1], stringSplited[i+2], stringSplited[i+3], stringSplited[i+4], Integer.parseInt(stringSplited[i+5].trim()));
                listFiles.add(fileShared);
            }
            return 1;
        }
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
            serverSocket = new DatagramSocket();
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
     * @throws java.net.SocketTimeoutException - If the answer don't coming.
     * @throws java.io.IOException - If the communication could not be started.
     */
    private String receive() throws SocketTimeoutException, IOException {
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
    
    /**
     * Start the Server for connections RMI.
     * @throws RemoteException - If some problem occur.
     */
    public void upServer() throws RemoteException{
        new RemoteInterfaceImpl().start();
    }
    
    /**
     * Get the file using the FileShareProperty.
     * @param fileProperty - File to get.
     * @return 1 - The file is download.
     * @throws NotBoundException - If occur problems on connections.
     * @throws MalformedURLException - If occur problems on connections.
     * @throws RemoteException - If occur problems on connections.
     * @throws IOException - If occur problems on connections.
     */
    public int getFile(FileSharedProperty fileProperty) throws NotBoundException, MalformedURLException, RemoteException, IOException {
        Registry reg = LocateRegistry.getRegistry(fileProperty.getIpHost(), 55601);
        RemoteInterface rm = (RemoteInterface) reg.lookup("Remote");
        String fileString = rm.getFile(fileProperty.getWay().get());

        OutputStream os;
        OutputStreamWriter osw;
        BufferedWriter bw;
        Iterator<file.share.server.model.FileShared> it;
        file.share.server.model.FileShared fileShared;

        File file = new File("./Downloads/");
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File("./Downloads/" + fileProperty.getName().get() + fileProperty.getExtension().get());
        if (file.exists()) {// Delete the archive for that to save the new.
            file.delete();
        }
        file.createNewFile();

        os = new FileOutputStream(file);
        osw = new OutputStreamWriter(os);
        bw = new BufferedWriter(osw);
        bw.write(fileString);
        bw.close();
        osw.close();
        os.close();

        return 1;
    }

    /**
     * @return the listFiles
     */
    public ArrayList<FileShared> getListFiles() {
        return listFiles;
    }
}
