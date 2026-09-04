import application.controller.UserController;
import framework.server.HttpServer;

public class Main {
    public static void main (String[] args) {
        HttpServer server = new HttpServer();
        server.registerController(new UserController());
        server.start(8080);
    }
}
