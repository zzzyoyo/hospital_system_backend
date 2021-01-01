package fudan.se.lab2.controller.request;

public class RebuttalRequest {
    private String essayTitle;
    private String rebuttalText;
    private String username;

    public RebuttalRequest(){}
    public RebuttalRequest(String essayTitle,String rebuttalText,String username){
        this.essayTitle = essayTitle;
        this.rebuttalText = rebuttalText;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEssayTitle() {
        return essayTitle;
    }

    public String getRebuttalText() {
        return rebuttalText;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEssayTitle(String essayTitle) {
        this.essayTitle = essayTitle;
    }

    public void setRebuttalText(String rebuttalText) {
        this.rebuttalText = rebuttalText;
    }
}
