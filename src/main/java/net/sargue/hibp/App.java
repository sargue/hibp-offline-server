package net.sargue.hibp;

import net.sargue.hibp.api.Api;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;

public class App {
    private HttpServer server;
    private PwnedPasswords pwnedPasswords;

    public App(String listeningURI, File passwordsFile) {
        URI uri = UriBuilder.fromUri(listeningURI).build();
        pwnedPasswords = new PwnedPasswords(passwordsFile);
        server = GrizzlyHttpServerFactory.createHttpServer(uri, new Api());
    }

    public void stop() {
        server.shutdown();
    }

    public PwnedPasswords getPwnedPasswords() {
        return pwnedPasswords;
    }
}
