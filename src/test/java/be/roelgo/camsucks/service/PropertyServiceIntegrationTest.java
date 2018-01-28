package be.roelgo.camsucks.service;

import be.roelgo.camsucks.CamSucksApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations = "classpath:application-test.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CamSucksApplication.class})
public class PropertyServiceIntegrationTest {

    @Autowired
    private PropertyService propertyService;

    @Test
    public void getCpuMax() {
        assertThat(propertyService.getCpuMax()).isEqualTo(80);
    }
}