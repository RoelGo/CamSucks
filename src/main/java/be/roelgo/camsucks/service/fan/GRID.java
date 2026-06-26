package be.roelgo.camsucks.service.fan;

/**
 * Model for the NZXT GRID+ fan controller. Uses a {@link Communicator} to talk to the device.
 *
 * <p>The GRID+ drives its fans by setting an output voltage (max 12V). {@link #setFanSpeed(int)}
 * translates a 0-100% input into that voltage and sends the corresponding command. To avoid
 * pointless serial traffic, a command identical to the previous one is skipped.
 *
 * <p>Ported from Roel's original CamSucks {@code GRID} class; the JavaFX bindings and the
 * RPM/amperage/voltage polling helpers from the original were dropped since only fan-speed
 * output is required here.
 */
public class GRID {

    private final Communicator communicator;

    // The last voltage command sent, used to suppress duplicate writes.
    private byte[] lastCommand;

    /**
     * Opens a communicator on the given port and, if the connection succeeds, initialises the controller.
     *
     * @param port the serial port the GRID+ is attached to
     */
    public GRID(String port) {
        // Seed with a sentinel that won't match any real command, so the first send always goes through.
        lastCommand = new byte[]{0, 0, 0, 0, 0, 1, 2};

        communicator = new Communicator(port);
        communicator.connect();

        if (communicator.isConnected()) {
            communicator.initIOStream();
        }
    }

    /**
     * Calculates the voltage corresponding to {@code percent} of the 12V maximum and sends the
     * command to set it.
     *
     * <p>The GRID+ does not recognise voltages between 0V and 4V, so anything in that range is
     * driven at 4V (and 0% means off). The fractional part of the voltage is snapped to .00 or .50.
     *
     * @param percent the desired fan speed, 0-100 (callers are expected to clamp to this range)
     */
    public void setFanSpeed(int percent) {
        if (!communicator.isConnected()) {
            return;
        }

        int firstByte;
        int lastByte;

        if (percent <= 0) {
            // Off.
            firstByte = 0;
            lastByte = 0;
        } else if (percent < 34) {
            // Anything that would resolve below 4V is floored to 4V.
            firstByte = 4;
            lastByte = 0;
        } else {
            int wantedVoltage = (1200 * percent) / 100; // centivolts (e.g. 50% -> 600 -> 6.00V)
            firstByte = wantedVoltage / 100;             // whole volts
            lastByte = wantedVoltage - (firstByte * 100); // remaining centivolts

            // Snap the fractional volt to .00 or .50.
            lastByte = (lastByte < 50) ? 0x00 : 0x50;
        }

        byte[] command = {0x44, 0x00, (byte) 0xC0, 0x00, 0x00, (byte) firstByte, (byte) lastByte};

        // Only send if the voltage actually changed (compare the voltage bytes).
        if (lastCommand[5] != command[5] || lastCommand[6] != command[6]) {
            communicator.writeData(command);
            lastCommand = command;
        }
    }

    /**
     * Disconnects the underlying communicator if it is connected.
     */
    public void disconnect() {
        communicator.disconnect();
    }

    /**
     * @return {@code true} if the controller's serial port is open
     */
    public boolean isConnected() {
        return communicator.isConnected();
    }
}
