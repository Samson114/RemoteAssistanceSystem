
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class getcmd extends JFrame {
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
	byte[] bt = new byte[1024];
	JTextArea ta = new JTextArea();
	JScrollPane js = new JScrollPane(ta);
	abs_receive current;
	Robot conmouse = new Robot();
	String nip;

	public getcmd(String nip) throws IOException, AWTException {
		this.nip = nip;
		ta.setEditable(false);
		ta.setText("Connected!");
		socket = new DatagramSocket(10086);
		packet = new DatagramPacket(bt, bt.length);
		socket1 = new DatagramSocket(10000);
		packet1 = new DatagramPacket(bt, bt.length);
		socket2 = new DatagramSocket(10005);
		packet2 = new DatagramPacket(bt, bt.length);
		current = new real_receive();
		inti();
	}

	private void inti() throws IOException, AWTException {
		this.setSize(300, 100);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(js, BorderLayout.CENTER);
		this.setVisible(true);
		new Thread(new getscreen(nip)).start();
		;
		new Thread(new setcli()).start();
		new Thread(new setmou()).start();
		new Thread(new getdata()).start();
	}

	public class setcli implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					socket2.receive(packet2);
					String index = new String(packet2.getData(), 0,
							packet2.getLength());
					if (index.equals("1"))
						current.leftclick();
					else if (index.equals("3"))
						current.rightclick();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public class setmou implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					socket1.receive(packet1);
					int x = Integer.parseInt(new String(packet1.getData(), 0,
							packet1.getLength()).split("\\*")[0]);
					int y = Integer.parseInt(new String(packet1.getData(), 0,
							packet1.getLength()).split("\\*")[1]);
					conmouse.mouseMove(x, y);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public class getdata implements Runnable {
		@Override
		public void run() {
			String keychar = null;
			while (true) {
				try {
					socket.receive(packet);
					keychar = (new String(packet.getData(), 0,
							packet.getLength()));
					if (!keychar.startsWith("*")) {
						if (keychar.equals("1")) {
							current.pressed_1();
						} else if (keychar.equals("2")) {
							current.pressed_2();
						} else if (keychar.equals("3")) {
							current.pressed_3();
						} else if (keychar.equals("4")) {
							current.pressed_4();
						} else if (keychar.equals("5")) {
							current.pressed_5();
						} else if (keychar.equals("6")) {
							current.pressed_6();
						} else if (keychar.equals("7")) {
							current.pressed_7();
						} else if (keychar.equals("8")) {
							current.pressed_8();
						} else if (keychar.equals("9")) {
							current.pressed_9();
						} else if (keychar.equals("-")) {
							current.pressed_cut();
						} else if (keychar.equals("+")) {
							current.pressed_plus();
						} else if (keychar.equals("a")) {
							current.pressed_a();
						} else if (keychar.equals("b")) {
							current.pressed_b();
						} else if (keychar.equals("c")) {
							current.pressed_c();
						} else if (keychar.equals("d")) {
							current.pressed_d();
						} else if (keychar.equals("e")) {
							current.pressed_e();
						} else if (keychar.equals("f")) {
							current.pressed_f();
						} else if (keychar.equals("g")) {
							current.pressed_g();
						} else if (keychar.equals("h")) {
							current.pressed_h();
						} else if (keychar.equals("i")) {
							current.pressed_i();
						} else if (keychar.equals("j")) {
							current.pressed_j();
						} else if (keychar.equals("k")) {
							current.pressed_k();
						} else if (keychar.equals("l")) {
							current.pressed_l();
						} else if (keychar.equals("m")) {
							current.pressed_m();
						} else if (keychar.equals("n")) {
							current.pressed_n();
						} else if (keychar.equals("o")) {
							current.pressed_o();
						} else if (keychar.equals("p")) {
							current.pressed_p();
						} else if (keychar.equals("q")) {
							current.pressed_q();
						} else if (keychar.equals("r")) {
							current.pressed_r();
						} else if (keychar.equals("s")) {
							current.pressed_s();
						} else if (keychar.equals("t")) {
							current.pressed_t();
						} else if (keychar.equals("u")) {
							current.pressed_u();
						} else if (keychar.equals("v")) {
							current.pressed_v();
						} else if (keychar.equals("w")) {
							current.pressed_w();
						} else if (keychar.equals("x")) {
							current.pressed_x();
						} else if (keychar.equals("y")) {
							current.pressed_y();
						} else if (keychar.equals("z")) {
							current.pressed_z();
						} else if (keychar.equals(".")) {
							current.pressed_period();
						} else if (keychar.equals(":")) {
							current.pressed_period();
						}
					} else {
						keychar = keychar.substring(1);
						if (keychar.equals("27")) {
							current.pressed_esc();
						} else if (keychar.equals("20")) {
							current.pressed_caps();
						} else if (keychar.equals("16")) {
							current.pressed_shift();
						} else if (keychar.equals("0")) {

						} else if (keychar.equals("8")) {
							current.pressed_del();
						} else if (keychar.equals("10")) {
							current.pressed_enter();
						} else if (keychar.equals("17")) {
							current.pressed_ctrl();
						} else if (keychar.equals("18")) {

						} else if (keychar.equals("157")) {

						} else if (keychar.equals("32")) {
							current.pressed_space();
						} else if (keychar.equals("37")) {
							current.pressed_left();
						} else if (keychar.equals("38")) {
							current.pressed_up();
						} else if (keychar.equals("39")) {
							current.pressed_right();
						} else if (keychar.equals("40")) {
							current.pressed_down();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws IOException, AWTException {
		final JFrame f = new JFrame();
		final JTextField tf = new JTextField("Please input the IP");
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
						new getcmd(tf.getText());
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (AWTException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
	}
}
