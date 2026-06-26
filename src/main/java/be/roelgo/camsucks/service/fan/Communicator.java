package be.roelgo.camsucks.service.fan;

import com.fazecast.jSerialComm.SerialPort;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class communicates with a serial COM device using the jSerialComm library.
 *
 * <p>Originally based on Henry Poon's serial communicator (https://blog.henrypoon.com/),
 * adapted by Roel for the NZXT GRID+ controller. The original implementation relied on the
 * unmaintained {@code gnu.io} (RXTX) library; it has been rewritten on top of
 * {@code com.fazecast:jSerialComm}, which ships platform-native binaries and requires no
 * system-level installation.
 *
 * <p>This class is intentionally a thin, framework-free transport: it opens/configures/closes
 * a single serial port and writes raw byte commands. All failures are handled "fail-soft"
 * (logged, never thrown) so that a missing or busy device never crashes the application.
 */
public class Communicator {

    private final Log logger = LogFactory.getLog(getClass());

    // The NZXT GRID+ controller communicates at 4800 baud, 8 data bits, 1 stop bit, no parity.
    private static final int BAUD_RATE = 4800;
    private static final int DATA_BITS = 8;

    // Timeouts (milliseconds).
    private static final int OPEN_TIMEOUT = 2000;
    private static final int WRITE_TIMEOUT = 2000;

    // GRID+ ignores commands that arrive too quickly; this pacing keeps communication stable.
    private static final long INTER_COMMAND_PAUSE_MS = 50;

    private final String portName;
    private SerialPort serialPort;
    private boolean connected = false;

    /**
     * @param portName the descriptor of the serial port to connect to (e.g. {@code /dev/tty.usbmodemXXXX} or {@code COM3})
     */
    public Communicator(String portName) {
        this.portName = portName;
    }

    /**
     * Opens the serial port with the GRID+ parameters (4800 baud, 8 data bits, 1 stop bit, no parity).
     * On any failure the communicator stays disconnected and a warning is logged.
     */
    public void connect() {
        if (portName == null || portName.isBlank()) {
            logger.warn("No serial port configured (camsucks.fan-port is empty); fan controller is disabled.");
            return;
        }

        try {
            serialPort = SerialPort.getCommPort(portName);
            serialPort.setComPortParameters(BAUD_RATE, DATA_BITS, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, OPEN_TIMEOUT, WRITE_TIMEOUT);

            if (serialPort.openPort()) {
                connected = true;
                logger.info(portName + " opened successfully.");
            } else {
                logger.warn("Failed to open " + portName + " (port could not be opened).");
            }
        } catch (Exception e) {
            logger.warn("Failed to open " + portName + " (" + e + ")");
        }
    }

    /**
     * Sends the GRID+ initialisation byte ({@code 0xC0}).
     *
     * @return {@code true} if the controller is connected and the init byte was written
     */
    public boolean initIOStream() {
        if (!connected) {
            return false;
        }
        writeData(0xC0);
        return true;
    }

    /**
     * Closes the serial port. Safe to call when not connected.
     */
    public void disconnect() {
        if (serialPort != null && serialPort.isOpen()) {
            try {
                serialPort.closePort();
                logger.info("Disconnected from " + portName + ".");
            } catch (Exception e) {
                logger.warn("Failed to close " + portName + " (" + e + ")");
            }
        }
        connected = false;
    }

    /**
     * @return {@code true} if the serial port is currently open
     */
    public boolean isConnected() {
        return connected && serialPort != null && serialPort.isOpen();
    }

    /**
     * Sends a single-byte command over the serial connection.
     *
     * @param command a single byte command (only the low 8 bits are used)
     */
    public void writeData(int command) {
        writeData(new byte[]{(byte) command});
    }

    /**
     * Sends a byte-array command over the serial connection.
     *
     * @param command the bytes to write
     */
    public void writeData(byte[] command) {
        if (!isConnected()) {
            logger.warn("Cannot write data: not connected to " + portName + ".");
            return;
        }
        try {
            int written = serialPort.writeBytes(command, command.length);
            if (written < 0) {
                logger.warn("Failed to write data to " + portName + " (write returned " + written + ").");
            }
            pause();
        } catch (Exception e) {
            logger.warn("Failed to write data to " + portName + " (" + e + ")");
        }
    }

    private void pause() {
        try {
            Thread.sleep(INTER_COMMAND_PAUSE_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
