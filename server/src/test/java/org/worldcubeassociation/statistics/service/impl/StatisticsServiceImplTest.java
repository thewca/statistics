/**
 *
 */
package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.worldcubeassociation.statistics.api.WCAApi;
import org.worldcubeassociation.statistics.dto.StatisticsGroupResponseDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceImplTest {

    @InjectMocks
    private StatisticsServiceImpl service;

    @Mock
    private WCAApi wcaApi;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Random RANDOM = new Random();

    @Before
    public void setup() {
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    public void getStatisticsByPath() throws IOException {
        String path = "path-test-temp"; // It's better to avoid matching exising tests

        // Create a file to be consumed
        File file = new File(String.format("statistics-list/%s.json", path));
        file.getParentFile().mkdirs(); // Create folders
        file.createNewFile(); // Create file

        int columnNumber = 1 + RANDOM.nextInt(10);

        // File the file with some content
        StatisticsResponseDTO expectedResponse = new StatisticsResponseDTO();
        expectedResponse.setPath(path);
        expectedResponse.setTitle("Test");
        expectedResponse.setStatistics(getDefaultStatistics(columnNumber));
        expectedResponse.setDisplayMode("DEFAULT");

        MAPPER.writeValue(file, expectedResponse);

        StatisticsResponseDTO response = service.getStatistic(path);

        assertEquals(expectedResponse, response);

        // Delete the test file
        file.delete();
    }

    private List<String> getDefaultHeaders(int columnNumber) {
        List<String> headers = new ArrayList<>();
        for (int i = 0; i < columnNumber; i++) {
            headers.add("Header " + i);
        }
        return headers;
    }

    private List<StatisticsGroupResponseDTO> getDefaultStatistics(int columnNumber) {
        List<StatisticsGroupResponseDTO> statistics = new ArrayList<>();

        int numberOfQueries = 1 + RANDOM.nextInt(5);

        for (int i = 0; i < numberOfQueries; i++) {
            StatisticsGroupResponseDTO groupItem = new StatisticsGroupResponseDTO();
            groupItem.setHeaders(getDefaultHeaders(columnNumber));
            groupItem.setKeys(Arrays.asList("Key " + i));
            List<List<String>> content = new ArrayList<>();
            int rows = 1 + RANDOM.nextInt(50);
            for (int j = 0; j < rows; j++) {
                List<String> row = new ArrayList<>();
                for (int k = 0; k < columnNumber; k++) {
                    row.add(String.format("Result %s %s %s", i, j, k));
                }
                content.add(row);
            }
            groupItem.setContent(content);
            statistics.add(groupItem);
        }

        return statistics;
    }
}
