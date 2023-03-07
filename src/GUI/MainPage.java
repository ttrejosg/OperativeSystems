package GUI;
import Models.Process;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    private Stage stageForm; double x, y;
    private Alert alert;

    //Constructor
    public MainPage(){
        this.alert = new Alert(Alert.AlertType.INFORMATION);
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
    }

    //Methods

    //Method that is responsible for displaying and initializing the create form
    public void initializeCreateForm(ActionEvent event) throws IOException {
        tittleForm.setText("CREAR PROCESO");
        bottonForm.setText("Crear");
        bottonForm.setOnAction(this::createProcess);
    }

    //Method that is responsible for displaying and initializing the edit form
    public void initializeEditForm(ActionEvent event, Process process) throws IOException {
        Process selectedprocess = process;
        tittleForm.setText("EDITAR PROCESO");
        bottonForm.setText("Editar");
        bottonForm.setOnAction(this::editProcess);
        this.inputName.setText(selectedprocess.getName());
        this.inputRunTime.getValueFactory().setValue(selectedprocess.getRunTime());
    }

    //Method that is responsible for displaying and initializing the form
    @FXML
    void showForm(ActionEvent ev) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            boolean canContinue = true;

            //Init Spinner
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
            inputRunTime.setValueFactory(valueFactory);

            if(ev.getSource().toString().contains("Crear")) this.initializeCreateForm(ev);
            else {
                Process selectedprocess = this.processTable.getSelectionModel().getSelectedItem();
                if(selectedprocess != null) this.initializeEditForm(ev, selectedprocess);
                else {
                    this.showMessageDialog("Editar proceso", "Debe seleccionar un proceso a editar");
                    canContinue = false;
                }
            }

            if(canContinue){
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                this.stageForm = new Stage();
                stageForm.setScene(scene);
                stageForm.initStyle(StageStyle.TRANSPARENT);

                root.setOnMousePressed(event -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {

                    stageForm.setX(event.getScreenX() - x);
                    stageForm.setY(event.getScreenY() - y);

                });

                //Show
                stageForm.initModality(Modality.APPLICATION_MODAL);
                stageForm.showAndWait();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Method that is responsible for creating a process
    void createProcess(ActionEvent event) {
        String name = inputName.getText();
        int runTime = inputRunTime.getValue();
        Process process;
        if(!name.isEmpty() && runTime != 0){
            process = new Process(name, runTime);
            this.processes = this.processTable.getItems();
            this.processes.add(process);
            this.processTable.setItems(this.processes);
            this.stageForm.close();
        } else this.showMessageDialog("Crear proceso", "Todos los campos deben estar llenos");
    }

    //Method that is responsible for edit a process
    void editProcess(ActionEvent event) {
        String name = inputName.getText();
        int runTime = inputRunTime.getValue();
        Process process;
        if(!name.isEmpty() && runTime != 0){
            Process selectedprocess = this.processTable.getSelectionModel().getSelectedItem();
            selectedprocess.setName(name);
            selectedprocess.setRunTime(runTime);
            this.processTable.refresh();
            this.stageForm.close();
        } else this.showMessageDialog("Editar proceso", "Todos los campos deben estar llenos");

    }

    //Method that is responsible for deleting a process
    @FXML
    void deleteProcess(ActionEvent event) {
        Process selectedprocess = this.processTable.getSelectionModel().getSelectedItem();
        if(selectedprocess != null){
            this.processes = this.processTable.getItems();
            this.processes.remove(selectedprocess);
            this.processTable.setItems(this.processes);
        } else this.showMessageDialog("Eliminar proceso", "Debe seleccionar un proceso a eliminar");
    }

    //
    @FXML
    void execute(ActionEvent event) {

    }

    //Method that is responsible for exiting the application
    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    // Method that is responsible for returning to the main tab, from the form
    @FXML
    void cancelForm(ActionEvent event) {
        stageForm.close();
    }

    /**
     * Method that is responsible for displaying a dialog window
     * @param tittle dialog window title
     * @param text dialog window message
     */
    public void showMessageDialog(String tittle,String text){
        alert.setTitle(tittle);
        alert.setContentText(text);
        alert.showAndWait();
    }
}