package r6.drone.desktop.gui;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import r6.drone.desktop.gamepad.ControllerInfo;
import r6.drone.desktop.gamepad.Controllers;

public class MainController {

    @FXML private Slider leftAxisSlider;
    @FXML private Slider rightAxisSlider;

    @FXML private Label leftOutput;
    @FXML private Label rightOutput;

    @FXML private Label controllerName;

    @FXML private Label connection;
    @FXML private Button disableBtn;
    @FXML private Button enableBtn;

    @FXML private ChoiceBox<ControllerInfo> controllerList;

    @FXML private Button refreshBtn;

    @FXML private TextArea logView;

    @FXML private MediaView cameraView;

    private boolean enabled = false;
    private boolean connected = false;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @FXML
    public void initialize() {
        Controllers controllers = Controllers.getInstance();

        controllerList.setConverter(new StringConverter<ControllerInfo>() {

            @Override
            public ControllerInfo fromString(String arg0) {
                return new ControllerInfo(arg0, -1);
            }

            @Override
            public String toString(ControllerInfo arg0) {
                if (arg0 == null) {
                    return "";
                }

                return arg0.toString();
            }
            
        });

        controllerList.getItems().setAll(controllers.getAllControllerInfo());

        refreshBtn.setOnAction((event) -> {
            controllers.refresh(); 
            controllerList.getItems().setAll(controllers.getAllControllerInfo());
        });

        controllerList.setOnAction((event) -> controllerName.setText(getSelectedController().toString()));

        disableBtn.setOnAction((event) -> {enabled = false; setConnectionText();});
        enableBtn.setOnAction((event) -> {enabled = true; setConnectionText();});

        BooleanBinding connectedBinding = Bindings.createBooleanBinding(() -> !connected);

        disableBtn.disableProperty().bind(connectedBinding);
        enableBtn.disableProperty().bind(connectedBinding);
    }

    public ControllerInfo getSelectedController() {
        return controllerList.getSelectionModel().getSelectedItem();
    }

    public void setControllerConnected(boolean isConnected) {
        if (isConnected) {
            controllerName.setTextFill(Color.GREEN);
        }else {
            controllerName.setTextFill(Color.RED);
        }
    }

    public void setConnection(boolean state) {
        connected = state;
        if (!state) {
            enabled = false;
        }
        setConnectionText();
    }

    public void setConnectionText() {
        connection.setText(
            (connected ? "Connected" : "Disconnected")
            + " " +
            (enabled ? "Enabled" : "Disabled")
        );

    }

    public void appendMessage(String msg) {
        logView.appendText("["+ dateFormat.format(new Date()) +"] " + msg + "\n");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setOutput(double left, double right) {
        leftAxisSlider.setValue(left);
        rightAxisSlider.setValue(right);
        leftOutput.setText(left + "");
        rightOutput.setText(right + "");
    }
    
}
