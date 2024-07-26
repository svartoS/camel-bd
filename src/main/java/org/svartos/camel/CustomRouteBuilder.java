package org.svartos.camel;

import org.apache.camel.builder.RouteBuilder;
import org.svartos.util.PropertyUtil;

public class CustomRouteBuilder extends RouteBuilder {
    String uri =
            "database://potgresql?url=" + PropertyUtil.getProperty("db.url") +
            "&user=" + PropertyUtil.getProperty("db.username") +
            "&password=" + PropertyUtil.getProperty("db.password") +
            "&initialDelay=" + PropertyUtil.getProperty("camel.initialDelay") +
            "&delay=" + PropertyUtil.getProperty("camel.delay");
    @Override
    public void configure() throws Exception {
        from(uri).log("Polling database");
    }
}