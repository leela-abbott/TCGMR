package abbott.ai.tcgm.comm;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.util.*;
import org.apache.log4j.Logger;

public class FTPSession
{
	private static Logger logger = Logger.getLogger("TCGM.Comm.FTPSession");
	public static final String CARRIAGE_RETURN = "\r\n";

	public static final int CODE_CONNECT_OK = 220;
	public static final int CODE_DISCONNECT_OK = 221;
	public static final int CODE_LOGGEDIN = 230;
	public static final int CODE_TRANSFER_OK = 226;
	public static final int CODE_CD_OK = 250;
	public static final int CODE_PWD_OK = 257;

	protected static final int CODE_DL_FILE_OK = 150;
	protected static final int CODE_UL_FILE_OK = 150;
	protected static final int CODE_JES_UL_ACCEPTED = 125;

	public static final int CODE_DELETE_FILE_OK = 250;
	public static final int CODE_MKDIR_OK = 257;
	public static final int CODE_RMDIR_OK = 250;
	public static final int CODE_RNFR_OK = 350;
	public static final int CODE_RNTO_OK = 250;

	public static final String[] ASCII_EXT = { ".TXT", ".XML", ".HTM", ".BAT", ".CSS", ".HTML", ".ASP", ".C", ".H", ".CPP", ".PHP", ".SHTML", ".DHTML" };

	public static final int TRANSFER_PORT = 0;
	public static final int TRANSFER_PASV = 1;

	private static final int MODE_UNDEFINED = -1;
	public static final int MODE_AUTO = 0;
	public static final int MODE_BINARY = 1;
	public static final int MODE_ASCII = 2;
	public static final int MODE_JES = 3;

	private String host;
	private int port;
	private String proxyHost;
	private int proxyPort;
	private boolean useProxy = false;
	private String login;
	private String password;
	private int transfer = TRANSFER_PORT;
	private int mode = MODE_AUTO;
	private int _mode = MODE_UNDEFINED;
	private String lastRequest = "";
	private String lastReply = "";
	private RemoteFile[] directoryList = null;
	private int lastCode = -1;
	private boolean connected = false;
	private boolean log = false;
	private FileAccess fileAccess = null;
	private String workingDirectory = "";
	private String stringDirectoryList = "";
	private long fileSize = 0;

	private String passive_host = "";
	private int passive_port = -1;

	private TCPConnection comm = null;

	public FTPSession(String host)
	{
		this.host = host;
		this.port = 21;
		useProxy = false;

		comm = new TCPConnection(TCPConnection.CONNECTION_CLIENT_TYPE);
	}

	public FTPSession(String host, int port)
	{
		this.host = host;
		this.port = port;
		useProxy = false;

		comm = new TCPConnection(TCPConnection.CONNECTION_CLIENT_TYPE);
	}

	public FTPSession(String host, int port, String proxyHost, int proxyPort)
	{
		this.host = host;
		this.port = port;
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		useProxy = true;

		comm = new TCPConnection(TCPConnection.CONNECTION_CLIENT_TYPE);
	}

	public void setMode(int mode)
	{
		this.mode = mode;
	}

	public void setTransfer(int transfer)
	{
		this.transfer = transfer;
	}

	public int reconnect()
	{
		disconnect();
		return(connect());
	}

	public int connect()
	{
		int code = -1;
		if (useProxy)
		{
			code = comm.connect(proxyHost, proxyPort);
		}
		else
		{
			code = comm.connect(host, port);
		}
		if (code != 0)
		{
			lastCode = -1;
			return(-1);
		}
		if (!read(comm)) return(-1);
		connected = true;
		_mode = MODE_UNDEFINED;
		return(0);
	}

	public int disconnect()
	{
		_mode = MODE_UNDEFINED;
		if (!connected)
		{
			lastCode = -1;
			return(0);
		}
		write(comm, "QUIT");
		read(comm);
		comm.disconnect();
		connected = false;
		return(0);
	}

	public int login(String login, String password)
	{
		this.login = login;
		this.password = password;
		if (useProxy)
		{
			write(comm, "USER " + login + "@" + host + ":" + port);
		}
		else
		{
			write(comm, "USER " + login);
		}
		if (!read(comm)) return(-1);
		write(comm, "PASS " + password);
		if (!read(comm)) return(-1);
		_mode = MODE_UNDEFINED;
		return(0);
	}

