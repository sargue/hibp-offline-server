package net.sargue.hibp.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.io.InputStream;

import static javax.ws.rs.core.MediaType.TEXT_HTML;

@Path("passwords")
public class Passwords {
    @GET
    @Produces(TEXT_HTML)
    public InputStream get() {
        return ClassLoader.getSystemClassLoader().getResourceAsStream("passwords.html");
    }
}
