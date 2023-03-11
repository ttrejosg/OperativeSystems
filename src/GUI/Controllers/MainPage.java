package GUI.Controllers;
import GUI.GuiUtil;
import Models.PlanificationAlgorithm;
import Models.Process;
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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static javafx.stage.StageStyle.TRANSPARENT;

public class MainPage implements Initializable {
    //Components of Process Table
    @FXML
    private TableView<Process> processTable;
    @FXML
    private TableColumn<Process, String> colName;
    @FXML
    private TableColumn<Process, Integer> colRunTime, colPriority;

    //PlanificationAlgorithm selection
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Spinner<Integer> spinnerQuantum;
    @FXML
    private Text txtQuantum;

    //Components of the form
    @FXML
    private TextField inputName;
    @FXML
    private Spinner<Integer> inputRunTime, inputPriority;
    @FXML
    private Button bottonForm;
    @FXML
    private Text tittleForm;

    //Components of Window
    @FXML
    private AnchorPane scenePane;
    private Stage stageForm;
    private ObservableList<Process> processes;

    //Constructor
    public MainPage(){
    }

    //Method that is responsible for initializing the window
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Process table initialization
        this.colName.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        this.colRunTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("runTime"));
        this.colPriority.setCellValueFactory(new PropertyValueFactory<Process, Integer>("priority"));

        //PlanificationAlgorithm selection combo-box initialization
        String[] algorithms = {"Orden de llegada", "Primero el más corto", "Prioridad", "Round Robin"};
        this.comboBox.getItems().setAll(algorithms);

        //Form
        this.stageForm = new Stage();

        //Spinner quantum
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        this.spinnerQuantum.setValueFactory(valueFactory);
    }

    //Methods

    //Method that is responsible for initialize and show the create form
    private void initializeCreateForm(ActionEvent event) throws IOException {
        Scene formScene = GuiUtil.loadSceneFrom("form",this);
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        this.inputRunTime.setValueFactory(valueFactory1);
        this.inputPriority.setValueFactory(valueFactory2);
        this.tittleForm.setText("CREAR PROCESO");
        this.bottonForm.setText("Crear");
        this.bottonForm.setOnAction(this::createProcess);
        this.showFormStage(formScene);
    }

    //Method that is responsible for initialize and show the edit form
    private void initializeEditForm(ActionEvent event) throws IOException {
        Scene formScene = GuiUtil.loadSceneFrom("form",this);
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        this.inputRunTime.setValueFactory(valueFactory1);
        this.inputPriority.setValueFactory(valueFactory2);
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

    //Method that is responsible for show the stage
    private void showFormStage(Scene scene){
        this.stageForm.setScene(scene);
        this.stageForm.initStyle(TRANSPARENT);
        GuiUtil.setDragAndDropOnStage(this.stageForm);
        //Show
        this.stageForm.initModality(Modality.APPLICATION_MODAL);
        this.stageForm.showAndWait();
    }

    //Method that is responsible for create a process
    @FXML
    private void createProcess(ActionEvent event) {
        String name = inputName.getText();
        int runTime = inputRunTime.getValue();
        int priority = inputPriority.getValue();
        if(!name.isEmpty() && runTime != 0 && priority!=0){
            Process process = new Process(name, runTime, priority);
            ObservableList<Process> processes = this.processTable.getItems();
            processes.add(process);
            this.processTable.setItems(processes);
            this.stageForm.close();
        } else GuiUtil.showMessageDialog("Crear proceso", "Todos los campos deben estar llenos");
    }

    //Method that is responsible for edit a process
    @FXML
    private void editProcess(ActionEvent event) {
        String name = inputName.getText();
        int runTime = inputRunTime.getValue();
        int priority = inputPriority.getValue();
        if(!name.isEmpty() && runTime != 0 && priority!=0){
            Process selectedprocess = this.processTable.getSelectionModel().getSelectedItem();
            selectedprocess.setName(name);
            selectedprocess.setRunTime(runTime);
            selectedprocess.setPriority(priority);
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

    //Method that is responsible for show the new tab and calling the methods responsible for executing the
    //scheduling algorithm according to the selection
    @FXML
    private void execute(ActionEvent event) {
        String selectedItem = this.comboBox.getSelectionModel().getSelectedItem();
        ArrayList<Process> processes = new ArrayList<>(this.processTable.getItems());
        if(!processes.isEmpty()){
            if(selectedItem == null) GuiUtil.showMessageDialog("Ejecutar Algoritmo", "Debe seleccionar una de las opciones");
            else if(selectedItem.equals("Orden de llegada")){
                PlanificationAlgorithm.FCFS(processes);
                showAlgorithmPage(processes);
            } else if (selectedItem.equals("Primero el más corto")) {
                PlanificationAlgorithm.SJF(processes);
                showAlgorithmPage(processes);
            } else if (selectedItem.equals("Prioridad")) {
                PlanificationAlgorithm.priority(processes);
                showAlgorithmPage(processes);
            } else if (selectedItem.equals("Round Robin")) {
                int quantum = spinnerQuantum.getValue();
                showAlgorithmPage(PlanificationAlgorithm.roundRobin(processes, quantum));
            }
        }else GuiUtil.showMessageDialog("Ejecutar Algoritmo","No hay Procesos para ejecutar!");
    }

    //Method that is responsible for show the PlanificationAlgorithm Page
    private void showAlgorithmPage(ArrayList<Process> processes){
        AlgorithmPage algorithmPage = new AlgorithmPage();
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.setScene(GuiUtil.loadSceneFrom("AlgorithmPage", algorithmPage));
        GuiUtil.setDragAndDropOnStage(stage);

        //Calculate the averages
        double averageWaitTime = 0;
        int averageReturnTime = 0;
        for(Process currentProcess: processes){
            averageWaitTime += currentProcess.getWaitTime();
            averageReturnTime += currentProcess.getReturnTime();
        }
        averageWaitTime /= processes.size();
        averageReturnTime /= processes.size();

        //Set the information in the page
        algorithmPage.loadProcessInformation(this.processTable.getItems(), averageWaitTime, averageReturnTime);
        stage.show();
    }

    //Method that is responsible for showing the quantum selector if the Round Robin
    //algorithm is selected, or stop showing them if not
    @FXML
    void comboAction(ActionEvent event) {
        String selectedItem = this.comboBox.getSelectionModel().getSelectedItem();
        if(selectedItem!=null && selectedItem.equals("Round Robin")){
            this.spinnerQuantum.setVisible(true);
            this.txtQuantum.setVisible(true);
        }else{
            this.spinnerQuantum.setVisible(false);
            this.txtQuantum.setVisible(false);
        }
    }

    //Method that is responsible for close the application
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

    //Method that is responsible for loading the information of the processes in the table
    public void loadProcessTable(ObservableList<Process> processes){
        this.processTable.setItems(processes);
    }
}