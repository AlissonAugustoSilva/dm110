package br.inatel.dm110.equipamentos.interfaces;

public interface EquipamentosInt {

	void startScan(String ip, String mask);

	String checkbyIP(String ip);

}
