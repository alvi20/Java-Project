package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileController {
    @FXML
    private ImageView userPhotoIV;

    @FXML
    private VBox vbox;

    @FXML
    private Label fullName;

    private String name= null;

    private String email = null;

    private List<Post> allPosts = new ArrayList<>();

    PreparedStatement pst = null;

    DatabaseConnection connection = new DatabaseConnection();
    Connection connectDB = connection.getConnection();

    public void setEmail (Person someone) throws IOException {
        email = someone.getEmail();
        getImageView(email);
        getData();
        fullName.setText(name);
        postsButtonPushed();
    }

    @FXML
    private void aboutPushed() throws IOException, SQLException {
        vbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileAbout.fxml"));
        Parent parent = loader.load();

        ProfileAboutController controller = loader.getController();
        controller.getEmail(email);

        vbox.getChildren().add(parent);
    }

    @FXML
    private void aboutUsButtonPushed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AboutUs.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("About Us");
        stage.setScene(scene);
        stage.show();
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
    public void postsButtonPushed() throws IOException {
        vbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PostsDisplay.fxml"));
        Parent parent = loader.load();

        PostsDisplayController controller = loader.getController();
        controller.getEmail(email);

        vbox.getChildren().add(parent);
    }

    void getData(){
        String getData = "SELECT * FROM useraccounts WHERE Email = '" + email + "'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(getData);

            while (resultSet.next()) {
                String postUserId = resultSet.getString("idUserAccounts");
                name = resultSet.getString("Firstname") + " " + resultSet.getString("Lastname");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void homeButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent parent = loader.load();

        MainPageController controller = loader.getController();
        controller.getEmail(email);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Accommodation");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @FXML
    private void changePhotoButtonPushed(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Photo");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png", "*.jpg"));

        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            try {
                FileInputStream fis = new FileInputStream(file);
                String imageUpdate = "Update useraccounts set UserPhoto = ? where Email = '" + email + "'";
                pst = connectDB.prepareStatement(imageUpdate);
                pst.setBinaryStream(1,fis,fis.available());

                pst.executeUpdate();

                String inputImage = "select * from useraccounts where Email = '" + email +"'";
                Statement statement = connectDB.createStatement();
                ResultSet resultSet = statement.executeQuery(inputImage);
                while (resultSet.next()){
                    InputStream is = resultSet.getBinaryStream("UserPhoto");
                    Image image = new Image(is,200, 200, true, true);
                    userPhotoIV.setImage(image);
                }
                pst.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void getImageView(String _email){
        String inputImage = "select * from useraccounts where Email = '" + _email +"'";
        Statement statement = null;
        try {
            statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(inputImage);
            while (resultSet.next()){
                InputStream is = resultSet.getBinaryStream("UserPhoto");
                if(is != null){
                    Image image = new Image(is,200, 200, true, true);
                    userPhotoIV.setImage(image);
                }else {
                    userPhotoIV.setImage(new Image("Image/login-icon-3042.png"));
                    userPhotoIV.setFitHeight(200);
                    userPhotoIV.setFitWidth(200);
                    userPhotoIV.setSmooth(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}