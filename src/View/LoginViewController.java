package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class LoginViewController extends ControllerAbstract {

    @FXML
    public LoginDisplayer LoginDisplayer;
    @FXML
    public javafx.scene.control.Button startGameButton;
    @FXML
    public javafx.scene.image.ImageView image1;
    @FXML
    public javafx.scene.control.TextField rowNumInput;
    @FXML
    public javafx.scene.control.TextField colNumInput;

    private MediaPlayer media;


    public void OnButtonPressed() throws IOException {
        if (rowNumInput==null || colNumInput==null ||isInteger(rowNumInput.getText(),10) && isInteger(colNumInput.getText(),10)){
            viewModel.setColInput(Integer.valueOf(rowNumInput.getText()));
            viewModel.setRowsInput(Integer.valueOf(colNumInput.getText()));
            System.out.println(viewModel.colInput);
            System.out.println(viewModel.rowsInput);
            // open the next scene
            Stage stage = new Stage();
            stage.setTitle("Maze");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/MyView.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1000, 800);
            scene.getStylesheets().add(getClass().getResource("../View/View.css").toExternalForm());
            ControllerAbstract view = fxmlLoader.getController();
            //MyViewController view = fxmlLoader.getController();

            view.setViewModel(viewModel);
            //view.setResizeEvent(scene);
            viewModel.addObserver(view);
            stage.setScene(scene);
            stage.show();


        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Insert numbers!");
            alert.setTitle("Error");
            alert.show();
        }
    }

    public boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                return false;

            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayLogin();
            startGameButton.setDisable(false);
        }
    }

    @Override
    public void displayMaze(int[][] maze){
        //not implemented;
        //LoginDisplayer.setLogin();
    }

    @Override
    public void displayLogin() {
        LoginDisplayer.setLogin();
    }


    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    //region String Property for Binding
    public StringProperty PositionRow = new SimpleStringProperty();

    public StringProperty PositionColumn = new SimpleStringProperty();

    public String getPositionRow() {
        return PositionRow.get();
    }

    public StringProperty PositionRowProperty() {
        return PositionRow;
    }

    public String getPositionColumn() {
        return PositionColumn.get();
    }

    public StringProperty PositionColumnProperty() {
        return PositionColumn;
    }
/*
    @Override
    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
            }
        });

    }
*/
    public void mouseClicked(MouseEvent mouseEvent) {
        this.LoginDisplayer.requestFocus();
    }

    public void musicBackground(){
        Media musicFile = new Media("../Images/backgroundSound.mp3");
        media=new MediaPlayer(musicFile);
        media.play();
        media.setVolume(0.3);

    }

    //endregion

}
