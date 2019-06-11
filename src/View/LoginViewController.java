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
import java.util.Observable;
import java.util.Observer;

public class LoginViewController implements Observer,IView {

    @FXML
    private MyViewModel viewModel;
    public LoginDisplayer LoginDisplayer;
    public javafx.scene.control.TextField txtfld_rowsNum;
    public javafx.scene.control.TextField txtfld_columnsNum;
    public javafx.scene.control.Label lbl_rowsNum;
    public javafx.scene.control.Label lbl_columnsNum;
    public javafx.scene.control.Button btn_generateMaze;


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        bindProperties(viewModel);
        displayLogin();
    }

    private void bindProperties(MyViewModel viewModel) {
        //lbl_rowsNum.textProperty().bind(viewModel.characterPositionRow);
        //lbl_columnsNum.textProperty().bind(viewModel.characterPositionColumn);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayLogin();
            btn_generateMaze.setDisable(false);
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

    public void generateMaze() {
        int height = Integer.valueOf(txtfld_rowsNum.getText());
        int width = Integer.valueOf(txtfld_columnsNum.getText());
        btn_generateMaze.setDisable(true);
        viewModel.generateMaze(width, height);
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
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        this.LoginDisplayer.requestFocus();
    }

    //endregion

}
