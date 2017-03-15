package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.caelum.modelo.Pedido;

public class TesteConsumidorTopicoEstoqueSelector {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception{
		
		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.setClientID("estoque");
		System.out.println("EXECUTANDO COMSUMIDOR ESTOQUE SELECTOR -- APERTE QUALQUER TECLA PARA FINALIZAR...");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topico = (Topic) context.lookup("loja");
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura-selector","ebook = false or ebook is null",false);
		
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println("RECEBENDO MENSAGEM: "+pedido.toString());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
