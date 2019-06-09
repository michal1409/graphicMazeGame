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
public class LoginDisplayer extends Canvas {

    private int FirstPictureHight;
    private int FirstPictureWidth;
    private int SecondPictureHight;
    private int SecondPictureWidth;


    public void setLogin() {
        redraw();
    }

    public void redraw() {
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / 2;
        double cellWidth = canvasWidth / 4;
        //todo - edit the height and width by the picture size

        try {
            Image FisrtImage = new Image(new FileInputStream(ImageFileNameFirst.get()));
            Image SecondImage = new Image(new FileInputStream(ImageFileNameSecond.get()));

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());

            //Draw Login
            //todo - edit the height and width by the picture location
            gc.drawImage(FisrtImage,0 * cellWidth, 0 * cellHeight, cellWidth, cellHeight);
            gc.drawImage(SecondImage,3 * cellWidth, 0 * cellHeight, cellWidth, cellHeight);

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        }

    }

    //region Properties
    private StringProperty ImageFileNameFirst = new SimpleStringProperty();
    private StringProperty ImageFileNameSecond = new SimpleStringProperty();

    public String getImageFileNameFirst() {
        return ImageFileNameFirst.get();
    }

    public void setImageFileNameFirst(String ImageFileNameFirst) {
        this.ImageFileNameFirst.set(ImageFileNameFirst);
    }

    public String getImageFileNameSecond() {
        return ImageFileNameSecond.get();
    }

    public void setImageFileNameSecond(String ImageFileNameSecond) {
        this.ImageFileNameSecond.set(ImageFileNameSecond);
    }

    //endregion

}
