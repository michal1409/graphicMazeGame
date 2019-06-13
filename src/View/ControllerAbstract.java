package View;

import ViewModel.MyViewModel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;
import java.util.Observer;

public abstract class ControllerAbstract implements Observer,IView {
    public MyViewModel viewModel;
    public Stage stageCon;
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }
    public void setStage(Stage stage1){ stageCon=stage1;}

}
