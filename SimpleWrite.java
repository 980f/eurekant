import java.io.*;
import java.util.*;
import javax.comm.*;

//uses PortName.*;

public class SimpleWrite {
  static Enumeration portList;
  static CommPortIdentifier portId;
  static String messageString1 = "Hello, world!\n";
  static String messageString2 = "This is a Test\n";
  static String messageString3 = "Eureka Software\n";
  static SerialPort serialPort;
  static OutputStream outputStream;

  public static void main(String[] args) {
    portList = CommPortIdentifier.getPortIdentifiers();

    while (portList.hasMoreElements()) {
      portId = (CommPortIdentifier) portList.nextElement();
      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
        if (portId.getName().equals(PortName(1).Text())) {
          System.out.println("Writing to COM1:....");
          try {
            serialPort = (SerialPort)
            portId.open("SimpleWriteApp", 2000);
          } catch (PortInUseException e) {
            System.out.println("Port in use...Bailing.");
          }

          try {
            outputStream = serialPort.getOutputStream();
          } catch (IOException e) {
            /**/
          }

          try {
            serialPort.setSerialPortParams(9600,
            SerialPort.DATABITS_8,
            SerialPort.STOPBITS_1,
            SerialPort.PARITY_NONE);
          } catch (UnsupportedCommOperationException e) {
            /**/
          }

          try {
            outputStream.write(messageString1.getBytes());
            outputStream.write(messageString2.getBytes());
            outputStream.write(messageString3.getBytes());
          } catch (IOException e){
            /**/
          }
        }
      }
    }
  }
}

//$Id: SimpleWrite.java,v 1.2 2000/03/30 13:48:26 anonymous Exp $
