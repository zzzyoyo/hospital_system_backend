package fudan.se.lab2.controller.request;

public class WardNurseSelectRequest {
    private  int area_type;
    private  int trans;
    private int leave;
    private int status;
    private String  wardNurseName;
    public WardNurseSelectRequest(){}
    public WardNurseSelectRequest(int area_type, String wardNurseName,int leave,int trans,int status){
        this.trans = trans;
        this.wardNurseName = wardNurseName;
        this.area_type= area_type;
        this.leave = leave;
        this.status = status;
    }

    public int getArea_type() {
        return area_type;
    }

    public int getLeave() {
        return leave;
    }

    public int getStatus() {
        return status;
    }

    public int getTrans() {
        return trans;
    }

    public String getWardNurseName() {
        return wardNurseName;
    }
}
