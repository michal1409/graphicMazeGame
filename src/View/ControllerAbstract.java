package View;

import ViewModel.MyViewModel;

import java.awt.*;
import java.util.Observer;

public abstract class ControllerAbstract implements Observer,IView {
    private MyViewModel view;
}
