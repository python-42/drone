package r6.drone.desktop.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import r6.drone.common.Constants;

public class RobotConnection {

    private final Socket socket;
    private final OutputStream out;

    public static boolean robotReachable() throws UnknownHostException, IOException {
        return InetAddress.getByName(Constants.ADDRESS).isReachable(Constants.ROBOT_PING_TIMEOUT);
    }

    public RobotConnection() throws UnknownHostException, IOException {
        socket = new Socket(Constants.ADDRESS, Constants.CONNECTION_PORT);
        out = socket.getOutputStream();
    }

    public void sendMotorValues(double left, double right) throws IOException {
        out.write(Constants.LEFT_MOTOR_BYTE);
        out.write(ByteBuffer.allocate(8).putDouble(left).array());

        out.write(Constants.RIGHT_MOTOR_BYTE);
        out.write(ByteBuffer.allocate(8).putDouble(right).array());

        out.flush();
    }

    public void sendJumpCommand() throws IOException {
        out.write(Constants.JUMP_BYTE);
        out.flush();
    }

}
