package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostTask extends Task<Void> {

    List<Post> allPosts = new ArrayList<>();

    VBox vBox;

    String email=null;

    public PostTask(VBox vBox) {
        this.vBox = vBox;
    }

    public PostTask(VBox vBox, String _email) {
        this.vBox = vBox;
        this.email = _email;
    }

    @Override
    protected Void call() {
        try {
            if(email==null){
                allPosts = allPosts();
            }
            else {
                allPosts = UserPosts();
            }
            for (int i= allPosts.size()-1; i>=0; i--){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PostView.fxml"));
                Parent root = loader.load();

                PostViewController controller = loader.getController();
                controller.setData(allPosts.get(i));

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        vBox.getChildren().add(root);
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Post> allPosts() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        Statement statement = null;
        try {
            statement = connectDB.createStatement();

            String getData2 = "SELECT * FROM userpost";

            ResultSet resultSet2 = statement.executeQuery(getData2);

            allPosts.clear();
            while (resultSet2.next()){
                Post post = new Post();
                post.setUserProfileId(resultSet2.getString("idUserPost"));
                post.setName(resultSet2.getString("UserName"));
                post.setTime(resultSet2.getString("Time"));
                post.setDescription(resultSet2.getString("Description"));
                post.setArea(resultSet2.getString("Area"));
                post.setLocation(resultSet2.getString("Location"));
                post.setBed(resultSet2.getInt("Bed"));
                post.setGender(resultSet2.getString("Gender"));
                post.setPrice(resultSet2.getInt("Price"));

                allPosts.add(post);
            }
            resultSet2.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allPosts;
    }

    private List<Post> UserPosts() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        String getData = "SELECT * FROM useraccounts WHERE Email = '" + email + "'";

        Statement statement = null;
        try {
            statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(getData);

            String postUserId = null;
            while (resultSet.next()) {
                postUserId = resultSet.getString("idUserAccounts");
            }

            String getData2 = "SELECT * FROM userpost WHERE idUserPost = '" + postUserId + "'";

            ResultSet resultSet2 = statement.executeQuery(getData2);

            allPosts.clear();
            while (resultSet2.next()){
                Post post = new Post();
                post.setUserProfileId(resultSet2.getString("idUserPost"));
                post.setName(resultSet2.getString("UserName"));
                post.setTime(resultSet2.getString("Time"));
                post.setDescription(resultSet2.getString("Description"));
                post.setArea(resultSet2.getString("Area"));
                post.setLocation(resultSet2.getString("Location"));
                post.setBed(resultSet2.getInt("Bed"));
                post.setGender(resultSet2.getString("Gender"));
                post.setPrice(resultSet2.getInt("Price"));

                allPosts.add(post);
            }
            resultSet2.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allPosts;
    }
}
