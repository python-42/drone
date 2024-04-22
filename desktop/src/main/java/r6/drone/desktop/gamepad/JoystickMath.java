package r6.drone.desktop.gamepad;

import r6.drone.common.Constants;
import r6.drone.common.Pair;

public class JoystickMath {

    private JoystickMath() {}

    public static Pair correctJoysticks(double forward, double rotate) {
        forward = applyDeadband(forward, Constants.JOYSTICK_DEADBAND);
        rotate = applyDeadband(rotate, Constants.JOYSTICK_DEADBAND);

        if (forward == 0) {
            //ignore strafe power if there is no forward power
            return new Pair(rotate, rotate * -1);
        }

        //if driving forward / backward do not rotate

        if (rotate < 0) {
            return new Pair(0, forward);
        }

        if (rotate > 0) {
            return new Pair(forward, 0);
        }

        return new Pair(forward, forward);
    }

    public static double applyDeadband(double input, double deadband) {
        if (Math.abs(input) > deadband) {
            if (input > 0.0) {
                return (input - deadband) / (1.0 - deadband);
            } else {
                return (input + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }

}
