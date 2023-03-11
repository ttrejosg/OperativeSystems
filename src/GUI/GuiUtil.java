package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GuiUtil {

    public static double mouseX, mouseY;
    private static Alert alert = new Alert(Alert.AlertType.INFORMATION);


    public static Scene loadSceneFrom(String fxmlName){
        try{
            FXMLLoader loader = new FXMLLoader(GuiUtil.class.getResource("./FXML/" + fxmlName + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            return scene;
        }catch (Exception e){
            System.out.println("Scene Loading Error");
            e.printStackTrace();
            return null;
        }
    }

    public static Scene loadSceneFrom(String fxmlName,Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(GuiUtil.class.getResource("./FXML/" + fxmlName + ".fxml"));
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            return scene;
        } catch (Exception e) {
            System.out.println("Scene Loading Error");
            e.printStackTrace();
            return null;
        }
    }

    public static Parent loadComponentFrom(String fxmlName){


        return null;
    }

    public static void setDragAndDropOnStage(Stage stage){
        Parent root = stage.getScene().getRoot();
        root.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - mouseX);
            stage.setY(event.getScreenY() - mouseY);
        });
    }

    /**
     * Method that is responsible for displaying a dialog window
     * @param tittle dialog window title
     * @param text dialog window message
     */
    public static void showMessageDialog(String tittle,String text){
        alert.setTitle(tittle);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
