package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostViewController {
    @FXML
    private ImageView userPhotoIV;

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label areaLocation;

    @FXML
    private Label bedLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label time;

    @FXML
    private VBox commentVBox;

    String email = null;

    DatabaseConnection connection = new DatabaseConnection();
    Connection connectDB = connection.getConnection();

    public void setData(Post post){
        getImageView(post.getUserProfileId());
        nameLabel.setText(post.getName());
        time.setText(post.getTime());
        descriptionLabel.setText(post.getDescription());
        areaLocation.setText(post.getArea());
        locationLabel.setText(post.getLocation());
        bedLabel.setText(String.valueOf(post.getBed()));
        genderLabel.setText(post.getGender());
        priceLabel.setText(String.valueOf(post.getPrice()));
    }

    private void getImageView(String _userProfileId){
        String inputImage = "select * from useraccounts where idUserAccounts = '" + _userProfileId +"'";
        Statement statement = null;
        try {
            statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(inputImage);
            while (resultSet.next()){
                InputStream is = resultSet.getBinaryStream("UserPhoto");
                if(is != null){
                    Image image = new Image(is,200, 200, true, true);
                    userPhotoIV.setImage(image);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void askQuestionHBoxClick() throws IOException {
        commentVBox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentView.fxml"));
        Parent root = loader.load();

        CommentViewController controller = loader.getController();
        controller.getEmail(email);

        commentVBox.getChildren().add(root);
    }


}
