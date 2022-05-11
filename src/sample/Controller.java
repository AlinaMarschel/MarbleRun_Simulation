package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    @FXML TextField startSpeed;

    public void initialize()
    {
        this.simulation = new Simulation(9.81f);
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
        Marble marble = new Marble(
                Float.parseFloat(xposition.getText() ),
                Float.parseFloat(yposition.getText() ),
                (int)Slider.getValue(),
                Color.getValue(),
                Float.parseFloat(startSpeed.getText() )
        );
        simulation.addObject(marble);
        canvas.getChildren().addAll(marble.circle);
        System.out.println(marble.toString());
    }


}
