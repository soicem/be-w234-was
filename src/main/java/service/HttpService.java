package service;

import java.io.InputStream;
import java.util.Map;

public interface HttpService {
    Map<String, String> analyze(InputStream in);

    byte[] responseHTML(String path);
}
