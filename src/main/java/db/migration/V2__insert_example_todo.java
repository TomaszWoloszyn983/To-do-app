package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


/**
 * Tutaj mamy przykład migracji do nieSQLowej bazy danych.
 * Jest to migracja Javowa i działa tutaj identycznie jak nasza SQLowa z V1.
 */
public class V2__insert_example_todo extends BaseJavaMigration {
    @Override
    public void migrate(Context context) {
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("insert into tasks (description, done) values ('Learn Java migrations', true)");
    }
}
