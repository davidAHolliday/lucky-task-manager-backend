package com.luckydashboard.dashboard.task.manager.data;

import com.luckydashboard.dashboard.task.manager.model.Admin;
import com.luckydashboard.dashboard.task.manager.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<Admin,String> {

}
