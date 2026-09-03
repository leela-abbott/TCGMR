package abbott.ai.tcgm.entities;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class TCGMModel extends TCGMEntity implements java.io.Serializable
{
	// Typesafe enum pattern to represent possible constants

	// Factor models are used to hold collection of attributes
	// Analysis models hold collections of analytical data tied to 1 or more factor models
	public static class Type
	{
		private final String name;

		/**
		 *
		 * @param name Type name
		 */
		Type(String name)
		{
			this.name = name;
		}

		/**
		 *
		 * @return Type name
		 */
		public String toString()
		{
			return this.name;
		}

		public static final Type FACTOR = new Type("FACTOR");
		public static final Type ANALYSIS = new Type("ANALYSIS");
		public static final Type PERPETUAL = new Type("PERPETUAL");
		public static final Type COSTEXCH = new Type("COSTEXCH");
	}

	/**
	 * Models can be open or closed.
	 */
	public static class Status
	{
		private final String name;

		/**
		 *
		 * @param name Status name
		 */
		Status(String name)
		{
			this.name = name;
		}

		/**
		 *
		 * @return Status name
		 */
		public String toString()
		{
			return this.name;
		}

		public static final Status OPEN = new Status("OPEN");
		public static final Status CLOSED = new Status("CLOSED");
		public static final Status OPENCLOSED = new Status("OPEN,CLOSED");
		public static final Status DELETED = new Status("DELETED");
	}
	/*****************************************************************************************/
	private String name = "";
	private String desc = "";
	private Type type = null;
	private Status status = null;
	private boolean isCalculated;
	private String memo = "";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public TCGMModel()
	{
		// light base model object
		// defaults to an open factor model
	}

	/**
	 *
	 * @param modelName String
	 * @param type Type
	 */
	public TCGMModel(String modelName, Type type)
	{
		this.setName(modelName);
		this.setType(type);
	}

	/**
	 *
	 * @param name model name
	 * @param desc model description
	 * @param type model Type
	 * @return TCGMModel
	 */
	public static final TCGMModel getNewModel(String name, String desc, Type type)
	{
		TCGMModel model;
		if (type == Type.FACTOR)
		{
			model = new FactorModel();
		}
		else if (type == Type.ANALYSIS)
		{
			model = new AnalysisModel();
		}
		else
		{
			model = new PerpetualModel();
		}

		model.name = name;
		model.desc = desc;
		return model;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return model name
	 */
	public String getName()
	{
		if(this.name == null)
		{
			this.name = "";
		}
		return this.name.trim();
	}
	/**
	 *
	 * @param name model name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param desc model description
	 */
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	/**
	 *
	 * @return model description
	 */
	public String getDesc()
	{
		if(this.desc == null)
		{
			this.desc = "";
		}
		return desc.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param type model Type
	 */
	public void setType(Type type)
	{
		this.type = type;
	}
	/**
	 *
	 * @return model Type
	 */
	public Type getType()
	{
		return this.type;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param s model Status
	 */
	public void setStatus(Status s)
	{
		this.status = s;
	}
	/**
	 *
	 * @return model Status
	 */
	public Status getStatus()
	{
		return this.status;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return TCGMModel
	 */
	public TCGMModel getTCGMModel()
	{
		TCGMModel m = new TCGMModel();
		m.setName(name);
		m.setDesc(desc);
		m.setType(type);
		return m;
	}

	/**
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("\nName: ");
		sb.append(this.getName());
		sb.append("\nDesc: ");
		sb.append(this.getDesc());
		sb.append("\nCalculated: ");
		sb.append(this.getIsCalculated());
		sb.append("\nStatus: ");
		sb.append( this.getStatus()==null ? "null" : this.getStatus().toString());
		sb.append("\nType: ");
		sb.append( this.getType()==null ? "null" : this.getType().toString());

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param isCalculated boolean
	 */
	public void setIsCalculated(boolean isCalculated)
	{
		this.isCalculated = isCalculated;
	}
	/**
	 *
	 * @return boolean
	 */
	public boolean isIsCalculated()
	{
		return this.isCalculated;
	}
	/**
	 *
	 * @return boolean
	 */
	public boolean getIsCalculated()
	{
		return this.isCalculated;
	}
	/**
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param string
	 */
	public void setMemo(String string) {
		memo = string;
	}

}
