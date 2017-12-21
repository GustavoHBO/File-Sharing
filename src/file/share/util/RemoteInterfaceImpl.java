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
package file.share.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */
public class RemoteInterfaceImpl extends UnicastRemoteObject implements RemoteInterface {

    public RemoteInterfaceImpl() throws RemoteException {
        super();
    }
    
    @Override
    public String getFile(String way) throws RemoteException, FileNotFoundException, IOException {
        String fileString = "";
        File file = new File(way);
        if (file.exists()) {
            FileReader fileReader;
            fileReader = new FileReader(file);// FileReader it read the archive.
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(fileReader);//BufferedReader it store the data read in a buffer.

            while (bufferedReader.ready()) {
                fileString += bufferedReader.readLine() + "\n";
            }
            System.out.println(fileString);
            bufferedReader.close();
            fileReader.close();
        }
        return fileString;
    }
    
    public void start() {
        try {
            Registry reg = LocateRegistry.createRegistry(55601);
            reg.rebind("Remote", new RemoteInterfaceImpl());
            System.out.println("Server start");
        } catch (RemoteException ex) {
            Logger.getLogger(RemoteInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
