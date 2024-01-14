package com.luckydashboard.dashboard.task.manager.data;

import com.luckydashboard.dashboard.task.manager.model.Task;
import com.luckydashboard.dashboard.task.manager.model.TransactionModal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionModelRepository extends MongoRepository<TransactionModal,String> {

}
