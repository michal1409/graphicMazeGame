package ViewModel;

import Model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;

    private int characterPositionRowIndex;
    private int characterPositionColumnIndex;
    //public StringProperty characterPositionRow = new SimpleStringProperty("1"); //For Binding
    //public StringProperty characterPositionColumn = new SimpleStringProperty("1"); //For Binding
    public int rowsInput;
    public int colInput;


    public MyViewModel(IModel model){
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o==model){
            characterPositionRowIndex = model.getCharacterPositionRow();
            //characterPositionRow.set(String.valueOf(characterPositionRowIndex));
            characterPositionColumnIndex = model.getCharacterPositionColumn();
            //characterPositionColumn.set(String.valueOf(characterPositionColumnIndex));
            setChanged();
            notifyObservers();
        }
    }

    public void generateMaze(int width, int height){
        model.generateMaze(width, height);
    }

    public void generateReadMaze(int w, int h, int[][] maze, int posCol, int posRow, int goalCol, int goalRow){ model.generateReadMaze(w,h,maze, posCol, posRow, goalCol, goalRow);}
    public void moveCharacter(KeyCode movement){
        model.moveCharacter(movement);
    }

    public int[][] getMaze() {
        return model.getMaze();
    }

    public int[][] getSolution(){
        return model.getSolution(characterPositionRowIndex,characterPositionColumnIndex);
    }

    public int getCharacterPositionRow() {
        return characterPositionRowIndex;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumnIndex;
    }

    public int getGoalPositionRow(){return model.getGoalPositionRow();}

    public int getGoalPositionColumn(){return model.getGoalPositionColumn();}

    public int getRowsInput(){return rowsInput;}
    public int getColInput(){return colInput;}
    public void setRowsInput(int n){rowsInput=n;}
    public void setColInput(int n){colInput=n;}


    public void setGoalRow(int n){ this.model.setGoalRow(n);}
    public void setGoalCol(int n){ this.model.setGoalCol(n);}
    public void setCurrRow(int n){ this.model.setCurrRow(n);}
    public void setCurrCol(int n){this.model.setCurrCol(n);}

}
