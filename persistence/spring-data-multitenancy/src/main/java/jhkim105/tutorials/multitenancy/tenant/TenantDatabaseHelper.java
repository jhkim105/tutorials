package jhkim105.tutorials.multitenancy.tenant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantDatabaseHelper {

  private final JpaProperties jpaProperties;
  private final DataSource dataSource;

  public void createDatabase(Tenant tenant) {
    Map<String, Object> settings = new HashMap<>();
    settings.put(Environment.URL, tenant.getJdbcUrl());
    settings.put(Environment.USER, tenant.getDbUsername());
    settings.put(Environment.PASS, tenant.getDbPassword());
    settings.put(Environment.SHOW_SQL, true);
    settings.put(Environment.FORMAT_SQL, true);
    settings.putAll(jpaProperties.getProperties());

    StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

    MetadataSources metadataSources = new MetadataSources(serviceRegistry);

    PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
    new LocalSessionFactoryBuilder(null, resourceLoader, metadataSources)
        .scanPackages(TenantDatabaseConfig.DOMAIN_PACKAGE);

    Metadata metadata = metadataSources.buildMetadata();
    SchemaExport schemaExport = new SchemaExport();
    schemaExport.createOnly(EnumSet.of(TargetType.DATABASE), metadata);

  }

  public void dropDatabase(Tenant tenant) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.execute("drop database " + tenant.getDatabaseName());
  }

}
