package abbott.ai.tcgm.entities;
//import abbott.ai.tcgm.data.*;
import java.io.Serializable;
/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class NotesTran extends TCGMTranEntity implements Serializable
{
	private Notes notes = new Notes();
	private String notesTranId = "";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public NotesTran()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return string representation of the class properties and their values.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Notes Tran Id:");
		sb.append(this.getNotesTranId());
		sb.append("\n");
		sb.append(this.getNotes().toString());
		sb.append("\n");
		sb.append(super.toString());

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param notes Notes object
	 */
	public void setNotes(Notes notes)
	{
		this.notes = notes;
	}
	/**
	 *
	 * @return notes
	 */
	public Notes getNotes()
	{
		if(this.notes == null)
		{
			this.notes = new Notes();
		}
		return this.notes;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Notes Tran Id
	 */
	public String getNotesTranId()
	{
		if(this.notesTranId == null)
		{
			this.notesTranId = "";
		}
		return this.notesTranId.trim();
	}
	/**
	 *
	 * @param notesTranId Notes Tran Id
	 */
	public void setNotesTranId(String notesTranId)
	{
		this.notesTranId = notesTranId;
	}
}