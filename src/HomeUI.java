import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomeUI extends Application {
    Stage homeStage = new Stage();
    public static void main(String[]args){
        User.main(args);
        launch(args);
    }
    @Override
    public void start(Stage Primarystage){
        homeStage = Primarystage;
        homeInterface();
    }

    public void homeInterface(){
        //Primary UI Content Holder
        GridPane mainHolder = new GridPane();


        //Home Primary Action Event Holder


        //Home User information Display


    }


}
