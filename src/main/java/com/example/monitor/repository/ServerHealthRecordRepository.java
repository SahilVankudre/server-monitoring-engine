package com.example.monitor.repository;

import com.example.monitor.model.ServerHealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerHealthRecordRepository extends JpaRepository<ServerHealthRecord, Long> {
    List<ServerHealthRecord> findByMonitoredServerIdOrderByTimestampDesc(Long serverId);
}