package fudan.se.lab2.controller.request;

public class DoctorSelectRequest {
    private  int type;
    private  int trans;
    private int leave;
    private int status;
    public DoctorSelectRequest(){}
    public DoctorSelectRequest(int area_type, int leave,int trans,int status){
        this.trans = trans;
        this.type = area_type;
        this.leave = leave;
        this.status = status;

    }

    public int getType() {
        return type;
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
}
