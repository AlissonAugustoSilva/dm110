package br.inatel.dm110.equipamentos.impl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import br.inatel.dm110.equipamentos.api.EquipamentosService;
import br.inatel.dm110.equipamentos.interfaces.EquipamentosIntRemote;

@RequestScoped
public class EquipamentosServiceImpl implements EquipamentosService{

	@EJB(lookup = "java:app/ejb-0.1-SNAPSHOT/EquipamentosBean!br.inatel.dm110.equipamentos.interfaces.EquipamentosIntRemote")
	private EquipamentosIntRemote remote;
	
	@Override
	public void scan(String ip, String mask) {
			remote.startScan(ip, mask);
		}
	
	@Override
	public String checkstatus(String ip) {
		return remote.checkbyIP(ip); 
	}	
}
