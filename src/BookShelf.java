import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BookShelf {
    private  MongoClient mc = new MongoClient("Localhost", 27017);
    private  DB dbs = mc.getDB("LibraryProject");
    private  DBCollection BookCollection = dbs.getCollection("BookShelf");
    private  DBCollection LoanCollection = dbs.getCollection("Loan");

    public void searchDisplay(Stage primaryStage){
        Scene bookDisplay;

        GridPane mainHolder = new GridPane();mainHolder.setPadding(new Insets(25,0,25,25));
        mainHolder.setHgap(17);mainHolder.setVgap(25);

        //Primary Title
        Label Title = new Label("Search");
        Title.setPadding(new Insets(10,10,10,10));
        GridPane.setHalignment(Title, HPos.CENTER);Title.setFont(new Font("Calibri",19));
        mainHolder.add(Title,0,0,2,1);

        //Menu Logo
        Image logo = new Image(getClass().getResourceAsStream("Menu_logo.png"));
        ImageView logoD = new ImageView(logo);
        logoD.setFitHeight(50);logoD.setFitWidth(111);
        mainHolder.add(logoD,2,0,1,1);

        //Label - Brief Info to navigating
        Label info= new Label("Please begin Searching by Choosing a Category");
        GridPane.setHalignment(info,HPos.CENTER);
        info.setStyle("-fx-border-color:black;");
        info.setPadding(new Insets(10,10,10,10));
        info.setFont(new Font("Calibri",15));
        mainHolder.add(info,0,1,2,1);

        //Button Section
        GridPane container = new GridPane();
        container.setPadding(new Insets(10,10,10,10));

        //Computer Science
        Image csL = new Image(getClass().getResourceAsStream("Computer_Logo.png"));
        ImageView csLV = new ImageView(csL);
        csLV.setFitWidth(125);csLV.setFitHeight(110);
        Button csA = new Button("",csLV);
        csA.setPadding(new Insets(10,10,10,10));
        csA.setStyle("-fx-background-color:white;");

        //Law
        Image lL = new Image(getClass().getResourceAsStream("Law_Logo.png"));
        ImageView lLv = new ImageView(lL);
        lLv.setFitWidth(125);lLv.setFitHeight(110);
        Button lA = new Button("",lLv);
        lA.setPadding(new Insets(10,10,10,10));
        lA.setStyle("-fx-background-color:white;");

        //History
        Image hL = new Image(getClass().getResourceAsStream("History_Logo.png"));
        ImageView hLv = new ImageView(hL);
        hLv.setFitWidth(125);hLv.setFitHeight(110);
        Button hA = new Button("",hLv);
        hA.setPadding(new Insets(10,10,10,10));
        hA.setStyle("-fx-background-color:white;");

        //Business
        Image bL = new Image(getClass().getResourceAsStream("Business_Logo.png"));
        ImageView bLv = new ImageView(bL);
        bLv.setFitWidth(125);bLv.setFitHeight(110);
        Button bA = new Button("",bLv);
        bA.setPadding(new Insets(10,10,10,10));
        bA.setStyle("-fx-background-color:white;");

        container.addRow(0,csA,lA,hA,bA);
        container.setHgap(10);
        mainHolder.add(container,0,2,2,1);

        mainHolder.setVgap(10);
        bookDisplay = new Scene(mainHolder,805,355);
        primaryStage.setTitle("Search");
        primaryStage.setScene(bookDisplay);

    }
}
