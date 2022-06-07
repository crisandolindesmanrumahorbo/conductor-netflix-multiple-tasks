package com.rumahorbo.conductormultipletask;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.rumahorbo.conductormultipletask.service.RestService;
import com.rumahorbo.conductormultipletask.worker.palindrome.AdditionPalindromeWorker;
import com.rumahorbo.conductormultipletask.worker.palindrome.VerifyIntegerPalindromeWorker;
import com.rumahorbo.conductormultipletask.worker.userproduct.ProductWorker;
import com.rumahorbo.conductormultipletask.worker.userproduct.UserWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ConductorMultipleTaskApplication {

    public static void main(String[] args) {
        conductorNetflix();
        SpringApplication.run(ConductorMultipleTaskApplication.class, args);
    }

    private static void conductorNetflix() {
        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI("http://localhost:8080/api/"); // Point this to the server API

        Worker additionPalindromeWorker = new AdditionPalindromeWorker(new VerifyIntegerPalindromeWorker());
        Worker verifyIntegerPalindromeWorker = new VerifyIntegerPalindromeWorker();
        Worker userWorker = new UserWorker(new RestService());
        Worker productWorker = new ProductWorker(new RestService());
        List<Worker> workers = Arrays.asList(additionPalindromeWorker, verifyIntegerPalindromeWorker, userWorker, productWorker);

        int threadCount = workers.size(); // number of threads used to execute workers.  To avoid starvation, should be
        // same or more than number of workers

        // Create TaskRunnerConfigurer
        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, workers)
                        .withThreadCount(threadCount)
                        .build();

        // Start the polling and execution of tasks
        configurer.init();
    }

}
