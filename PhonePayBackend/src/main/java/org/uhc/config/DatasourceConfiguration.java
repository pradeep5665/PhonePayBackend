/**
 * 
 */
package org.uhc.config;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration 
public class DatasourceConfiguration {

  @Bean(name = "db2Datasource")
  @ConfigurationProperties(prefix = "db2.datasource")
  public DataSource db2Datasource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "postgresDatasource")
  @ConfigurationProperties(prefix = "postgresql.datasource")
  public DataSource postgresDatasource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "db2NamedJdbcTemplate")
  public NamedParameterJdbcTemplate db2NamedJdbcTemplate(
      @Qualifier("db2Datasource") DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean(name = "postgresNamedJdbcTemplate")
  public NamedParameterJdbcTemplate postgresNamedJdbcTemplate(
      @Qualifier("postgresDatasource") DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }
  
  @Bean(name = "db2JdbcTemplate")
  public JdbcTemplate db2JdbcTemplate(@Qualifier("db2Datasource") DataSource ds) {
   return new JdbcTemplate(ds);
  }
  
  @Bean(name = "postgresJdbcTemplate")
  public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDatasource") DataSource ds) {
   return new JdbcTemplate(ds);
  }

}

