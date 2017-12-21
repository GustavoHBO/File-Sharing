package file.share.server.controller;

import file.share.server.model.FileShared;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


/**
 * Class controller of the actions of the user.
 * @author Gustavo Henrique
 */
public class Controller {
    
    private static Controller controller; // Statement controller.

    private ArrayList<FileShared> listFile;
    
    private Map exemple;
    
    private final String TOKENSEPARATOR = "!=";
    
     /* Design Pattern Singleton */
    
    /**
     * The constructor is private for use the singleton
     */
    private Controller() throws IOException{
        listFile = new ArrayList<>();
        readAllData();
    }
    
    /**
     * Return the instance of controller.
     * @return controller - An instance.
     * @throws java.io.IOException - If the data don't was read.
     */
    public static Controller getInstance() throws IOException{
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
    
                                                /* Starting the methods of control */
    
    /**
     * Get the @param and register.
     * @param fileData - Data of file to save.
     * @return 0 - If the data wasn't saved, 1 - If the data was saved.
     * @throws java.io.IOException
     */
    public int registerFile(String fileData) throws IOException {

        FileShared fileShared;
        String[] fileSplited = fileData.split(TOKENSEPARATOR);
        
        if (listFile == null) {
            listFile = new ArrayList<>();
        }
        
        fileShared = new FileShared(fileSplited[0], fileSplited[1], fileSplited[2], fileSplited[3], fileSplited[4], Integer.parseInt(fileSplited[5].trim()));
        if(findFileHash(fileShared.getHash()) != null){
            return 0;
        } else {
            listFile.add(fileShared);
            saveAllData();
            return 1;
        }
    }
    
    /**
     * Remove a file using the @param for find the archive.
     * @param hash - Identifier of file.
     * @return 0 - If the file wasn't removed, 1 - If the file was removed.
     * @throws IOException - If the data was not be removed.
     */
    public int removeFile(int hash) throws IOException{
        saveAllData();
        return listFile.remove(findFileHash(hash)) ? 1 : 0;
    }
    
    /**
     * Get the files with identifier equal on the list.
     * @param id - Identifier archive, hash code or name.
     * @return null - If haven't files, data - Data formated to send.
     */
    public String getFilesId(String id){
        String data = "";
        FileShared fileShared;
        ArrayList<FileShared> list;
        Iterator<FileShared> it;

        if (id.trim().isEmpty()) {
            list = listFile;
        } else {
            list = findFilesName(id);
        }
        if (list == null) {
            return data;
        }

        it = list.iterator();

        while (it.hasNext()) {
            fileShared = it.next();
            data += fileShared.getIpHost();
            data += TOKENSEPARATOR;
            data += fileShared.getWay();
            data += TOKENSEPARATOR;
            data += fileShared.getName();
            data += TOKENSEPARATOR;
            data += fileShared.getExtension();
            data += TOKENSEPARATOR;
            data += fileShared.getDate();
            data += TOKENSEPARATOR;
            data += fileShared.getSize();
            if (it.hasNext()) {
                data += TOKENSEPARATOR;
            }
        }
        return data;
    }
    
    /**
     * Return the file with the code hash @param
     * @param hash - Code of the file.
     * @return null - If the file not exists, file - If the file exists.
     */
    public FileShared findFileHash(int hash){
        
        Iterator<FileShared> it = listFile.iterator();
        FileShared file;
        
        while(it.hasNext()){
            file = it.next();
            if(file.getHash() == hash){
                return file;
            }
        }
        return null;
    }
    
    /**
     * Return a list with files with @param same.
     * @param name - Name of the file.
     * @return null - If haven't anything file with this name, filesList - List of archives with the @param equals.
     */
    public ArrayList<FileShared> findFilesName(String name) {
        ArrayList<FileShared> filesList = null;
        Iterator<FileShared> it = listFile.iterator();
        FileShared file;

        while (it.hasNext()) {
            file = it.next();
            if (file.getName().toUpperCase().contains(name.toUpperCase()) || Integer.toString(file.getHash()).equals(name)) {
                if (filesList == null) {
                    filesList = new ArrayList();
                }
                filesList.add(file);
            }
        }
        return filesList;
    }
    
                                                /* Ending the methods of control */
    
                                                      /* Methods in storage */
    /**
     * Save the data in an archive .im
     * @return 0 - If the list is null or empty, 1 case the data was save.
     * @throws IOException - If the archive can't be create.
     */
    public int saveAllData() throws IOException {
        OutputStream os;
        OutputStreamWriter osw;
        BufferedWriter bw;
        Iterator<FileShared> it;
        FileShared fileShared;

        File file = new File("./file");
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File("./file/data.f");
        if (file.exists()) {// Delete the archive for that to save the new.
            file.delete();
        }
        file.createNewFile();
        
        if(listFile == null){
            return 0;
        } else if (listFile.isEmpty()){
            return 0;
        } else {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            it = listFile.iterator();
            while (it.hasNext()) {
                fileShared = it.next();
                bw.write(fileShared.getIpHost());
                bw.write(TOKENSEPARATOR);
                bw.write(fileShared.getWay());
                bw.write(TOKENSEPARATOR);
                bw.write(fileShared.getName());
                bw.write(TOKENSEPARATOR);
                bw.write(fileShared.getExtension());
                bw.write(TOKENSEPARATOR);
                bw.write(fileShared.getDate());
                bw.write(TOKENSEPARATOR);
                bw.write(Integer.toString(fileShared.getSize()));
                if(it.hasNext()){// If have next, then create new line for next product.
                    bw.newLine();
                }
            }
            bw.close();
            osw.close();
            os.close();
        }
        return 1;
    }
    
    /**
     * Read all data in storage and put in a list.
     * @throws IOException - If the file can't to be read.
     */
    public void readAllData() throws IOException {
        File file = new File("./file/data.f");
        if (file.exists()) {
            FileReader fileReader;
            fileReader = new FileReader(file);// FileReader it read the archive.
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(fileReader);//BufferedReader it store the data read in a buffer.
            FileShared fileShared;
            String dataLine;
            String[] dataLineSplited;

            if (listFile == null) {
                listFile = new ArrayList<>();
            }

            while (bufferedReader.ready()) {
                dataLine = bufferedReader.readLine();
                dataLineSplited = dataLine.split(TOKENSEPARATOR);
                fileShared = new FileShared(dataLineSplited[0], dataLineSplited[1], dataLineSplited[2], dataLineSplited[3], dataLineSplited[4], Integer.parseInt(dataLineSplited[5].trim()));
                listFile.add(fileShared);
            }
            bufferedReader.close();
            fileReader.close();
        }
    }
}
