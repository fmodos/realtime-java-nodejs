import java.awt.FlowLayout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisTest extends JFrame {

	final Jedis jedisPub;

	JTextArea logUsers = new JTextArea();
	JTextArea typeArea = new JTextArea();

	public RedisTest() {
		// connect to the redis pub
		jedisPub = new Jedis("localhost");

		// connect to the redis sub
		final Jedis jedisSub = new Jedis("localhost");
		new Thread(new Runnable() {
			public void run() {
				jedisSub.subscribe(new JedisPubSub() {

					@Override
					public void onUnsubscribe(String arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSubscribe(String arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPUnsubscribe(String arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPSubscribe(String arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPMessage(String arg0, String arg1, String arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onMessage(String arg0, String arg1) {
						// received message of userConnected or userDisconnected
						String m = (arg0 + "-" + arg1);
						logUsers.setText(logUsers.getText() + "\n" + m);

					}
				}, "userDisconnected", "userConnected");
			}
		}).start();

		typeArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// public every character typed by the user
				jedisPub.publish("fromJava", String.valueOf(arg0.getKeyChar()));

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		JPanel jPanel = new JPanel(new FlowLayout());
		typeArea.setSize(300, 400);
		typeArea.setColumns(50);
		typeArea.setRows(10);
		logUsers.setSize(300, 400);
		logUsers.setRows(10);
		logUsers.setColumns(50);
		jPanel.add(typeArea);
		jPanel.add(logUsers);
		add(jPanel);
		setSize(600, 400);
		setVisible(true);
	}

	public static void main(String[] args) {
		new RedisTest();
	}

}
