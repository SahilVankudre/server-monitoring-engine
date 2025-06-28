package com.example.monitor.repository;

import com.example.monitor.model.MonitoredServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoredServerRepository extends JpaRepository<MonitoredServer, Long> {}