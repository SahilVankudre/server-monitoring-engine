package com.example.monitor.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "server_health_record")
public class ServerHealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isHealthy;
    private Long responseTimeMs;
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitored_server_id", nullable = false)
    private MonitoredServer monitoredServer;

    public ServerHealthRecord() {}

    public ServerHealthRecord(MonitoredServer monitoredServer, Boolean isHealthy, Long responseTimeMs, LocalDateTime timestamp) {
        this.monitoredServer = monitoredServer;
        this.isHealthy = isHealthy;
        this.responseTimeMs = responseTimeMs;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Boolean getIsHealthy() { return isHealthy; }
    public void setIsHealthy(Boolean isHealthy) { this.isHealthy = isHealthy; }

    public Long getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(Long responseTimeMs) { this.responseTimeMs = responseTimeMs; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public MonitoredServer getMonitoredServer() { return monitoredServer; }
    public void setMonitoredServer(MonitoredServer monitoredServer) { this.monitoredServer = monitoredServer; }
}
