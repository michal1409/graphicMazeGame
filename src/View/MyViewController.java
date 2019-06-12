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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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



    private void bindProperties(MyViewModel viewModel) {
        String s1=null;
        String s2=null;
        StringProperty characterRow = new SimpleStringProperty(s1.valueOf(viewModel.getRowsInput()));
        StringProperty characterCol = new SimpleStringProperty(s1.valueOf(viewModel.getColInput()));
        lbl_rowsNum.textProperty().bind(characterRow);
        lbl_columnsNum.textProperty().bind(characterCol);
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
        this.characterPositionRow.set(""+ characterPositionRow);
        this.characterPositionColumn.set("" + characterPositionColumn);
    }

    @Override
    public void displayLogin() {
        //not implemented
    }

    public void generateMaze() {
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
        this.maze = viewModel.getSolution();
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
        mazeDisplayer.resize(scene.getWidth(),scene.getHeight());
    }

    public void About(ActionEvent actionEvent) {
        //showAboutAlert();
        try {
            Stage stage = new Stage();
            stage.setTitle("About the game");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
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

    public void SaveMaze(ActionEvent actionEvent){

    }

    public void LoadMaze(ActionEvent actionEvent){

    }

    //endregion
    //todo - save maze : use file chooser to save the maze.

}
