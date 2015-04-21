package manunya.simpletodo;

import java.io.Serializable;

/**
 * Created by Manunya on 4/19/2015.
 */
public class TodoItem implements Serializable{
    private static final long serialVersionUID = 5177222050535318633L;
    private int id;
    private String body;
    private int priority = -1; // not using it yet
    private long due_date; //store as Unix time

    public TodoItem(String body, long due_date) {
        super();
        this.body = body;
        this.due_date = due_date;
    }

    public TodoItem(String body) {
        this(body, -1);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDueDate() {
        return due_date;
    }

    public void setDueDate(long due_date) {
        this.due_date = due_date;
    }
}

