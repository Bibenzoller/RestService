package com.concretepage.jewelry.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.concretepage.jewelry.entity.Jewelry;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class JewelryDAOImpl implements JewelryDAO {
	@PersistenceContext	
	private EntityManager entityManager;	
	@Override
	public Jewelry getJewelryByBarCode(String barCode) {

		return entityManager.find(Jewelry.class, barCode);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Jewelry> getAllJewelry() {
		String hql = "FROM Jewelry as atcl ORDER BY atcl.barCode";
		return (List<Jewelry>) entityManager.createQuery(hql).getResultList();
	}	
	@Override
	public void addJewelry(Jewelry jewelry) {

		entityManager.persist(jewelry);
	}
	@Override
	public void updateJewelry(Jewelry jewelry) {
		Jewelry jwlry = getJewelryByBarCode(jewelry.getBarCode());
		jwlry.setArticle(jewelry.getArticle());
		jwlry.setDescription(jewelry.getDescription());
		jwlry.setBarCode(jewelry.getBarCode());
		jwlry.setCost(jewelry.getCost());

		entityManager.flush();
	}
	@Override
	public void deleteJewelry(String barCode) {
		entityManager.remove(getJewelryByBarCode(barCode));
	}
	@Override
	public boolean jewelryExists(String barCode) {
		String hql = "FROM Jewelry as atcl WHERE atcl.barCode = ?";
		int count = entityManager.createQuery(hql).setParameter(1,barCode)
				.getResultList().size();
		return count > 0 ? true : false;
	}
}
