package com.tenpo.mathfusion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenpo.mathfusion.model.CallHistory;

@Repository
public interface CallHistoryRepository extends JpaRepository<CallHistory, Long> {
    Page<CallHistory> findAllByOrderByTimestampDesc(Pageable pageable);
}