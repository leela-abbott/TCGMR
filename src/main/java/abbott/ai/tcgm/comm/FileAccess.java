

package abbott.ai.tcgm.comm;

import java.io.*;
import java.text.SimpleDateFormat;

public class FileAccess
{
  public static final String CARRIAGE_RETURN = "\r\n";
  private FileWriter fw = null;
  private String nomFich = null;
  private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

  public FileAccess(String sNomFich)
  {
    nomFich = sNomFich;
  }

  public synchronized int open()
  {
    if (fw == null)
    {
      try
      {
        fw = new FileWriter(nomFich, true);
      }
      catch (IOException e)
      {
        // Open file KO
        return(-1);
      }
    }
    else return(0); // File already open

    return(1); // Open file OK
  }

  public synchronized int close()
  {
    if (fw != null)
    {
      try
      {
        fw.flush();
        fw.close();
        fw = null;
      }
      catch (IOException e)
      {
        // File closed KO
        return(-1);
      }
    }
    else return(0); // File already closed

    return(1); // File closed OK
  }

  public synchronized int writeln(String chaine)
  {
    return(write(chaine + CARRIAGE_RETURN));
  }

  public synchronized int write(String chaine)
  {
    if (fw == null) return(0);
    try
    {
      // log
      fw.write(sdf.format(new java.util.Date()) + " : " + chaine);

      // flush the file
      fw.flush();
    }
    catch(IOException e)
    {
      // Write KO
      return(-1);
    }

    // Write OK
    return(1);
  }
}
