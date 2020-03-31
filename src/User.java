import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class User extends Application {
    private MongoClient mongo = new MongoClient("Localhost", 27017);
    private DB LibraryProject = mongo.getDB("LibraryProject");
    private DBCollection UsersCollection = LibraryProject.getCollection("Users");

    Stage UserFloorStage;

    //Instances of the class
    private double userID;
    private static String[] Username = new String[2];

    //Login UI scene
    public void logInUi(){
        Scene loggingIn;

        GridPane logInLayout = new GridPane();
        logInLayout.setPadding(new Insets(15,7,15,7));
        logInLayout.setVgap(6);logInLayout.setHgap(6);
        Label userText = new Label("USERNAME:");
        logInLayout.add(userText,1,3,2,0);

        loggingIn = new Scene (logInLayout,300,300);
    }

    //Registration UI Scene
    public void registrationUI(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserFloorStage = primaryStage;

        UserFloorStage.setTitle("Log In");
        UserFloorStage.show();

    }
}
