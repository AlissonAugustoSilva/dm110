package br.inatel.dm110.equipamentos.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import br.inatel.dm110.equipamentos.api.EquipamentosListTO;
import br.inatel.dm110.equipamentos.api.EquipamentosTO;

@Stateless
public class ObjectMessageSender {

	@Resource(lookup = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(lookup = "java:/jms/queue/equipamentosqueue")
	private Queue queue;

	public void sendObjectMessage(EquipamentosListTO list) {
		try (
				Connection connection = connectionFactory.createConnection();
				Session session = connection.createSession();
				MessageProducer producer = session.createProducer(queue);
		) {
			if(list instanceof EquipamentosListTO) {
				for(EquipamentosTO item : list.getEqps()) {
					ObjectMessage textMessage = session.createObjectMessage(item);
					producer.send(textMessage);
				}
				
			}
			
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendTextMessage(String text) {
		try (
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession();
			MessageProducer producer = session.createProducer(queue);
		) {
			TextMessage textMessage = session.createTextMessage(text);
			producer.send(textMessage);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
}








