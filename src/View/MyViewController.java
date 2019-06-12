package View;

import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class MyViewController extends ControllerAbstract {

    @FXML
    public MazeDisplayer mazeDisplayer;
    //public javafx.scene.control.TextField txtfld_rowsNum;
    //public javafx.scene.control.TextField txtfld_columnsNum;
    public javafx.scene.control.Label lbl_rowsNum;
    public javafx.scene.control.Label lbl_columnsNum;
    public javafx.scene.control.Button btn_generateMaze;
    public javafx.scene.control.Button btn_solveMaze;
    private int[][] maze;
    private MediaPlayer media1;
    private MediaPlayer media2;
    private boolean pressed = false;


    private void bindProperties(MyViewModel viewModel) {
        lbl_rowsNum.textProperty().bind(viewModel.characterPositionRow);
        lbl_columnsNum.textProperty().bind(viewModel.characterPositionColumn);

        //this.maze = viewModel.getMaze();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayMaze(viewModel.getMaze());
            btn_generateMaze.setDisable(false);

        }
    }

    @Override
    public void displayMaze(int[][] maze) {
        mazeDisplayer.setMaze(maze);
        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
        int goalPositionRow = viewModel.getGoalPositionRow();
        int goalPositionColumn = viewModel.getGoalPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        mazeDisplayer.setGoalPosition(goalPositionRow, goalPositionColumn);
        this.characterPositionRow.set("" + characterPositionRow);
        this.characterPositionColumn.set("" + characterPositionColumn);

    }

    @Override
    public void displayLogin() {
        //not implemented
    }

    public void generateMaze() {
        musicBackground();
        bindProperties(viewModel);
        this.maze = viewModel.getMaze();
        //todo - set the correct values
        //int heigth = Integer.valueOf(txtfld_rowsNum.getText());
        //int width = Integer.valueOf(txtfld_columnsNum.getText());
        int heigth = viewModel.getRowsInput();
        int width = viewModel.getColInput();
        btn_generateMaze.setDisable(true);
        viewModel.generateMaze(width, heigth);
    }

    public void solveMaze(ActionEvent actionEvent) {
        if (!pressed) {
            this.maze = viewModel.getSolution();
            pressed = !pressed;
        } else {
            mazeDisplayer.HidePath();
            pressed = !pressed;
        }

    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
        if (mazeDisplayer.isGoalReached()) {
            musicSuccess();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Dress up like a rock star. Tell girls you play bass in a band");
            alert.setTitle("Congratulations!!!");
            alert.show();


        }
    }

    //region String Property for Binding
    public StringProperty characterPositionRow = new SimpleStringProperty();

    public StringProperty characterPositionColumn = new SimpleStringProperty();

    public String getCharacterPositionRow() {
        return characterPositionRow.get();
    }

    public StringProperty characterPositionRowProperty() {
        return characterPositionRow;
    }

    public String getCharacterPositionColumn() {
        return characterPositionColumn.get();
    }

    public StringProperty characterPositionColumnProperty() {
        return characterPositionColumn;
    }

    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Height: " + newSceneHeight);
            }
        });
        mazeDisplayer.resize(scene.getWidth(), scene.getHeight());
    }

    public void About(ActionEvent actionEvent) {
        //showAboutAlert();
        try {
            Stage stage = new Stage();
            stage.setTitle("About the game");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 246, 192);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Help(ActionEvent actionEvent) {
        //showAboutAlert();
        try {
            Stage stage = new Stage();
            stage.setTitle("Help!");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Help.fxml").openStream());
            Scene scene = new Scene(root, 246, 192);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void mouseClicked(MouseEvent mouseEvent) {

        this.mazeDisplayer.requestFocus();

    }


    public void HidePath(ActionEvent actionEvent) {

        mazeDisplayer.HidePath();
    }

    public void SaveMaze(ActionEvent actionEvent) {

    }

    public void LoadMaze(ActionEvent actionEvent) {

    }

    public void musicBackground() {
        if (media1!=null){
            media1.stop();
        }
        Media sound = new Media(Paths.get("backgroundSound.mp3").toUri().toString());
        media1 = new MediaPlayer(sound);
        media1.play();
        media1.setVolume(0.2);
    }

    public void musicSuccess() {
                Media play = new Media(Paths.get("Legendary.mp3").toUri().toString());
        media2 = new MediaPlayer(play);
        media2.play();
        media2.setVolume(0.4);
        media1.stop();
    }
    public void closeStage(){
        Platform.exit();
    }
    private void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    // Close program
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }



    //endregion
    //todo - save maze : use file chooser to save the maze.


}