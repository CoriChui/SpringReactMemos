package com.kaonstudio.security.memos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE memos SET title = ?2, description = ?3, created_at = ?4 WHERE id = ?1", nativeQuery = true)
    int update(Long id, String title, String description, String createdAt);

}
