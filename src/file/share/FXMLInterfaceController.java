/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.share;

import file.share.controller.Controller;
import file.share.model.FileShared;
import file.share.model.FileSharedProperty;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TableView<FileSharedProperty> tableViewFileDownload;
    
                         /* TableView */
    private ObservableList<FileSharedProperty> observableListDownload;
    
                         /* TableView */
    @FXML
    private TableColumn<FileSharedProperty, String> tableColumnNameDownload;
    @FXML
    private TableColumn<FileSharedProperty, String> tableColumnDateDownload;
    @FXML
    private TableColumn<FileSharedProperty, String> tableColumnSizeDownload;
    @FXML
    private TableColumn<FileSharedProperty, String> tableColumnExtensionDownload;
    
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
        try {
            controller.upServer();
        } catch (RemoteException ex) {
            System.out.println("Error: Não foi possível iniciar o servidor!");
        }
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
    private void eventButtonFindFileDownload() {
        try {
            if (controller.findFileServer(textFieldFindFileDownload.getText().trim()) == 0) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("Arquivo não encontrado.");
                alertErro.setContentText("O arquivo que você procura não está\nsendo compartilhado.");
                alertErro.showAndWait();
            }
            loadTableFile();
        } catch (SocketTimeoutException ex) {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Error");
            alertErro.setHeaderText("O servidor não respondeu à solicitação,\ntente novamente.");
            alertErro.setContentText("ERROR: SocketTimeoutException");
            alertErro.showAndWait();
        } catch (UnknownHostException ex) {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Error");
            alertErro.setHeaderText("Host não reconhecido.");
            alertErro.setContentText("ERROR: UnknownHostException");
            alertErro.showAndWait();
        } catch (IOException ex) {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Error");
            alertErro.setHeaderText("Ocorreu um erro, tente novamente.");
            alertErro.setContentText("ERROR: IOException");
            alertErro.showAndWait();
        }
    }
    
    /**
     * Event Button Download the file selected.
     */
    @FXML
    private void eventButtonDownloadFileDownload() {
        
        FileSharedProperty fileProperty = tableViewFileDownload.getSelectionModel().getSelectedItem();
        if (fileProperty != null) {
            try {
                if (controller.getFile(fileProperty) == 0) {
                    Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Error");
                    alertErro.setHeaderText("A transferência não foi completada");
                    alertErro.setContentText("Error: NotBoundException");
                    alertErro.showAndWait();
                } else {
                    Alert alertDialog = new Alert(Alert.AlertType.INFORMATION);
                    alertDialog.setTitle("Informação");
                    alertDialog.setHeaderText("Arquivo baixado.");
                    alertDialog.setContentText("Verifique a sua pasta download.");
                    alertDialog.showAndWait();
                    loadTableFile();
                }
            } catch (NotBoundException ex) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("A transferência não foi completada");
                alertErro.setContentText("Error: NotBoundException");
                alertErro.showAndWait();
            } catch (RemoteException ex) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("A transferência não foi completada");
                alertErro.setContentText("Error: RemoteException");
                alertErro.showAndWait();
            } catch (IOException ex) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("A transferência não foi completada");
                alertErro.setContentText("Error: IOException");
                alertErro.showAndWait();
            }
        }
    }
    
    /**
     * Event Button remove the file selected on host.
     */
    @FXML
    private void eventButtonRemoveFileDownload(){
        FileSharedProperty fileProperty = tableViewFileDownload.getSelectionModel().getSelectedItem();
        if(fileProperty != null){
            try {
                if(controller.removeFile(fileProperty.getHash().get()) == 1){
                    Alert alertDialog = new Alert(Alert.AlertType.INFORMATION);
                    alertDialog.setTitle("Informação");
                    alertDialog.setHeaderText("Arquivo removido.");
                    alertDialog.setContentText("O arquivo não será mais compartilhado!");
                    alertDialog.showAndWait();
                    loadTableFile();
                } else {
                    Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Error");
                    alertErro.setHeaderText("Arquivo não removido.");
                    alertErro.setContentText("O arquivo não pôde ser removido.");
                    alertErro.showAndWait();
                }
            } catch (SocketTimeoutException ex) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("O servidor não respondeu à solicitação, tente novamente.");
                alertErro.setContentText("ERROR: SocketTimeoutException");
                alertErro.showAndWait();
            } catch (UnknownHostException ex) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("Host não reconhecido.");
                alertErro.setContentText("ERROR: UnknownHostException");
                alertErro.showAndWait();
            } catch (IOException ex) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("Ocorreu um erro, tente novamente.");
                alertErro.setContentText("ERROR: IOException");
                alertErro.showAndWait();
            }
        }
    }
    
    /**
     * Event Button turn the pane in invisible.
     */
    @FXML
    private void eventButtonBackDownload(){
        paneDownloadFile.setVisible(false);
    }
            
                                    /* TableView */
    /**
     * Load the files on table view on pane Download.
     */
    private void loadTableFile(){
        Iterator<FileShared> it;
        FileShared fileShared;
        it = controller.getListFiles().iterator();
        
        observableListDownload = FXCollections.observableArrayList();
        while(it.hasNext()){
            fileShared = it.next();
            System.out.println("Colocando o arquivo: " + fileShared.getHash());
            if(it.hasNext())
                System.out.println("Tem ainda");
            observableListDownload.add(new FileSharedProperty(fileShared.getIpHost(), Integer.toString(fileShared.getHash()), fileShared.getWay(), 
            fileShared.getName(), fileShared.getExtension(), fileShared.getDate(), Integer.toString(fileShared.getSize())));
        }
        tableColumnNameDownload.setCellValueFactory(cellData -> cellData.getValue().getName());
        tableColumnExtensionDownload.setCellValueFactory(cellData -> cellData.getValue().getExtension());
        tableColumnDateDownload.setCellValueFactory(cellData -> cellData.getValue().getDate());
        tableColumnSizeDownload.setCellValueFactory(cellData -> cellData.getValue().getSize());
        tableViewFileDownload.setItems(observableListDownload);
    }
    
                                    /* Share */
    
    /**
     * Event Button select the file chose.
     */
    @FXML
    private void eventButtonChooseFileShare() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo para Compartilhamento");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter(".txt", "*.txt"), new ExtensionFilter("Todos arquivos", "*"));
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
    private void eventButtonShareFileShare() {
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
            String myIp = "";
            Enumeration e;
            try {
                e = NetworkInterface.getNetworkInterfaces();
                while (e.hasMoreElements()) {
                    NetworkInterface i = (NetworkInterface) e.nextElement();
                    Enumeration ds = i.getInetAddresses();
                    while (ds.hasMoreElements()) {
                        InetAddress myself = (InetAddress) ds.nextElement();
                        if (!myself.isLoopbackAddress() && myself.isSiteLocalAddress()) {
                            myIp = myself.getHostAddress();
                        }
                    }
                }

                fileShared = new FileShared(myIp, selectedFile.getAbsolutePath(), name, extension, date, (int) selectedFile.length());
                try {
                    if (controller.registerFile(fileShared) == 1) {
                        Alert alertDialog = new Alert(Alert.AlertType.INFORMATION);
                        alertDialog.setTitle("Informação");
                        alertDialog.setHeaderText("Arquivo compartilhado.");
                        alertDialog.setContentText("Arquivo pronto para ser baixado!");
                        alertDialog.showAndWait();
                    } else {
                        Alert alertErro = new Alert(Alert.AlertType.ERROR);
                        alertErro.setTitle("Error");
                        alertErro.setHeaderText("O arquivo não foi adicionado.");
                        alertErro.setContentText("O arquivo selecionado não pôde ser compartilhado.");
                        alertErro.showAndWait();
                    }
                } catch (SocketTimeoutException ex) {
                    Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Error");
                    alertErro.setHeaderText("O servidor não respondeu à solicitação, tente novamente.");
                    alertErro.setContentText("ERROR: SocketTimeoutException");
                    alertErro.showAndWait();
                } catch (UnknownHostException ex) {
                    Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Error");
                    alertErro.setHeaderText("Host não reconhecido.");
                    alertErro.setContentText("ERROR: UnknownHostException");
                    alertErro.showAndWait();
                } catch (IOException ex) {
                    Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Error");
                    alertErro.setHeaderText("Ocorreu um erro, tente novamente.");
                    alertErro.setContentText("ERROR: IOException");
                    alertErro.showAndWait();
                }
            } catch (SocketException ex) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Error");
                alertErro.setHeaderText("Não foi possível compartilhar o arquivo.");
                alertErro.setContentText("ERROR: SocketException");
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
