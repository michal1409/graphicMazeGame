package Model;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyModel extends Observable implements IModel {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    //TODO - set the private properties from jar and check null and outOfBound
    private int characterPositionRow = 0; //to get start point
    private int characterPositionColumn = 0; //to get stars point
    private int[][] maze; // to build from jar
    private int maxRow = 20;
    private int maxCol = 20;


    public MyModel() {
        //Raise the servers
    }

    public void startServers() {
    }

    public void stopServers() {
    }

    public void generateMaze(int width, int height){
        threadPool.execute(() -> {
            IMazeGenerator mg = new MyMazeGenerator();
            Maze MyMaze = mg.generate(20, 20);
            this.maze = MyMaze.getData();
            this.characterPositionRow = MyMaze.getStartPosition().getRow();
            this.characterPositionColumn = MyMaze.getStartPosition().getCol();

            try {
                //TODO - check : is needed ??
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers();
        });
    }
    public void moveCharacter(KeyCode movement){
        //TODO - check if the move is no into wall or outOfBound
        switch (movement) {
            case UP:
                if(characterPositionRow>0 && notWall(characterPositionRow-1,characterPositionColumn)){characterPositionRow--;}
                break;
            case DOWN:
                if(characterPositionRow<this.maxRow-1 && notWall(characterPositionRow+1,characterPositionColumn) ){characterPositionRow++;}
                break;
            case RIGHT:
                if(characterPositionColumn<this.maxCol-1 && notWall(characterPositionRow,characterPositionColumn+1) ){characterPositionColumn++;}
                break;
            case LEFT:
                if(characterPositionColumn>0 && notWall(characterPositionRow,characterPositionColumn-1) ){characterPositionColumn--;}
                break;
        }
        setChanged();
        notifyObservers();
    }
    public int[][] getMaze(){
        return maze;
    }
    public int getCharacterPositionRow(){
        return characterPositionRow;
    }
    public int getCharacterPositionColumn(){
        return characterPositionColumn;
    }

    private boolean notWall(int row,int col){
        if(maze[row][col]==0){
            return true;
        }
        return false;
    }

}
