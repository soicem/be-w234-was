package service;

import java.io.InputStream;

public interface HttpService {
    String analyze(InputStream in);

    byte[] responseHTML(String path);
}
