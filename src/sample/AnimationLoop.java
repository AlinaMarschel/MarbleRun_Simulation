package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class AnimationLoop {

    private Timeline timeline;
    float elapsedTime;
    Simulation simulation;
    Marble marble;

    public AnimationLoop(Simulation simulation)
    {
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1/60f), this::tick));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.simulation = simulation;
        this.elapsedTime = 0;
    }

    private void tick(ActionEvent actionEvent)
    {
        this.elapsedTime += timeline.getCurrentTime().toSeconds();
        simulation.update();
    }

    public void startSimulator()
    {
        this.timeline.play();
    }

    public void stopSimulator()
    {
        this.timeline.stop();
    }

}
