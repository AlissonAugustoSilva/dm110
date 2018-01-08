package br.inatel.dm110.equipamentos.api;

import java.io.Serializable;

public class EquipamentosTO implements Serializable{

	private static final long serialVersionUID = 2693625650697491893L;

	private String ip;
	private boolean status;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

}
