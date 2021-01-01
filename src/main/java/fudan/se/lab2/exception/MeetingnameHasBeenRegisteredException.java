package fudan.se.lab2.exception;

/**
     * @author Han Minglu
 */
public class MeetingnameHasBeenRegisteredException extends RuntimeException{


  private static final long serialVersionUID = -6074753940710869977L;

   public MeetingnameHasBeenRegisteredException(String meetingname) {
            super("Name of the meeting '" + meetingname + "' has been registered");
   }
}


