package br.inatel.dm110.equipamentos.api;

import java.io.Serializable;
import java.util.List;

public class EquipamentosListTO implements Serializable{

	private static final long serialVersionUID = 2693625650697491893L;
	
	private List<EquipamentosTO> eqps;

	public List<EquipamentosTO> getEqps() {
		return eqps;
	}

	public void setEqps(List<EquipamentosTO> eqps) {
		this.eqps = eqps;
	}
	

}
