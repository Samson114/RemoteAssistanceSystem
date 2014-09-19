
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class getimg implements Runnable {
	ServerSocket ss;
	Socket sk;
	InputStream is;
	ImageIcon fina = new ImageIcon(
			(this.getClass().getResource("./img/test.jpg")));
	JLabel jl = new JLabel(fina);
	RandomAccessFile inFile = null;
	JFrame f1;
	public getimg(JFrame f1) throws IOException {
		this.f1 = f1;
		f1.add(jl, BorderLayout.CENTER);
		f1.setVisible(true);
		ss = new ServerSocket(10100);
	}

	public class setimg implements Runnable {
		@Override
		public void run() {
			int count = 0;
			int index = 0;
			byte byteBuffer[] = new byte[500000];
			String FilePath = null;
			JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setDialogTitle("Choice Folder");
			int ret = fileChooser.showOpenDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION) {
			FilePath = fileChooser.getSelectedFile().getAbsolutePath()+"/";
			FilePath = FilePath.replace("./", "");
			File path = new File(FilePath+"MyAppImage/");
			/**
			 * FullScreen
			 */
//			GraphicsEnvironment ge = GraphicsEnvironment
//					.getLocalGraphicsEnvironment();
//			// 通过调用GraphicsEnvironment的getDefaultScreenDevice方法获得当前的屏幕设备了
//			GraphicsDevice gd = ge.getDefaultScreenDevice();
//			// 全屏设置
//			gd.setFullScreenWindow(f1);
			if(!path.exists()){
				path.mkdir();
			}
			}else{
			JOptionPane.showMessageDialog(null, "Try Again!");
			System.exit(0);
			}
			while (true) {
				try {
					sk = ss.accept();
					is = sk.getInputStream();
					File file = new File(
							((FilePath+"MyAppImage/" + count + ".jpg")));
					inFile = new RandomAccessFile(file, "rw");
					while ((index = is.read(byteBuffer)) != -1) {
						inFile.write(byteBuffer, 0, index);
					}
					ImageIcon newi = new ImageIcon(file.toString());
					jl.setIcon(newi);
					inFile.close();
					is.close();
					file.delete();
					++count;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void run() {
		new Thread(new setimg()).start();
	}
}
