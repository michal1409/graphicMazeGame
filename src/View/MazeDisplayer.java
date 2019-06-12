package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Aviadjo on 3/9/2017.
 */
public class MazeDisplayer extends Canvas {

    private int[][] maze;
    private int goalCol ;
    private int goalRow;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;

    public void setMaze(int[][] maze) {
        this.maze = maze;
        redraw();
    }

    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        redraw();
    }

    public void setGoalPosition(int row, int column) {
        goalRow = row;
        goalCol = column;
        redraw();
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public void redraw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;

            try {
                Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
                Image characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
                Image GoalImage = new Image(new FileInputStream(ImageFileNameGoal.get()));
                Image PathImage = new Image(new FileInputStream(ImageFileNamePath.get()));

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (maze[i][j] == 1) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage,j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                        else if(maze[i][j] == 8 && (i!=goalRow || j!=goalCol)){
                            gc.drawImage(PathImage,j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                        if(i==goalRow && j==goalCol){
                            gc.drawImage(GoalImage,j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                    }
                }

                //Draw Character
                //gc.setFill(Color.RED);
                //gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                gc.drawImage(characterImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                gc.drawImage(GoalImage,goalCol * cellHeight, goalRow* cellWidth, cellHeight, cellWidth);
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }
    }

    public void HidePath(){
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if(maze[i][j] == 8 ){
                    maze[i][j] = 0;
                }
            }
        }
        redraw();
    }

    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();
    private StringProperty ImageFileNamePath = new SimpleStringProperty();


    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

    public String getImageFileNameGoal() { return ImageFileNameGoal.get(); }


    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.ImageFileNameGoal.set(imageFileNameGoal);
    }

    public String getImageFileNamePath() {
        return ImageFileNamePath.get();
    }

    public void setImageFileNamePath(String imageFileNamePath) {
        this.ImageFileNamePath.set(imageFileNamePath);
    }

    public boolean isGoalReached(){
        if (characterPositionColumn==goalCol && characterPositionRow==goalRow)
            return true;
        else
            return false;
    }

    @Override
    public double minHeight(double width)
    {
        return 200;
    }

    @Override
    public double maxHeight(double width)
    {
        return 1000;
    }

    @Override
    public double prefHeight(double height)
    {
        return 600;
    }

    @Override
    public double prefWidth(double width)
    {
        return 600;
    }

    @Override
    public double minWidth(double height)
    {
        return 200;
    }

    @Override
    public double maxWidth(double height)
    {
        return 10000;
    }

    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public void resize(double width, double height)
    {
        super.setWidth(width);
        super.setHeight(height);
        redraw();
    }
    //endregion

}
