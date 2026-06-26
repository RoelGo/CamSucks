package be.roelgo.camsucks.service;

import be.roelgo.camsucks.CamSucksApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CamSucksApplication.class})
@ActiveProfiles({"test"})
public class PropertyServiceIntegrationTest {

    @Autowired
    private PropertyService propertyService;

    @Test
    public void getCpuMax() {
        assertThat(propertyService.getCpuMax()).isEqualTo(80);
    }
}