package io.vertx.example;

public class Result {
	private String name;
    private long timestamp;
 
    public Result(String name, long timestamp) {
        super();
        this.name = name;
        this.timestamp = timestamp;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public long getTimestamp() {
        return timestamp;
    }
 
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
 
    @Override
    public String toString() {
        return "Result [name=" + name + ", value=" + timestamp + "]";
    }
}
