package fudan.se.lab2.controller.request;

import java.util.Map;

public class UploadRequest {
    private String title;
    private String authorname;
    private String meetingFullname;
    private String summary;
   // private MultipartFile file;
    private String[]topic;
    private Map<String,String>[] writer;

    public UploadRequest(){}
    public UploadRequest( String title,String authorname,String[]topic,String summary,String meetingFullname,Map<String,String>[]writer){
        this.title = title;
        this.authorname = authorname;
        this. meetingFullname = meetingFullname;

        this.summary = summary;
        this.topic = topic;
        //this.file = file;
        this.writer = writer;

    }
   /*public MultipartFile getFile(){
       return file;

   }
*/
    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthorname() {
        return authorname;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public String[] getTopic() {
        return topic;
    }

    public Map<String, String>[] getWriter() {
        return writer;
    }
}
