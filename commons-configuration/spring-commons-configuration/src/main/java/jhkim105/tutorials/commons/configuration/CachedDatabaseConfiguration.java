package jhkim105.tutorials.commons.configuration;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Slf4j
public class CachedDatabaseConfiguration extends DatabaseConfiguration {

  public CachedDatabaseConfiguration(DataSource datasource, String table, String keyColumn,
      String valueColumn) {
    super(datasource, table, null, keyColumn, valueColumn, null);
  }

  @Override
  @Cacheable(value = "property")
  public Object getProperty(String key) {
    log.debug("key:{}", key);
    return super.getProperty(key);
  }

  @CacheEvict(value = "property", key ="#key")
  public void addProperty(String key, Object obj) {
    super.addProperty(key, obj);
  }

  @CacheEvict(value = "property", key ="#key")
  public void setProperty(String key, Object value) {
    super.setProperty(key, value);
  }

}
