package ContactManagement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class Task extends HBox {

    private Label index;
    private TextField taskName;
    private Button doneButton;

    private boolean markedDone;

    Task() {
        this.setPrefSize(500, 20); // sets size of task
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task
        markedDone = false;

        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(40, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the task
        this.getChildren().add(index); // add index label to task

        taskName = new TextField(); // create task name text field
        taskName.setPrefSize(380, 20); // set size of text field
        taskName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        taskName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(taskName); // add textlabel to task

        doneButton = new Button("Done"); // creates a button for marking the task as done
        doneButton.setPrefSize(100, 20);
        doneButton.setPrefHeight(Double.MAX_VALUE);
        doneButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        this.getChildren().add(doneButton);
    }

    public void setTaskIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.taskName.setPromptText("Task " + num);
    }

    public TextField getTaskName() {
        return this.taskName;
    }

    public Button getDoneButton() {
        return this.doneButton;
    }

    public boolean isMarkedDone() {
        return this.markedDone;
    }

    public void toggleDone() {
        markedDone = !markedDone;
        if(markedDone){
            this.setStyle("-fx-border-color: #000000; -fx-border-width: 0; -fx-font-weight: bold;"); // remove border of task
                for (int i = 0; i < this.getChildren().size(); i++) {
                    this.getChildren().get(i).setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;"); // change color of task to green
                }
            } else {
                for (int i = 0; i < this.getChildren().size(); i++) {
                    this.getChildren().get(i).setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task
                }
            }
        
    }



}

class TaskList extends VBox {

    TaskList() {
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

    public void updateTaskIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Task) {
                ((Task) this.getChildren().get(i)).setTaskIndex(index);
                index++;
            }
        }
    }

    public void removeCompletedTasks() {
        this.getChildren().removeIf(task -> task instanceof Task && ((Task) task).isMarkedDone());
        this.updateTaskIndices();
    }

    public void loadTasks() {
        try{
            File file = new File("tasks.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = "";
            while((line = reader.readLine()) != null){
                Task task = new Task();
                task.getTaskName().setText(line);
                this.getChildren().add(task);
                Button doneButton = task.getDoneButton();
                    doneButton.setOnAction(e1 -> {
                    // Call toggleDone on click
                    task.toggleDone();
                });
            }
            reader.close();
            updateTaskIndices();
        }
        catch (Exception e){
            System.out.println("tasks.txt was not found");
        }

    }

   
    public void saveTasks() {
        try{
            
        FileWriter file = new FileWriter("tasks.txt");
            for (int i = 0; i < this.getChildren().size(); ++i) {
                    if (this.getChildren().get(i) instanceof Task) {
                    Task task = (Task) this.getChildren().get(i);
                    String text = task.getTaskName().getText();
                    file.write(text + "\n");
                }
            }
            file.close();
        } catch (Exception e) {
            System.out.println("Error saving tasks");
            }
        
    }

    public void sortTasks() {
        ArrayList<String> taskListArr = new ArrayList<String>();
        //String taskString = "";
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Task) {
                Task task = (Task) this.getChildren().get(i);
                System.out.println(task.getTaskName().getText());
                taskListArr.add(task.getTaskName().getText());
                
            }
        }
        
        Collections.sort(taskListArr);
        this.getChildren().clear();

        for(int j = 0; j < taskListArr.size(); j++){
            Task task = new Task();
            task.getTaskName().setText(taskListArr.get(j));
            this.getChildren().add(task);
            Button doneButton = task.getDoneButton();
                    doneButton.setOnAction(e1 -> {
                    // Call toggleDone on click
                    task.toggleDone();
            });
        }

    }
}

class Footer extends HBox {

    private Button addContact, clearButton, loadButton, saveButton, sortButton;

    Footer() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addContact = new Button("Add Contact"); // text displayed on add button
        addContact.setStyle(defaultButtonStyle); // styling the button
        clearButton = new Button("Clear finished"); // text displayed on clear tasks button
        clearButton.setStyle(defaultButtonStyle);

        loadButton = new Button("Load Tasks"); 
        loadButton.setStyle(defaultButtonStyle);

        saveButton = new Button("Save Tasks");
        saveButton.setStyle(defaultButtonStyle);

        sortButton = new Button("Sort Tasks(By Name)");
        sortButton.setStyle(defaultButtonStyle);

        this.getChildren().addAll(addContact, clearButton,loadButton, saveButton, sortButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

    }

    public Button getAddContact() {
        return addContact;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getSortButton(){
        return sortButton;
    }
}

class Header extends HBox {

    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Contacts"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class AppFrame extends BorderPane{

    private Header header;
    private Footer footer;
    private TaskList taskList;

    private Button addButton;
    private Button clearButton;
    private Button saveButton;
    private Button loadButton;
    private Button sortButton;

    AppFrame()
    {
        // Initialise the header Object
        header = new Header();

        // Create a tasklist Object to hold the tasks
        taskList = new TaskList();
        
        // Initialise the Footer Object
        footer = new Footer();

        ScrollPane scroller = new ScrollPane(taskList);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scroller);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);

        // Initialise Button Variables through the getters in Footer
        addButton = footer.getAddContact();
        clearButton = footer.getClearButton();
        loadButton = footer.getLoadButton();
        saveButton = footer.getSaveButton();
        sortButton = footer.getSortButton();

        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners()
    {

        // Add button functionality
        addButton.setOnAction(e -> {
            // Create a new task
            Task task = new Task();
            // Add task to tasklist
            taskList.getChildren().add(task);
            // Add doneButtonToggle to the Done button
            Button doneButton = task.getDoneButton();
            doneButton.setOnAction(e1 -> {
                // Call toggleDone on click
                task.toggleDone();
            });
            

            // Update task indices
            taskList.updateTaskIndices();
        });
        
        // Clear finished tasks
        clearButton.setOnAction(e -> {
            taskList.removeCompletedTasks();
        });

        loadButton.setOnAction(e -> {
            taskList.loadTasks();
            taskList.updateTaskIndices();
        });

        saveButton.setOnAction(e -> {
            taskList.saveTasks();
        });

        sortButton.setOnAction(e -> {
            taskList.sortTasks();
            taskList.updateTaskIndices();
        });

    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the TaskList
        AppFrame root = new AppFrame();

        // Set the title of the app
        primaryStage.setTitle("Contact Management");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 500, 600));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}