	public int relogin()
	{
		return(login(login, password));
	}

	public int cd(String directory)
	{
		write(comm, "CWD " + directory);
		if (!read(comm)) return(-1);
		return(0);
	}

	public int size(String fileName)
	{
		write(comm, "SIZE " + fileName);
		if (!read(comm)) return(-1);
		fileSize = Long.parseLong(lastReply.substring(1 + lastReply.indexOf(" "), lastReply.lastIndexOf(CARRIAGE_RETURN)));
		return(0);
	}

	public int noop()
	{
		write(comm, "NOOP");
		if (!read(comm)) return(-1);
		return(0);
	}

	public int pwd()
	{
		write(comm, "PWD");
		if (!read(comm)) return(-1);
		int start = 1 + lastReply.indexOf("\"");
		int end = lastReply.indexOf("\"", start);
		workingDirectory = lastReply.substring(start, end);
		return(0);
	}

	public int delete(String fileName)
	{
		write(comm, "DELE " + fileName);
		if (!read(comm)) return(-1);
		return(0);
	}

	public int restart(long restartByte)
	{
		write(comm, "REST " + restartByte);
		if (!read(comm)) return(-1);
		return(0);
	}

	public int makedir(String directory)
	{
		write(comm, "MKD " + directory);
		if (!read(comm)) return(-1);
		return(0);
	}

	public int removedir(String directory)
	{
		write(comm, "RMD " + directory);
		if (!read(comm)) return(-1);
		return(0);
	}

	public int parentdir(String directory)
	{
		write(comm, "CDUP");
		if (!read(comm)) return(-1);
		return(0);
	}

	public int rename(String oldFileName, String newFileName)
	{
		write(comm, "RNFR " + oldFileName);
		if (!read(comm)) return(-1);
		if (lastCode != CODE_RNFR_OK) return(-1);
		write(comm, "RNTO " + newFileName);
		if (!read(comm)) return(-1);
		return(0);
	}

	public String lastRequest()
	{
		return(lastRequest);
	}

	public String lastReply()
	{
		return(lastReply);
	}

	public int lastCode()
	{
		return(lastCode);
	}

	public int pasv()
	{
		write(comm, "PASV");
		if (!read(comm)) return(-1);
		int c1 = lastReply.indexOf('(');
		int c2 = lastReply.indexOf(')', c1);
		String pasv = lastReply.substring(1 + c1, c2);
		String[] ch = chaineSplit(pasv, ",", false);
		passive_host = ch[0] + "." + ch[1] + "." + ch[2] + "." + ch[3];
		passive_port = Integer.parseInt(ch[4]) * 256 + Integer.parseInt(ch[5]);
		return(0);
	}

	public int ascii()
	{
		write(comm, "TYPE A");
		_mode = MODE_ASCII;
		if (!read(comm)) return(-1);
		return(0);
	}

	public int binary()
	{
		write(comm, "TYPE I");
		_mode = MODE_BINARY;
		if (!read(comm)) return(-1);
		return(0);
	}

	public int jes() {
		write (comm, "SITE FILEType=JES");
		_mode = MODE_JES;
		if (!read(comm)) return(-1);
		return(0);
	}

	public int list()
	{
		return(list(null));
	}

