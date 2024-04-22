package org.example;

import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        if (args.length != 2) {
            logger.info("use 2 arguments");
        }


    }
}
