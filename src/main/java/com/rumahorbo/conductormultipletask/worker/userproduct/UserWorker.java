package com.rumahorbo.conductormultipletask.worker.userproduct;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.rumahorbo.conductormultipletask.model.User;
import com.rumahorbo.conductormultipletask.service.RestService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserWorker implements Worker {

    private final RestService restService;

    @Override
    public String getTaskDefName() {
        return "add_user";
    }

    @Override
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        String firstName = (String) task.getInputData().get("firstName");
        User user = new User();
        user.setFirstName(firstName);
        result.addOutputData("addUserMessage", "User with name " + restService.createUser(user).block().getFirstName() + "is stored");
        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }
}