	public int list(TransferMonitor transferMonitor)
	{
		if (transfer == TRANSFER_PASV)
		{
			pasv();
			write(comm, "LIST");
			ThreadListPasv tl = new ThreadListPasv(passive_host, passive_port);
			tl.start();
			if (!read(comm)) return(-1);

			if (transferMonitor != null)
			{
				transferMonitor.setTransferThread(tl);
				transferMonitor.start();
			}

			try
			{
				tl.join();
				if (transferMonitor != null) transferMonitor.join();
			}
			catch (Exception e){}
			if (!read(comm)) return(-1);
			stringDirectoryList = tl.getList();
		}
		else if (transfer == TRANSFER_PORT)
		{
			String localIp = "";
			try
			{
				localIp = java.net.InetAddress.getLocalHost().getHostAddress().replace('.', ',');
			}
			catch(Exception e){}

			ThreadListPort tl = new ThreadListPort();

			int dataPort = tl.getLocalPort();
			int low = dataPort % 256;
			int high = dataPort / 256;

			write(comm, "PORT " + localIp + "," + high + "," + low);
			if (!read(comm)) return(-1);

			tl.start();
			write(comm, "LIST");
			if (!read(comm)) return(-1);

			if (transferMonitor != null)
			{
				transferMonitor.setTransferThread(tl);
				transferMonitor.start();
			}

			try
			{
				tl.join();
				if (transferMonitor != null) transferMonitor.join();
			}
			catch (Exception e){}
			if (!read(comm)) return(-1);
			stringDirectoryList = tl.getList();
		}

		// Fill the directoryList
		directoryList = RemoteFile.createFromList(stringDirectoryList);

		// OK
		return(0);
	}

	public int raw(String command)
	{
		write(comm, command);
		if (!read(comm)) return(-1);
		return(0);
	}

	public int download(String remote, String local)
	{
		return(download(null, remote, local, 0));
	}

	public int download(TransferMonitor transferMonitor, String remote, String local)
	{
		return(download(transferMonitor, remote, local, 0));
	}

	public int download(String remote, String local, long restartByte)
	{
		return(download(null, remote, local, 0));
	}

	public int download(TransferMonitor transferMonitor, String remote, String local, long restartByte)
	{
		switch (mode)
		{
			case MODE_AUTO:
				if (hasAsciiExtension(remote))
				{
					if (_mode != MODE_ASCII) ascii();
				}
				else
				{
					if (_mode != MODE_BINARY) binary();
				}
				break;
			case MODE_BINARY:
				if (_mode != MODE_BINARY) binary();
				break;
			case MODE_ASCII:
				if (_mode != MODE_ASCII) ascii();
				break;
		}

		if (transfer == TRANSFER_PASV)
		{
			pasv();
			if (restartByte > 0)
			{
				restart(restartByte);
			}
			write(comm, "RETR " + remote);
			ThreadDownloadPasv td = new ThreadDownloadPasv(passive_host, passive_port, local, restartByte);
			td.start();
			if (!read(comm)) return(-1);

			if (lastCode != CODE_DL_FILE_OK)
			{
				td.interrupt();
				return(-1);
			}

			if (transferMonitor != null)
			{
				transferMonitor.setTransferThread(td);
				transferMonitor.start();
			}

			try
			{
				td.join();
				if (transferMonitor != null) transferMonitor.join();
			}
			catch (Exception e){}
			if (!read(comm)) return(-1);
		}
		else if (transfer == TRANSFER_PORT)
		{
			String localIp = "";
			try
			{
				localIp = java.net.InetAddress.getLocalHost().getHostAddress().replace('.', ',');
			}
			catch(Exception e){}

			ThreadDownloadPort td = new ThreadDownloadPort(local, restartByte);

			int dataPort = td.getLocalPort();
			int low = dataPort % 256;
			int high = dataPort / 256;

			write(comm, "PORT " + localIp + "," + high + "," + low);
			if (!read(comm)) return(-1);

			if (restartByte > 0)
			{
				restart(restartByte);
			}

			td.start();
			write(comm, "RETR " + remote);
			if (!read(comm)) return(-1);

			if (lastCode != CODE_DL_FILE_OK)
			{
				td.interrupt();
				td.closeServerSocket();
				return(-1);
			}

			if (transferMonitor != null)
			{
				transferMonitor.setTransferThread(td);
				transferMonitor.start();
			}

			try
			{
				td.join();
				if (transferMonitor != null) transferMonitor.join();
			}
			catch (Exception e){}
			td.closeServerSocket();
			if (!read(comm)) return(-1);
		}
		return(0);
	}

