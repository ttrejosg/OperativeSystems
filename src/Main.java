import GUI.GuiUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class Main extends Application{

    @Override
    public void start(Stage stage){
        Scene mainPageScene = GuiUtil.loadSceneFrom("MainPage");
        stage.setScene(mainPageScene);
        stage.initStyle(StageStyle.TRANSPARENT);
        GuiUtil.setDragAndDropOnStage(stage);
        stage.show();
    }

    public static void main(String[] args) {launch(args);}

}