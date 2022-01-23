package org.worldcubeassociation.statistics.integration;

import com.google.common.base.CaseFormat;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.worldcubeassociation.statistics.util.LoadResourceUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.nio.file.Files;

import static org.junit.platform.commons.function.Try.success;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yaml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AbstractTest {
    private static final String PROTOCOL_HTTP = "http://";
    private static final String TEST_HOST = "localhost";
    private static final int API_PORT = 8081;

    protected final RequestSpecification SPEC = createRequestSpecification();

    private static RequestSpecification createRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(String.format("%s%s:%s", PROTOCOL_HTTP, TEST_HOST, API_PORT))
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    public void validateResponse(Object index, Response response, DefaultComparator comparator) {
        StackWalker walker = StackWalker.getInstance();
        StackWalker.StackFrame stackFrameOptional = walker.walk(stream -> stream
                .filter(f -> f.getClassName().contains("org.worldcubeassociation.statistics.integration.controller"))
                .findFirst()).orElseThrow(() -> new RuntimeException("I couldn't recover test's stackframe"));

        final String methodName = stackFrameOptional.getMethodName();
        final String fullClassName = stackFrameOptional.getClassName();
        final String className = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN,
                fullClassName.substring(fullClassName.lastIndexOf(".") + 1));

        String resource = String.format("jsons/%s/%s_%s.json", className, methodName, index.toString());

        final String actualPayload = response.getBody().prettyPrint();

        try {

            final String expectedPayload = LoadResourceUtil.getResource(resource);

            JSONAssert.assertEquals(
                    expectedPayload, actualPayload, comparator);

        } catch (UncheckedIOException | JSONException ex) {
            if (ex.getCause() instanceof FileNotFoundException) {
                try {
                    File file = new File("src/test/resources/" + resource);
                    file.getParentFile().mkdirs();
                    Files.write(file.toPath(), actualPayload.getBytes());
                    success("Test file did not exist. File created and test succeeded");

                } catch (Exception e) {
                    throw new AbstractTest.TestCasePayloadGeneratedException(actualPayload, resource);
                }
            }
        }
    }

    public void validateResponse(Object index, Response response) {
        validateResponse(index, response, new DefaultComparator(JSONCompareMode.STRICT));
    }

    private static class TestCasePayloadGeneratedException extends RuntimeException {
        private static final int MAX_PAYLOAD_LEN = 50;

        public TestCasePayloadGeneratedException(String payload, String resource) {
            super(String.format("Resource \"%s\" not found for this execution." +
                                    " So resources was generated based on this \"%s %s\" payload." +
                                    " Run tests again!", resource
                            , payload.substring(0, Math.min(payload.length(), MAX_PAYLOAD_LEN))
                            , payload.length() > MAX_PAYLOAD_LEN ? "..." : ""
                    )
            );
        }
    }

    public void validateResponseIgnoreAttribute(int index, Response response, String ignoreAttribute) {
        validateResponse(index, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization(ignoreAttribute, (o1, o2) -> true)));
    }
}
