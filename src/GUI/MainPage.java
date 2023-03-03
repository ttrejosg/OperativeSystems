package GUI;
import Models.Process;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainPage implements Initializable {
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TableView<Process> processTable;
    private Alert alert;
    private Stage stage; Scene scene;
    public double x, y;

    public MainPage(){
        this.alert = new Alert(Alert.AlertType.INFORMATION);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] algorithms = {"Orden de llegada", "Primero el más corto", "Prioridad", "Round Robin"};
        this.comboBox.getItems().addAll(algorithms);
    }

    @FXML
    void deleteProcess(ActionEvent event) {

    }

    @FXML
    void execute(ActionEvent event) {

    }

    @FXML
    void showCreateForm(ActionEvent event) {

    }

    @FXML
    void showEditForm(ActionEvent event) {

    }

    public void showMessageDialog(String tittle,String text){
        alert.setTitle(tittle);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void changeToBattleView(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Battle.fxml"));
        Parent root = loader.load();
        //Battle battleView= loader.getController();
        //battleView.setInformation(player1, player2, this.personajes, this.armas);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        //root.setOnMousePressed(event -> {
        //    battleView.x = event.getSceneX();
        //    battleView.y = event.getSceneY();
        //});
        //root.setOnMouseDragged(event -> {
        //    stage.setX(event.getScreenX() - battleView.x);
        //    stage.setY(event.getScreenY() - battleView.y);
        //});
        stage.show();
    }
}



