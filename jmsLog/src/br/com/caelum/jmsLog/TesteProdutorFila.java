package br.com.caelum.jmsLog;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		System.out.println("EXECUTANDO PRODUTOR LOG...");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("log");
		
		MessageProducer messageProducer = session.createProducer(fila);
		
		for (int id = 0; id <= 20; id++) {
			int prioridade = new Random().nextInt(10);
			Message message = session.createTextMessage("ERRO | ERRO PRIORIDADE "+prioridade);
			messageProducer.send(message,DeliveryMode.NON_PERSISTENT,prioridade,80000);
		}
		
		System.out.println("MENSAGENS ENVIADO!!");
		
		session.close();
		connection.close();
		context.close();

	}

}
