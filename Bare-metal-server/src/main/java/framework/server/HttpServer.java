package framework.server;

import framework.http.Router;
import framework.service.RouterScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    public static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private final Router router = new Router();

    public void registerController(Object controller) {
        RouterScanner.scan(controller, router);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server running on port: {}", port);
            ExecutorService threadPool = Executors.newFixedThreadPool(50);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, router);
                threadPool.execute(handler);
            }
        } catch (Exception e) {
            logger.error("Server Error: {}", e.getMessage());
        }
    }
}