	public int download(TransferMonitor transferMonitor, RemoteFile remoteFile, String local, long restartByte)
	{
		switch (mode)
		{
			case MODE_AUTO:
				if (hasAsciiExtension(remoteFile.name))
				{
					if (_mode != MODE_ASCII) ascii();
				}
				else
				{
					if (_mode != MODE_BINARY) binary();
				}
				break;
			case MODE_BINARY:
				if (_mode != MODE_BINARY) binary();
				break;
			case MODE_ASCII:
				if (_mode != MODE_ASCII) ascii();
				break;
		}

		if (transfer == TRANSFER_PASV)
		{
			pasv();
			if (restartByte > 0)
			{
				restart(restartByte);
			}
			write(comm, "RETR " + remoteFile.name);
			ThreadDownloadPasv td = new ThreadDownloadPasv(passive_host, passive_port, local, restartByte);
			td.setFinalSize(remoteFile.size);
			td.start();
			if (!read(comm)) return(-1);

			if (lastCode != CODE_DL_FILE_OK)
			{
				td.interrupt();
				return(-1);
			}

			if (transferMonitor != null)
			{
				transferMonitor.setTransferThread(td);
				transferMonitor.start();
			}

			try
			{
				td.join();
				if (transferMonitor != null) transferMonitor.join();
			}
			catch (Exception e){}
			if (!read(comm)) return(-1);
		}
		else if (transfer == TRANSFER_PORT)
		{
			String localIp = "";
			try
			{
				localIp = java.net.InetAddress.getLocalHost().getHostAddress().replace('.', ',');
			}
			catch(Exception e){}

			ThreadDownloadPort td = new ThreadDownloadPort(local, restartByte);

			int dataPort = td.getLocalPort();
			int low = dataPort % 256;
			int high = dataPort / 256;

			write(comm, "PORT " + localIp + "," + high + "," + low);
			if (!read(comm)) return(-1);

			if (restartByte > 0)
			{
				restart(restartByte);
			}

			td.setFinalSize(remoteFile.size);
			td.start();
			write(comm, "RETR " + remoteFile.name);
			if (!read(comm)) return(-1);

			if (lastCode != CODE_DL_FILE_OK)
			{
				td.interrupt();
				td.closeServerSocket();
				return(-1);
			}

			if (transferMonitor != null)
			{
				transferMonitor.setTransferThread(td);
				transferMonitor.start();
			}

			try
			{
				td.join();
				if (transferMonitor != null) transferMonitor.join();
			}
			catch (Exception e){}
			td.closeServerSocket();
			if (!read(comm)) return(-1);
		}
		return(0);
	}

	public int upload(String local, String remote)
	{
		return(upload(null, local, remote, 0));
	}

	public int upload(TransferMonitor transferMonitor, String local, String remote)
	{
		return(upload(transferMonitor, local, remote, 0));
	}

	public int upload(String local, String remote, long restartByte)
	{
		return(upload(null, local, remote, restartByte));
	}

	public int upload(TransferMonitor transferMonitor, String local, String remote, long restartByte)
	{
		switch (mode)
		{
			case MODE_AUTO:
				if (hasAsciiExtension(local))
				{
					if (_mode != MODE_ASCII) ascii();
				}
				else
				{
					if (_mode != MODE_BINARY) binary();
				}
				break;
			case MODE_BINARY:
				if (_mode != MODE_BINARY) binary();
				break;
			case MODE_ASCII:
				if (_mode != MODE_ASCII) ascii();
				break;
			case MODE_JES:
				if (_mode != MODE_JES) jes();
				break;
		}

		if (transfer == TRANSFER_PASV)
		{
			pasv();
			if (restartByte > 0)
			{
				restart(restartByte);
			}
			write(comm, "STOR " + remote);
			ThreadUploadPasv tu = new ThreadUploadPasv(passive_host, passive_port, local, restartByte);
			tu.start();
			if (!read(comm)) return(-1);

			if (lastCode != CODE_UL_FILE_OK)
			{
				tu.interrupt();
				return(-1);
			}

			if (transferMonitor != null)
			{
				transferMonitor.setTransferThread(tu);
				transferMonitor.start();
			}

			try
			{
				tu.join();
				if (transferMonitor != null) transferMonitor.join();
			}
			catch (Exception e){}
			if (!read(comm)) return(-1);
		}
		else if (transfer == TRANSFER_PORT)
		{
			String localIp = "";
			try
			{
				localIp = java.net.InetAddress.getLocalHost().getHostAddress().replace('.', ',');
			}
			catch(Exception e){}

			ThreadUploadPort tu = new ThreadUploadPort(local, restartByte);

			int dataPort = tu.getLocalPort();
			int low = dataPort % 256;
			int high = dataPort / 256;

			write(comm, "PORT " + localIp + "," + high + "," + low);
			if (!read(comm)) return(-1);

			if (restartByte > 0)
			{
				restart(restartByte);
			}

			tu.start();
			//if (mode != MODE_JES) {
				write(comm, "STOR " + remote);
				if (!read(comm)) return(-1);
				logger.debug(lastReply);
			//}
/*
			if (lastCode == CODE_JES_UL_ACCEPTED) {
				if (!read(comm)) return(-1);
				this.logger.debug(lastReply);
			}
*/

			if ( lastCode != CODE_UL_FILE_OK && ( lastCode != CODE_JES_UL_ACCEPTED  ) )
			{
				tu.interrupt();
				tu.closeServerSocket();
				return(-1);
			}

			if (transferMonitor != null)
			{
				transferMonitor.setTransferThread(tu);
				transferMonitor.start();
			}

			try
			{
				tu.join();
				if (transferMonitor != null) transferMonitor.join();
			}
			catch (Exception e){}

			tu.closeServerSocket();
			if (!read(comm)) return(-1);

		}
		return(0);
	}

