package abbott.ai.tcgm.comm;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.net.*;
import java.io.*;


public class TCPConnection
{
  public static final int CONNECTION_SERVER_TYPE = 1;
  public static final int CONNECTION_CLIENT_TYPE = 2;

  public static final int SO_TIMEOUT = 0;
  public static final int BUFFER_SIZE = 65536;  // 64KBytes

  private int m_type = 0;
  private Socket m_socket = null;
  private ServerSocket m_serversocket = null;
  private String m_host = null;
  private int m_port = -1;
  private byte[] m_buffer = new byte[BUFFER_SIZE];

  private InputStream m_inputstream = null;
  private OutputStream m_outputstream = null;
  private InputStreamReader m_inputstreamreader = null;
  private BufferedReader m_bufferedreader = null;

  private boolean connected = false;

  // Constructor
  public TCPConnection(int type)
  {
	m_type = type;
  }

  // Constructor for a server connection
  public TCPConnection(Socket socket)
  {
	m_type = CONNECTION_SERVER_TYPE;
	m_socket = socket;

	try
	{
	  m_outputstream = m_socket.getOutputStream();
	  m_inputstream = m_socket.getInputStream();
	  m_inputstreamreader = new InputStreamReader(m_inputstream);
	  m_bufferedreader = new BufferedReader(m_inputstreamreader);
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
	  m_inputstreamreader = new InputStreamReader(m_inputstream);
	  m_bufferedreader = new BufferedReader(m_inputstreamreader);
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
	  //m_inputstream.close();
	  m_bufferedreader.close();
	  m_outputstream.flush();
	  m_outputstream.close();
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

  public String read() throws IOException
  {
	if (!connected) return(null);
	return(m_bufferedreader.readLine());
  }

  public int write(byte[] toWrite)
  {
	if (toWrite == null) return(0);
	if (toWrite.length == 0) return(0);

	if (!connected) return(0);

	try
	{
	  m_outputstream.write(toWrite);
	  m_outputstream.flush();
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