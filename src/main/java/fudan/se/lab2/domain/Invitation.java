package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
@Table(name = "invitation")
public class Invitation {


        private static final long serialVersionUID = -6140085056226164016L;

        @Id
       @Column(name="invitationId")
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column
        private String chairUsername;

        //@Column(name="pcmemberUsername")
        private String PCmemberUsername;
    //@Column(name="meetingShortname")
        private String  meetingShortname;
        //state 初始为0，待接受；拒绝为-1；同意为1
        private int state;

        public Invitation(){}

        public Invitation(String chairUsername,String PCmemberUsername, String meetingShortname){
            this.chairUsername = chairUsername;
            this.PCmemberUsername = PCmemberUsername;
            this.meetingShortname = meetingShortname;
            this.state = 0;

        }

        public void setState(int state){
            if((state == 1)||(state == -1)||(state ==0)){
                this.state = state;
            }
        }
        public int getState(){
            return this.state;
        }

        public String getPCmemberUsername(){return  this.PCmemberUsername;}

        public String getChairUsername(){return this.chairUsername;}

        public String getMeetingShortname(){return this.meetingShortname;}

}
