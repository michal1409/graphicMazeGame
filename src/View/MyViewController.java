package View;

import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.file.Paths;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyViewController extends ControllerAbstract {

    @FXML
    public MazeDisplayer mazeDisplayer;
    public BorderPane BorderPane;
    public javafx.scene.control.Button btn_generateMaze;
    public javafx.scene.control.Button btn_solveMaze;
    private int[][] maze;

    private MediaPlayer media1;
    private MediaPlayer media2;
    private boolean pressed = false;
    int heigth;
    int width;


    private void bindProperties(MyViewModel viewModel) {
        mazeDisplayer.heightProperty().bind(BorderPane.heightProperty().multiply(0.9));
        mazeDisplayer.widthProperty().bind(BorderPane.widthProperty().multiply(0.7));
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
        musicBackground();
        bindProperties(viewModel);
        this.maze = viewModel.getMaze();
        //todo - set the correct values
        //int heigth = Integer.valueOf(txtfld_rowsNum.getText());
        //int width = Integer.valueOf(txtfld_columnsNum.getText());
        heigth = viewModel.getRowsInput();
        width = viewModel.getColInput();
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
        //long width = 0;
        //long height = 0;
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
        mazeDisplayer.redraw();
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

    public void saveMaze() {
        String mazeDetails = String.valueOf(heigth) + " " + String.valueOf(width)+ " " + String.valueOf(this.viewModel.getCharacterPositionColumn()+" "
        + String.valueOf(this.viewModel.getCharacterPositionRow()+" "+String.valueOf(this.viewModel.getGoalPositionColumn()+" "+
                String.valueOf(this.viewModel.getGoalPositionRow()))));

        int [][] maze = this.viewModel.getMaze();
        for (int i=0; i<maze.length; i++){
            for (int j=0; j<maze.length; j++){
                if (maze[i][j]==1)
                    mazeDetails=mazeDetails+" "+String.valueOf(1);
                else
                    mazeDetails=mazeDetails+" "+String.valueOf(0);
            }
        }



        Text sample = new Text(mazeDetails);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(this.stageCon);
        if (file != null) {
            saveTextToFile(mazeDetails, file);
        }
    }
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(MyViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readMaze() {
        FileChooser fileChooser = new FileChooser();
        TextArea textArea = TextAreaBuilder.create()
                .prefWidth(400)
                .wrapText(true)
                .build();
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(this.stageCon);
        if(file != null){
            textArea.setText(readFile(file));
            String mazeTexe = textArea.getText();
            String[] splited = mazeTexe.split("\\s+");
            this.viewModel.setRowsInput(Integer.parseInt(splited[0]));
            this.viewModel.setColInput(Integer.parseInt(splited[1]));
            this.maze = new int[Integer.parseInt(splited[0])][Integer.parseInt(splited[1])];
            int index=6;
            for (int i=0; i<Integer.parseInt(splited[0]); i++) {
                for (int j = 0; j < Integer.parseInt(splited[0]); j++) {
                    maze[i][j] = Integer.parseInt(splited[index]);
                    index++;
                }
            }
            musicBackground();
            bindProperties(viewModel);
            heigth = viewModel.getRowsInput();
            width = viewModel.getColInput();
            btn_generateMaze.setDisable(true);
            viewModel.generateReadMaze(width, heigth,maze, Integer.parseInt(splited[2]), Integer.parseInt(splited[3]), Integer.parseInt(splited[4]), Integer.parseInt(splited[5]));

        }

    }

    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyViewController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(MyViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

    public void handleScroll(ScrollEvent event) {
        if (!event.isControlDown()) return;
        if (mazeDisplayer.getScaleX() <= 1 && mazeDisplayer.getScaleX() >= 0.25) {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }

            mazeDisplayer.setScaleX(mazeDisplayer.getScaleX() * zoomFactor);
            mazeDisplayer.setScaleY(mazeDisplayer.getScaleY() * zoomFactor);
        } else if (mazeDisplayer.getScaleX() > 1) {
            mazeDisplayer.setScaleX(1);
            mazeDisplayer.setScaleY(1);
        } else if (mazeDisplayer.getScaleX() < 0.25) {
            mazeDisplayer.setScaleX(0.25);
            mazeDisplayer.setScaleY(0.25);
        }
        event.consume();
    }

    //endregion
    //todo - save maze : use file chooser to save the maze.

}
