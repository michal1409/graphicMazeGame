package View;

import ViewModel.MyViewModel;

import java.awt.*;
import java.util.Observer;

public abstract class ControllerAbstract implements Observer,IView {
    public MyViewModel viewModel;
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
