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
    @FXML TextField angle;

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

    // Kugeln werden erstellt und der Scene hinzugefügt bei Button Click
    public void createMarble()
    {
        // Abfrage ob die Textfelder ausgefüllt sind
        if(xposition.getText().isEmpty() | yposition.getText().isEmpty() | startSpeed.getText().isEmpty())
        {
            System.out.println("Bitte Werte eintragen");
        } else
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

    // Hindernisse werden der Scene hinzugefügt
    public void createObstacle()
    {
        Obstacle obstacle = new Obstacle(200, 350, 400, 10, Float.parseFloat(angle.getText() ));
        simulation.addObject(obstacle);
        canvas.getChildren().addAll(obstacle.box);
    }


}
