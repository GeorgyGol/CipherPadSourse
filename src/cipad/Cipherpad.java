package cipad;

import java.awt.EventQueue;

import javax.swing.JFrame;

import cipad.visual.JMainFrame;

public class Cipherpad extends JFrame {

	/**
	 * Launch the application.
	 */
	
	public static boolean isDebug=false;
	
	public static void PrintDebug(String strMessage){
		if(Cipherpad.isDebug){
			System.out.println(strMessage);
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JMainFrame frame = new JMainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
