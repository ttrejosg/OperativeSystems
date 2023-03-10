package GUI;
import Models.Process;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.stage.StageStyle.TRANSPARENT;

public class MainPage implements Initializable {
    //Components of Process Table
    @FXML
    private TableView<Process> processTable;
    @FXML
    private TableColumn<Process, String> colName;
    @FXML
    private TableColumn<Process, Integer> colRunTime;
    private ObservableList<Process> processes;

    //Algorithm selection combo-box
    @FXML
    private ComboBox<String> comboBox;

    //Components of the form
    @FXML
    private TextField inputName;
    @FXML
    private Spinner<Integer> inputRunTime;
    @FXML
    private Button bottonForm;
    @FXML
    private Text tittleForm;

    //Components of Window
    @FXML
    private AnchorPane scenePane;
    private Stage stageForm;

    //Constructor
    public MainPage(){
    }

    //Method that is responsible for initializing the window
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Process table initialization
        this.processes = FXCollections.observableArrayList();
        this.colName.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        this.colRunTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("runTime"));

        //Algorithm selection combo-box initialization
        String[] algorithms = {"Orden de llegada", "Primero el más corto", "Prioridad", "Round Robin"};
        this.comboBox.getItems().setAll(algorithms);
        //Form
        this.stageForm = new Stage();
    }

    //Methods

    //Method that is responsible for initialize and display the create form
    private void initializeCreateForm(ActionEvent event) throws IOException {
        Scene formScene = GuiUtil.loadSceneFrom("form",this);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        this.inputRunTime.setValueFactory(valueFactory);
        this.tittleForm.setText("CREAR PROCESO");
        this.bottonForm.setText("Crear");
        this.bottonForm.setOnAction(this::createProcess);
        this.showFormStage(formScene);
    }

    //Method that is responsible for initialize and display the edit form
    private void initializeEditForm(ActionEvent event) throws IOException {
        Scene formScene = GuiUtil.loadSceneFrom("form",this);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        this.inputRunTime.setValueFactory(valueFactory);
        Process selectedprocess = this.processTable.getSelectionModel().getSelectedItem();
        if(selectedprocess != null){
            this.tittleForm.setText("EDITAR PROCESO");
            this.bottonForm.setText("Editar");
            this.bottonForm.setOnAction(this::editProcess);
            this.inputName.setText(selectedprocess.getName());
            this.inputRunTime.getValueFactory().setValue(selectedprocess.getRunTime());
            this.showFormStage(formScene);
        }
        else {
            GuiUtil.showMessageDialog("Editar proceso", "Debe seleccionar un proceso a editar");
        }
    }

    //Method that is responsible for initialize the right form
    @FXML
    private void showForm(ActionEvent ev) throws IOException {
        String buttonText = ev.getSource().toString();
        if(buttonText.contains("Crear")) this.initializeCreateForm(ev);
        else if(buttonText.contains("Editar")) this.initializeEditForm(ev);
    }

    private void showFormStage(Scene scene){
        this.stageForm.setScene(scene);
        this.stageForm.initStyle(TRANSPARENT);
        GuiUtil.setDragAndDropOnStage(this.stageForm);
        //Show
        this.stageForm.initModality(Modality.APPLICATION_MODAL);
        this.stageForm.showAndWait();
    }

    //Method that is responsible for creating a process
    @FXML
    private void createProcess(ActionEvent event) {
        String name = inputName.getText();
        int runTime = inputRunTime.getValue();
        Process process;
        if(!name.isEmpty() && runTime != 0){
            process = new Process(name, runTime);
            this.processes = this.processTable.getItems();
            this.processes.add(process);
            this.processTable.setItems(this.processes);
            this.stageForm.close();
        } else GuiUtil.showMessageDialog("Crear proceso", "Todos los campos deben estar llenos");
    }

    //Method that is responsible for edit a process
    @FXML
    private void editProcess(ActionEvent event) {
        String name = inputName.getText();
        int runTime = inputRunTime.getValue();
        Process process;
        if(!name.isEmpty() && runTime != 0){
            Process selectedprocess = this.processTable.getSelectionModel().getSelectedItem();
            selectedprocess.setName(name);
            selectedprocess.setRunTime(runTime);
            this.processTable.refresh();
            this.stageForm.close();
        } else GuiUtil.showMessageDialog("Editar proceso", "Todos los campos deben estar llenos");
    }

    //Method that is responsible for deleting a process
    @FXML
    private void deleteProcess(ActionEvent event) {
        Process selectedprocess = this.processTable.getSelectionModel().getSelectedItem();
        if(selectedprocess != null){
            this.processes = this.processTable.getItems();
            this.processes.remove(selectedprocess);
            this.processTable.setItems(this.processes);
        } else GuiUtil.showMessageDialog("Eliminar proceso", "Debe seleccionar un proceso a eliminar");
    }

    //
    @FXML
    private void execute(ActionEvent event) {

    }

    //Method that is responsible for exiting the application
    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    // Method that is responsible for returning to the main tab, from the form
    @FXML
    private void cancelForm(ActionEvent event) {
        stageForm.close();
    }

}