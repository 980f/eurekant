/* $Id$ */
package JUtils;

//import JUTils.ErrorLogStream;

class TestErrorLogStream {
  //#<instance ErrorLogStream.Member>
  public static ErrorLogStream dbg=new ErrorLogStream("TestErrorLogStream", true);
  //#</instance ErrorLogStream.Member>


  public static void stupidOne(){
    dbg.Enter("stupidOne");
    dbg.Message("wunner");
    dbg.Exit();
  }

  public static void stupidTwo(){
    dbg.Enter("stupidTwo");
    dbg.Message("Twobie");
    dbg.Exit(true);
  }

  public static void stupidThree(){
    dbg.Enter("stupidThree");
    dbg.Message("Threebie");
    dbg.Exit(192);
  }


  public static void main (String arguments[]){
    dbg.Enter("main");
    dbg.Message("should have a prefix \"ErrorLogStream::main\"");
    dbg.Message("calling one");
    stupidOne();
    dbg.Message("calling two");
    stupidTwo();
    dbg.Message("calling three");
    stupidThree();
    dbg.Message("about to leave");
    dbg.Exit();
  }



}
//$Id$
