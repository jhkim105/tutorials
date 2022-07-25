package jhkim105.tutorials.multitenancy.master.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class TenantDeleteEvent extends ApplicationEvent {

  @Getter
  private final Tenant tenant;

  public TenantDeleteEvent(Object source, Tenant tenant) {
    super(source);
    this.tenant = tenant;
  }
}
