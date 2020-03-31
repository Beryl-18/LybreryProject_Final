import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import javafx.application.Application;
import javafx.stage.Stage;

public class User extends Application {
    private MongoClient mongo = new MongoClient("Localhost", 27017);
    private DB LibraryProject = mongo.getDB("LibraryProject");
    private DBCollection UsersCollection = LibraryProject.getCollection("Users");

    //Instances of the class
    private double UserID;
    private static String[] Username = new String[2];

    public void LogInUi(){

    }
    public void RegistrationUI(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
