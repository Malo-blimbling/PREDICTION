package com.uvprojet.ServiceCommunication;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByPublicationId(Long publicationId);
}
