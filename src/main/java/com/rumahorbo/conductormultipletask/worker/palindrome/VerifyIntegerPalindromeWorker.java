package com.rumahorbo.conductormultipletask.worker.palindrome;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

public class VerifyIntegerPalindromeWorker implements Worker {

    @Override
    public String getTaskDefName() {
        return "verify_if_integer_is_palindrome";
    }

    @Override
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        String integer = (String) task.getInputData().get("integer");
        result.addOutputData("isPalindrome", Boolean.toString(isPalindrome(Integer.parseInt(integer))));
        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }

    public boolean isPalindrome(int value) {
        if (value < 0) {
            return false;
        }
        String integer = Integer.toString(value);
        int leftPointer = 0;
        int rightPointer = integer.length() - 1;
        while (leftPointer <= rightPointer) {
            char leftChar = integer.charAt(leftPointer);
            char rightChar = integer.charAt(rightPointer);
            if (leftChar != rightChar) {
                return false;
            }
            leftPointer++;
            rightPointer--;
        }
        return true;
    }
}
