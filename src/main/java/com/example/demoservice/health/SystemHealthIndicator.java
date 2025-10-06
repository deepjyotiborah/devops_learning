package com.example.demoservice.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

@Component
public class SystemHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            Map<String, Object> details = new HashMap<>();
            
            // Add memory information
            details.put("memory", getMemoryInformation());
            
            // Add JVM information
            details.put("jvm", getJvmInformation());
            
            // Add Operating system information
            details.put("os", getOperatingSystemInformation());
            
            // Add CPU information
            details.put("cpu", getCpuInformation());
            
            // Add Disk information
            details.put("disk", getDiskInformation());
            
            return Health.up()
                    .withDetails(details)
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withException(e)
                    .build();
        }
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
        memoryInfo.put("usagePercent", String.format("%.2f%%", (usedMemory * 100.0) / totalMemory));
        
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
        osInfo.put("systemLoadAverage", String.format("%.2f", osBean.getSystemLoadAverage()));
        
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
            diskInfo.put("usagePercent", String.format("%.2f%%", (usedSpace * 100.0) / totalSpace));
        }
        
        return diskInfo;
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

