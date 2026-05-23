package com.example.repository;



import com.example.model.ChatHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    List<ChatHistory> findBySessionIdOrderByCreatedAtAsc(String sessionId);

    @Query("SELECT c FROM ChatHistory c WHERE c.sessionId = :sessionId ORDER BY c.createdAt DESC")
    List<ChatHistory> findRecentBySessionId(@Param("sessionId") String sessionId, Pageable pageable);

    @Query("SELECT COUNT(c) FROM ChatHistory c")
    long countTotalChats();

    @Query("SELECT COUNT(DISTINCT c.sessionId) FROM ChatHistory c")
    long countDistinctSessions();

    @Query(value = "SELECT COUNT(*) FROM CHAT_HISTORY WHERE TRUNC(CREATED_AT) = TRUNC(SYSDATE)", nativeQuery = true)
    long countTodayChats();

    @Query("SELECT c FROM ChatHistory c WHERE LOWER(c.userMessage) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.botReply) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY c.createdAt DESC")
    List<ChatHistory> searchByKeyword(@Param("keyword") String keyword);
}