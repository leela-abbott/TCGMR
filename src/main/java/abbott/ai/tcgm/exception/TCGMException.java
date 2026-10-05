package abbott.ai.tcgm.exception;

public class TCGMException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String throwingClass;
    private final String throwingMethod;
    private final String parameterList;
    private final String errorMessage;

    /**
     * Copy Constructor
     */
    public TCGMException(TCGMException te) {
        super(te.getErrorMessage(), te);
        this.throwingClass = te.getThrowingClass();
        this.throwingMethod = te.getThrowingMethod();
        this.parameterList = te.getParameterList();
        this.errorMessage = te.getErrorMessage();
    }

    /**
     * Constructor with message and location details
     */
    public TCGMException(String pThrowingClass, String pThrowingMethod, String pParameterList, String pErrorMessage) {
        super(pErrorMessage);
        this.throwingClass = pThrowingClass;
        this.throwingMethod = pThrowingMethod;
        this.parameterList = pParameterList;
        this.errorMessage = pErrorMessage;
    }

    /**
     * Constructor without parameter list
     */
    public TCGMException(String pThrowingClass, String pThrowingMethod, String pErrorMessage) {
        this(pThrowingClass, pThrowingMethod, null, pErrorMessage);
    }

    /**
     * Constructor that accepts a root cause (Highly Recommended for Java Exceptions)
     */
    public TCGMException(String pThrowingClass, String pThrowingMethod, String pParameterList, String pErrorMessage, Throwable cause) {
        super(pErrorMessage, cause);
        this.throwingClass = pThrowingClass;
        this.throwingMethod = pThrowingMethod;
        this.parameterList = pParameterList;
        this.errorMessage = pErrorMessage;
    }

    // Getters
    public String getThrowingClass() { return this.throwingClass; }
    public String getThrowingMethod() { return this.throwingMethod; }
    public String getParameterList() { return this.parameterList; }
    public String getErrorMessage() { return this.errorMessage; }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Throwing Class: ").append(this.getThrowingClass());
        buf.append("\nThrowing Method: ").append(this.getThrowingMethod());
        buf.append("\nParameter List: ").append(this.getParameterList());
        buf.append("\nError Message: ").append(this.getErrorMessage());
        return buf.toString();
    }
}
