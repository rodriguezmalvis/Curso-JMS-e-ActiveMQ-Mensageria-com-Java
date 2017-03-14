package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
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
		System.out.println("EXECUTANDO PRODUTOR ...");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageProducer messageProducer = session.createProducer(fila);
		
		for (int id = 0; id <= 1000; id++) {
			Message message = session.createTextMessage("<pedido><id>"+id+"</id></pedido>");
			messageProducer.send(message);
		}
		
		System.out.println("MENSAGENS ENVIADO!!");
		
		session.close();
		connection.close();
		context.close();

	}

}
