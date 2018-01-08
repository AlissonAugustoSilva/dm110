package br.inatel.dm110.equipamentos.entities;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_equipamentos", sequenceName = "seq_equipamentos", allocationSize = 1)
public class Equipamentos {

	@Id
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
	public Equipamentos(String ip, boolean status) {
		
		this.ip = ip;
		this.status = status;
	}
	public Equipamentos() {
			
	}	
}