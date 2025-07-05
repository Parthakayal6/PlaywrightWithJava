package utils;

import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkLogger.class);

    public static void attachNetworkListeners(Page page) {
        // Log failed requests
        page.onRequestFailed(request -> {
            LOGGER.warn("Request failed: {}", request.url());
            LOGGER.warn("Failure reason: {}", request.failure());
        });

        // Log successful requests
        page.onRequest(request ->
                LOGGER.info("Request: {} {}", request.method(), request.url())
        );

        // Log responses
        page.onResponse(response ->
                LOGGER.info("Response: {} {}", response.status(), response.url())
        );
    }
}