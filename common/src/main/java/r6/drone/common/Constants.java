package r6.drone.common;

public class Constants {
    private Constants() {}

    public static final double JOYSTICK_DEADBAND = 0.1675;

    public static final String ADDRESS = "10.10.10.10"; //TODO correct
    public static final int CONNECTION_PORT = 2006;
    public static final int ROBOT_PING_TIMEOUT = 100;

    public static final byte LEFT_MOTOR_BYTE = 0;
    public static final byte RIGHT_MOTOR_BYTE = 1;
    public static final byte JUMP_BYTE = 2;
}
