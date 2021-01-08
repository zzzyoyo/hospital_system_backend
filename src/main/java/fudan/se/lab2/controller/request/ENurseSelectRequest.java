package fudan.se.lab2.controller.request;

public class ENurseSelectRequest {
   private  int area_type;
    private  int rating;
    private  int status;

    public ENurseSelectRequest(int area_type, int rating, int status) {
        this.area_type = area_type;
        this.rating = rating;
        this.status = status;
    }

    public ENurseSelectRequest() {
    }

    public int getArea_type() {
        return area_type;
    }


    public int getRating() {
        return rating;
    }

    public int getStatus() {
        return status;
    }
}
