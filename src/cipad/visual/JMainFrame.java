package cipad.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import cipad.Cipherpad;
import cipad.crypto.AES;
import cipad.crypto.TextTransfer;
import cipad.crypto.AES.constCipher;
import cipad.crypto.AesException;
import cipad.visual.toppane.JTopPane;
import cipad.visual.toppane.JTopPaneListener;

public class JMainFrame extends JFrame implements JTopPaneListener, JMainMenuListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7080734358090198712L;
	
	private JTabbedPane tabbedPane;
	private JPanel panelWork;
	private JPanel panelHelp;
	
	private JTopPane topPanel;
	private JTopPane botPanel;
	
	private AES.constCipher ciType=AES.constCipher.AES;
    private String strCiKey="";
	
    private JMainMenu mainMenu;
    public static final String sTopName="TOP";
    public static final String sBotName="BOT";
    
	protected void InitComponents(){
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setSize(1200, 700);
		setLocationRelativeTo(null); // place in center screen - 'null'
		
		mainMenu=new JMainMenu();
		mainMenu.addListener(this);
		
		setJMenuBar(mainMenu);

		tabbedPane=new JTabbedPane();
		panelWork = new JPanel(false);
		panelWork.setLayout(new GridLayout(2, 1));
		
		// super.getContentPane().setLayout(new GridLayout(2, 1));
		
		topPanel=new JTopPane(sTopName);
		topPanel.addListener(this);
		topPanel.InitPanel();
		
		botPanel=new JTopPane(sBotName);
		botPanel.addListener(this);
		botPanel.InitPanel();
		
		panelWork.add(topPanel,BorderLayout.CENTER);
		panelWork.add(botPanel,BorderLayout.CENTER);
		
		// super.getContentPane().add(topPanel,BorderLayout.CENTER);
		// super.getContentPane().add(botPanel,BorderLayout.CENTER);
		
		tabbedPane.addTab("Work", panelWork);
		
		panelHelp=new JPanel();
		panelHelp.setLayout(new GridLayout(1, 1));
		JHelpText txtHelp=new JHelpText();
		panelHelp.add(txtHelp.getScrolledTextArea());
		
		tabbedPane.addTab("Help", panelHelp);
		
		super.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }
    
	private void SetLook(String strTheme){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		    	Cipherpad.PrintDebug(info.getName());
		        if (strTheme.equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {

		}
	}
	
	public JMainFrame() {
		super("Шифроблокнот");
		SetLook("Nimbus"); // Motif
		// SetLook("CDE/Motif"); // Motif
		//SetLook("Windows"); // Motif
		//SetLook("Windows Classic"); // Motif
		InitComponents();
	}

	private void showCipherError(String strMess){
		JOptionPane.showMessageDialog(null, strMess, "Ошибка!!!",
				 JOptionPane.ERROR_MESSAGE);
	}
	
	public void doCipher() {
		if(!(mainMenu.isFixKeySelected() && !strCiKey.isEmpty())){
			try{
				onKeyChanged("");
			}
			catch(IllegalArgumentException e){
				return;
			}
		}
		
		AES a_c=new AES(strCiKey, ciType);
		Cipherpad.PrintDebug("in encript " + ciType.getMethodString());
		String strCText=topPanel.getText();
		if(strCText.equals("") || strCText == null){
			showCipherError("Please input TEXT for cipher");
			return;
		}
		
		try {
			String strRet=a_c.encrypt(strCText);
			topPanel.setText(strRet);
		} catch (AesException e) {
			showCipherError(e.getLocalizedMessage());
		}
	}
	
	public void doDeCipher() {
		
		if(!(mainMenu.isFixKeySelected() && !strCiKey.isEmpty())){
			try{
				onKeyChanged("");
			}
			catch(IllegalArgumentException e){
				return;
			}
		}
		
		String strDeText=botPanel.getText();
		if(strDeText.equals("") || strDeText == null){
			showCipherError("Please input TEXT for decipher");
			return;
		}
		
		AES a_c=new AES(strCiKey, ciType);
		
		try {
			String strRet=a_c.decrypt(strDeText);
			botPanel.setText(strRet);
		} catch (AesException e) {
			showCipherError(e.getLocalizedMessage());
		}
		
	}

	@Override
	public void onMuniItemPressed(String mnCommand) {
		switch(mnCommand){
			case "ci_undo": break;
			case "cipher": doCipher(); break;
			
			case "ci_new": topPanel.CreaterNewFile(); break;
			case "ci_open": topPanel.OpenFile(); break;
			case "ci_save": topPanel.SaveFile(); break;
			
			case "de_new": botPanel.CreaterNewFile(); break;
			case "de_open": botPanel.OpenFile(); break;
			case "de_save": botPanel.SaveFile(); break;
			
			case "des": ciType=AES.constCipher.DES; break;
			case "aes": ciType=AES.constCipher.AES; break;
			case "desede": ciType=AES.constCipher.DESede; break;
			case "Blowfish": ciType=AES.constCipher.Blowfish;	break;
			
			case "decipher": doDeCipher(); break;
			
			case "exit": dispose(); 
				break;
		}
		// System.out.println(mnCommand);
		// System.out.println(ciType.getMethodString());
	}

	@Override
	public void onCheckItemChange(String sType, String sCommand, Boolean bState) {
		//System.out.println(sType + " " + bState);
		if(sType.equals("JCheckBoxMenuItem")){
			if(bState && strCiKey.isEmpty()){
				try{
					onKeyChanged("");
				}
				catch(IllegalArgumentException e){
					return;
				}
			}
		}
	}
	
	@Override
	public void onKeyChanged(String strNewKey) throws IllegalArgumentException{

		String strKey=JOptionPane.showInputDialog("Please input a value"); 
		if(strKey.isEmpty()){
			showCipherError("Небходимо ввести ключ");
			throw(new IllegalArgumentException());
		}
		strCiKey=strKey;
	}

	@Override
	public void onFileNameChanged(String strNewName, String sSourse) {
		switch(sSourse){
			case sTopName: mainMenu.setCipherSaveEnabled(false); break;
			case sBotName: mainMenu.setDeCipherSaveEnabled(false); break;
		}
	}

	@Override
	public void onTextChanged(String sSourse) {
		switch(sSourse){
			case sTopName: mainMenu.setCipherSaveEnabled(true); break;
			case sBotName: mainMenu.setDeCipherSaveEnabled(true); break;
		}
		
	}

	@Override
	public void onPopupMenuClick(String sCommand, String sName) {
		// TODO Auto-generated method stub
		JTopPane tmp;
		TextTransfer clipboard;
		
		if(sName.equals(JMainFrame.sTopName)){
			tmp=topPanel;
		}
		else {
			tmp=botPanel;
		}
		switch(sCommand){
		case "undo": tmp.Undo(); break;
		case "cipher": doCipher(); break;
		case "cut":
			clipboard=new TextTransfer();
			clipboard.setClipboardContents(tmp.getText());
			tmp.deleteSelection();
			break;
		case "copy":
			clipboard=new TextTransfer();
			clipboard.setClipboardContents(tmp.getText());
			break;
		case "paste":
			clipboard=new TextTransfer();
        	try {
        		String strPaste=clipboard.getClipboardContents();
				if(!strPaste.equals("")){
					tmp.insertText(strPaste);
					clipboard.clearContents();
				}
			} catch (UnsupportedFlavorException | IOException e1) {
				e1.printStackTrace();
			}

			break;
		
		case "new": tmp.CreaterNewFile(); break;
		case "open": tmp.OpenFile(); break;
		case "save": tmp.SaveFile(); break;

		case "decipher": doDeCipher(); break;
		
		case "exit": dispose(); 
			break;
	}
	}
	
}
