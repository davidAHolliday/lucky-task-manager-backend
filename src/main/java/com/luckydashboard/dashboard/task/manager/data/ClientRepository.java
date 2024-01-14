package com.luckydashboard.dashboard.task.manager.data;

import com.luckydashboard.dashboard.task.manager.model.Client;
import com.luckydashboard.dashboard.task.manager.model.TransactionModal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client,String> {

}
