package org.svartos.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.support.DefaultComponent;

import java.util.Map;

public class DatabaseComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        DatabaseEndpoint endpoint = new DatabaseEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
