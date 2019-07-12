package io.vertx.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.Future;

public class HitHard implements Callable<InputStream> {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

		File file = new File("PostOutput.txt");
		if (!file.exists()) {
			file.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

		long startTime = System.nanoTime();

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);

		List<Future<InputStream>> resultList = new ArrayList<>();

		// to invoke the method parallel using executor frame work, non blocking call
		for (int i = 0; i <= 100; i++) {

			Future<InputStream> result = executor.submit(new HitHard());

			resultList.add(result);
		}

		// stream the response from the feature object and write to the file.
		resultList.parallelStream().forEach(s -> {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader((s.get())));
				String output;
				while ((output = br.readLine()) != null) {

					writer.write(output);
					writer.write(System.lineSeparator());

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

		long endTime = System.nanoTime();

		long timeElapsed = endTime - startTime;

		writer.write("Execution time in nanoseconds  : " + timeElapsed);

		writer.write(System.lineSeparator());
		writer.write("Execution time in milliseconds : " + timeElapsed / 1000000);
		
		System.out.println("Program is successfull test result are available in  PostOutput.txt");

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
			executor.shutdown();

		}
	}

	@Override
	public InputStream call() throws Exception {

		URL url = new URL("http://localhost:8080/character");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "application/json");

		String input = "{\"characterName\":\"Alton Lannister\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		return conn.getInputStream();
	}
}
