import GUI.GuiUtil;
import GUI.Controllers.MainPage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class App extends Application{

    @Override
    public void start(Stage stage){
        MainPage mainPageCtr = new MainPage();
        Scene mainPageScene = GuiUtil.loadSceneFrom("MainPage",mainPageCtr);
        stage.setScene(mainPageScene);
        stage.initStyle(StageStyle.TRANSPARENT);
        GuiUtil.setDragAndDropOnStage(stage);
        stage.show();
    }

    public static void main(String[] args) {launch(args);}

}