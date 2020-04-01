import com.mongodb.*;
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
    //Instances
    private MongoClient mongo = new MongoClient("Localhost", 27017);
    private DB LibraryProject = mongo.getDB("LibraryProject");
    private DBCollection UsersCollection = LibraryProject.getCollection("Users");
    private String usernameIn;
    private String passwordIn;
    Stage UserFloorStage;
    private double userID;

    //Behaviours
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
    private GridPane logInLayout = new GridPane();
    private Label warning;TextField passIn = new TextField();TextField userIn = new TextField();

    public void logInUi(){
        UserFloorStage.setTitle("Log In");
        Scene loggingIn;
        //setting pane and adding all the nodes

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
        userIn.setPromptText("Username");
        logInLayout.add(userIn,1,3);

        //Password Text
        Label passText = new Label("PASSWORD:");
        logInLayout.add(passText, 0, 4);

        //Password TextField

        passIn.setPromptText("Password");
        logInLayout.add(passIn,1,4);

        //Warning Label is added to the frame
        warning = new Label("");
        logInLayout.add(warning,1,5);

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

    //Log In Validation Section -------
    private void logInValidation(ActionEvent event){
        setUsernameIn(userIn.getText());setPasswordIn(passIn.getText());
        if (userExist(getUsernameIn(),getPasswordIn())){
            UserFloorStage.close();
        }
        else {
          warning.setText("Incorrect Username/Password");
            userIn.setText("");
            passIn.setText("");
        }
    }
    private boolean userExist(String userINPUT, String passINPUT){
        boolean checkResult = false;
        BasicDBObject QueryUser = new BasicDBObject("Username", userINPUT);
        Cursor C = UsersCollection.find(QueryUser);
        if(C.hasNext()){
            DBObject CLocation = C.next();
            String retrievedPassword = (String) CLocation.get("Password");
            if(retrievedPassword.equals(passINPUT)){
                String fname = (String) CLocation.get("First Name");
                String lname = (String) CLocation.get("Last Name");
                System.out.println(fname+" "+lname);
                setUserID((double)CLocation.get("_id"));
                checkResult = true;
            }
        }
        return checkResult;
    }
    // End of Validation Section --------

    public void registrationHandler(){
        System.out.println("hello");
    }
    //Registration UI Scene
    public void registrationUI(){

    }



    //Mutators
    private void setUsernameIn(String Input) {
        usernameIn = Input;
    }
    private void setPasswordIn(String Input){
        passwordIn = Input;
    }
    private void setUserID(double Input){
        userID = Input;
    }

    //Accessors
    public double getUserID(){
        return userID;
    }
    public String getUsernameIn(){
        return usernameIn;
    }
    private String getPasswordIn(){
        return passwordIn;
    }

}
