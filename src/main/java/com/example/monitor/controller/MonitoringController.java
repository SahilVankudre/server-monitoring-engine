package com.example.monitor.controller;

import com.example.monitor.model.MonitoredServer;
import com.example.monitor.model.ServerHealthRecord;
import com.example.monitor.repository.MonitoredServerRepository;
import com.example.monitor.repository.ServerHealthRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class MonitoringController {

    @Autowired
    private MonitoredServerRepository serverRepository;

    @Autowired
    private ServerHealthRecordRepository recordRepository;

    @GetMapping
    public List<MonitoredServer> getAllServers() {
        return serverRepository.findAll();
    }

    @PostMapping
    public MonitoredServer addServer(@RequestBody MonitoredServer server) {
        return serverRepository.save(server);
    }

    @GetMapping("/{id}/history")
    public List<ServerHealthRecord> getHistory(@PathVariable Long id) {
        return recordRepository.findByMonitoredServerIdOrderByTimestampDesc(id);
    }
}
