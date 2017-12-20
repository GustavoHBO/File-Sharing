/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.share;

import file.share.controller.Controller;
import file.share.model.FileShared;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
    
    /* Statement the controller */
    private Controller controller;
    
    File selectedFile;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = Controller.getInstance();
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
    private void eventButtonChooseFileShare() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo para Compartilhamento");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Arquivos TXT", "*.txt"));
        selectedFile = fileChooser.showOpenDialog(null);
        
        if (selectedFile != null) {

            Alert alertDialog = new Alert(Alert.AlertType.INFORMATION);
            alertDialog.setTitle("Informação");
            alertDialog.setHeaderText("Arquivo selecionado.");
            alertDialog.setContentText("Arquivo pronto para ser compartilhado!");
            alertDialog.showAndWait();
            
        } else {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Error");
            alertErro.setHeaderText("Não foi selecionado nenhum arquivo.");
            alertErro.setContentText("Selecione um arquivo para poder compartilhar!");
            alertErro.showAndWait();
        }
    }
    
    /**
     * Event Button share the file chose.
     */
    @FXML
    private void eventButtonShareFileShare(){
        FileShared fileShared;
        if (selectedFile != null) {

            /* Obtain the name and extension */
            String extension;
            String name = selectedFile.getName();
            extension = name.substring(name.lastIndexOf("."));
            name = name.substring(0, name.lastIndexOf("."));
            
            /* Obtain the date */
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String date = formatDate.format(new Date());
            
            /*Creating the file*/
            fileShared = new FileShared(selectedFile.getAbsolutePath(), name, extension, date, (int)selectedFile.length());
            System.out.println((int)selectedFile.length());
            try {
                controller.registerFile(fileShared);
            } catch (UnknownHostException ex) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("Host não reconhecido.");
                alertErro.setContentText("UnknownHostException");
                alertErro.showAndWait();
            } catch (IOException ex) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("Ocorreu um erro, reinicio a aplicação.");
                alertErro.setContentText("IOException");
                alertErro.showAndWait();
            }
            
        } else {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Error");
            alertErro.setHeaderText("Não foi selecionado nenhum arquivo.");
            alertErro.setContentText("Selecione um arquivo para poder compartilhar!");
            alertErro.showAndWait();
        }
    }
    
    /**
     * Event Button share the file chose.
     */
    @FXML
    private void eventButtonBackFileShare(){
        paneShareFile.setVisible(false);
    }
}
