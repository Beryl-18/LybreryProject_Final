import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class User extends Application {
    private MongoClient mongo = new MongoClient("Localhost", 27017);
    private DB LibraryProject = mongo.getDB("LibraryProject");
    private DBCollection UsersCollection = LibraryProject.getCollection("Users");

    Stage UserFloorStage;

    //Instances of the class
    private double userID;
    private static String[] Username = new String[2];

    public static void main(String[] args){launch(args);}
    @Override
    public void start(Stage primaryStage) {
        try {
            UserFloorStage = primaryStage;
            logInUi();
        }
        catch(Exception e){
            System.out.println("message"+e.getMessage());
        }
    }

    //Login UI scene
    public void logInUi(){
        UserFloorStage.setTitle("Log In");
        Scene loggingIn;
        //setting pane and adding all the nodes
        GridPane logInLayout = new GridPane();
        logInLayout.setPadding(new Insets(20, 10, 10, 15));
        logInLayout.setVgap(6);
        logInLayout.setHgap(14);

        //Title Text
        Label Title = new Label("Please Log In to Continue");
        Title.setFont(new Font("Calibri",18));
//        GridPane.setHalignment(Title, HPos.CENTER);
        logInLayout.add(Title,1,0,2,1);

        //Username Text
        Label userText = new Label("USERNAME:");
        logInLayout.add(userText, 0, 3);

        //Username TextField
        TextField userIn = new TextField();
        userIn.setPromptText("Username");
        logInLayout.add(userIn,1,3);

        //Password Text
        Label passText = new Label("PASSWORD:");
        logInLayout.add(passText, 0, 4);

        //Password TextField
        TextField passIn = new TextField();
        passIn.setPromptText("Password");
        logInLayout.add(passIn,1,4);

        //LogInButton
        Button logInEvent = new Button("Log In");
        logInLayout.add(logInEvent,1,6);
        logInEvent.setOnAction(event -> logInValidation(event) );

        //RegisterButton
        Button RegisterEvent = new Button("Register");
        GridPane.setHalignment(RegisterEvent,HPos.RIGHT);
        logInLayout.add(RegisterEvent,1,6);
        RegisterEvent.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                registrationHandler();
            }
        });

        //Setting the scene on the stage
        loggingIn = new Scene(logInLayout, 400, 200);
        UserFloorStage.setScene(loggingIn);
        UserFloorStage.show();
    }

    public void registrationHandler(){
        System.out.println("hello");
    }
    private void logInValidation(ActionEvent event){

    }
    private boolean userExist(){
        return false;
    }

    //Registration UI Scene
    public void registrationUI(){

    }
}
