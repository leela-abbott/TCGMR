package abbott.ai.tcgm.comm;

//import java.util.*;
public class RemoteFile
{
  public static final String CARRIAGE_RETURN = "\r\n";

  public boolean isDirectory;
  public int rightsOwner;
  public int rightsGroup;
  public int rightsOther;
  public String link;
  public String owner;
  public String group;
  public long size;
  public String date;
  public String name;

  public RemoteFile()
  {
    super();
  }

  public static RemoteFile createFromString(String toParse)
  {
    if (toParse == null) return(null);
    String[] data = FTPSession.chaineSplit(toParse, " ", false);
    if (data.length != 9) return(null);

    RemoteFile remoteFile = new RemoteFile();
    remoteFile.isDirectory = (data[0].charAt(0) == 'd');
    remoteFile.rightsOwner = (data[0].charAt(1) == 'r' ? 4 : 0) + (data[0].charAt(2) == 'w' ? 2 : 0) + (data[0].charAt(3) == 'x' ? 1 : 0);
    remoteFile.rightsGroup = (data[0].charAt(4) == 'r' ? 4 : 0) + (data[0].charAt(5) == 'w' ? 2 : 0) + (data[0].charAt(6) == 'x' ? 1 : 0);
    remoteFile.rightsOther = (data[0].charAt(7) == 'r' ? 4 : 0) + (data[0].charAt(8) == 'w' ? 2 : 0) + (data[0].charAt(9) == 'x' ? 1 : 0);
    remoteFile.link = data[1];
    remoteFile.owner = data[2];
    remoteFile.group = data[3];
    remoteFile.size = Long.parseLong(data[4]);
    remoteFile.date = data[5] + " " + data[6] + " " + data[7];
    remoteFile.name = data[8];
    return(remoteFile);
  }

  public static RemoteFile[] createFromList(String list)
  {
    if (list == null) return(null);
    String[] files = FTPSession.chaineSplit(list, CARRIAGE_RETURN, false);
    RemoteFile[] tab = new RemoteFile[files.length];
    int nbOk = 0;
    for (int i = 0; i < files.length; i++)
    {
      tab[i] = RemoteFile.createFromString(files[i]);
      if (tab[i] != null) nbOk++;
    }

    // Remove null elements from tab
    RemoteFile[] tab2 = new RemoteFile[nbOk];
    int pos = 0;
    for (int i = 0; i < tab.length; i++)
    {
      if (tab[i] != null) tab2[pos++] = tab[i];
    }

    return(tab2);
  }

  public static RemoteFile findRemoteFile(RemoteFile[] tab, String fileName)
  {
    if (fileName == null) return(null);
    if (tab == null) return(null);
    for (int i = 0; i < tab.length; i++)
    {
      if (fileName.equals(tab[i].name)) return(tab[i]);
    }
    return(null);
  }
}
