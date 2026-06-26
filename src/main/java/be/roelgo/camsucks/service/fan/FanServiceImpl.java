package be.roelgo.camsucks.service.fan;

import be.roelgo.camsucks.service.PropertyService;
import be.roelgo.camsucks.service.model.FanService;
import be.roelgo.camsucks.service.model.Speed;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * {@link FanService} backed by an NZXT GRID+ controller.
 *
 * <p>The controller is initialised on construction: a {@link GRID} is opened on the serial port
 * configured via {@code camsucks.fan-port}. If that port is missing or busy the underlying
 * communicator fails soft (logs and stays disconnected), so the application still starts.
 *
 * <p>Excluded from the {@code test} profile so the integration tests, which boot the full Spring
 * context, never attempt to open real hardware.
 */
@Component
@Profile("!test")
public class FanServiceImpl implements FanService {

    private static final int MIN_PERCENT = 0;
    private static final int MAX_PERCENT = 100;

    private final GRID grid;

    public FanServiceImpl(PropertyService propertyService) {
        this.grid = new GRID(propertyService.getFanPort());
    }

    @Override
    public void sendSpeed(Speed speed) {
        grid.setFanSpeed(toPercent(speed));
    }

    /**
     * Converts a {@link Speed} into a whole-number percentage clamped to [0, 100]. The regulator
     * can produce values outside that range, so the clamp guards the protocol against bad input.
     */
    private int toPercent(Speed speed) {
        Double percentage = speed.getPercentage();
        if (percentage == null) {
            return MIN_PERCENT;
        }
        long rounded = Math.round(percentage);
        return (int) Math.max(MIN_PERCENT, Math.min(MAX_PERCENT, rounded));
    }
}
