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

import java.io.File;
import java.util.Scanner;

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
            if (!onLoan((double) individualBook.get("_id"))) {
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

            //Book Details Button
            Button details = new Button("Details");
            GridPane.setHalignment(details,HPos.CENTER);details.setFont(new Font("Calibri",13));
            details.setPadding(new Insets(5,5,5,5));
            details.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    RadioButton selectedButton = (RadioButton) idGroup.getSelectedToggle();
                    bookInfo(Double.parseDouble(selectedButton.getText()));
                }
            });


            mainHolder.add(goB,0,3);
            mainHolder.add(loan,1,3);
            mainHolder.add(details,2,3);
            mainHolder.add(innerHolder,0,2,2,1);
            mainHolder.setStyle("-fx-background-color:white;");

            booksAvailable = new Scene(mainHolder,605,380);

            bookShelfUI.setTitle("Search");
            bookShelfUI.setScene(booksAvailable);



    }
    private boolean onLoan(double BookId){
        BasicDBObject BookInCheck = new BasicDBObject("Book_id",BookId);
        Cursor C = LoanCollection.find(BookInCheck);
        return C.hasNext();

    }
    public void bookInfo(double book_id){
        Stage bookInfoStage = new Stage();

        //Top Half of Content
        GridPane topHolder = new GridPane();
        topHolder.setHgap(20);topHolder.setVgap(4);
        topHolder.setPadding(new Insets(10,10,10,10));

        //primary Content of Book
        //The GridPane to hold the content
        GridPane contentHolder = new GridPane();
        contentHolder.setPadding(new Insets(5,5,5,5));
        contentHolder.setVgap(8);contentHolder.setHgap(8);

        //Labels for each of the items
        Label BookName = new Label(getBookName(book_id));
        BookName.setPadding(new Insets(7,7,7,7));
        BookName.setFont(new Font("Calibri", 17));
        contentHolder.add(BookName,0,0,4,1);

        Label AuthorName = new Label(getAuthor(book_id));
        AuthorName.setPadding(new Insets(7,7,7,7));
        AuthorName.setFont(new Font("Calibri", 14));
        GridPane.setHalignment(AuthorName,HPos.LEFT);
        contentHolder.add(AuthorName,0,1,4,1);

        Label idLabel = new Label("ID: ");
        idLabel.setPadding(new Insets(7,7,7,7));
        idLabel.setFont(new Font("Calibri", 14));
        contentHolder.add(idLabel,0,2);


        Label idValue = new Label(String.valueOf(book_id));
        idValue.setPadding(new Insets(7,7,7,7));
        idValue.setFont(new Font("Calibri", 14));
        contentHolder.add(idValue,1,2);

        Label yearLabel = new Label("Year: ");
        yearLabel.setPadding(new Insets(7,7,7,7));
        yearLabel.setFont(new Font("Calibri", 15));
        contentHolder.add(yearLabel,2,2);

        Label yearValue = new Label(String.valueOf(getYear(book_id)));
        yearValue.setPadding(new Insets(7,7,7,7));
        yearValue.setFont(new Font("Calibri", 14));
        contentHolder.add(yearValue,3,2);

        Label categoryLabel = new Label("Category: ");
        categoryLabel.setPadding(new Insets(7,7,7,7));
        categoryLabel.setFont(new Font("Calibri", 15));
        contentHolder.add(categoryLabel,0,3);

        Label categoryValue = new Label(getUserSelectCat());
        categoryValue.setPadding(new Insets(7,7,7,7));
        categoryValue.setFont(new Font("Calibri", 14));
        contentHolder.add(categoryValue,1,3);

        topHolder.add(contentHolder,0,0,2,2);

        //Book Image Content, at the left of the Top bar - need to change the logo, to book cover page
        String imageLocation = "BookImages/"+book_id +".jpg";
        Image logo = new Image(getClass().getResourceAsStream(imageLocation));
        ImageView logoD = new ImageView(logo);
        logoD.setFitWidth(110);
        logoD.setFitHeight(123);
        GridPane.setHalignment(logoD,HPos.CENTER);GridPane.setValignment(logoD,VPos.CENTER);
        topHolder.add(logoD,2,0,1,2);

        //Bottom half containing Book Summary
        GridPane bottomHolder = new GridPane();
        bottomHolder.setPadding(new Insets(10,10,10,10));
        bottomHolder.setHgap(8);
        bottomHolder.setVgap(10);

        Label aboutBookLabel = new Label("About This Book");
        aboutBookLabel.setPadding(new Insets(7,7,7,7));
        aboutBookLabel.setFont(new Font("Calibri", 13));
        bottomHolder.add(aboutBookLabel,0,0,2,1);

        //Retrieving the Summary from a file and appending to a textarea
        String summaryText = "";

        try {
            File file = new File("src/BookInformation/BookSummaries.txt");
            Scanner reader = new Scanner(file);

            boolean abstractfound = false;
            while (!abstractfound) {
                if (reader.next().equals(String.valueOf(book_id))) {
                    summaryText = reader.next() + reader.nextLine();
                    abstractfound = true;
                    reader.close();
                } else {
                    reader.next();
                }
            }

            TextArea summaryContent = new TextArea();
            summaryContent.setFont(new Font("Calibri",14));
            summaryContent.setStyle("-fx-letter-spacing:0.75px;");
            summaryContent.setPadding(new Insets(10,10,10,10));
            summaryContent.setText(summaryText);
            summaryContent.setEditable(false);
            summaryContent.setWrapText(true);

            bottomHolder.add(summaryContent,0,1,3,1);
        }
        catch(Exception e){
            System.out.println("error "+e.getMessage());
        }
        //All content holder
        GridPane mainContentHolder = new GridPane();
        mainContentHolder.setPadding(new Insets(10,12,10,12));
        mainContentHolder.setVgap(3);
        mainContentHolder.addRow(0,topHolder);
        mainContentHolder.addRow(1,bottomHolder);

        Scene fullBookDetails = new Scene(mainContentHolder,538,490);
        bookInfoStage.setTitle("Book Information");
        bookInfoStage.setResizable(false);
        bookInfoStage.setScene(fullBookDetails);
        bookInfoStage.show();bookInfoStage.toFront();

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
            loanReceipt.setResizable(false);
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
            returnReceipt.setResizable(false);
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

    private String getBookName(double book_Id){
        String bName;

        BasicDBObject bookSearch = new BasicDBObject("_id",book_Id);
        Cursor c = BookCollection.find(bookSearch);
        DBObject book = c.next();
        bName = (String) book.get("BookName");

        return bName;
    }
    private String getAuthor(double book_Id){
        String author;

        BasicDBObject bookSearch = new BasicDBObject("_id",book_Id);
        Cursor c = BookCollection.find(bookSearch);
        DBObject book = c.next();
        author = (String) book.get("BookAuthor");

        return author;
    }
    private int getYear(double book_Id){
        int year;

        BasicDBObject bookSearch = new BasicDBObject("_id",book_Id);
        Cursor c = BookCollection.find(bookSearch);
        DBObject book = c.next();
        double rawYear = (double) book.get("Year");
        year =(int) Math.round(rawYear);

        return year;
    }
    public String getUserSelectCat(){
        return userSelectCat;
    }
}