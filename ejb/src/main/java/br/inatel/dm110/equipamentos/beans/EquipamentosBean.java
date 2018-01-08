package br.inatel.dm110.equipamentos.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.inatel.dm110.equipamentos.api.EquipamentosListTO;
import br.inatel.dm110.equipamentos.api.EquipamentosTO;
import br.inatel.dm110.equipamentos.dao.EquipamentosDAO;
import br.inatel.dm110.equipamentos.entities.Equipamentos;
import br.inatel.dm110.equipamentos.interfaces.EquipamentosIntLocal;
import br.inatel.dm110.equipamentos.interfaces.EquipamentosIntRemote;
import br.inatel.dm110.equipamentos.utils.Util;

@Stateless
@Remote(EquipamentosIntRemote.class)
@Local(EquipamentosIntLocal.class)
public class EquipamentosBean implements EquipamentosIntRemote,EquipamentosIntLocal{

	@EJB
	private EquipamentosDAO dao;
	
	@EJB
	private ObjectMessageSender sender;

	@Override
	public void startScan(String ip, String mask) {	
		List<EquipamentosTO> eqps = new ArrayList<>();
		String[] ips  = Util.generateIps(ip, Integer.parseInt(mask));
		for (int i = 0; i < ips.length; i++) {
			String gerado = ips[i];
			EquipamentosTO po = new EquipamentosTO();
			po.setStatus(false);
			po.setIp(gerado);
			sender.sendTextMessage(gerado.substring(gerado.length() - 4,gerado.length() ));
			if(
				gerado.substring(gerado.length() - 2,gerado.length() )!=".0" && gerado.substring(gerado.length() - 4,gerado.length() ) != ".255" 
			) {
				eqps.add(po);
			}
		}
		for (int i =0; i<eqps.size();i+=10) {
			List<EquipamentosTO> eqps2 = new  ArrayList<>();
			eqps2 = eqps.subList(i, Math.min(eqps.size(),i+10));
			EquipamentosListTO eqpsList = new EquipamentosListTO();
			eqpsList.setEqps(eqps2);
			sender.sendObjectMessage(eqpsList);
		}
		
	}

	@Override
	public String checkbyIP(String ip) {
		Equipamentos po =  dao.checkbyip(ip);
		if(po == null) {
			return "Este IP não foi mapeado";
		}else {
			EquipamentosTO pto = new EquipamentosTO();
			pto.setIp(po.getIp());
			pto.setStatus(po.getStatus());
			return (pto.getStatus() ? "ativo" : "inativo" );
		}
		
	}

	

}
