package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("Marble Run");
        primaryStage.setScene(new Scene(root, 1090, 800));
        primaryStage.setResizable(false);

        primaryStage.show();

    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
