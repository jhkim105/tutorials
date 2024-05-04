package jhkim105.tutorials.utils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

public class HibernateUtils {

  public static void generateSchema(String url, String username, String password, String... packagesToScan) {
    Map<String, Object> settings = new HashMap<>();
    settings.put(Environment.URL, url);
    settings.put(Environment.USER, username);
    settings.put(Environment.PASS, password);
    settings.put(Environment.SHOW_SQL, true);
    settings.put(Environment.FORMAT_SQL, true);

    StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

    MetadataSources metadataSources = new MetadataSources(serviceRegistry);

//    Arrays.stream(packagesToScan).forEach(
//        packageName -> ReflectionUtils.getAnnotatedClass(packageName, Entity.class).forEach(metadataSources::addAnnotatedClass)
//    );

    PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
    new LocalSessionFactoryBuilder(null, resourceLoader, metadataSources)
        .scanPackages(packagesToScan);

    Metadata metadata = metadataSources.buildMetadata();
    SchemaExport schemaExport = new SchemaExport();
    schemaExport.setFormat(true);
    schemaExport.setOutputFile("create.sql");
//    schemaExport.createOnly(EnumSet.of(TargetType.DATABASE), metadata);
    schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);


  }

}
