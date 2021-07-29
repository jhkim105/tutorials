package com.example.demo;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceType =
            TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "slave" : "master";
        return dataSourceType;
    }
}