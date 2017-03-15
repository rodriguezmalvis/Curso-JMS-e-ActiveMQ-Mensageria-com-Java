package br.com.caelum.jms;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorTopico {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		System.out.println("EXECUTANDO PRODUTOR TOPICO...");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) context.lookup("loja");
		
		MessageProducer messageProducer = session.createProducer(topico);
		
		for (int id = 0; id <= 0; id++) {
			Message message = session.createTextMessage("<pedido><id>"+new Random().nextInt(1000000)+"</id></pedido>");
			//message.setBooleanProperty("ebook", false);
			messageProducer.send(message);
		}
		
		System.out.println("MENSAGENS ENVIADO!!");
		
		session.close();
		connection.close();
		context.close();

	}

}
