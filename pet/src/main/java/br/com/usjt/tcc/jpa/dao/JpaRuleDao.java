package br.com.usjt.tcc.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.usjt.tcc.interfaces.dao.RuleDao;
import br.com.usjt.tcc.model.PetShop;
import br.com.usjt.tcc.model.Rule;

@Repository
@Transactional
public class JpaRuleDao implements RuleDao {

	@PersistenceContext
	private EntityManager entityManager;

	public JpaRuleDao() {
	}

	public JpaRuleDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void adiciona(Rule rule) {
		entityManager.persist(rule);
	}

	public void atualiza(Rule rule) {
		entityManager.merge(rule);
	}

	@SuppressWarnings("unchecked")
	public List<Rule> lista() {
		Query query = entityManager.createQuery("select r from Rule r");
		return query.getResultList();
	}

	public Rule busca(Long id) {
		return entityManager.find(Rule.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Rule> lista(PetShop petShop) {

		// Query query =
		// entityManager.createQuery("SELECT rule FROM rule INNER JOIN product"+
		// " ON product = rule.product"+
		// " WHERE product.petShop = :pPetShop");

		// Query query =
		// entityManager.createQuery("SELECT r FROM Rule r INNER JOIN Product p"+
		// " ON p = r.product"+
		// " WHERE p.petShop = :pPetShop");

		Query query = entityManager
				.createQuery("SELECT r FROM Rule r JOIN r.product p"
						+ " WHERE p.petShop = :pPetShop");

		query.setParameter("pPetShop", petShop);
		return query.getResultList();

		// SELECT rule.* FROM rule INNER JOIN product ON product.`id` =
		// rule.`product_id`
		// WHERE `product`.`petShop_id` = 3
	}

}
