package jhkim105.tutorials.domain;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Component
public class GroupListener implements PreInsertEventListener, PostInsertEventListener {

  @Override
  public boolean onPreInsert(PreInsertEvent event) {
    final Object entity = event.getEntity();
    if (!(entity instanceof Group)) {
      return false;
    }
    log.info(">> GroupListener.onPreInsert: [{}]", event.getEntity());
    log.info("currentTx: {}", TransactionSynchronizationManager.getCurrentTransactionName());

    return false;
  }

  @Override
  public void onPostInsert(PostInsertEvent event) {
    // 여기에서 예외가 발생하면 롤백됨.
    final Object entity = event.getEntity();
    if (!(entity instanceof Group)) {
      return;
    }
    log.info(">> GroupListener.onPostInsert: [{}]", event.getEntity());
    createLogOnPostCommitSuccess(event);
  }

  private void createLogOnPostCommitSuccess(PostInsertEvent event) {
    event.getSession().getActionQueue().registerProcess((success, sessionImplementor) -> {
      // 예외가 발생하더라도 롤백되지 않음
      if (success) {
        log.info(">> GroupListener.onPostInsert PostCommit success: [{}]", event.getEntity());
      }
    });
  }
  @Override
  public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
    return false; // event.getSession().getActionQueue().registerProcess() 가 실패하더라도 rollback 되지 않음
  }

}
