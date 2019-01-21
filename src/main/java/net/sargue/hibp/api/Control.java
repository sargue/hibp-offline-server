package net.sargue.hibp.api;

import net.sargue.hibp.Main;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.logging.Logger;

@Path("control")
public class Control {
    private static final Logger log = Logger.getLogger(Control.class.getName());

    @POST
    @Path("stop")
    public void stop() {
        log.info("Stopping server.");
        Main.getApp().stop();
    }
}
