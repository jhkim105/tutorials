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

    // 저장되지 않는다. (dirty checking 을 하지 않음)
    // envers 가 설정되어 있으면 저장된다.
    User user = (User)entity;
    user.setDescription("onPreInsert");
    user.getGroup().setDescription("onPreInsert");

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
      // 예외가 발생하면 롤백됨
      if (success) {
        log.info(">> UserListener.onPostInsert PostCommit success: [{}]", event.getEntity());
      }
    });
  }


  @Override
  public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
    return true; // event.getSession().getActionQueue().registerProcess() 가 실패하면 rollback 됨
  }
}
