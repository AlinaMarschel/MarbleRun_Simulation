package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Controller {

    MainView mainView = new MainView();

    AnimationLoop simulator;
    Simulation simulation;

    @FXML
    private AnchorPane canvas;

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

    public void createMarble()
    {
        Marble marble = new Marble(400,50,60, Color.BLANCHEDALMOND);
        simulation.addObject(marble);
        canvas.getChildren().addAll(marble.circle);
        System.out.println(marble.toString());
    }


}
