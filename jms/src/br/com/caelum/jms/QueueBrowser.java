package br.com.caelum.jms;

import java.util.Enumeration;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class QueueBrowser {

	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		System.out.println("EXECUTANDO QUEUE BROWSER -- APERTE QUALQUER TECLA PARA FINALIZAR...");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		
		javax.jms.QueueBrowser queueBrowser = session.createBrowser((Queue) fila);
		
		Enumeration msgs = queueBrowser.getEnumeration();
		
		
        if (!msgs.hasMoreElements()) {
            System.out.println("Não há mensagens na fila");
        } else {
            while (msgs.hasMoreElements()) {
                final TextMessage msg = (TextMessage) msgs.nextElement();
                System.out.println("Mensagem: " + msg.getText());
            }
        }
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
		
	}

}
