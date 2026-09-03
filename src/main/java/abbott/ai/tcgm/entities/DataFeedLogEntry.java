package abbott.ai.tcgm.entities;
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;

public class DataFeedLogEntry  {
  File file;
  Timestamp startTime;
  Timestamp endTime;
  String    startTimeInStringFormat;
  String    endTimeInStringFormat;
  
  String completionMsg;
  boolean selected;

  public DataFeedLogEntry() {
  }

  public File getFile() {
    return file;
  }

  public void setFileByPath(String filePath) {
    file = new File(filePath);
  }

  public void setFile(File newDfl_file) {
    file = newDfl_file;
  }

  public String getFullFileName() {
    if (file != null)
      return file.getAbsolutePath();
    else
      return "Unspecified";
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp newStartTime) {
    startTime = newStartTime;
  }

  public String getStartTimeInStringFormat() {
	return startTimeInStringFormat;
  }

  public void setStartTimeInStringFormat(String newStartTimeInStringFormat) {
	startTimeInStringFormat = newStartTimeInStringFormat;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp newEndTime) {
    endTime = newEndTime;
  }

  public String getEndTimeInStringFormat() {
	return endTimeInStringFormat;
  }

  public void setEndTimeInStringFormat(String newEndTimeInStringFormat) {
	endTimeInStringFormat = newEndTimeInStringFormat;
  }

  public String getCompletionMsg() {
    return completionMsg;
  }

  public void setCompletionMsg(String newCompletionMsg) {
    completionMsg = newCompletionMsg;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean newSelected) {
    selected = newSelected;
  }
}