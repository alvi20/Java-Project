package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class signUpController implements Initializable {

    @FXML private PasswordField confirmPasswordPF;

    @FXML private TextField emailTextField;

    @FXML private TextField firstNameTextField;

    @FXML private TextField lastNameTextField;

    @FXML private PasswordField passwordPF;

    @FXML private Label errorLabel;

    @FXML private DatePicker birthdayPicker;

    @FXML private ComboBox genderComboBox;

    @FXML private TextField showConfirmTF;

    @FXML private CheckBox showHideCheckBox;

    @FXML private TextField showpassTF;

    public void loginPageButtonPushed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.show();
    }

    public void signUpButtonPushed(ActionEvent event) throws IOException {
        if(firstNameTextField.getText().trim().isEmpty() || lastNameTextField.getText().trim().isEmpty() ||
                emailTextField.getText().trim().isEmpty() ||
                passwordPF.getText().trim().isEmpty() || confirmPasswordPF.getText().trim().isEmpty() ||
                genderComboBox.getValue() ==null || birthdayPicker.getValue() == null)
        {
            errorLabel.setText("Please, fill up all the blanks!");
        }
        else if(!passwordPF.getText().matches(".*\\d.*")){
            errorLabel.setText("Password should contain Digit!");
        }
        else if(!passwordPF.getText().equals(confirmPasswordPF.getText())){
            errorLabel.setText("Sorry, confirm password doesn't not match with the password");
        }
        else {
            registerUser(event);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        errorLabel.setText("");

        genderComboBox.setPromptText("Gender");
        genderComboBox.getItems().addAll("Male","Female");

        showpassTF.setManaged(false);
        showpassTF.setVisible(false);

        showConfirmTF.setManaged(false);
        showConfirmTF.setVisible(false);

        showpassTF.managedProperty().bind(showHideCheckBox.selectedProperty());
        showpassTF.visibleProperty().bind(showHideCheckBox.selectedProperty());

        showConfirmTF.managedProperty().bind(showHideCheckBox.selectedProperty());
        showConfirmTF.visibleProperty().bind(showHideCheckBox.selectedProperty());

        passwordPF.managedProperty().bind(showHideCheckBox.selectedProperty().not());
        passwordPF.visibleProperty().bind(showHideCheckBox.selectedProperty().not());

        confirmPasswordPF.managedProperty().bind(showHideCheckBox.selectedProperty().not());
        confirmPasswordPF.visibleProperty().bind(showHideCheckBox.selectedProperty().not());

        showpassTF.textProperty().bindBidirectional(passwordPF.textProperty());
        showConfirmTF.textProperty().bindBidirectional(confirmPasswordPF.textProperty());
    }

    public void registerUser(ActionEvent event){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        String firstname = firstNameTextField.getText();
        String lastname = lastNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordPF.getText();
        String gender = genderComboBox.getValue().toString();
        String birthday = birthdayPicker.getValue().toString();

        String insertFields = "INSERT INTO useraccounts (Firstname, Lastname, Email, Password, Gender, Birthday) VALUES ('";
        String insertValues = firstname +"' , '"+  lastname +"' , '"+ email +"' , '"+ password +"' , '"+ gender +"' , '"+ birthday +"')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,1440,900);

            MainPageController controller = loader.getController();
            controller.getEmail(email);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Accommodation");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
