package com.example.monitor.service;

import com.example.monitor.model.MonitoredServer;
import com.example.monitor.model.ServerHealthRecord;
import com.example.monitor.repository.MonitoredServerRepository;
import com.example.monitor.repository.ServerHealthRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonitoringService {
    private static final Logger logger = LoggerFactory.getLogger(MonitoringService.class);

    @Autowired
    private MonitoredServerRepository serverRepository;

    @Autowired
    private ServerHealthRecordRepository recordRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void performHealthChecks() {
        logger.info("=== Scheduled health check started at {} ===", LocalDateTime.now());

        List<MonitoredServer> servers = serverRepository.findAll();
        if (servers.isEmpty()) {
            logger.info("No servers to monitor.");
            return;
        }

        for (MonitoredServer server : servers) {
            String ip = server.getIpAddress();
            long responseTime = -1;
            Boolean isHealthy = false;

            try {
                long start = System.currentTimeMillis();
                restTemplate.getForEntity("http://" + ip, String.class);
                responseTime = System.currentTimeMillis() - start;
                isHealthy = true;
            } catch (RestClientException e1) {
                try {
                    long start = System.currentTimeMillis();
                    restTemplate.getForEntity("https://" + ip, String.class);
                    responseTime = System.currentTimeMillis() - start;
                    isHealthy = true;
                } catch (RestClientException e2) {
                    // remains DOWN
                }
            }

            server.setStatus(isHealthy ? "UP" : "DOWN");
            server.setResponseTimeMs(isHealthy ? responseTime : -1);
            server.setLastChecked(LocalDateTime.now());
            serverRepository.save(server);

            ServerHealthRecord record = new ServerHealthRecord(server, isHealthy, responseTime, LocalDateTime.now());
            recordRepository.save(record);

            logger.info("Checked {} => status={}, responseTimeMs={}, lastChecked={}",
                    ip, server.getStatus(), server.getResponseTimeMs(), server.getLastChecked());
        }
    }
}

