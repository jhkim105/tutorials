package com.example.oauth;

import com.example.oauth.KeycloakServerProperties.AdminUser;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.services.managers.ApplianceBootstrap;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.services.resources.KeycloakApplication;
import org.keycloak.util.JsonSerialization;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Slf4j
public class EmbeddedKeycloakApplication extends KeycloakApplication {
    static KeycloakServerProperties keycloakServerProperties;
    public EmbeddedKeycloakApplication() {
        createMasterRealmAdminUser();
        createBaeldungRealm();
    }
 
    private void createMasterRealmAdminUser() {
        KeycloakSession session = getSessionFactory().create();
        ApplianceBootstrap applianceBootstrap = new ApplianceBootstrap(session);
        AdminUser admin = keycloakServerProperties.getAdminUser();
        try {
            session.getTransactionManager().begin();
            applianceBootstrap.createMasterRealmUser(admin.getUsername(), admin.getPassword());
            session.getTransactionManager().commit();
        } catch (Exception ex) {
            log.warn("Couldn't create keycloak master admin user: {}", ex.getMessage());
            session.getTransactionManager().rollback();
        }
        session.close();
    }
 
    private void createBaeldungRealm() {
    KeycloakSession session = getSessionFactory().create();
    try {
        session.getTransactionManager().begin();
        RealmManager manager = new RealmManager(session);
        Resource lessonRealmImportFile = new ClassPathResource(
              keycloakServerProperties.getRealmImportFile());
        manager.importRealm(JsonSerialization.readValue(lessonRealmImportFile.getInputStream(),
              RealmRepresentation.class));
        session.getTransactionManager().commit();
    } catch (Exception ex) {
        log.warn("Failed to import Realm json file: {}", ex.getMessage());
        session.getTransactionManager().rollback();
    }
 
    session.close();
    }
}