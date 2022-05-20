package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfileAboutController {

    @FXML
    private Label birthday;

    @FXML
    private Label email;

    @FXML
    private Label gender;

    @FXML
    private Label name;

    String nameS;

    void getEmail(String _email) throws SQLException {
        email.setText(_email);
        getData();
    }

    void getData() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        String data = "SELECT * FROM useraccounts WHERE Email = '" + email.getText() + "'";

        Statement statement = connectDB.createStatement();
        ResultSet resultSet = statement.executeQuery(data);

        while (resultSet.next()){
            nameS = resultSet.getString("Firstname") + " " + resultSet.getString("Lastname");
            name.setText(nameS);
            gender.setText(resultSet.getString("Gender"));
            birthday.setText(resultSet.getString("Birthday"));
        }
    }

}
