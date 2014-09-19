import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class getscreen implements Runnable {
	Robot robot;
	OutputStream os;
	int port = 10010;
	RandomAccessFile outFile;
	Socket sk;
	byte[] bt = new byte[1];
	String nip;

	public getscreen(String nip) throws AWTException, IOException {
		robot = new Robot();
		this.nip = nip;
	}

	@Override
	public void run() {
		String FilePath = null;
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Choice Folder");
		int ret = fileChooser.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			FilePath = fileChooser.getSelectedFile().getAbsolutePath() + "/";
			FilePath = FilePath.replace("./", "");
			File path = new File(FilePath + "MyAppImage/");
			if (!path.exists()) {
				path.mkdir();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Try Again!");
			System.exit(0);
		}
		int count = 0;
		while (true) {
			try {
				sk = new Socket(nip, 10100);
				os = sk.getOutputStream();
				Dimension dimension = Toolkit.getDefaultToolkit()
						.getScreenSize();
				BufferedImage getimg = (robot
						.createScreenCapture(new Rectangle(0, 0,
								((int) dimension.getWidth()), ((int) dimension
										.getHeight()))));
				File file = new File(FilePath + "MyAppImage/" + count + ".jpg");
				ImageIO.write(getimg, "jpg", file);
				int index = 0;
				byte byteBuffer[] = new byte[500000];
				outFile = new RandomAccessFile(file, "r");
				while ((index = outFile.read(byteBuffer)) != -1) {
					os.write(byteBuffer, 0, index);
				}
				outFile.close();
				sk.close();
				os.close();
				file.delete();
				++count;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
