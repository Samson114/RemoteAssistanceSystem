
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class sendcmd extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DatagramSocket socket;
	DatagramPacket packet;
	DatagramSocket socket1;
	DatagramPacket packet1;
	DatagramSocket socket2;
	DatagramPacket packet2;
	InetAddress ip;
	int port = 10086;
	int port1 = 10000;
	int port2 = 10005;
	byte[] bt = new byte[1024];

	public sendcmd(String nip) throws IOException {
		socket = new DatagramSocket();
		ip = InetAddress.getByName(nip);
		packet = new DatagramPacket(bt, bt.length, ip, port);
		socket1 = new DatagramSocket();
		packet1 = new DatagramPacket(bt, bt.length, ip, port1);
		socket2 = new DatagramSocket();
		packet2 = new DatagramPacket(bt, bt.length, ip, port2);
		init();
	}

	private void init() throws IOException {
		this.setSize(1366, 768);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(new getimg(this)).start();
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				try {
					sendclick(e.getButton() + "");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				try {
					sendmouselocation(e.getX() + "*" + e.getY());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				try {
					if (hascode(e.getKeyCode())) {
						sendcode(e.getKeyChar() + "");
					} else {
						sendcode("*" + e.getKeyCode() + "");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	protected void sendclick(String string) throws IOException {
		packet2.setData(string.getBytes());
		socket2.send(packet2);
	}

	protected void sendmouselocation(String string) throws IOException {
		packet1.setData(string.getBytes());
		socket1.send(packet1);
	}

	protected boolean hascode(int keyCode) {
		if (keyCode == 27 || keyCode == 20 || keyCode == 16 || keyCode == 0
				|| keyCode == 112 || keyCode == 113 || keyCode == 114
				|| keyCode == 115 || keyCode == 116 || keyCode == 117
				|| keyCode == 118 || keyCode == 119 || keyCode == 120
				|| keyCode == 121 || keyCode == 8 || keyCode == 10
				|| keyCode == 17 || keyCode == 18 || keyCode == 157
				|| keyCode == 32 || keyCode == 37 || keyCode == 38
				|| keyCode == 39 || keyCode == 40 || keyCode == 0) {
			return false;
		}
		return true;
	}

	protected void sendcode(String keyChar) throws IOException {
		packet.setData((keyChar + "").getBytes());
		socket.send(packet);
	}

	public static void main(String[] args) throws IOException {
		final JFrame f = new JFrame();
		final JTextField tf = new JTextField("Please Input the IP");
		f.setSize(200, 55);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(tf, BorderLayout.CENTER);
		f.setVisible(true);
		tf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					f.setVisible(false);
					try {
						new sendcmd(tf.getText());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
	}
}
