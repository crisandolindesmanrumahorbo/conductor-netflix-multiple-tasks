package com.rumahorbo.conductormultipletask.worker.palindrome;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdditionPalindromeWorker implements Worker {

    private final VerifyIntegerPalindromeWorker verifyIntegerPalindromeWorker;

    @Override
    public String getTaskDefName() {
        return "addition_to_get_palindrome";
    }

    @Override
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        String integer = (String) task.getInputData().get("integer");
        result.addOutputData("palindromeMessage", "Addition to closest palindrome is " + getAdditionToPalindrome(Integer.parseInt(integer)));
        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }

    public int getAdditionToPalindrome(int integer) {
        boolean isPalindrome = false;
        int addition = 0;
        while (!isPalindrome) {
            addition++;
            isPalindrome = verifyIntegerPalindromeWorker.isPalindrome(integer + addition);
        }
        return addition;
    }
}
