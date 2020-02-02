package model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "MArama")
public class MArama {

    private String id;
    String content;
    Date startTime;
    Date endTime;
    String description;


    public MArama(String id, String content, Date startTime, Date endTime, String description) {
        this.id = id;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }


    @Override
    public String toString() {
        return "MArama{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


}
