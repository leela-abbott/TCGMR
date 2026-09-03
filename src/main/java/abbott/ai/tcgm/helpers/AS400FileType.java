//Source file: C:\\DATA\\jbproject\\TCGM\\src\\abbott\\ai\\tcgm\\helpers\\AS400FileType.java

package abbott.ai.tcgm.helpers;


public class AS400FileType 
{
   private final String name;
   public static final AS400FileType Bpcs = new AS400FileType ("Bpcs");
   public static final AS400FileType NChiBpcs = new AS400FileType ("NChiBpcs");
   public static final AS400FileType AffReportSm = new AS400FileType ("AffReportSm");
   public static final AS400FileType AffReportLg = new AS400FileType ("AffReportLg");
   
   /**
   @param name
   @roseuid 3D77C4D70088
    */
   private AS400FileType(String name) 
   {this.name = name;    
   }
   
   /**
   @return java.lang.String
   @roseuid 3D77C4D70291
    */
   public String toString() 
   {return name;    
   }
}
