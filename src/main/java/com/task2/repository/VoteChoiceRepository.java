package com.task2.repository;

import com.task2.model.VoteChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface VoteChoiceRepository extends JpaRepository<VoteChoice, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM VoteChoice WHERE vote.id = :vote_id")
    void deleteByVoteId(Long vote_id);

    @Query("select v from VoteChoice v where v.id = :answer_id")
    VoteChoice findByAnswerId(Long answer_id);
}
