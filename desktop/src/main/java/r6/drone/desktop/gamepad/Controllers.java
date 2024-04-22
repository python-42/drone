package r6.drone.desktop.gamepad;

import java.util.ArrayList;
import java.util.List;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import com.studiohartman.jamepad.ControllerUnpluggedException;

public class Controllers {
    
    private static Controllers INSTANCE = null;

    private final ControllerManager controllers;

    public static Controllers getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Controllers();
        }

        return INSTANCE;
    }

    private Controllers() {
        controllers = new ControllerManager();
        controllers.initSDLGamepad();
    }

    public List<ControllerInfo> getAllControllerInfo() {

        int num = controllers.getNumControllers();

        List<ControllerInfo> rtn = new ArrayList<>(num);

        for (int i = 0; i < num; i++) {
            try {
                rtn.add(new ControllerInfo(controllers.getControllerIndex(i)));
            } catch (ControllerUnpluggedException e) {}
        }

        return rtn;
    }

    public ControllerState getControllerState(int index) {
        return controllers.getState(index);
    }

    public int getConnectedControllerCount() {
        return controllers.getNumControllers();
    }

    public void refresh() {
        controllers.update();
    }

    public void quit() {
        controllers.quitSDLGamepad();
    }

}
