package com.rumahorbo.conductormultipletask;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.rumahorbo.conductormultipletask.worker.AdditionPalindromeWorker;
import com.rumahorbo.conductormultipletask.worker.VerifyIntegerPalindromeWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ConductorMultipleTaskApplication {

    public static void main(String[] args) {
        conductorNetflix();
        SpringApplication.run(ConductorMultipleTaskApplication.class, args);
    }

    private static void conductorNetflix() {
        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI("http://localhost:8080/api/"); // Point this to the server API

        int threadCount = 2; // number of threads used to execute workers.  To avoid starvation, should be
        // same or more than number of workers

        Worker worker1 = new AdditionPalindromeWorker(new VerifyIntegerPalindromeWorker());
        Worker worker2 = new VerifyIntegerPalindromeWorker();

        // Create TaskRunnerConfigurer
        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, Arrays.asList(worker1, worker2))
                        .withThreadCount(threadCount)
                        .build();

        // Start the polling and execution of tasks
        configurer.init();
    }

}
