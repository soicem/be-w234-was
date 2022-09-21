package service;

import model.User;
import persistence.UserRepository;
import util.HttpRequestUtils;

import java.io.*;
import java.util.Map;

public class HttpServiceImpl implements HttpService {

    private static final UserRepository userRepository = new UserRepository();

    @Override
    public String analyze(InputStream in) {
        try {
            HttpRequestUtils httpRequestUtils = new HttpRequestUtils();
            BufferedReader br = null;

            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String path = httpRequestUtils.extractPath(br.readLine());

            Map<String, String> m = httpRequestUtils.parseQueryString(path);
            userRepository.add(new User(m.get("userId"), m.get("password"), m.get("name"), m.get("email")));
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] responseHTML(String path) {
        try {
            HttpRequestUtils httpRequestUtils = new HttpRequestUtils();
            return httpRequestUtils.makeBody(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
