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

import static javafx.application.Application.launch;

public class User extends Application{
    //Instances
    private MongoClient mongo = new MongoClient("Localhost", 27017);
    private DB LibraryProject = mongo.getDB("LibraryProject");
    private DBCollection UsersCollection = LibraryProject.getCollection("Users");
    private String usernameIn;
    private String passwordIn;
    Stage UserFloorStage;
    private double userID;
    private String Username;
    //Behaviours

    HomeUI parent = new HomeUI();

    public static void main(String[] args){launch(args);}

    @Override
    public void start(Stage primaryStage){
        UserFloorStage = primaryStage;
        logInUi();
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
        RegisterEvent.setOnAction(e-> registrationUI());

        //Setting the scene on the stage
        loggingIn = new Scene(logInLayout, 400, 200);
        UserFloorStage.setScene(loggingIn);
        UserFloorStage.show();
    }

    //Log In Validation Section -------
    private void logInValidation(ActionEvent event){
        setUsernameIn(userIn.getText());setPasswordIn(passIn.getText());
        if (userExist(getUsernameIn(),getPasswordIn())){
            parent.homeInterface(UserFloorStage,User.this);
        }
        else {
          warning.setText("Incorrect Username/Password");
            userIn.setText("");
            passIn.setText("");
        }
    }
    private boolean userExist(String userINPUT, String passINPUT){
        boolean checkResult = false;
        BasicDBObject queryUser = new BasicDBObject("Username", userINPUT);
        Cursor C = UsersCollection.find(queryUser);
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

    //Start of Registration Procedure
    //Registration UI Scene
    public void registrationUI(){
        UserFloorStage.setTitle("Registration");
        GridPane registrationLayout = new GridPane();
        registrationLayout.setPadding(new Insets(20,20,20,20));
        registrationLayout.setVgap(7);
        registrationLayout.setHgap(7);

        //Registration Text
        Label registerTitle = new Label("Register to be a Member");
        registerTitle.setFont(new Font("Calibri",15));
        GridPane.setHalignment(registerTitle,HPos.CENTER);
        registrationLayout.add(registerTitle,0,0,2,1);

        //First Name
        Label firstN = new Label("First Name");
        TextField firstNameIn = new TextField();
        firstNameIn.setPromptText("First Name");
        registrationLayout.addRow(2, firstN,firstNameIn);

        //Last Name
        Label lastN = new Label("Last Name");
        TextField lastNameIn = new TextField();
        lastNameIn.setPromptText("Last Name");
        registrationLayout.addRow(3,lastN,lastNameIn);

        //Password Fields
        Label pass = new Label("Password: ");
        TextField passIn = new TextField();
        passIn.setPromptText("Password");
        registrationLayout.addRow(4,pass,passIn);

        //Confirm Pass
        Label cPass = new Label("Re-type Password: ");
        TextField cPassIn = new TextField();
        cPassIn.setPromptText("Re-type Password");
        registrationLayout.addRow(5,cPass,cPassIn);

        //Password Matching Warning
        Label passWarning = new Label("");
        registrationLayout.addRow(6,passWarning);

        //Registration Handle Button
        Button registerButton = new Button("REGISTER");
        GridPane.setHalignment(registerButton,HPos.RIGHT);
        GridPane.setValignment(registerButton,VPos.BOTTOM);
        registerButton.setPadding(new Insets(10,10,10,10));
        registrationLayout.addRow(7,registerButton);
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!passIn.getText().isEmpty()){
                    if(passIn.getText().equals(cPassIn.getText())){
                        String[] userCred = new String[3];
                        userCred[0] = firstNameIn.getText();
                        userCred[1] = lastNameIn.getText();
                        userCred[2] = passIn.getText();
                        registrationHandle(userCred);
                    }
                    else {
                        passWarning.setText("Please Enter Matching Passwords");
                    }
                }
                else {
                    passWarning.setText("Please Enter a Password");
                }
            }
        });


        //Setting the scene up
        Scene registerNow = new Scene(registrationLayout,360,300);
        UserFloorStage.setScene(registerNow);
        UserFloorStage.show();

    }
        //Registration handle
        private void registrationHandle(String[] creds){
        //Array information - element[0] is First Name and Username, element[1] is last Name, element[2] is Password
            double userID=0;
            //Search Algorithm within Database to find out empty Id
            double i=1; boolean finder = false;
            while(!finder) {
                BasicDBObject idSearchQ = new BasicDBObject("_id", i);
                Cursor C = UsersCollection.find(idSearchQ);
                if (C.hasNext()) {
                    i++;
                }
                else {
                    userID = i;
                    finder = true;
                }
            }
            //Inserting the whole object into the database
            BasicDBObject registerDetails = new BasicDBObject("_id",userID).append("Username",creds[0]).append("Password",creds[2])
                    .append("First Name",creds[0]).append("Last Name",creds[1]);
            try{
                UsersCollection.insert(registerDetails);
                setUserID(userID);
                parent.homeInterface(UserFloorStage,User.this);
            }
            catch(Exception e){
                System.out.println("Warning: "+e.getMessage());
            }

        }
    //End of Registration


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
    private void setUsername(){
        BasicDBObject userObject = new BasicDBObject("_id",getUserID());
        Cursor c = UsersCollection.find(userObject);
        Username = (String) c.next().get("Username");
    }


    //Accessors
    public String getUsername(){
        setUsername();
        return Username;
    }
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
