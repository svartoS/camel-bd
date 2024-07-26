package org.svartos.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseInitializer {
    public void initializeDatabase() throws Exception {
        if (Boolean.parseBoolean(PropertyUtil.getProperty("db.initialization.enabled"))) {
            try (Connection conn = DriverManager.getConnection(
                    PropertyUtil.getProperty("db.url"),
                    PropertyUtil.getProperty("db.username"),
                    PropertyUtil.getProperty("db.password"));
                 Statement stmt = conn.createStatement()) {

                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.sql");
                if (inputStream == null) {
                    throw new RuntimeException("Unable to find db.sql");
                }
                Scanner scanner = new Scanner(inputStream).useDelimiter(";");
                while (scanner.hasNext()) {
                    String sqlStatement = scanner.next().trim();
                    if (!sqlStatement.isEmpty()) {
                        stmt.execute(sqlStatement);
                    }
                }
                scanner.close();
            }
        }
    }
}