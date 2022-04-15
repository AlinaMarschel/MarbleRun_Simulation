package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Controller {

    MainView mainView = new MainView();

    AnimationLoop simulator;
    Simulation simulation;

    @FXML
    private AnchorPane canvas;
    @FXML Slider Slider;
    @FXML ColorPicker Color;
    @FXML TextField xposition;
    @FXML TextField yposition;

    public void initialize()
    {
        this.simulation = new Simulation(981f);
        this.simulator = new AnimationLoop(this.simulation);
    }

    public void startSimulation()
    {
        System.out.println("StartedSimulation");
        simulator.startSimulator();
    }

    public void stopSimulation()
    {
        System.out.println("StoppedSimulation");
        simulator.stopSimulator();

    }

    public void createMarble() //minimaler wert, sonst ist die kugel in der gui
    {
        Marble marble = new Marble(Double.parseDouble(xposition.getText() ),Double.parseDouble(yposition.getText() ),(int)Slider.getValue(),Color.getValue());
        simulation.addObject(marble);
        canvas.getChildren().addAll(marble.circle);
        System.out.println(marble.toString());
    }


}
