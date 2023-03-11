package GUI.Controllers;

import GUI.GuiUtil;
import Models.Process;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AlgorithmPage implements Initializable{
    //Components of Process Table
    @FXML
    private TableView<Process> processTable;
    @FXML
    private TableColumn<Process, String> colName;
    @FXML
    private TableColumn<Process, Integer> colReturnTime, colRunTime, colWaitTime,colPriority;

    //Averages
    @FXML
    private TextField tfAverageReturnTime, tfAverageWaitTime;

    @FXML
    private StackedBarChart<Integer, String> ganttDiagram;

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
        this.colName.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        this.colRunTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("runTime"));
        this.colWaitTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("waitTime"));
        this.colReturnTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("returnTime"));
        this.colPriority.setCellValueFactory(new PropertyValueFactory<Process, Integer>("Priority"));
    }

    //Methods

    //Method that is responsible for loading the information of the processes in the table
    public void loadProcessInformation(ObservableList<Process> processes, double averageWaitTime, double averageReturnTime){
        this.processTable.setItems(processes);
        this.tfAverageWaitTime.setText(Double.toString(averageWaitTime));
        this.tfAverageReturnTime.setText(Double.toString(averageReturnTime));

        ArrayList<XYChart.Series<Integer, String>> serieses = new ArrayList<>();
        ArrayList<Process> processesArray = new ArrayList<>(processes);

        XYChart.Series<Integer, String> mainSeries = new XYChart.Series<>();

        for(Process currentProcess: processesArray){
            XYChart.Series<Integer, String> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(currentProcess.getReturnTime() - currentProcess.getWaitTime(), currentProcess.getName()));
            mainSeries.getData().add(new XYChart.Data<>(currentProcess.getWaitTime(), currentProcess.getName()));
            serieses.add(series);
        }

        serieses.add(0, mainSeries);
        ganttDiagram.getData().setAll(serieses);
        ganttDiagram.setLayoutX(1);

        for(Node n:ganttDiagram.lookupAll(".default-color1.chart-bar")) {
            n.setStyle("-fx-bar-fill: red;");
        }
    }

    //Method that is responsible for exiting the application
    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    // Method that is responsible for returning to the main page
    @FXML
    void goBack(ActionEvent event) {
        MainPage controller = new MainPage();
        Scene mainScene = GuiUtil.loadSceneFrom("MainPage", controller);
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.setScene(mainScene);
        GuiUtil.setDragAndDropOnStage(stage);
        controller.loadProcessTable(this.processTable.getItems());
        stage.show();
    }

}