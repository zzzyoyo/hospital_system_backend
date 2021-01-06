package fudan.se.lab2.controller.request;

public class OperateNurseRequest {
    private String nurseName;
    private int area_type;

    public OperateNurseRequest() {
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public int getArea_type() {
        return area_type;
    }

    public void setArea_type(int area_type) {
        this.area_type = area_type;
    }

    public OperateNurseRequest(String nurseName, int area_type) {
        this.nurseName = nurseName;
        this.area_type = area_type;
    }
}
