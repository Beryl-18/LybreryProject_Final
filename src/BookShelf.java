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
    private HomeUI menuform;
    public void searchDisplay(Stage primaryStage, User formOfUser, HomeUI formOfMainMenu){
        bookShelfUI = primaryStage;
        userDetails = formOfUser;
        menuform = formOfMainMenu;
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
                displayBooks(getUserSelectCat());
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
                displayBooks(getUserSelectCat());
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
                displayBooks(getUserSelectCat());
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
                displayBooks(getUserSelectCat());
            }
        });

        container.addRow(0,csA,lA,hA,bA);
        container.setHgap(10);container.setVgap(10);

        //Go Back and Exit Buttons
        Button goB = new Button("Go Back");
        GridPane.setHalignment(goB,HPos.RIGHT);goB.setFont(new Font("Calibri",13));
        goB.setPadding(new Insets(5,5,5,5));
        goB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                formOfMainMenu.homeInterface(bookShelfUI,userDetails);
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

        container.add(goB,1,1);container.add(exit,2,1);

        mainHolder.add(container,0,2,2,1);

        mainHolder.setVgap(10);
        mainHolder.setStyle("-fx-background-color:white;");
        bookDisplay = new Scene(mainHolder,805,355);
        primaryStage.setTitle("Search");
        primaryStage.setScene(bookDisplay);

    }

    private void displayBooks(String category) {
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
        int i = 1;
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
                    searchDisplay(bookShelfUI,userDetails,menuform);
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

            bookShelfUI.setTitle("Search");
            bookShelfUI.setScene(booksAvailable);



    }

    private boolean OnLoan(double BookId){
        BasicDBObject BookInCheck = new BasicDBObject("Book_id",BookId);
        Cursor C = LoanCollection.find(BookInCheck);
        return C.hasNext();

    }

    private void takeLoan(double book_Id){
        try {
            //Allowing loan, create record of loan in the Loan Collection
            BasicDBObject LoanDetails = new BasicDBObject();
            LoanDetails.put("Book_id", book_Id);
            LoanDetails.put("Student_Id", userDetails.getUserID());
            LoanCollection.insert(LoanDetails);

            //Setting up a new stage to alert the user of the loan
            Stage loanReceipt = new Stage();

            //Receipt Mainholder Pane
            GridPane receiptHolder = new GridPane();
            receiptHolder.setVgap(25);

            Label LoanTitle = new Label("Loan Receipt");
            LoanTitle.setPadding(new Insets(10,10,10,10));
            LoanTitle.setFont(new Font("Calibri",18));
            GridPane.setValignment(LoanTitle,VPos.CENTER);GridPane.setHalignment(LoanTitle, HPos.CENTER);

            receiptHolder.add(LoanTitle,0,0);

            //Top half - Loaned Book information
            GridPane bookcontent = new GridPane();bookcontent.setVgap(7);bookcontent.setHgap(7);

                //Labels containing Book details
                Label bookIDLabel = new Label("Book ID:");
                bookIDLabel.setPadding(new Insets(10,10,10,10));
                bookIDLabel.setFont(new Font("Calibri",15));

                Label bookID = new Label(String.valueOf(book_Id));
                bookID.setPadding(new Insets(10,10,10,10));
                bookID.setFont(new Font("Calibri",13));

                Label bookNameLabel = new Label("Book Name: ");
                bookNameLabel.setPadding(new Insets(10,10,10,10));
                bookNameLabel.setFont(new Font("Calibri",15));

                //retrieving the book name from database & Setting the label to the name
                BasicDBObject bookSearch = new BasicDBObject("_id",book_Id);
                Cursor C = BookCollection.find(bookSearch);
                DBObject bookObject = C.next();

                Label bookName = new Label((String) bookObject.get("BookName"));
                bookName.setPadding(new Insets(10,10,10,10));
                bookName.setFont(new Font("Calibri",13));

            bookcontent.addRow(0,bookIDLabel,bookID);
            bookcontent.addRow(1,bookNameLabel,bookName);

            //Bottom half - User Information
            GridPane userInfoContainer = new GridPane(); userInfoContainer.setHgap(7);userInfoContainer.setHgap(7);

                //Labels Containing User information
                Label userIDLabel = new Label("User ID:");
                userIDLabel.setPadding(new Insets(10,10,10,10));
                userIDLabel.setFont(new Font("Calibri",15));

                Label userID = new Label(String.valueOf(userDetails.getUserID()));
                userID.setPadding(new Insets(10,10,10,10));
                userID.setFont(new Font("Calibri",13));

                Label userNameLabel = new Label("User Name: ");
                userNameLabel.setPadding(new Insets(10,10,10,10));
                userNameLabel.setFont(new Font("Calibri",15));

                Label userName = new Label(userDetails.getUsername());
                userName.setPadding(new Insets(10,10,10,10));
                userName.setFont(new Font("Calibri",13));

             userInfoContainer.addRow(0,userIDLabel,userID);
             userInfoContainer.addRow(1,userNameLabel,userName);

             receiptHolder.addRow(1,bookcontent);
             receiptHolder.addRow(2,userInfoContainer);


            Scene loanInformation = new Scene(receiptHolder,377,304);
            loanReceipt.setScene(loanInformation);
            loanReceipt.setTitle("Loan Receipt");
            searchDisplay(bookShelfUI,userDetails,menuform);
            loanReceipt.show();
            loanReceipt.toFront();

        }
        catch(Exception e){
            System.exit(0);
            System.out.println("error "+e.getMessage());
        }


    }

    public void bookReturn(Stage primaryStage, User formOfUser, HomeUI homeForm){
        //UI to Return the Books Loaned by User
        bookShelfUI = primaryStage; userDetails = formOfUser;
        menuform = homeForm;
        GridPane mainHolder = new GridPane();mainHolder.setPadding(new Insets(25,0,25,25));
        mainHolder.setHgap(17);mainHolder.setVgap(25);
        Scene booksAvailable;

        //Primary Title
        Label Title = new Label("Return books");
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


        //Retriving Books Loaned by the User that's logged in, from the database
        BasicDBObject userInLoan = new BasicDBObject("Student_Id",userDetails.getUserID());
        Cursor c = LoanCollection.find(userInLoan);

        ToggleGroup idGroup = new ToggleGroup();
        int i = 1;
        //Searching if the User had loaned books
        if (c.hasNext()) {
            while (c.hasNext()) {
                DBObject loanObject = c.next();
                double bookID = (double) loanObject.get("Book_id");

                //Book Collection Search, to retrieve more details of the book
                BasicDBObject bookDetails = new BasicDBObject("_id",bookID);
                    Cursor bookShelfC = BookCollection.find(bookDetails);
                    while(bookShelfC.hasNext()){
                        DBObject individualBook = bookShelfC.next();
                        //Each Item being displayed as Label
                        //RadioButton'ed ID
                        //required conversions, as ID does not show up otherwise.
                        double idNumber = (double) individualBook.get("_id");
                        String idConverted = String.valueOf(idNumber);

                        RadioButton id = new RadioButton(idConverted);
                        id.setPadding(new Insets(5, 5, 5, 5));
                        container.add(id, 0, i);
                        id.setToggleGroup(idGroup);

                        //Bookname
                        Label bookName = new Label((String) individualBook.get("BookName"));
                        bookName.setPadding(new Insets(10, 10, 10, 10));
                        bookName.setFont(new Font("Calibri", 11));
                        container.add(bookName, 1, i);

                        i++;
                    }
            }
        }
        else {
            Label message = new Label("No Books have been taken on Loan");
            message.setPadding(new Insets(10, 10, 10, 10));
            message.setFont(new Font("Calibri", 15));
            container.add(message, 1, 3);
        }

        //Bottom Container - Go Back and Exit buttons
        Button goB = new Button("Go Back");
        GridPane.setHalignment(goB,HPos.RIGHT);goB.setFont(new Font("Calibri",13));
        goB.setPadding(new Insets(5,5,5,5));
        goB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                homeForm.homeInterface(bookShelfUI,userDetails);
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

        //Return Button
        Button bookReturn = new Button("Return");
        GridPane.setHalignment(bookReturn,HPos.CENTER);bookReturn.setFont(new Font("Calibri",13));
        bookReturn.setPadding(new Insets(5,5,5,5));
        bookReturn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RadioButton selectedbutton = (RadioButton) idGroup.getSelectedToggle();
                returnGui(Double.parseDouble(selectedbutton.getText()));
            }
        });

        //adding everything to the mainholder
        mainHolder.add(goB,0,3);
        mainHolder.add(bookReturn,1,3);
        mainHolder.add(exit,2,3);
        mainHolder.add(innerHolder,0,2,2,1);
        mainHolder.setStyle("-fx-background-color:white;");
        booksAvailable = new Scene(mainHolder,605,380);

        bookShelfUI.setTitle("Return A Book");
        bookShelfUI.setScene(booksAvailable);
    }

    private void returnGui(double book_Id){
        try {
            //Allowing loan, create record of loan in the Loan Collection
            BasicDBObject returnDetails = new BasicDBObject();
            returnDetails.put("Book_id", book_Id);
            returnDetails.put("Student_Id", userDetails.getUserID());
            LoanCollection.remove(returnDetails);

            //Setting up a new stage to alert the user of the loan
            Stage returnReceipt = new Stage();

            //Receipt Mainholder Pane
            GridPane receiptHolder = new GridPane();
            receiptHolder.setVgap(25);

            Label LoanTitle = new Label("Return Receipt");
            LoanTitle.setPadding(new Insets(10,10,10,10));
            LoanTitle.setFont(new Font("Calibri",18));
            GridPane.setValignment(LoanTitle,VPos.CENTER);GridPane.setHalignment(LoanTitle, HPos.CENTER);

            receiptHolder.add(LoanTitle,0,0);

            //Top half - Loaned Book information
            GridPane bookcontent = new GridPane();bookcontent.setVgap(7);bookcontent.setHgap(7);

            //Labels containing Book details
            Label bookIDLabel = new Label("Book ID:");
            bookIDLabel.setPadding(new Insets(10,10,10,10));
            bookIDLabel.setFont(new Font("Calibri",15));

            Label bookID = new Label(String.valueOf(book_Id));
            bookID.setPadding(new Insets(10,10,10,10));
            bookID.setFont(new Font("Calibri",13));

            Label bookNameLabel = new Label("Book Name: ");
            bookNameLabel.setPadding(new Insets(10,10,10,10));
            bookNameLabel.setFont(new Font("Calibri",15));

            //retrieving the book name from database & Setting the label to the name
            BasicDBObject bookSearch = new BasicDBObject("_id",book_Id);
            Cursor C = BookCollection.find(bookSearch);
            DBObject bookObject = C.next();

            Label bookName = new Label((String) bookObject.get("BookName"));
            bookName.setPadding(new Insets(10,10,10,10));
            bookName.setFont(new Font("Calibri",13));

            bookcontent.addRow(0,bookIDLabel,bookID);
            bookcontent.addRow(1,bookNameLabel,bookName);

            //Bottom half - User Information
            GridPane userInfoContainer = new GridPane(); userInfoContainer.setHgap(7);userInfoContainer.setHgap(7);

            //Labels Containing User information
            Label userIDLabel = new Label("User ID:");
            userIDLabel.setPadding(new Insets(10,10,10,10));
            userIDLabel.setFont(new Font("Calibri",15));

            Label userID = new Label(String.valueOf(userDetails.getUserID()));
            userID.setPadding(new Insets(10,10,10,10));
            userID.setFont(new Font("Calibri",13));

            Label userNameLabel = new Label("User Name: ");
            userNameLabel.setPadding(new Insets(10,10,10,10));
            userNameLabel.setFont(new Font("Calibri",15));

            Label userName = new Label(userDetails.getUsername());
            userName.setPadding(new Insets(10,10,10,10));
            userName.setFont(new Font("Calibri",13));

            userInfoContainer.addRow(0,userIDLabel,userID);
            userInfoContainer.addRow(1,userNameLabel,userName);

            receiptHolder.addRow(1,bookcontent);
            receiptHolder.addRow(2,userInfoContainer);


            Scene returnInformation = new Scene(receiptHolder,358,325);
            returnReceipt.setScene(returnInformation);
            returnReceipt.setTitle("Return Receipt");
            menuform.homeInterface(bookShelfUI,userDetails);
            returnReceipt.show();
            returnReceipt.toFront();
        }
        catch(Exception e){
            System.exit(0);
            System.out.println("error "+e.getMessage());
        }
    }
    public void setUserSelectCat(String input){
        userSelectCat = input;
    }
    public String getUserSelectCat(){
        return userSelectCat;
    }

}