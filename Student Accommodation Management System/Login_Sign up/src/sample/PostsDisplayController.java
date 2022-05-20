package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PostsDisplayController implements Initializable {

    @FXML private ChoiceBox<String> areaChoiceBox;

    @FXML private ChoiceBox<String> bedChoiceBox;

    @FXML private TextArea descriptionTextArea;

    @FXML private RadioButton femaleRadioBox;

    @FXML private ChoiceBox<String> locationChoiceBox;

    @FXML private RadioButton maleRadioBox;

    @FXML private TextField priceTextField;

    @FXML private Label errorLabel;

    @FXML private VBox vbox;

    private String name= null;

    private String email = null;

    private String postUserId = null;

    private List<Post> allPosts = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Grouping radioButton
        ToggleGroup toggleGroup = new ToggleGroup();
        maleRadioBox.setToggleGroup(toggleGroup);
        femaleRadioBox.setToggleGroup(toggleGroup);

        locationChoiceBox.getItems().add("Dhaka");
        locationChoiceBox.setValue("Dhaka");

        areaChoiceBox.getItems().addAll("Bashundhara R/A", "Baridhara", "Banani", "Dhanmondi",
                "Mirpur 1", "Mirpur 2", "Mirpur 6", "Mirpur 7", "Mirpur 10",
                "Mirpur 11", "Mirpur 12", "Mirpur 13", "Mirpur 14");
        areaChoiceBox.setValue("Bashundhara R/A");

        bedChoiceBox.getItems().addAll("1" , "2" , "3" , "4");
    }

    public void getEmail(String _email){
        email=_email;
        PostTask postTask = new PostTask(vbox,email);
        Thread thread = new Thread(postTask);
        thread.setDaemon(true);
        thread.start();

    }

    public void setEmail(String _email){
        email = _email;
        PostTask postTask = new PostTask(vbox);
        Thread thread = new Thread(postTask);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void submitButtonPushed() throws IOException {
        if(areaChoiceBox.getValue() != null && locationChoiceBox.getValue() != null && bedChoiceBox != null && (maleRadioBox.isSelected() || femaleRadioBox.isSelected())
            && !priceTextField.getText().isEmpty()) {
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();

            String gender = "";
            if (maleRadioBox.isSelected()) {
                gender = "Male";
            } else if (femaleRadioBox.isSelected()) {
                gender = "female";
            }

            String getData = "SELECT * FROM useraccounts WHERE Email = '" + email + "'";

            Statement statement = null;
            try {
                statement = connectDB.createStatement();
                ResultSet resultSet = statement.executeQuery(getData);

                while (resultSet.next()) {
                    postUserId = resultSet.getString("idUserAccounts");
                    name = resultSet.getString("Firstname") + " " + resultSet.getString("Lastname");
                }
                resultSet.close();


                String time = String.valueOf(LocalDate.now());

                String insertFields = "INSERT INTO userpost (idUserPost, UserName, Area, Location , Bed , Gender , Price , Description, Time) VALUES ('";
                String insertValues = postUserId + "' , '" + name + "' , '" + areaChoiceBox.getValue() + "' , '" + locationChoiceBox.getValue() + "' , '" +
                        bedChoiceBox.getValue() + "' , '" + gender + "' , '" + priceTextField.getText() + "' , '" + descriptionTextArea.getText() + "' , '" + time + "')";
                String insertToRegister = insertFields + insertValues;

                statement.executeUpdate(insertToRegister);

                Post post = new Post();
                post.setUserProfileId(postUserId);
                post.setName(name);
                post.setTime(time);
                post.setDescription(descriptionTextArea.getText());
                post.setArea(String.valueOf(areaChoiceBox.getValue()));
                post.setLocation(String.valueOf(locationChoiceBox.getValue()));
                post.setBed(Integer.parseInt(String.valueOf(bedChoiceBox.getValue())));
                post.setGender(gender);
                post.setPrice(Integer.parseInt(priceTextField.getText()));
                allPosts.add(post);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("PostView.fxml"));
                Parent root = loader.load();

                PostViewController controller = loader.getController();
                controller.setData(post);

                vbox.getChildren().add(0, root);

                priceTextField.clear();
                descriptionTextArea.clear();
                femaleRadioBox.setSelected(false);
                maleRadioBox.setSelected(false);
                bedChoiceBox.setValue("");
                areaChoiceBox.setValue("Bashundhara R/A");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connectDB.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            errorLabel.setText("Please fill up all the blanks!");
        }

    }

    @FXML
    private void onEnterMouseDescriptionTextArea() {
        descriptionTextArea.setMinHeight(200);
    }

    @FXML
    private void onExitMouseDescriptionTextArea() {
        descriptionTextArea.setMinHeight(40);
    }
}
