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
public class UserListener implements PreInsertEventListener, PostInsertEventListener {

  @Override
  public boolean onPreInsert(PreInsertEvent event) {
    final Object entity = event.getEntity();
    if (!(entity instanceof User)) {
      return false;
    }
    log.info("UserListener.onPreInsert: [{}]", event.getEntity());
    log.info("currentTx: {}", TransactionSynchronizationManager.getCurrentTransactionName());

    return false;
  }

  @Override
  public void onPostInsert(PostInsertEvent event) {
    final Object entity = event.getEntity();
    if (!(entity instanceof User)) {
      return;
    }
    log.info(">> UserListener.onPostInsert: [{}]", event.getEntity());
    createLogOnPostCommitSuccess(event);
  }

  private void createLogOnPostCommitSuccess(PostInsertEvent event) {
    event.getSession().getActionQueue().registerProcess((success, sessionImplementor) -> {
      // commit 이후에 실행됨
      if (success) {
        log.info(">> UserListener.onPostInsert PostCommit success: [{}]", event.getEntity());
      }
      throw new RuntimeException("111");
    });

  }


  @Override
  public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
    return true; // // true, false 동일하게 동작함
  }
}
