package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HttpUtilTest {

    private HttpUtil httpUtil;
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @BeforeEach
    void beforeEach() {
        httpUtil = new HttpUtil();
    }

    @Test
    void extractPath() {
    }

    @Test
    void makeBody() throws IOException {
        assertTrue(
                java.util.Arrays.equals(httpUtil.makeBody("/index.html"),
                Files.readAllBytes(new File("./webapp/index.html").toPath()))
        );

        assertFalse(
                java.util.Arrays.equals(httpUtil.makeBody("/hello.html"),
                        Files.readAllBytes(new File("./webapp/index.html").toPath()))
        );
    }
}