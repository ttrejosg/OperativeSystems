import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class Main extends Application{
    private double mouseX, mouseY;

    @Override
    public void start(Stage stage){
        Scene mainPageScene = this.loadScene("GUI/MainPage.fxml");
        stage.setScene(mainPageScene);
        stage.initStyle(StageStyle.TRANSPARENT);
        this.setMouseEvents(stage);
        stage.show();
    }

    private Scene loadScene(String fxmlPath){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
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

    private void setMouseEvents(Stage stage){
        Parent root = stage.getScene().getRoot();
        root.setOnMousePressed(event -> {
            this.mouseX = event.getSceneX();
            this.mouseY = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - this.mouseX);
            stage.setY(event.getScreenY() - this.mouseY);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}