/* $Id$ */
//package ;

//import java.;
class Success {
  protected boolean ok;

  Success(){
    ok=false;
  }

  public Succeeded(){
    return ok;
  }

  public boolean is(boolean desired){ //pro forma, more meaningful with significant enumerations
    return ok==desired;
  }
}
//$Id$
