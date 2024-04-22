package r6.drone.desktop.gui;

import java.io.IOException;

import com.studiohartman.jamepad.ControllerState;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import r6.drone.common.Pair;
import r6.drone.desktop.connection.RobotConnection;
import r6.drone.desktop.gamepad.ControllerInfo;
import r6.drone.desktop.gamepad.Controllers;
import r6.drone.desktop.gamepad.JoystickMath;

public class App extends Application {

    private MainController fxml;
    private Controllers controllers = Controllers.getInstance();

    private RobotConnection conn = null;

    private boolean windowClosed = false;

    @Override
    public void start(Stage stage) throws IOException {
        ClassLoader resourceLoader = ClassLoader.getSystemClassLoader();
        Font.loadFont(resourceLoader.getResourceAsStream("R6-Font.ttf"), 0);
        FXMLLoader loader = new FXMLLoader(resourceLoader.getResource("main.fxml"));
        Parent root = loader.load();
        fxml = loader.getController();

        stage.setOnCloseRequest(event -> {windowClosed = true; controllers.quit();});
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

        fxml.appendMessage("Connected controllers: " + controllers.getConnectedControllerCount());
        fxml.appendMessage("Driver station startup complete.");
        
        new Thread(() -> {
            while(!windowClosed) {
                ControllerInfo controller = fxml.getSelectedController();

                if (controller != null) {
                    ControllerState state = controllers.getControllerState(controller.getIndex());

                    Pair adjustedInput = JoystickMath.correctJoysticks(state.leftStickY, state.rightStickX);

                    updateGUIOutput(adjustedInput.LEFT, adjustedInput.RIGHT, state.isConnected);

                    try {
                        if (conn == null) {
                            if (RobotConnection.robotReachable()) {
                                conn = new RobotConnection();
                                fxml.appendMessage("Successfully connected to robot");
                            }
                        }else {
                            conn.sendMotorValues(adjustedInput.LEFT, adjustedInput.RIGHT);
                            if (state.aJustPressed) {
                                conn.sendJumpCommand();
                            }
                        }
                    }catch (IOException exception) {
                        fxml.appendMessage("ERROR: " + exception.getMessage());
                    }
                }
            }
        }).start();
    }

    private void updateGUIOutput(double left, double right, boolean connected) {
        Platform.runLater(() -> fxml.setOutput(left, right));
        fxml.setControllerConnected(connected);
    }
    
}
