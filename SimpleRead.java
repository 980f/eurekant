import java.io.*;
import java.util.*;
import javax.comm.*;

//uses PortName.*;

public class SimpleRead implements Runnable, SerialPortEventListener {
  static CommPortIdentifier portId;
  static Enumeration portList;

  InputStream inputStream;
  SerialPort serialPort;
  Thread readThread;

  public static void main(String[] args) {
    portList = CommPortIdentifier.getPortIdentifiers();

    while (portList.hasMoreElements()) {
      portId = (CommPortIdentifier) portList.nextElement();

      if (portId.getName().equals(PortName(1).Text())) {
        SimpleRead reader = new SimpleRead();
      }
    }
  }

  public SimpleRead() {
    try {
      serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
    } catch (PortInUseException e) {}

    try {
      inputStream = serialPort.getInputStream();
    } catch (IOException e) {}

    try {
      serialPort.addEventListener(this);
    } catch (TooManyListenersException e) {}

    serialPort.notifyOnDataAvailable(true);

    try {
      serialPort.setSerialPortParams(9600,
      SerialPort.DATABITS_8,
      SerialPort.STOPBITS_1,
      SerialPort.PARITY_NONE);
    } catch (UnsupportedCommOperationException e) {}

    readThread = new Thread(this);
    readThread.start();
  }

  public void run() {
    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {}
  }

  public void serialEvent(SerialPortEvent event) {
    switch(event.getEventType()) {
      case SerialPortEvent.BI:
      case SerialPortEvent.OE:
      case SerialPortEvent.FE:
      case SerialPortEvent.PE:
      case SerialPortEvent.CD:
      case SerialPortEvent.CTS:
      case SerialPortEvent.DSR:
      case SerialPortEvent.RI:
      case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
      break;
      case SerialPortEvent.DATA_AVAILABLE:
      byte[] readBuffer = new byte[20];

      try {
        while (inputStream.available() > 0) {
          //int numBytes = inputStream.read(readBuffer);
          System.out.print((char)inputStream.read());
        }
        System.out.print(new String(readBuffer));
      } catch (IOException e) {}
      break;
    }
  }
}
//$Id: SimpleRead.java,v 1.2 2000/03/30 13:48:26 anonymous Exp $
