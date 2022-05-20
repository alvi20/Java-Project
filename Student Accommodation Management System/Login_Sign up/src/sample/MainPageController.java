package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController  {
    @FXML
    private VBox vbox;

    private String email;

    Person person = new Person();

    public void getEmail (String email){
        this.email = email;

        homeButtonPushed();
    }

    @FXML
    private void aboutUsButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutUs.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        AboutUsController controller = loader.getController();
        controller.getEmail(email);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("About Us");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void homeButtonPushed() {
        vbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PostsDisplay.fxml"));
        try {
            Parent parent = loader.load();
            PostsDisplayController controller = loader.getController();
            controller.setEmail(email);

            vbox.getChildren().add(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void logoutButtonPushed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void profileButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        person.setEmail(email);

        ProfileController controller = loader.getController();
        controller.setEmail(person);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }
}