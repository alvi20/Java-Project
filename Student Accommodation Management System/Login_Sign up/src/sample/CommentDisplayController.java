package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CommentDisplayController {

    @FXML
    private Label userCommentName;

    @FXML
    private ImageView userCommentPhoto;

    @FXML
    private Label userCommentText;

    public void setComment(Comments comments){
        userCommentName.setText(comments.getUserName());
        userCommentText.setText(comments.getUserComment());
        userCommentPhoto.setImage(new Image("Image/login-icon-3042.png"));
    }


}
