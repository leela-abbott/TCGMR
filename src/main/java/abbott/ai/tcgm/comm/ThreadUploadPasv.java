

package abbott.ai.tcgm.comm;

import java.io.*;

public class ThreadUploadPasv extends TransferThread
{
  public static final int BUFFER_SIZE = 65536;  // 64KBytes

  String host;
  int port;
  String fileName;
  long restartByte = 0;

  public ThreadUploadPasv(String host, int port, String fileName, long restartByte)
  {
	super();

	this.host = host;
	this.port = port;
	this.fileName = fileName;
	this.restartByte = restartByte;
	this.offsetSize = restartByte;
  }

  public void performTransfer()
  {
	boolean cont = true;
	BufferedConnection data = new BufferedConnection(TCPConnection.CONNECTION_CLIENT_TYPE);
	data.connect(host, port);

	try
	{
	  RandomAccessFile fis = new RandomAccessFile(fileName, "r");
	  if (restartByte > 0) fis.seek(restartByte);

	  byte[] buffer = new byte[BUFFER_SIZE];
	  while (cont)
	  {
	int lu = fis.read(buffer, 0, BUFFER_SIZE);
	if (lu == -1) cont = false;
	else
	{
	  transferredSize += lu;
	  data.write(buffer, 0, lu);
	}
	  }
	  fis.close();
	}
	catch (Exception e)
	{
	  //this.logger.debug(e);
	}
	data.disconnect();
  }

  public int getTransferType()
  {
	return TransferThread.TYPE_UPLOAD;
  }
}
