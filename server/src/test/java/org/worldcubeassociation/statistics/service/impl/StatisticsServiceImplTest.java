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
import org.worldcubeassociation.statistics.model.Statistics;
import org.worldcubeassociation.statistics.repository.StatisticsRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceImplTest {

    @InjectMocks
    private StatisticsServiceImpl service;

    @Mock
    private StatisticsRepository statisticsRepository;

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
        String path = "path-test-temp"; // It's better to avoid matching existing tests

        Statistics statistics = new Statistics();
        when(statisticsRepository.findById(path)).thenReturn(Optional.of(statistics));

        assertNotNull(service.getStatistic(path));
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
