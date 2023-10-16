package ImageUploader;

// Add necessary imports
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.File;

// JavaFX Application main entry point
public class Main extends Application {

    // To display images
    private ImageView imageView = new ImageView();

    // To open a file dialog for selecting images
    private FileChooser fileChooser = new FileChooser();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Uploader");

        Button uploadButton = new Button("Upload Image");

        // Call uploadImage method on button click
        uploadButton.setOnAction(e -> uploadImage(primaryStage));

        VBox vbox = new VBox(uploadButton, imageView);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void uploadImage(Stage primaryStage) {

        // Select which extensions are allowed
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);


        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            imageView.setImage(image);

            // Resize the window to fit the image
            primaryStage.setWidth(image.getWidth() + 100);
            primaryStage.setHeight(image.getHeight() + 100);
        }
    }
}
