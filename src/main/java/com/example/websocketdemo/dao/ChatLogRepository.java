package com.example.websocketdemo.dao;

import com.example.websocketdemo.model.ChatLog;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**CRUD Repository Spring Data SQL Database
 * @author Sean Chapman
 *
 */
@RepositoryRestResource
public interface ChatLogRepository extends CrudRepository<ChatLog, Long> {

}


