/**
 *
 */
package org.worldcubeassociation.statistics.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.worldcubeassociation.statistics.api.WCAApi;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WCAServiceImplTest {

    @InjectMocks
    private WCAServiceImpl service;

    @Mock
    private WCAApi wcaApi;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(service, "wcaBaseUrl", "https://staging.worldcubeassociation.org");
        ReflectionTestUtils.setField(service, "wcaAppId", "example-application-id");
    }

    @Test
    public void getResultSetTest() {
        String url = service.getWcaAuthenticationUrl("http://localhost:3000");
        assertEquals(
                "https://staging.worldcubeassociation"
                        + ".org/oauth/authorize?client_id=example-application-id&redirect_uri=http://localhost:3000"
                        + "&response_type=token&scope=public",
                url);
    }

    @Test
    public void getUserInfo() {
        String token = "Bearer token123";
        when(wcaApi.getUserInfo(anyString())).thenReturn(new UserInfoDTO());
        UserInfoDTO userInfo = service.getUserInfo(token);
        assertNotNull(userInfo);

        // The workflow simply redirects to WCA
        verify(wcaApi, times(1)).getUserInfo(anyString());
    }

}
