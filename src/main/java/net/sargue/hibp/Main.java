package net.sargue.hibp;

import java.io.File;

public class Main {

    private static App app;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: hibp-offline-server listening-URI path-to-passwords");
            System.err.println("Where:");
            System.err.println("  listening-URI: Where to listen, ex: http://localhost:8080");
            System.err.println("  path-to-password: path to hibp password list, ordered by hash, ex: pwned-passwords-sha1-ordered-by-hash-v4.txt");
        }

        File file = new File(args[1]);
        if (!file.exists()) {
            System.err.println("Password file doesn't exist.");
            System.exit(1);
        }
        if (!file.canRead()) {
            System.err.println("Can't read password file.");
            System.exit(2);
        }

        app = new App(args[0], file);
    }

    public static App getApp() {
        return app;
    }
}
