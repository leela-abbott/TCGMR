/*
 * Created on Feb 4, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm;

import java.util.Comparator;

/**
 * @author pesalvk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
class ListComperator implements Comparator
{
	public int intStart= 0;
	
	public ListComperator(int startNumber){
		intStart = startNumber;
	}
	public int compare(Object list1, Object list2) 
	{
		String str1 = (String)list1;
		String str2 = (String)list2;

		return str1.substring(intStart,str1.length()).compareToIgnoreCase(str2.substring(intStart,str2.length()));
	}
}
