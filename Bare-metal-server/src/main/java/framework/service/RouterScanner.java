package framework.service;

import framework.annotations.*;
import framework.http.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class RouterScanner {
    private static final Logger logger = LoggerFactory.getLogger(RouterScanner.class);

    public static void scan(Object controller, Router router) {
        Class<?> clazz = controller.getClass();

        boolean isRestController = clazz.isAnnotationPresent(RestController.class);

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(GET.class)) {
                registerRoute(router, "GET", method.getAnnotation(GET.class)
                        .value(), controller, method, isRestController);

            } else if (method.isAnnotationPresent(POST.class)) {
                registerRoute(router, "POST", method.getAnnotation(POST.class)
                        .value(), controller, method, isRestController);
                
            } else if (method.isAnnotationPresent(PUT.class)) {
                registerRoute(router, "PUT", method.getAnnotation(PUT.class)
                        .value(), controller, method, isRestController);

            } else if (method.isAnnotationPresent(DELETE.class)) {
                registerRoute(router, "DELETE", method.getAnnotation(DELETE.class)
                        .value(), controller, method, isRestController);
            }
        }
    }

    private static void registerRoute(Router router, String verb, String path,
                                      Object controller, Method method, boolean isJson) {
        router.register(verb, path, (request, response) -> {
            try {
                Object result;
                if (method.getParameterCount() == 0) {
                    result = method.invoke(controller);

                } else {
                    result = method.invoke(controller, request, response);

                }

                if (result != null) {
                    String responseBody = result.toString();

                    response.setBody(responseBody);
                    response.setContentType("application/json");

                    if (response.getStatusCode() == 0) {
                        response.setStatusCode(200);

                    }


                }

            } catch (Exception e) {
                logger.error("Internal Server Error: {}", e.getMessage());
                response.setStatusCode(500);
                response.setBody("{\"error\": \"Internal Server Error\"}");
            }


        });
        logger.info("Mapped {} {} -> {}", verb, path, method.getName());
    }
}
