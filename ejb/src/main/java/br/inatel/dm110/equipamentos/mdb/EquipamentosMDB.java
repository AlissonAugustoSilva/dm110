package br.inatel.dm110.equipamentos.mdb;

import java.io.IOException;

import java.io.InputStream;
import java.util.Scanner;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import br.inatel.dm110.equipamentos.api.EquipamentosTO;
import br.inatel.dm110.equipamentos.dao.EquipamentosDAO;
import br.inatel.dm110.equipamentos.entities.Equipamentos;

@MessageDriven(activationConfig  = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/equipamentosqueue")
})
public class EquipamentosMDB implements MessageListener{
	
	@EJB
	private EquipamentosDAO dao;
	
	@Override
	public void onMessage(Message message) {		
		try {
			if (message instanceof ObjectMessage) {
				ObjectMessage objMessage = (ObjectMessage) message;
				Object obj = objMessage.getObject();
				EquipamentosTO to = (EquipamentosTO) obj;
				
				if(obj instanceof EquipamentosTO) {
					Equipamentos eqp = new Equipamentos();
					eqp.setIp(to.getIp());
					System.out.println("Verificando ip: "+ to.getIp());
					if(execPing(to.getIp())) {
						eqp.setStatus(true);
					} else {
						eqp.setStatus(false);
					}
					if(dao.checkbyip(to.getIp())!=null) {
						dao.edit(eqp);
					}
					else {
						dao.insert(eqp);
					}
				}		
			}else if(message instanceof TextMessage){
				TextMessage objMessage = (TextMessage) message;
				String  obj = objMessage.getText();
				System.out.println( 	 obj);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean execPing(String address) {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("ping -n 1 " + address);
			InputStream is = process.getInputStream();
			InputStream es = process.getErrorStream();
			String input = processStream(is);
			String error = processStream(es);
			int code = process.waitFor();
			if (code != 0) {
				return false;
			}
			if (error.length() > 0) {
				return false;
			}
			if (input.contains("Host de destino inacess")) {
				return false;
			}
			return true;
		} catch (IOException | InterruptedException e) {
			return false;
		}
	}

	public static String processStream(InputStream is) {
		StringBuilder input = new StringBuilder();
		try (Scanner scanner = new Scanner(is)) {
			while (scanner.hasNextLine()) {
				input.append(scanner.nextLine()).append("\n");
			}
		}
		return input.toString();
	}
}
