package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.UserRepository;
import util.HttpRequestUtils;
import util.HttpUtil;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    UserRepository userRepository = new UserRepository();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpUtil httpUtil = new HttpUtil();
            HttpRequestUtils httpRequestUtils = new HttpRequestUtils();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String path = httpUtil.extractPath(br.readLine());
            logger.info("target path: " + path);

            Map<String, String> m = httpRequestUtils.parseQueryString(path);
            logger.info(m.toString());

            userRepository.add(new User(m.get("userId"), m.get("password"), m.get("name"), m.get("email")));
            logger.info(userRepository.findAll().toString());

            DataOutputStream dos = new DataOutputStream(out);

            byte[] body = httpUtil.makeBody(path);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
