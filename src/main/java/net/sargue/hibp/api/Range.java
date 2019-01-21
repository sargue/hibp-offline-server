package net.sargue.hibp.api;

import net.sargue.hibp.Main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("range")
public class Range {
    @GET
    @Path("/{hash:[a-fA-F0-9]{5}}")
    @Produces(TEXT_PLAIN)
    public String checkRange(@PathParam("hash") String hashPrefix) {
        return Main.getApp().getPwnedPasswords().checkHashRange(hashPrefix);
    }
}
