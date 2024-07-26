package org.svartos;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.svartos.camel.DatabaseComponent;
import org.svartos.camel.CustomRouteBuilder;
import org.svartos.util.DatabaseInitializer;

public class MainApp {
    public static void main(String[] args) throws Exception {
        DatabaseInitializer initializer = new DatabaseInitializer();
        initializer.initializeDatabase();

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addComponent("database", new DatabaseComponent());
        camelContext.addRoutes(new CustomRouteBuilder());

        camelContext.start();

        Thread.sleep(2 * 60 * 1000);

        camelContext.stop();
    }

}