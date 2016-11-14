import java.io.*;
import java.net.*;

public class TextClient2
{

  public TextClient2()
  {
  }

  public void runClient(String server, String texto)
  {
    Socket client;
    OutputStream outstream;
    int count=0;
    byte bytes[]=new byte[1024];

    try
    {
      System.out.print("Arranca el Cliente de FTP\n");
      client=new Socket(server, 8000);
      System.out.print("Socket Creado\n");
      System.out.print("Enviando Datos al Servidor\n");

      outstream=client.getOutputStream();

      System.out.print("Enviando el texto:\n");
      String s=new String(texto+"\n");
      System.out.print(s);
      for (int i=0;i<s.length();i++)
        outstream.write((int)s.charAt(i));

      System.out.print("Transmision Completa. Cerrando Socket.\n");
      System.out.print("\n");
      System.out.print("\n");
      outstream.close();
      client.close();
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }

  public static void main(String args[])
  {
    TextClient2 c=new TextClient2();
    String mensaje = new String("");
    for (int i=0; i<args.length; i++)
      mensaje = mensaje.concat(args[i]+" ");

    c.runClient("localhost", mensaje);
  }
}

