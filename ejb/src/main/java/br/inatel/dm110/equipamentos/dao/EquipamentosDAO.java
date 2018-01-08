package br.inatel.dm110.equipamentos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.inatel.dm110.equipamentos.entities.Equipamentos;
@Stateless

public class EquipamentosDAO {
	@PersistenceContext(unitName = "equipamentos")
	private EntityManager em;
	
	public List<Equipamentos> consultar() {
		List<Equipamentos> eqps  = null;
		eqps = em.createQuery("from Equipamentos e", Equipamentos.class).getResultList();
		return eqps;
	}
	public void insert(Equipamentos eqp) {
		em.persist(eqp);
	}
	public Equipamentos checkbyip(String ip) {
		Equipamentos equipamento = null;
		TypedQuery<Equipamentos> tq= em.createQuery("from Equipamentos e where ip=?", Equipamentos.class);
		try {
			equipamento = tq.setParameter(1, ip).getSingleResult();			
		} catch(Exception e){
			return null;
		}
		return equipamento;
	}
	public Equipamentos edit(Equipamentos eqp) {	
		em.merge(eqp);
		return eqp;		
	}
}
