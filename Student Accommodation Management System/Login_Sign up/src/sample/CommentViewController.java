package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommentViewController {

    @FXML
    private TextArea commentTestArea;

    @FXML
    private VBox commentViewVBox;

    String userId,name;

    private String email;

    DatabaseConnection connection = new DatabaseConnection();
    Connection connectDB = connection.getConnection();


    public void getEmail(String _email){
        email= _email;
    }

    @FXML
    private void onEnterButtonTap() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentDisplay.fxml"));
        Parent root = loader.load();

        userId = "1";
        name = "Mahadi";

        Comments comments = new Comments();
        comments.setUserComment(commentTestArea.getText());
        comments.setUserId(userId);
        comments.setUserName(name);

        CommentDisplayController controller = loader.getController();
        controller.setComment(comments);

        commentViewVBox.getChildren().add(root);

    }

}
