import java.io.*;
import java.net.*;

public class TextServer2
{
  String orden;

  public TextServer2()
  {
  }

  public void runServer()
  {
    orden = new String("");
    ServerSocket server;
    Socket connection;
    InputStream instream;
    int count=0, count2=0;
    byte bytesout[]=new byte[1024];
    String texto;

    try
    {
      server=new ServerSocket(8000,100);
      System.out.print("Arranca el Servidor de FTP\n");

      while(true)
      {
        texto =new String("");
        System.out.print("Esperando por la coneccion de un cliente\n");
        connection=server.accept();
        System.out.print("Coneccion recibida del Cliente...\n");
        System.out.print("Recibiendo datos...\n");

        instream=connection.getInputStream();
        char c;
        System.out.print("Recibiendo el texto:\n");
        while ((c=(char) instream.read())!='\n')
          texto += String.valueOf(c);
        System.out.print(texto);
        setOrden(texto);
        System.out.print("\n");

        System.out.print("Recepcion completa. Cerrando socket.\n");
        System.out.print("\n");
        System.out.print("\n");
        instream.close();
        connection.close();
//        server.close();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    System.out.print("Termina\n");
  }

  public void setOrden(String texto)
  {
    orden = texto;
  }

  public String getOrden()
  {
    return orden;
  }

  public static void main(String args[])
  {
    TextServer2 s=new TextServer2();
      s.runServer();
  }
}
