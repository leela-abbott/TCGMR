package abbott.ai.tcgm.helpers;

import java.util.Vector;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.action.form.*;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */

public class ModelMngr implements TCGMMngr
{
	/**
	 * Default Constructor
	 */
	public ModelMngr()
	{
	}


	/**
	 *
	 * @param userToken contains id and password
	 * @param model Model object
	 * @return Vector
	 * @throws TCGMException
	 */
	public Vector getModels(UserToken userToken, TCGMModel model) throws TCGMException
	{
		return DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(userToken, model.getType() ).getVO(model);
	}
	/**
	 *
	 * @param userToken contains id and password
	 * @param model ModelObject
	 * @throws TCGMException
	 */
	public void createModel(UserToken userToken, TCGMModel model) throws TCGMException
	{
		ModelDao mdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(userToken, model.getType() );
		mdao.createModel(model);
	}

	public void createAnalysisModel(UserToken ut, AnalysisModel model) throws TCGMException {
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.ANALYSIS );

		int modelId = md.createModel(model);
	}

	/**
	 *
	 * @param userToken contains id and password
	 * @param model Model Object
	 * @param baseModel Model Object
	 * @param options ModelCopyOptions object
	 * @throws TCGMException
	 */
	public void createModel(UserToken userToken, TCGMModel model, TCGMModel baseModel, ModelCopyOptions options) throws TCGMException
	{
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(userToken, model.getType() );
		md.createModel(model, baseModel, options);
	}

	/**
	 *
	 * @param userToken contains id and password
	 * @param modelName name of the model
	 * @throws TCGMException
	 */
/* 10-8-03 Deleting a factor model is added as a batch job, this model mngr method and the
			 corresponding DAO method can be removed.
	public void deleteModel(UserToken userToken, int modelId, TCGMModel.Type mtype) throws TCGMException
	{
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(userToken, mtype);
		md.deleteModel(modelId, mtype);
	}
*/

/* 10-8-03 Closing a factor model is added as a batch job, this model mngr method and the
			 corresponding DAO method can be removed.
	public void closeModel(UserToken userToken, int modelId, TCGMModel.Type mtype) throws TCGMException
	{
		DaoFactory df = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		ModelDao md = df.getModelDao(userToken, mtype);
		md.closeModel(modelId);
	}
*/

/* 10-8-03 Compacting a factor model is added as a batch job, this model mngr method and the
			 corresponding DAO method can be removed.
	public void compactModel(UserToken userToken, int modelId, TCGMModel.Type mtype) throws TCGMException
	{
		DaoFactory df = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		ModelDao md = df.getModelDao(userToken, mtype);
		md.compactModel(modelId);
	}
*/
	/**
	 *
	 * @param userToken contains id and password
	 * @param modelId id of the model
	 * @return model name
	 * @throws TCGMException
	 */
	public String getModelName(UserToken userToken, int modelId) throws TCGMException
	{
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(userToken, TCGMModel.Type.FACTOR);
		TCGMModel m = md.getModel(modelId);
		return m.getName();
	}

	public TCGMModel getModelFromId(UserToken userToken, int modelId, TCGMModel.Type mtype) throws TCGMException
	{
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(userToken, mtype);
		TCGMModel m = md.getModel(modelId);
		return m;
	}

	public TCGMModel getClosedModelFromId(UserToken userToken, int modelId, TCGMModel.Type mtype) throws TCGMException
	{
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(userToken, mtype);
		TCGMModel m = md.getClosedModel(modelId);
		return m;
	}
	
	//	A.Winter 6/27/05
	 public String getModelParmValue(UserToken userToken, int modelId, TCGMModel.Type mtype, String parmName) throws TCGMException
	 {
		 ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(userToken, mtype);
		 String parValue = md.getModelParm(modelId,parmName);
		 return parValue;
	 }
	//	A.Winter 6/27/05
	public void updateModel(UserToken ut, TCGMModel model) throws TCGMException {
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, model.getType());
		md.updateModel(model);
	}
	//	Udaya B Aravapalli 04/21/2006
	public void updateModelStatus(UserToken ut, TCGMModel model) throws TCGMException {
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, model.getType());
		md.updateModelStatus(model);
	}
	public void updateModelParms(UserToken ut, TCGMModel model) throws TCGMException {
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, model.getType());
		md.updateModelParms(model);
	}

	public void setModelParmII(UserToken ut, TCGMModel model, String parmName, String parmValue) throws TCGMException {
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, model.getType());
		md.setModelParmII(model.getModelIdInt(), parmName, parmValue);
	}	
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param model TCGMModel
	 * @return int
	 * @throws TCGMException
	 */
	public int getModelIdByName(UserToken userToken,TCGMModel model) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		ModelDao modelDao = daoFactory.getModelDao(userToken,model.getType());

		return modelDao.getModelIdByName(model.getName());
	}

	public String getModelStatus(UserToken userToken,String modelId) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		//ModelDao modelDao = daoFactory.getModelDao(userToken, model.getType());
		ModelDao modelDao = daoFactory.getModelDao(userToken);
		return modelDao.getModelStatus(modelId);
	}

	public int getLastModelCreatedByUser(UserToken userToken,TCGMModel.Type modelType) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		ModelDao modelDao = daoFactory.getModelDao(userToken, modelType);
		return modelDao.getLastModelCreatedByUser(modelType);
	}

	/*****************************************************************************************/
}