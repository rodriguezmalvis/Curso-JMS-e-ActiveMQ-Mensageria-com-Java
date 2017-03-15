package br.com.caelum.jms;

import java.io.StringWriter;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

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
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
//		StringWriter stringWriter = new StringWriter();
//		JAXB.marshal(pedido, stringWriter);
//		String xml = stringWriter.toString();
		
//		Message message = session.createTextMessage(xml);
		
		Message message = session.createObjectMessage(pedido);
		message.setBooleanProperty("ebook", false);
		messageProducer.send(message);
		
		System.out.println("MENSAGENS ENVIADO!!");
		
		session.close();
		connection.close();
		context.close();

	}

}
