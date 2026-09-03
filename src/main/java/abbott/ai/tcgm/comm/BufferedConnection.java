package abbott.ai.tcgm.comm;

import java.net.*;
import java.io.*;

public class BufferedConnection
{
  public static final int CONNECTION_SERVER_TYPE = 1;
  public static final int CONNECTION_CLIENT_TYPE = 2;

  public static final int SO_TIMEOUT = 100;
  public static final int BUFFER_SIZE = 65536;  // 64KBytes

  private int m_type = 0;
  private Socket m_socket = null;
  private ServerSocket m_serversocket = null;
  private String m_host = null;
  private int m_port = -1;
  private byte[] m_buffer = new byte[BUFFER_SIZE];

  private InputStream m_inputstream = null;
  private OutputStream m_outputstream = null;
  private BufferedInputStream m_bufferedinputstream = null;
  private BufferedOutputStream m_bufferedoutputstream = null;

  private boolean connected = false;

  // Constructor
  public BufferedConnection(int type)
  {
	m_type = type;
  }

  // Constructor for a server connection
  public BufferedConnection(Socket socket)
  {
	m_type = CONNECTION_SERVER_TYPE;
	m_socket = socket;

	try
	{
	  m_outputstream = m_socket.getOutputStream();
	  m_inputstream = m_socket.getInputStream();
	  m_bufferedoutputstream = new BufferedOutputStream(m_outputstream);
	  m_bufferedinputstream = new BufferedInputStream(m_inputstream);
	}
	catch (IOException e)
	{
	  return;
	}

	try
	{
	  m_socket.setSoTimeout(SO_TIMEOUT);
	}
	catch (Exception e){}

	connected = true;
  }

  // Connection
  public int connect(String host, int port)
  {
	if (connected) return(0);

	m_host = host;
	m_port = port;

	if (m_type == CONNECTION_SERVER_TYPE)
	{
	  //this.logger.debug("Server connection started...");
	  try
	  {
		m_serversocket = new ServerSocket(m_port);
		m_port = m_serversocket.getLocalPort();
		m_socket = m_serversocket.accept();
	  }
	  catch (IOException e)
	  {
		return(-1);
	  }
	}
	else
	{
	  //this.logger.debug("Client connection started...");
	  try
	  {
		m_socket = new Socket(m_host, m_port);
	  }
	  catch (IOException e)
	  {
		return(-1);
	  }
	}

	try
	{
	  m_outputstream = m_socket.getOutputStream();
	  m_inputstream = m_socket.getInputStream();
	  m_bufferedoutputstream = new BufferedOutputStream(m_outputstream);
	  m_bufferedinputstream = new BufferedInputStream(m_inputstream);
	}
	catch (IOException e)
	{
	  return(-1);
	}

	try
	{
	  m_socket.setSoTimeout(SO_TIMEOUT);
	}
	catch (Exception e){}

	connected = true;
	return(0);
  }

  // Disconnection
  public synchronized int disconnect()
  {
	if (!connected) return(0);

	try
	{
	  m_bufferedinputstream.close();
	  m_bufferedoutputstream.flush();
	  m_bufferedoutputstream.close();
	}
	catch (IOException e){}

	try
	{
	  m_socket.close();
	}
	catch (IOException e)
	{
	  return(-1);
	}

	connected = false;
	return(0);
  }

  public boolean isConnected()
  {
	return(connected);
  }

  public byte[] read()
  {
	if (!connected) return(null);

	int len = -1;
	try
	{
	  len = m_bufferedinputstream.read(m_buffer, 0, BUFFER_SIZE);
	}
	catch (InterruptedIOException e)
	{
	  len = 0;
	}
	catch (IOException e){}

	if (len == -1)
	{
	  //this.logger.debug("The connection has been closed.");
	  return(null);
	}

	byte[] ret = new byte[len];
	for (int i = 0; i < len; i++) ret[i] = m_buffer[i];
	return(ret);
  }

  public int write(byte[] toWrite)
  {
	if (toWrite == null) return(0);
	return(write(toWrite, 0, toWrite.length));
  }

  public int write(byte[] toWrite, int start, int end)
  {
	if (toWrite == null) return(0);
	if (toWrite.length == 0) return(0);

	if (!connected) return(0);

	try
	{
	  m_bufferedoutputstream.write(toWrite, start, end);
	  m_bufferedoutputstream.flush();
	}
	catch (IOException e)
	{
	  return(-1);
	}
	return(0);
  }

  public int getLocalPort()
  {
	return(m_port);
  }
}
