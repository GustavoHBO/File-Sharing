/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.share;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author gustavo
 */
public class FXMLInterfaceController implements Initializable {
    
    /* Statement Pane */
    @FXML
    private Pane panePrincipal;
    @FXML
    private Pane paneShareFile;
    @FXML
    private Pane paneDownloadFile;
    
    /* Statement Buttons */
    
                    /* Pane Principal */
    @FXML
    private Button buttonFindFilePrincipal;
    @FXML
    private Button buttonShareFilePrincipal;
    @FXML
    private Button buttonExitPrincipal;
    
                    /* Pane Download File */
                         /* Buttons */
    @FXML
    private Button buttonFindFileDownload;
    @FXML
    private Button buttonDownloadFileDownload;
    @FXML
    private Button buttonRemoveFileDownload;
    @FXML
    private Button buttonBackFileDownload;
    
                         /* TextField */
    @FXML
    private TextField textFieldFindFileDownload;
    
                         /* TableView */
    @FXML
    private TableView tableViewFileDownload;
    
                         /* TableView */
    @FXML
    private TableColumn tableColumnNameDownload;
    @FXML
    private TableColumn tableColumnDateDownload;
    @FXML
    private TableColumn tableColumnSizeDownload;
    @FXML
    private TableColumn tableColumnExtensionDownload;
    
                    /* Pane Share File */
    @FXML
    private Button buttonChooseFileShare;
    @FXML
    private Button buttonShareShare;
    @FXML
    private Button buttonBackShare;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
                                /* Method's Events */
                                    /* Principal */
    
    /**
     * Event Button find file in pane principal.
     */
    @FXML
    private void eventButtonFindFilePrincipal(){
        paneDownloadFile.setVisible(true);
    }
    
    /**
     * Event Button share file.
     */
    @FXML
    private void eventButtonShareFilePrincipal(){
        paneShareFile.setVisible(true);
    }
    
    /**
     * Event Button exit file.
     */
    @FXML
    private void eventButtonExitFilePrincipal(){
        System.exit(0);
    }
    
                                    /* Download */
    
    /**
     * Event Button find the file with name or hash in text field.
     */
    @FXML
    private void eventButtonFindFileDownload(){
        
    }
    
    /**
     * Event Button Download the file selected.
     */
    @FXML
    private void eventButtonDownloadFileDownload(){
        
    }
    
    /**
     * Event Button remove the file selected on host.
     */
    @FXML
    private void eventButtonRemoveFileDownload(){
        
    }
    
    /**
     * Event Button turn the pane in invisible.
     */
    @FXML
    private void eventButtonBackDownload(){
        paneDownloadFile.setVisible(false);
    }
            
                                    /* Share */
    
    /**
     * Event Button select the file chose.
     */
    @FXML
    private void eventButtonChooseFileShare(){
        
    }
    
    /**
     * Event Button share the file chose.
     */
    @FXML
    private void eventButtonShareFileShare(){
        
    }
    
    /**
     * Event Button share the file chose.
     */
    @FXML
    private void eventButtonBackFileShare(){
        paneShareFile.setVisible(false);
    }
}
