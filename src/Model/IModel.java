package Model;

import javafx.scene.input.KeyCode;

public interface IModel {

    void generateMaze(int width, int height);
    void moveCharacter(KeyCode movement);
    int[][] getMaze();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    int getGoalPositionRow();
    int getGoalPositionColumn();
    int[][] getSolution(int a, int b);
    void setGoalRow(int n);
    void setGoalCol(int n);
    void setCurrRow(int n);
    void setCurrCol(int n);
    int getCurrRow();
    int getCurrCol();
    void generateReadMaze(int col, int row, int[][] maze, int posCol, int posRow, int goalCol, int goalRow);
}
