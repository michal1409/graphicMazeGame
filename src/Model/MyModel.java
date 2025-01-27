package Model;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyModel extends Observable implements IModel {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    //TODO - set the private properties from jar and check null and outOfBound
    private int characterPositionRow = 0; //to get start point
    private int characterPositionColumn = 0; //to get stars point
    private int[][] maze; // to build from jar
    private Maze mazeObject;
    private int maxRow;
    private int maxCol;
    private int goalRow;
    private int goalCol;


    public MyModel() {
        //Raise the servers
    }

    public void startServers() {
    }

    public void stopServers() {
    }

    public void generateMaze(int col, int row){
        threadPool.execute(() -> {
            IMazeGenerator mg = new MyMazeGenerator();
            Maze MyMaze = mg.generate(row, col);
            this.maze = MyMaze.getData();
            //MyMaze.print();
            this.mazeObject = MyMaze;
            this.characterPositionRow = MyMaze.getStartPosition().getRow();
            this.characterPositionColumn = MyMaze.getStartPosition().getCol();
            this.maxRow = row;
            this.maxCol = col;
            this.goalRow = MyMaze.getGoalPosition().getRow();
            this.goalCol =MyMaze.getGoalPosition().getCol();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers();
        });
    }

    public void generateReadMaze(int col, int row, int[][] maze, int posCol, int posRow, int goalCol, int goalRow){
        threadPool.execute(() -> {
            IMazeGenerator mg = new MyMazeGenerator();
            Maze MyMaze = mg.generate(row, col);
            this.maze = maze;
            //MyMaze.print();
            this.mazeObject = MyMaze;
            this.characterPositionRow = posRow;
            this.characterPositionColumn = posCol;
            this.maxRow = row;
            this.maxCol = col;
            this.goalRow = goalRow;
            this.goalCol =goalCol;

            try {
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
            case DIGIT2:
                if(characterPositionRow>0 && notWall(characterPositionRow-1,characterPositionColumn)){characterPositionRow--;}
                break;
            case DIGIT8:
                if(characterPositionRow<this.maxRow-1 && notWall(characterPositionRow+1,characterPositionColumn) ){characterPositionRow++;}
                break;
            case DIGIT6:
                if(characterPositionColumn<this.maxCol-1 && notWall(characterPositionRow,characterPositionColumn+1) ){characterPositionColumn++;}
                break;
            case DIGIT4:
                if(characterPositionColumn>0 && notWall(characterPositionRow,characterPositionColumn-1) ){characterPositionColumn--;}
                break;
            case DIGIT1:
                if(characterPositionRow>0 && characterPositionColumn>0 && notWall(characterPositionRow-1,characterPositionColumn-1)){characterPositionRow--; characterPositionColumn--;}
                break;
            case DIGIT3:
                if(characterPositionColumn<this.maxCol-1 && characterPositionRow>0 && notWall(characterPositionRow-1,characterPositionColumn+1) ){characterPositionRow--; characterPositionColumn++;}
                break;
            case DIGIT7:
                if(characterPositionRow<this.maxRow-1 && characterPositionColumn>0 && notWall(characterPositionRow+1,characterPositionColumn-1) ){characterPositionColumn--; characterPositionRow++;}
                break;
            case DIGIT9:
                if(characterPositionColumn<this.maxCol-1 && characterPositionRow<this.maxRow-1 && notWall(characterPositionRow+1,characterPositionColumn+1) ){characterPositionRow++; characterPositionColumn++;}
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

    public int[][] getSolution(int startRow,int startCol){
        mazeObject.setStartPosition(startRow,startCol);
        SearchableMaze searchableMaze = new SearchableMaze(this.mazeObject);
        ISearchingAlgorithm searcher = new BreadthFirstSearch();
        Solution solution = searcher.solve(searchableMaze);
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        int[][] solved = this.maze.clone();
        for (int i = 0; i < solutionPath.size(); i++) {
            MazeState m = (MazeState)solutionPath.get(i);
            Position p = m.getMyPosition();
            int row = p.getRow();
            int col = p.getCol();
            solved[row][col] = 8;
        }
        setChanged();
        notifyObservers();
        return solved;
    }

    private boolean notWall(int row,int col){
        if(maze[row][col]==0 || maze[row][col]==8){
            return true;
        }
        return false;
    }

    public int getGoalPositionRow() {
        return goalRow;
    }

    public int getGoalPositionColumn() {
        return goalCol;
    }

    public void loadMaze(String name){
        byte[] bArray = null; //todo - get the saved byte array.
        Maze MyMaze = new Maze(bArray);
        this.maze = MyMaze.getData();
        this.maxRow = maze.length;
        this.maxCol = maze[0].length;
        this.mazeObject = MyMaze;
        this.characterPositionRow = MyMaze.getStartPosition().getRow();
        this.characterPositionColumn = MyMaze.getStartPosition().getCol();
        this.goalRow = MyMaze.getGoalPosition().getRow();
        this.goalCol =MyMaze.getGoalPosition().getCol();
    }

    public void saveMaze(String name){
        byte[] bArray = this.mazeObject.toByteArray();
        

    }

    public void setGoalRow(int n){ this.goalRow=n;}
    public void setGoalCol(int n){ this.goalCol=n;}
    public void setCurrRow(int n){ this.characterPositionRow=n;}
    public void setCurrCol(int n){ this.characterPositionColumn=n;}
    public int getCurrRow(){ return this.characterPositionRow;}
    public int getCurrCol(){ return this.characterPositionColumn;}



}
