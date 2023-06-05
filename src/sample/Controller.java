package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {

    AnimationLoop simulator;
    Simulation simulation;

    @FXML
    private AnchorPane canvas;
    @FXML AnchorPane sceneParent;

    // Marble Properties
    @FXML Slider Slider;
    @FXML ColorPicker Color;
    @FXML TextField xposition;
    @FXML TextField yposition;
    @FXML TextField startSpeedX;
    @FXML TextField startSpeedY;
    @FXML TextField windX;

    // Obstacle Properties
    @FXML TextField startX;
    @FXML TextField startY;
    @FXML TextField endX;
    @FXML TextField endY;
    @FXML TextField angle;

    // Show Speed and Position
    @FXML Label setTextSpeedX;
    @FXML Label setTextPosX;
    @FXML Label setTextSpeedY;
    @FXML Label setTextPosY;



    public void initialize()
    {
        this.simulation = new Simulation(-9.81d, this, sceneParent);
        this.simulator = new AnimationLoop(this.simulation);

        // Unten
        new MRLine(0,0,800,0);
        // Links
        new MRLine(0,0,0, 800);
        // Oben
        new MRLine(0,800,800,800);
        // Rechts
        new MRLine(800,0,800,800);

        for (MRLine line : MRLine.lines) {
            canvas.getChildren().add(line.darstellung);
        }
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
        if(xposition.getText().isEmpty() | yposition.getText().isEmpty() | startSpeedX.getText().isEmpty() | angle.getText().isEmpty() | windX.getText().isEmpty())
        {
            System.out.println("Bitte Werte eintragen");
        } else
        {

            // Eingabewerte für Startposition werden in einen 2D Vektor gespeichert
            double vecX = Double.parseDouble(xposition.getText());
            double vecY = Double.parseDouble(yposition.getText());
            Vector2D vectorMarble = new Vector2D(vecX, vecY);
            vectorMarble = vectorMarble.getDivided(100);

            // Eingabewerte für die Geschwindigkeit werden in einem 2D Vektor gespeichert
            double vecStartSpeedX = Double.parseDouble(startSpeedX.getText());
            double vecStartSpeedY = Double.parseDouble(startSpeedY.getText());
            Vector2D vecGeschwindigkeit = new Vector2D(vecStartSpeedX, vecStartSpeedY);

            // Eingabewerte für den Wind werden in einem 2D Vektor gespeichert
            double vecWindX = Double.parseDouble(windX.getText());
            Vector2D vecWind = new Vector2D(vecWindX, 0);

            // Eingabewert aus Slider
            double radius = (int)Slider.getValue();

            // Kugel wird erstellt
            Marble marble = new Marble(
                    vectorMarble,               // Positionsvektor
                    radius,                     // radius
                    Color.getValue(),           // Farbe
                    vecGeschwindigkeit,         // Geschwindigkeitsvektor
                    vecWind                     // Windbeschleunigungsvektor
            );

            marble.move(simulation.zeit, simulation.gravity);
            // Kugel wird zur Simulation hinzugefügt
            simulation.addObject(marble);
            canvas.getChildren().addAll(marble.circle);
            /*System.out.println(marble.toString());*/
        }
    }

    // Hindernisse werden der Scene hinzugefügt
    public void createObstacle()
    {
        MRLine line = new MRLine(
                Double.parseDouble(startX.getText()),
                Double.parseDouble(startY.getText()),
                Double.parseDouble(endX.getText()),
                Double.parseDouble(endY.getText())
        );

        canvas.getChildren().add(line.darstellung);
        System.out.println(line.berechnung);
    }

    // Positionswerte der Kugel wird angezeigt
    public void setText(Vector2D speed, Vector2D position) {
        setTextSpeedX.setText(Double.toString(speed.x));
        setTextSpeedY.setText(Double.toString(speed.y));
        setTextPosX.setText(Double.toString(position.x));
        setTextPosY.setText(Double.toString(position.y));
    }

}
