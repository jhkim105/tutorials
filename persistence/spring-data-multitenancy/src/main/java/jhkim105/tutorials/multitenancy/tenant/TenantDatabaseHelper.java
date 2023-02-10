package jhkim105.tutorials.multitenancy.tenant;

import com.querydsl.codegen.ClassPathUtils;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Converter;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantDatabaseHelper {

  private final JpaProperties jpaProperties;

  private final JdbcTemplate jdbcTemplate;

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
    addAttributeConverter(metadataSources);

    PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
    new LocalSessionFactoryBuilder(null, resourceLoader, metadataSources)
        .scanPackages(TenantDatabaseConfig.DOMAIN_PACKAGE);

    Metadata metadata = metadataSources.buildMetadata();
    SchemaExport schemaExport = new SchemaExport();
    schemaExport.createOnly(EnumSet.of(TargetType.DATABASE), metadata);

  }

  private void addAttributeConverter(MetadataSources metadataSources) {
    try {
      Set<Class<?>> classes = ClassPathUtils.scanPackage(Thread.currentThread().getContextClassLoader(), TenantDatabaseConfig.DOMAIN_PACKAGE);
      classes.stream()
          .filter(scannedClass -> scannedClass.isAnnotationPresent(Converter.class))
          .forEach(metadataSources::addAnnotatedClass);

    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public void dropDatabase(Tenant tenant) {
    dropDatabase(tenant.getDatabaseName());
  }

  public void dropDatabase(String databaseName) {
    if (!StringUtils.startsWith(databaseName, Tenant.DATABASE_NAME_PREFIX)) {
      throw new IllegalStateException(String.format("This database cannot be deleted. name: [%s]", databaseName));
    }
    jdbcTemplate.execute("drop database " + databaseName);
  }

  public List<String> getTenantDatabaseNames() {
    return jdbcTemplate.query("show databases like '" + Tenant.DATABASE_NAME_PREFIX + "%'",
        (rs, rowNum) -> rs.getString(1));
  }
}
