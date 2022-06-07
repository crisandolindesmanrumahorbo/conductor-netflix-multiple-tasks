package com.rumahorbo.conductormultipletask.worker.userQuote;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.rumahorbo.conductormultipletask.model.Product;
import com.rumahorbo.conductormultipletask.service.RestService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductWorker implements Worker {

    private final RestService restService;

    @Override
    public String getTaskDefName() {
        return "add_product";
    }

    @Override
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        String title = (String) task.getInputData().get("title");
        Product product = new Product();
        product.setTitle(title);
        result.addOutputData("addProductMessage", "Product with title " + restService.createProduct(product).block().getTitle() + " is stored");
        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }
}
