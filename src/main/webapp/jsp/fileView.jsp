<%@ page import="java.io.*,abbott.ai.tcgm.AppConst"%>
<html>
<title>TCGM</title>
<script language="JavaScript">
window.unload=window.parent.close();

</script>
<body >
<%
String filePath = "";
filePath = AppConst.getSharelocation();
System.out.println("File Path"+filePath);
String dir=request.getParameter("dir");
String filename=request.getParameter("fileName");
String file=filePath+dir+"\\"+filename;
try {

File f = new File(file);
FileInputStream istr = new FileInputStream(f);

BufferedInputStream bstr = new BufferedInputStream( istr ); // promote

int size = (int) f.length(); // get the file size (in bytes)
byte[] data = new byte[size]; // allocate byte array of right size
bstr.read( data, 0, size ); // read into byte array

bstr.close();
if(filename.indexOf(".pdf")>-1){
response.setContentType("application/pdf");
}
else if(filename.indexOf(".doc")>-1){
response.setContentType("application/msword");
}
else{
response.setContentType("text/html");
}
response.setHeader("Content-Disposition","attachment; filename="+filename);
OutputStream OutStrm = response.getOutputStream();

OutStrm.write(data);
OutStrm.flush();
OutStrm.close();
}catch(Exception e ){
out.println("The Requested file Cannot be Opened."+e.toString());
//e.printStackTrace();

} 



%>
</body>
</html>


