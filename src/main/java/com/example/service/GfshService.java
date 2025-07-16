package com.example.service;

import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class GfshService {
    public GfshService() {}

    public String runCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("gfsh", "-e", command);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
            return output.toString();
        }
    }
}