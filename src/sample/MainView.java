package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class MainView extends VBox
{

    public static final int EDITING = 0;
    public static final int SIMULATING = 1;

    private AnchorPane canvas;

    private int applicationState = EDITING;

    public MainView()
    {

    }

}
