package org.example.sweater.repository;

import org.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Ivan Kurilov on 19.04.2021
 */
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
