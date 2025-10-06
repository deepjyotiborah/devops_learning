package com.example.demoservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthController.class)
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getHealth_ShouldReturnHealthStatus() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("DemoService"))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void getHealth_ShouldReturnSystemInformation() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.system").exists())
                .andExpect(jsonPath("$.system.memory").exists())
                .andExpect(jsonPath("$.system.jvm").exists())
                .andExpect(jsonPath("$.system.os").exists())
                .andExpect(jsonPath("$.system.cpu").exists())
                .andExpect(jsonPath("$.system.disk").exists());
    }

    @Test
    void getHealth_ShouldReturnMemoryInformation() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.system.memory.max").exists())
                .andExpect(jsonPath("$.system.memory.total").exists())
                .andExpect(jsonPath("$.system.memory.free").exists())
                .andExpect(jsonPath("$.system.memory.used").exists())
                .andExpect(jsonPath("$.system.memory.usagePercent").exists())
                .andExpect(jsonPath("$.system.memory.status").exists());
    }

    @Test
    void getHealth_ShouldReturnJvmInformation() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.system.jvm.name").exists())
                .andExpect(jsonPath("$.system.jvm.vendor").exists())
                .andExpect(jsonPath("$.system.jvm.version").exists())
                .andExpect(jsonPath("$.system.jvm.uptime").exists());
    }

    @Test
    void getHealth_ShouldReturnOsInformation() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.system.os.name").exists())
                .andExpect(jsonPath("$.system.os.version").exists())
                .andExpect(jsonPath("$.system.os.architecture").exists())
                .andExpect(jsonPath("$.system.os.systemLoadAverage").exists());
    }

    @Test
    void getHealth_ShouldReturnCpuInformation() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.system.cpu.availableProcessors").exists());
    }

    @Test
    void getHealth_ShouldReturnDiskInformation() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.system.disk.total").exists())
                .andExpect(jsonPath("$.system.disk.free").exists())
                .andExpect(jsonPath("$.system.disk.usable").exists())
                .andExpect(jsonPath("$.system.disk.used").exists())
                .andExpect(jsonPath("$.system.disk.status").exists());
    }
}

