package GUI;

import Models.Process;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlgorithmPage implements Initializable{
    //Components of Process Table
    @FXML
    private TableView<Process> processTable;
    @FXML
    private TableColumn<Process, String> colName;
    @FXML
    private TableColumn<Process, Integer> colReturnTime, colRunTime, colWaitTime;
    private ObservableList<Process> processes;

    //Averages
    @FXML
    private TextField tfAverageReturnTime, tfAverageWaitTime;

    //Components of Window
    @FXML
    private AnchorPane scenePane;

    //Constructor
    public AlgorithmPage() {

    }

    //Method that is responsible for initializing the window
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Process table initialization
        this.processes = FXCollections.observableArrayList();
        this.colName.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        this.colRunTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("runTime"));
        this.colWaitTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("waitTime"));
        this.colReturnTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("returnTime"));
    }

    //Methods

    //Method that is responsible for exiting the application
    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    // Method that is responsible for returning to the main page
    @FXML
    void goBack(ActionEvent event) {

    }

}

