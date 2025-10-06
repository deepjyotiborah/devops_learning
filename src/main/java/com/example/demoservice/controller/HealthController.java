package com.example.demoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "Health monitoring endpoints with system information")
public class HealthController {

    private static final double MEMORY_THRESHOLD = 0.90; // 90% memory usage threshold
    private static final double DISK_THRESHOLD = 0.95; // 95% disk usage threshold

    @Operation(
            summary = "Get application health status",
            description = "Returns comprehensive health status including memory, JVM, OS, CPU, and disk metrics. " +
                    "Returns HTTP 200 when healthy and HTTP 503 when service is experiencing issues."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service is healthy"),
            @ApiResponse(responseCode = "503", description = "Service is unhealthy or experiencing issues")
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> getHealth() throws Exception {
        try {
            Map<String, Object> healthResponse = new HashMap<>();
            // Basic health information
            healthResponse.put("service", "DemoService");
            healthResponse.put("timestamp", LocalDateTime.now().toString());
            
            // System information
            Map<String, Object> systemInfo = getSystemInformation();
            healthResponse.put("system", systemInfo);
            
            // Determine overall health status
            boolean isHealthy = checkHealthStatus(systemInfo);
            healthResponse.put("status", isHealthy ? "UP" : "DOWN");
            healthResponse.put("message", isHealthy ? 
                "Service is running successfully" : 
                "Service is experiencing issues");
            
            // Return appropriate HTTP status code
            HttpStatus httpStatus = isHealthy ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(healthResponse, httpStatus);
            
        } catch (Exception e) {
            // Handle unexpected errors
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("service", "DemoService");
            errorResponse.put("status", "DOWN");
            errorResponse.put("timestamp", LocalDateTime.now().toString());
            errorResponse.put("message", "Health check failed");
            errorResponse.put("error", e.getMessage());
            
            return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    
    private Map<String, Object> getSystemInformation() {
        Map<String, Object> systemInfo = new HashMap<>();
        
        // Memory information
        systemInfo.put("memory", getMemoryInformation());
        
        // JVM information
        systemInfo.put("jvm", getJvmInformation());
        
        // Operating system information
        systemInfo.put("os", getOperatingSystemInformation());
        
        // CPU information
        systemInfo.put("cpu", getCpuInformation());
        
        // Disk information
        systemInfo.put("disk", getDiskInformation());
        
        return systemInfo;
    }
    
    private Map<String, Object> getMemoryInformation() {
        Map<String, Object> memoryInfo = new HashMap<>();
        Runtime runtime = Runtime.getRuntime();
        
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        memoryInfo.put("max", formatBytes(maxMemory));
        memoryInfo.put("total", formatBytes(totalMemory));
        memoryInfo.put("free", formatBytes(freeMemory));
        memoryInfo.put("used", formatBytes(usedMemory));
        
        double usagePercent = (usedMemory * 100.0) / totalMemory;
        memoryInfo.put("usagePercent", String.format("%.2f%%", usagePercent));
        memoryInfo.put("status", usagePercent < (MEMORY_THRESHOLD * 100) ? "healthy" : "critical");
        
        return memoryInfo;
    }
    
    private Map<String, Object> getJvmInformation() {
        Map<String, Object> jvmInfo = new HashMap<>();
        
        jvmInfo.put("name", System.getProperty("java.vm.name"));
        jvmInfo.put("vendor", System.getProperty("java.vm.vendor"));
        jvmInfo.put("version", System.getProperty("java.version"));
        jvmInfo.put("uptime", formatUptime(ManagementFactory.getRuntimeMXBean().getUptime()));
        
        return jvmInfo;
    }
    
    private Map<String, Object> getOperatingSystemInformation() {
        Map<String, Object> osInfo = new HashMap<>();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        
        osInfo.put("name", System.getProperty("os.name"));
        osInfo.put("version", System.getProperty("os.version"));
        osInfo.put("architecture", System.getProperty("os.arch"));
        
        double loadAverage = osBean.getSystemLoadAverage();
        osInfo.put("systemLoadAverage", loadAverage >= 0 ? String.format("%.2f", loadAverage) : "N/A");
        
        return osInfo;
    }
    
    private Map<String, Object> getCpuInformation() {
        Map<String, Object> cpuInfo = new HashMap<>();
        Runtime runtime = Runtime.getRuntime();
        
        cpuInfo.put("availableProcessors", runtime.availableProcessors());
        
        return cpuInfo;
    }
    
    private Map<String, Object> getDiskInformation() {
        Map<String, Object> diskInfo = new HashMap<>();
        File root = new File("/");
        
        long totalSpace = root.getTotalSpace();
        long freeSpace = root.getFreeSpace();
        long usableSpace = root.getUsableSpace();
        long usedSpace = totalSpace - freeSpace;
        
        diskInfo.put("total", formatBytes(totalSpace));
        diskInfo.put("free", formatBytes(freeSpace));
        diskInfo.put("usable", formatBytes(usableSpace));
        diskInfo.put("used", formatBytes(usedSpace));
        
        if (totalSpace > 0) {
            double usagePercent = (usedSpace * 100.0) / totalSpace;
            diskInfo.put("usagePercent", String.format("%.2f%%", usagePercent));
            diskInfo.put("status", usagePercent < (DISK_THRESHOLD * 100) ? "healthy" : "critical");
        }
        
        return diskInfo;
    }
    
    private boolean checkHealthStatus(Map<String, Object> systemInfo) {
        try {
            // Check memory health
            @SuppressWarnings("unchecked")
            Map<String, Object> memoryInfo = (Map<String, Object>) systemInfo.get("memory");
            String memoryStatus = (String) memoryInfo.get("status");
            
            // Check disk health
            @SuppressWarnings("unchecked")
            Map<String, Object> diskInfo = (Map<String, Object>) systemInfo.get("disk");
            String diskStatus = (String) diskInfo.get("status");
            
            // Service is healthy if both memory and disk are healthy
            return "healthy".equals(memoryStatus) && "healthy".equals(diskStatus);
        } catch (Exception e) {
            // If we can't determine health, assume unhealthy
            return false;
        }
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.2f %sB", bytes / Math.pow(1024, exp), pre);
    }
    
    private String formatUptime(long uptimeMillis) {
        long seconds = uptimeMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        
        if (days > 0) {
            return String.format("%dd %dh %dm %ds", days, hours % 24, minutes % 60, seconds % 60);
        } else if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes % 60, seconds % 60);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds % 60);
        } else {
            return String.format("%ds", seconds);
        }
    }
}

