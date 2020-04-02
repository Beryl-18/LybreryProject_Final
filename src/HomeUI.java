
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*
Things to do ****
- make all buttons disabled, mouse hover - enable property
- Add Logos/backgrounds
- Picture based Display pic for User - figure out storing and retrieving as HIGH PRIORITY
*****************
 */


public class HomeUI  {
    Stage homeStage;
    Scene home;
    User customer;

    public void homeInterface(Stage primaryStage, User formUser){
        homeStage = primaryStage;
        customer = formUser;
        //Primary UI Content Holder
        GridPane mainHolder = new GridPane();mainHolder.setPadding(new Insets(25,0,25,25));
        mainHolder.setHgap(17);mainHolder.setVgap(25);

        //Primary Title
        Label Title = new Label("Welcome to the Lybrery System");
        Title.setPadding(new Insets(10,10,10,10));
        GridPane.setHalignment(Title, HPos.CENTER);Title.setFont(new Font("Calibri",19));
        mainHolder.add(Title,0,0,2,1);

        //Menu Logo
        Image logo = new Image(getClass().getResourceAsStream("Menu_logo.png"));
        ImageView logoD = new ImageView(logo);
        logoD.setFitHeight(50);logoD.setFitWidth(111);
        mainHolder.add(logoD,2,0,1,1);



        //Home Primary Action Event Holder
        GridPane actionContainer = new GridPane(); actionContainer.setPadding(new Insets(15,25,15,25));actionContainer.setVgap(20);actionContainer.setHgap(25);


        //Loan Action
        Button loanAction = new Button("LOAN");
        GridPane.setHalignment(loanAction,HPos.CENTER);loanAction.setFont(new Font("Calibri",13));
        loanAction.setPadding(new Insets(5,5,5,5));
        actionContainer.add(loanAction,0,0);

        //Return Action
        Button returnAction = new Button("RETURN");
        GridPane.setHalignment(returnAction,HPos.CENTER);returnAction.setFont(new Font("Calibri",13));
        returnAction.setPadding(new Insets(5,5,5,5));
        actionContainer.add(returnAction,2,0);

        //Detail Action
        Button detailAction = new Button("DETAILS");
        GridPane.setHalignment(detailAction,HPos.CENTER);detailAction.setFont(new Font("Calibri",13));
        detailAction.setPadding(new Insets(5,5,5,5));
        actionContainer.add(detailAction,1,2);


        //Home User information & log out Display
        GridPane userContainer = new GridPane(); userContainer.setPadding(new Insets(9,25,9,25));userContainer.setVgap(10);
        //UserName LabelText
        Label userNameDisplay = new Label(customer.getUsername());
        userNameDisplay.setFont(new Font("Calibri",14));
        userNameDisplay.setPadding(new Insets(7,7,7,7));
        userContainer.addRow(1,userNameDisplay);

        //Log Out Action Button
        Button logOut = new Button("Log Out");
        logOut.setPadding(new Insets(3,3,3,3));logOut.setFont(new Font("Calibri",13));
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        userContainer.addRow(2,logOut);


        //Adding All sub containers to main Holder
            //Adding User Information - Right Bar
        userContainer.setStyle("-fx-border-color:black;");
        mainHolder.add(userContainer,2,1,1,2);
            //Adding Action Center
        mainHolder.add(actionContainer,0,1,2,2);
        mainHolder.setStyle("-fx-background-color:white;");
        home = new Scene(mainHolder,440,245);
        homeStage.setTitle("HOME");
        homeStage.setScene(home);
        homeStage.show();


    }


}
