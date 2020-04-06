import com.mongodb.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BookShelf {
    private  MongoClient mc = new MongoClient("Localhost", 27017);
    private  DB dbs = mc.getDB("LibraryProject");
    private  DBCollection BookCollection = dbs.getCollection("BookShelf");
    private  DBCollection LoanCollection = dbs.getCollection("Loan");
    private User userDetails;
    private String userSelectCat;
    private Stage bookShelfUI;
    public void searchDisplay(Stage primaryStage, User formofUser){
        bookShelfUI = primaryStage;
        userDetails = formofUser;
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
        csA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setUserSelectCat("Computer Science");
                displayBooks(primaryStage,getUserSelectCat());
            }
        });

        //Law
        Image lL = new Image(getClass().getResourceAsStream("Law_Logo.png"));
        ImageView lLv = new ImageView(lL);
        lLv.setFitWidth(125);lLv.setFitHeight(110);
        Button lA = new Button("",lLv);
        lA.setPadding(new Insets(10,10,10,10));
        lA.setStyle("-fx-background-color:white;");
        lA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setUserSelectCat("Law");
                displayBooks(primaryStage,getUserSelectCat());
            }
        });

        //History
        Image hL = new Image(getClass().getResourceAsStream("History_Logo.png"));
        ImageView hLv = new ImageView(hL);
        hLv.setFitWidth(125);hLv.setFitHeight(110);
        Button hA = new Button("",hLv);
        hA.setPadding(new Insets(10,10,10,10));
        hA.setStyle("-fx-background-color:white;");
        hA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setUserSelectCat("History");
                displayBooks(primaryStage,getUserSelectCat());
            }
        });

        //Business
        Image bL = new Image(getClass().getResourceAsStream("Business_Logo.png"));
        ImageView bLv = new ImageView(bL);
        bLv.setFitWidth(125);bLv.setFitHeight(110);
        Button bA = new Button("",bLv);
        bA.setPadding(new Insets(10,10,10,10));
        bA.setStyle("-fx-background-color:white;");
        bA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setUserSelectCat("Business");
                displayBooks(primaryStage,getUserSelectCat());
            }
        });

        container.addRow(0,csA,lA,hA,bA);
        container.setHgap(10);
        mainHolder.add(container,0,2,2,1);

        mainHolder.setVgap(10);
        mainHolder.setStyle("-fx-background-color:white;");
        bookDisplay = new Scene(mainHolder,805,355);
        primaryStage.setTitle("Search");
        primaryStage.setScene(bookDisplay);

    }

    public void displayBooks(Stage primaryStage, String category) {
        GridPane mainHolder = new GridPane();mainHolder.setPadding(new Insets(25,0,25,25));
        mainHolder.setHgap(17);mainHolder.setVgap(25);
        Scene booksAvailable;

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


        //ScrollPane holdng Inner container
        ScrollPane innerHolder = new ScrollPane();
        //Gridpane Inner Container
        GridPane container = new GridPane(); container.setPadding(new Insets(13,13,13,13));
        innerHolder.setContent(container);
        //width410,H195
        innerHolder.setPrefWidth(438);
        innerHolder.setPrefHeight(195);
        innerHolder.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        innerHolder.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        //Getting the books from the database
        BasicDBObject booksInCat = new BasicDBObject("Category",category);
        Cursor c = BookCollection.find(booksInCat);

        //Header Labels

        //Id Head
        Label idDisplay = new Label("ID");
        idDisplay.setPadding(new Insets(10,10,10,10));
        idDisplay.setFont(new Font("Calibri",16));
        container.add(idDisplay,0,0);

        //Book name Head
        Label bookDisplay = new Label("Book Title");
        bookDisplay.setPadding(new Insets(10,10,10,10));
        bookDisplay.setFont(new Font("Calibri",16));
        container.add(bookDisplay,1,0);

        //Loan status
        Label loanDisplay = new Label("Loan Status");
        loanDisplay.setPadding(new Insets(10,10,10,10));
        loanDisplay.setFont(new Font("Calibri",16));

        container.add(loanDisplay,2,0);

        ToggleGroup idGroup = new ToggleGroup();
        int i = 3;
        while(c.hasNext()) {
            DBObject individualBook = c.next();

            //Each Item being displayed as Label
            //RadioButton'ed ID
            //required conversions, as ID isn't showing up otherwise.
            double idnumber = (double) individualBook.get("_id");
            String idconverted = String.valueOf(idnumber);

            RadioButton id = new RadioButton(idconverted);
            id.setPadding(new Insets(5,5,5,5));
            container.add(id,0,i);
            id.setToggleGroup(idGroup);

            //Bookname
            Label bookName = new Label((String) individualBook.get("BookName"));
            bookName.setPadding(new Insets(10, 10, 10, 10));
            bookName.setFont(new Font("Calibri", 11));
            container.add(bookName, 1, i);

            //Loan Status
            Label loanStatus;
            if (!OnLoan((double) individualBook.get("_id"))) {
                loanStatus = new Label("Available");
            } else {
                loanStatus = new Label("UnAvailable");
                id.setDisable(true);
            }

            loanStatus.setPadding(new Insets(10, 10, 10, 10));
            loanStatus.setFont(new Font("Calibri", 11));
            container.add(loanStatus, 2, i);
            i++;
        }

            //Bottom Container - Go Back and Exit buttons
            Button goB = new Button("Go Back");
            GridPane.setHalignment(goB,HPos.RIGHT);goB.setFont(new Font("Calibri",13));
            goB.setPadding(new Insets(5,5,5,5));
            goB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    searchDisplay(primaryStage,userDetails);
                }
            });

            Button exit = new Button("Exit");
            GridPane.setHalignment(exit,HPos.CENTER);exit.setFont(new Font("Calibri",13));
            exit.setPadding(new Insets(5,5,5,5));
            exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });

            //Loan Button
            Button loan = new Button("Loan");
            GridPane.setHalignment(loan,HPos.CENTER);loan.setFont(new Font("Calibri",13));
            loan.setPadding(new Insets(5,5,5,5));
            loan.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    RadioButton selectedButton = (RadioButton) idGroup.getSelectedToggle();
                    takeLoan(Double.parseDouble(selectedButton.getText()));
                }
            });


            mainHolder.add(goB,0,3);
            mainHolder.add(loan,1,3);
            mainHolder.add(exit,2,3);
            mainHolder.add(innerHolder,0,2,2,1);
            mainHolder.setStyle("-fx-background-color:white;");

            booksAvailable = new Scene(mainHolder,605,380);

            primaryStage.setTitle("Search");
            primaryStage.setScene(booksAvailable);



    }

    private boolean OnLoan(double BookId){
        BasicDBObject BookInCheck = new BasicDBObject("Book_id",BookId);
        Cursor C = LoanCollection.find(BookInCheck);
        return C.hasNext();

    }

    private void takeLoan(double bookId){
        try {
            //Allowing loan, create record of loan in the Loan Collection
            BasicDBObject LoanDetails = new BasicDBObject();
            LoanDetails.put("Book_id", bookId);
            LoanDetails.put("Student_Id", userDetails.getUserID());
            LoanCollection.insert(LoanDetails);

            //Setting up a new stage to alert the user of the loan
            Stage loanAlert = new Stage();
            GridPane miniPane = new GridPane();

            Label message = new Label("Book Id: "+bookId+" is now on Loan");
            message.setPadding(new Insets(24,24,24,24));
            message.setFont(new Font("Calibri",20));
            GridPane.setHalignment(message,HPos.CENTER);
            GridPane.setValignment(message, VPos.CENTER);
            miniPane.add(message,0,0);
            Scene messageAlert = new Scene(miniPane,301,112);

            loanAlert.setScene(messageAlert);
            loanAlert.setTitle("Loan Alert");
            loanAlert.show();
            bookShelfUI.close();
            loanAlert.toFront();



        }
        catch(Exception e){
            System.out.println("error"+e.getMessage());
        }


    }

    public void setUserSelectCat(String input){
        userSelectCat = input;
    }
    public String getUserSelectCat(){
        return userSelectCat;
    }

}