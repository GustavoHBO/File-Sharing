/*
 * Copyright (C) 2017 gustavo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package file.share.server;

import file.share.server.controller.Controller;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author gustavo
 */
public class Server {
    
    private Controller controller;
    private DatagramSocket serverSocket;
    private final int PORTSERVER = 55600;
    private final int LENGTHCODEPROTOCOL = 2;
    
    /**
     * Start the server, and register him on cloud.
     * @throws SocketException - If the ip or port is in user.
     * @throws UnknownHostException  - Case the host be unknown.
     * @throws IOException  - If the socket can't be created.
     */
    private void startServerUDP() throws SocketException, UnknownHostException, IOException {
        controller = Controller.getInstance();
        serverSocket = new DatagramSocket(PORTSERVER); // Start the ServerSocket on port selected.
        byte[] receiveData = new byte[1024]; // Array of the bytes to storage the datagram received.
        
        while (true) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                
                Runnable run;
                run = new Runnable() {
                    @Override
                    public void run() {
                        String data = new String(receivePacket.getData());
                        identifyAction(data);
                    }
                    
                    private void identifyAction(String data) {
                        InetAddress ipSender = receivePacket.getAddress();
                        int portSender = receivePacket.getPort();
                        String initCode = data.substring(0, LENGTHCODEPROTOCOL);
                        String endCode;
                        int lastCodeIndex = data.lastIndexOf(initCode);
                        if (lastCodeIndex == 0) {
                            return;
                        }
                        endCode = data.substring(lastCodeIndex, lastCodeIndex + LENGTHCODEPROTOCOL);
                        if (initCode.equals(endCode)) {
                            String[] replySplited;
                            data = data.substring(LENGTHCODEPROTOCOL, lastCodeIndex);
                            System.out.println("Recebido: " + data);
                            switch (initCode) {
                                case "00":// Test the connection with it.
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                };
                new Thread(run).start();
            } catch (IOException ex) {
                System.out.println("Não foi possível iniciar o servidor.");
            }
        }
    }
}
