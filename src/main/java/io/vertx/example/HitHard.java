package io.vertx.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HitHard {
    
    
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		// TODO Auto-generated method stub
		
		ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(100);
		List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Task task = new Task("Task-" + i);
            taskList.add(task);
        }
        //Execute all tasks and get reference to Future objects
        List<Future<Result>> resultList = null;
 
        try {
            resultList = executor.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        executor.shutdown();
        
        for(Future<Result> result : resultList){
        	System.out.println("future.get = " + result.get().toString());
           
         }
		
	}

}
