package com.luckydashboard.dashboard.task.manager.data;

import com.luckydashboard.dashboard.task.manager.model.Loan;
import com.luckydashboard.dashboard.task.manager.model.TransactionModal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends MongoRepository<Loan,String> {

}
