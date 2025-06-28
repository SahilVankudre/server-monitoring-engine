package com.example.monitor.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "monitored_server")
public class MonitoredServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String ipAddress;
    private String status;
    private Long responseTimeMs;
    private LocalDateTime lastChecked;

    @OneToMany(mappedBy = "monitoredServer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServerHealthRecord> healthHistory;

    public MonitoredServer() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(Long responseTimeMs) { this.responseTimeMs = responseTimeMs; }

    public LocalDateTime getLastChecked() { return lastChecked; }
    public void setLastChecked(LocalDateTime lastChecked) { this.lastChecked = lastChecked; }

    public List<ServerHealthRecord> getHealthHistory() { return healthHistory; }
    public void setHealthHistory(List<ServerHealthRecord> healthHistory) { this.healthHistory = healthHistory; }
}
