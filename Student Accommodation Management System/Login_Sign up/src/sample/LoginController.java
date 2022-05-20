package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private Label errorLabel;

    @FXML private PasswordField passwordPF;

    @FXML private TextField emailTextField;

    @FXML private CheckBox showHideCheckBoxLogin;

    @FXML private TextField showPassTFLogin;

    @FXML
    private void signUpPageButtonPushed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("SIGN UP");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        showPassTFLogin.setManaged(false);
        showPassTFLogin.setVisible(false);

        showPassTFLogin.managedProperty().bind(showHideCheckBoxLogin.selectedProperty());
        showPassTFLogin.visibleProperty().bind(showHideCheckBoxLogin.selectedProperty());

        passwordPF.managedProperty().bind(showHideCheckBoxLogin.selectedProperty().not());
        passwordPF.visibleProperty().bind(showHideCheckBoxLogin.selectedProperty().not());

        showPassTFLogin.textProperty().bindBidirectional(passwordPF.textProperty());
    }

    @FXML
    private void loginButton(ActionEvent event) throws Exception{
        if(emailTextField.getText().trim().isEmpty() || passwordPF.getText().trim().isEmpty()){
            errorLabel.setText("Please try again!");
        }
        else {
            validateLogin(event);
        }
    }

    public void validateLogin(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectBD = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM useraccounts WHERE Email = '" + emailTextField.getText() + "' AND password = '" + passwordPF.getText() + "'";

        try {
            Statement statement = connectBD.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);

            while (resultSet.next()){
                if (resultSet.getInt(1) == 1){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root,1440,900);

                    MainPageController controller = loader.getController();
                    controller.getEmail(emailTextField.getText());

                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setTitle("Accommodation");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                }else {
                    errorLabel.setText("Invalid Login. Please enter valid Email and Password");
                    showPassTFLogin.clear();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