	public boolean read(TCPConnection comm)
	{
		boolean cont = true;
		String ret = "";
		int cptBlankLines = 0;
		while(cont)
		{
			String line = null;
			try
			{
				line = comm.read();
			}
			catch (java.io.IOException ioe)
			{
				lastReply = ioe.toString() + CARRIAGE_RETURN;
				lastCode = -1;
				comm.disconnect();
				connected = false;
				return(false);
			}
			if (line == null)
			{
				lastReply = "Disconnected by server" + CARRIAGE_RETURN;
				lastCode = -1;
				comm.disconnect();
				connected = false;
				return(false);
			}
			ret += line + CARRIAGE_RETURN;
			if (line.length() < 4) cptBlankLines++;
			else if (line.charAt(3) == ' ') cont = false;
		}

		// Get the response and the response code
		lastReply = ret;
		int pos = 0;
		for (int i = 0; i < cptBlankLines; i++)
		{
			pos = CARRIAGE_RETURN.length() + lastReply.indexOf(CARRIAGE_RETURN, pos);
		}
		lastCode = Integer.parseInt(lastReply.substring(pos, 3 + pos));

		// OK
		return(true);
	}

	public void write(TCPConnection comm, String chaine)
	{
		String toSend = chaine + CARRIAGE_RETURN;
		comm.write(toSend.getBytes());
		lastRequest = toSend;

		// log (not the password)
		if (toSend.indexOf("PASS ") == 0) toSend = "PASS ***" + CARRIAGE_RETURN;
	}

	public static String[] chaineSplit(String chaine, String delims, boolean trimStrings)
	{
		if (chaine == null) return(null);
		if (chaine.length() == 0) return(new String[0]);

		Vector res = new Vector();
		StringTokenizer stk = new StringTokenizer(chaine, delims, false);
		while (stk.hasMoreTokens()) res.addElement(stk.nextToken());
		String[] res2 = new String[res.size()];
		for (int i = 0; i < res.size(); i++)
		{
			res2[i] = (String)res.elementAt(i);
			if (trimStrings) res2[i] = res2[i].trim();
		}
		return(res2);
	}

	public static boolean hasAsciiExtension(String name)
	{
		if (name == null) return(false);
		for (int i = 0; i < ASCII_EXT.length; i++)
		{
			if (name.length() >= ASCII_EXT[i].length())
			{
			String extension = name.substring(name.length() - ASCII_EXT[i].length(), name.length());
			if (ASCII_EXT[i].equalsIgnoreCase(extension)) return(true);
		}
		}
		return(false);
	}

	public RemoteFile[] getDirectoryList()
	{
		return(directoryList);
	}

	public String getWorkingDirectory()
	{
		return(workingDirectory);
	}

	public long getFileSize()
	{
		return(fileSize);
	}

	public boolean isConnected()
	{
		return(connected);
	}
}