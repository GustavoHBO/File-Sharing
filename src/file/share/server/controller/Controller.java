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


/**
 * Class controller of the actions of the user.
 * @author Gustavo Henrique
 */
public class Controller {
    
    private static Controller controller; // Statement controller.

    private ArrayList<FileShared> listFile;
    
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
                bw.write(fileShared.getWay());
                bw.write(TOKENSEPARATOR);
                bw.write(fileShared.getName());
                bw.write(TOKENSEPARATOR);
                bw.write(fileShared.getExtension());
                bw.write(TOKENSEPARATOR);
                bw.write(fileShared.getDate());
                bw.write(TOKENSEPARATOR);
                bw.write(fileShared.getSize());
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
        File file = new File("./backup/deposit/product/data.im");
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
                fileShared = new FileShared(dataLineSplited[0], dataLineSplited[1], dataLineSplited[2], dataLineSplited[3], dataLineSplited[4]);
                listFile.add(fileShared);
            }
            bufferedReader.close();
            fileReader.close();
        }
    }
}
