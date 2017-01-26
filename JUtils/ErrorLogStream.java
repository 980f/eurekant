/* $Id: ErrorLogStream.java,v 1.1 2000/03/30 13:48:26 anonymous Exp $ */
package JUtils;

import java.util.Date;
import java.util.Stack;
//local JUtils;

public class ErrorLogStream {
  private boolean amLogging = false;
  private String className;

  private Stack context; //the stack for ActiveMethod
  private String ActiveMethod; //easier than using context.peek()

  public void Init(String cname,boolean enableit){
    amLogging = enableit;
    className=cname;
    context = new Stack();
    context.push("StackError"); //a guard entry
  }


  public ErrorLogStream(String cname,boolean enableit){
    Init(cname,enableit);
  }

  public ErrorLogStream(String cname){
    Init(cname,true);
  }

  public void Message(String message){
    if ( (JUtils.g_bLogAll || amLogging) && (null != JUtils.g_oJUtils)) {
      JUtils.g_oJUtils.AddLogEntry( //{
        (new Date()).toString()
        + ", Thread = " + Thread.currentThread()
        + ", " + className
        + "::" + ActiveMethod
        + ":"  + message
      );                            //}
    }
  }

  public void Enter(String methodName){
    context.push(ActiveMethod);
    ActiveMethod=methodName;
    Message("Entered");
  }

  public void Caught(Throwable oThrowable){
    Message("ERROR = " + oThrowable.toString());
    oThrowable.printStackTrace(System.out);
  }

  public void Exit(boolean returnatus) {
    Message("Returns:"+ (returnatus?"true":"false") );
    ActiveMethod= (String) context.pop();
  }

  public void Exit(long returnatus) {
    Message("Returns:"+ returnatus);
    ActiveMethod= (String) context.pop();
  }

  public void Exit() {
    Message("Exits");
    ActiveMethod= (String) context.pop();
  }
}

//$Id: ErrorLogStream.java,v 1.1 2000/03/30 13:48:26 anonymous Exp $
