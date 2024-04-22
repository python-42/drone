package r6.drone.desktop.gamepad;

import com.studiohartman.jamepad.ControllerIndex;
import com.studiohartman.jamepad.ControllerUnpluggedException;

public class ControllerInfo {
    
    private final String name;
    private final int index;

    public ControllerInfo(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public ControllerInfo(ControllerIndex index) throws ControllerUnpluggedException {
        this.name = index.getName();
        this.index = index.getIndex();
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return name + " (" + index + ")";
    }

}
