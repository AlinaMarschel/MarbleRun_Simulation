package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {

    MainView mainView = new MainView();

    AnimationLoop simulator;
    Simulation simulation;

    @FXML
    private AnchorPane canvas;

    // Marble Properties
    @FXML Slider Slider;
    @FXML ColorPicker Color;
    @FXML TextField xposition;
    @FXML TextField yposition;
    @FXML TextField startSpeedX;
    @FXML TextField startSpeedY;

    // Obstacle Properties
    @FXML TextField obstacleX;
    @FXML TextField obstacleY;
    @FXML TextField angle;

    // Show Speed and Position
    @FXML Label setTextSpeedX;
    @FXML Label setTextPosX;
    @FXML Label setTextSpeedY;
    @FXML Label setTextPosY;



    public void initialize()
    {
        this.simulation = new Simulation(9.81f, this);
        this.simulator = new AnimationLoop(this.simulation);

        //createObstacle();
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
        if(xposition.getText().isEmpty() | yposition.getText().isEmpty() | startSpeedX.getText().isEmpty() | angle.getText().isEmpty())
        {
            System.out.println("Bitte Werte eintragen");
        } else
        {
            Marble marble = new Marble(
                    Float.parseFloat(xposition.getText()),   // x-Position
                    Float.parseFloat(yposition.getText()),   // y-Position
                    (int)Slider.getValue(),                  // radius
                    Color.getValue(),                        // Farbe
                    Float.parseFloat(startSpeedX.getText()), // x-StartSpeed
                    Float.parseFloat(startSpeedY.getText())  // y-StartSpeed
            );
            simulation.addObject(marble);
            canvas.getChildren().addAll(marble.circle);
            System.out.println(marble.toString());
        }


    }

    // Hindernisse werden der Scene hinzugefügt
    public void createObstacle()
    {
        Obstacle obstacle = new Obstacle(
                Float.parseFloat(obstacleX.getText() ),
                Float.parseFloat(obstacleY.getText() ),
                400,
                10,
                Float.parseFloat(angle.getText() ));
        obstacle.box.getBoundsInParent();
        simulation.addObject(obstacle);
        canvas.getChildren().addAll(obstacle.box);
        canvas.getChildren().addAll(obstacle.line);
    }

    public void setText(float speedX, float posX, float speedY, float posY) {
        setTextSpeedX.setText(Float.toString(speedX));
        setTextSpeedY.setText(Float.toString(speedY));
        setTextPosX.setText(Float.toString(posX));
        setTextPosY.setText(Float.toString(posY));

    }

}
