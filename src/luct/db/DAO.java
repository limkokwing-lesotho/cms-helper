package luct.db;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DAO<T>{

	protected static Logger logger = LogManager.getLogger(DAO.class);
	private enum SaveType {CREATE, UPDATE, SAVE_OR_UPDATE}
	private Class<T> type;
	protected int pageSize = 50;
	private PersistenceCallback<T> pCallback;

	public interface PersistenceCallback<T>{
		public void onSave(Session session, T object);
		public void onUpdate(Session session, T object);
		public void onSaveOrUpdate(Session session, T object);
	}

	public DAO(Class<T> type){
		this.type = type;
	}

	public void setPersistenceCallback(PersistenceCallback<T> pCallback) {
		this.pCallback = pCallback;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Class<T> getType() {
		return type;
	}

	public void saveWithoutAudit(T obj) {
		save(obj, SaveType.CREATE);
	}

	public void updateWithoutAudit(T obj) {
		save(obj, SaveType.UPDATE);
	}

	public void saveOrUpdateWithoutAudit(T obj) {
		save(obj, SaveType.SAVE_OR_UPDATE);
	}

	public void save(T obj) {
		save(obj, SaveType.CREATE);
	}

	public void update(T obj) {
		save(obj, SaveType.UPDATE);
	}

	public void saveOrUpdate(T obj) {
		save(obj, SaveType.SAVE_OR_UPDATE);
	}


	private void save(T obj, SaveType saveType) {
		logger.debug("Saving: "+ obj +", SaveType: "+ saveType);
		Transaction tx = null;
		Session session = HibernateHelper.getSession();
		try {
			tx = session.beginTransaction();
			switch(saveType) {
			case CREATE:
				session.save(obj);
				if(pCallback != null) {
					pCallback.onSave(session, obj);
				}
				break;
			case UPDATE:
				session.update(obj);
				if(pCallback != null) {
					pCallback.onUpdate(session, obj);
				}
				break;
			case SAVE_OR_UPDATE:
				session.saveOrUpdate(obj);
				if(pCallback != null) {
					pCallback.onSaveOrUpdate(session, obj);
				}
				break;
			}
			tx.commit();
			logger.debug("Saving '"+ obj +"' Successful, SaveType: "+ saveType);
		}
		catch(HibernateException ex){
			try {
				if(tx != null){
					tx.rollback();
				}
			} catch (Exception e) {
				logger.error("Unable to rollback transaction", e);
			}
			logger.error("Error saving "+ type, ex);
		}
		catch (Exception ex) {
			logger.error("Error saving "+ type, ex);
		}
		finally{
			session.close();
		}
	}

	public T load(Serializable id) {
		logger.debug("load "+ type +" object with id "+ id);
		Session session = HibernateHelper.getSession();
		T obj = null;
		try{
			obj = session.load(type, id);
			logger.debug("load "+ type +" object with id "+ id +" successful: "+ obj);
		}
		catch (Exception ex) {
			logger.error("Unable to get "+ type, ex);
		}
		finally{
			session.close();
		}

		return obj;
	}

	public T get(Serializable id) {
		logger.debug("get "+ type +" object with id "+ id);
		Session session = HibernateHelper.getSession();
		T obj = null;
		try{
			obj = session.get(type, id);
			logger.debug("get "+ type +" object with id "+ id +" successful: "+ obj);
		}
		catch (Exception ex) {
			logger.error("Unable to get "+ type, ex);
		}
		finally{
			session.close();
		}

		return obj;
	}

	public static <T> T get(Serializable id, Class<T> type) {
		logger.debug("load "+ type +" object with id "+ id);
		Session session = HibernateHelper.getSession();
		T obj = null;
		try{
			obj = session.get(type, id);
			logger.debug("load "+ type +" object with id "+ id +" successful: "+ obj);
		}
		catch (Exception ex) {
			logger.error("Unable to get "+ type, ex);
		}
		finally{
			session.close();
		}

		return obj;
	}

	public List<T> all(int pageNumber) {
		logger.debug("getting all objects of "+ type +" at page "+ pageNumber);
		Session session = HibernateHelper.getSession();
		List<T> list = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("from ").append(type.getName());
			Query<T> query = session.createQuery(hql.toString(), type);
			query.setFirstResult(pageSize * pageNumber);
			query.setMaxResults(pageSize);
			list = query.list();
			logger.debug("Successfully retrieved "+list.size()+" of all objects of "+ type +" at page "+ pageNumber);
		}
		catch(Exception ex){
			logger.error("Unable to get "+ type, ex);
		}
		finally{
			session.close();
		}
		return list;
	}

	public List<T> all() {
		logger.debug("getting all objects of "+ type);
		Session session = HibernateHelper.getSession();
		List<T> list = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("from ").append(type.getName());
			Query<T> query = session.createQuery(hql.toString(), type);
			list = query.list();
			logger.debug("Successfully retrieved "+list.size()+" of all objects of "+ type);
		}
		catch(Exception ex){
			logger.error("Unable to get "+ type, ex);
		}
		finally{
			session.close();
		}
		return list;
	}

	public Long count() {
		logger.debug("count all objects of "+ type);
		Session session = HibernateHelper.getSession();
		Long size = null;
		try {
			Query<Long> query = session.createQuery("select count(*) from "+type.getName(), 
					Long.class);
			size = query.getSingleResult();
			logger.debug("count all objects of "+ type +" successful, result: "+ size);
		}
		catch(Exception ex){
			logger.error("Unable to count "+ type, ex);
		}
		finally{
			session.close();
		}
		return size;
	}

	public static synchronized Long count(Class<?> type) {
		logger.debug("count all objects of "+ type);
		Session session = HibernateHelper.getSession();
		Long size = null;
		try {
			Query<Long> query = session.createQuery("select count(*) from "+type.getName(), 
					Long.class);
			size = query.getSingleResult();
			logger.debug("count all objects of "+ type +" successful, result: "+ size);
		}
		catch(Exception ex){
			logger.error("Unable to count "+ type, ex);
		}
		finally{
			session.close();
		}
		return size;
	}

	public Integer countTotalPages() {
		logger.debug("count total pages of "+ type);
		Session session = HibernateHelper.getSession();
		Integer pages = null;
		try {
			Query<Long> query = session.createQuery("select count(*) from "+type.getName(), 
					Long.class);
			double size = query.getSingleResult();
			if(size > pageSize) {
				pages = (int) Math.round((size / pageSize));
			} 
			else {
				pages = 1;
			}
			logger.debug("count total pages of "+ type +" successful, result: "+ size);
		}
		catch(Exception ex){
			logger.error("Error counting pages of "+ type, ex);
		}
		finally{
			session.close();
		}
		return pages;
	}

	public void delete(Object obj) {
		logger.debug("Delete "+ obj);
		Session session = HibernateHelper.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
			logger.debug("Delete "+ obj+" successful");
		}
		catch(HibernateException ex){
			try {
				if(tx != null){
					tx.rollback();
				}
			} catch (Exception e) {
				logger.error("Unable to rollback transaction: ", e);
			}
			logger.error("Unable to delete "+ type, ex);
		}
		catch (Exception ex) {
			logger.error("Unable to delete "+ type, ex);
		}
		finally{
			session.close();
		}
	}

	public List<T> getUnsynced() {
		Session session = HibernateHelper.getSession();
		List<T> list = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("from ").append(type.getName())
					.append(" where version = 0");
			Query<T> query = session.createQuery(hql.toString(), type);
			list = query.list();
		}
		catch(Exception ex){
			logger.error(ex);
		}
		finally{
			session.close();
		}
		return list;
	}	

	public List<T> filter(int pageNumber) {
		logger.debug("Get "+ type +" objects, pageNumber: "+ pageNumber);

		List<T> results = new ArrayList<>();
		try(Session session = HibernateHelper.getSession()){
			StringBuilder hql = new StringBuilder("from ").append(type.getName());
			Query<T> query = session.createQuery(hql.toString(), type);
			query.setFirstResult(pageSize * pageNumber);
			query.setMaxResults(pageSize);
			results = query.getResultList();
			logger.debug("Get "+ type +" pageNumber: "+ pageNumber+ " successful, found "+ results.size() +" items");
		}
		catch (Exception ex) {
			logger.error("Error getting objects of "+ type +", with pageNumber: "+ pageNumber, ex);
		}

		return results;
	}

	public static boolean exists(Class<?> type, Serializable id) {
		if(id != null) {
			try(Session session = HibernateHelper.getSession()){
				return session.get(type, id) != null;
			}	
		}
		return false;
	}

	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return StringUtils.replace(str, "'", "''");
	}
}
