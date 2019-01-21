package net.sargue.hibp.api;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.logging.Logger;

@ApplicationPath("/")
public class Api extends ResourceConfig {
    private static final Logger log = Logger.getLogger(Api.class.getName());

    public Api() {
        packages("net.sargue.hibp.api");
        log.info("Started REST API.");
    }
}
