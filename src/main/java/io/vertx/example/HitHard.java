package io.vertx.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import io.vertx.core.json.JsonObject;

public class HitHard {
	static String USER_AGENT = "Mozilla/5.0";

	static long startTime;

	static String url = "http://localhost:8080/character";

	static int requestCounter = 0;
	static final int REQUEST_TO_SEND = 1000;
	static int reqNum = 1;

	public static void sendRequest() {

		try {
//			long startTimeRequest;
//			long endTimeRequest;

//			startTimeRequest = System.currentTimeMillis();

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://localhost:8080/character");

			// add header
			post.setHeader("User-Agent", USER_AGENT);

			JsonObject data = new JsonObject();
			data.put("characterName", "Test_" + reqNum);
			reqNum++;

			HttpEntity stringEntity = new StringEntity(data.toString(), ContentType.APPLICATION_JSON);

			post.setEntity(stringEntity);

			HttpResponse response = client.execute(post);
			/*
			 * System.out.println("\nSending 'POST' request to URL : " + url);
			 * System.out.println("Post parameters : " + post.getEntity());
			 * System.out.println("Response Code : " +
			 * response.getStatusLine().getStatusCode());
			 */

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

//			endTimeRequest = System.currentTimeMillis();
//			System.out.println("request exec time: " + (endTimeRequest - startTimeRequest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void makeRequests(int num) {
		long startTimeThread = System.currentTimeMillis();
		int i = 200;
		while (i-- > 0) {
			sendRequest();
		}
		long endTimeThread = System.currentTimeMillis();
		System.out.println("Executed 200 requests in thread " + num + " in " + (endTimeThread - startTimeThread) + "ms");
	}

	public static void main(String[] args) {
		PrintStream fileOut;
		try {
			fileOut = new PrintStream("./out.txt");
			System.setOut(fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		startTime = System.currentTimeMillis();

		ExecutorService executor = Executors.newFixedThreadPool(10);
		for(int i=1; i<=10;i++) {
			Runnable r = createRunnable(i);
			executor.execute(r);
		}
		
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
 
		}

		long endTime = System.currentTimeMillis();
		System.out.println("all request send after: " + (endTime - startTime) + "ms");

	}

	private static Runnable  createRunnable(int num) {
		Runnable  runnable = new Runnable () {
			public void run() {
				makeRequests(num);
			}
		};

		return runnable;
	}

}
