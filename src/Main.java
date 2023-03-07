import GUI.GuiUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class Main extends Application{

    @Override
    public void start(Stage stage){
        Scene mainPageScene = GuiUtils.loadSceneFrom("MainPage");
        stage.setScene(mainPageScene);
        stage.initStyle(StageStyle.TRANSPARENT);
        GuiUtils.setDragAndDropOnStage(stage);
        stage.show();
    }

    public static void main(String[] args) {launch(args);}

}