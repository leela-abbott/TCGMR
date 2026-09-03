package abbott.ai.tcgm.helpers;

public class TCGMStringComposer {

    public TCGMStringComposer() {
    }

    protected static StringBuffer parmReplace(StringBuffer sb, String token, String value) {
        int loc = 0;
        while ( (loc = sb.toString().indexOf(token) ) > 0 ) {
            sb.replace( loc, loc + token.length(), value );
        }
        return sb;
   }
}