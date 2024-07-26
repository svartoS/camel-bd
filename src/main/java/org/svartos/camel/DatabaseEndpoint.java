package org.svartos.camel;

import lombok.Data;
import org.apache.camel.Category;
import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.support.ScheduledPollConsumer;
import org.svartos.util.PropertyUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

@Data
@UriEndpoint(firstVersion = "1.0-SNAPSHOT", scheme = "database", title = "Database", syntax = "database:name", category = {Category.DATABASE})
public class DatabaseEndpoint extends DefaultEndpoint {
    @UriParam
    private String url;
    @UriParam
    private String user;
    @UriParam
    private String password;
    @UriParam(defaultValue = "1000")
    private long delay = 1000;
    @UriParam(defaultValue = "0")
    private long initialDelay = 0;

    Properties properties = new Properties();

    public DatabaseEndpoint(String uri, Component component) {
        super(uri, component);
    }

    @Override
    public Producer createProducer() throws Exception {
        throw new UnsupportedOperationException("Cannot produce to a DatabaseEndpoint: " + getEndpointUri());
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        ScheduledPollConsumer consumer = new ScheduledPollConsumer(this, processor) {
            String query = PropertyUtil.getProperty("db.query");

            @Override
            protected int poll() throws Exception {
                try (Connection connection = DriverManager.getConnection(url, user, password);
                     Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {

                    while (resultSet.next()) {
                        StringBuilder sb = new StringBuilder("Row: ");
                        sb.append(resultSet.getString("name"));
                        sb.append(" | ");
                        sb.append(resultSet.getString("position"));
                        sb.append(" | ");
                        sb.append(resultSet.getInt("salary"));

                        System.out.println(sb.toString());
                    }
                    System.out.println("--------------------------------------------------------");
                }
                return 1;
            }
        };

        consumer.setDelay(delay);
        consumer.setInitialDelay(initialDelay);
        configureConsumer(consumer);
        return consumer;
    }
}
