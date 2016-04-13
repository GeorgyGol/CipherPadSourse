package cipad.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;


public class JMainMenu extends JMenuBar implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static final String AbstractButton = null;

	
	private ArrayList<JMainMenuListener> listeners = new ArrayList<JMainMenuListener>();

	
    public void addListener(JMainMenuListener listener) {
        listeners.add(listener);
    }

    public void removeListener(JMainMenuListener listener) {
        listeners.remove(listener);
    }

    private void fireSubMenuPressed(String strCommand) {
        for(JMainMenuListener listener : listeners) {
            listener.onMuniItemPressed(strCommand); 
        }
    }

    private void fireCheckMenuChange(String sType, String sCommand, Boolean bState) {
        for(JMainMenuListener listener : listeners) {
            listener.onCheckItemChange(sType, sCommand, bState); 
        }
    }
    
    private void fireKeyChanged(String strCommand) {
        for(JMainMenuListener listener : listeners) {
            listener.onKeyChanged(strCommand);  
        }
    }
    
	private JMenuItem mnExit;
	private JMenu mnCiph, mnDeCiph, mnSett;
	
	private JMenuItem sbmCOpen, sbmCNew, sbmCSave; 
	private JMenuItem sbmCSipher, sbmCUndo;
	
	private JMenuItem sbmDOpen, sbmDNew, sbmDSave; 
	private JMenuItem sbmDeSipher, sbmDUndo;
	
	private JRadioButtonMenuItem sbmAES, sbmDES, sbmDESede, smbBlowfish;
	private JMenuItem sbmInputKey;
	private JCheckBoxMenuItem sbmFixKey;
	
	public boolean isCipherSaveEnable(){
		return sbmCSave.isEnabled();
	}
	
	public void setCipherSaveEnabled(boolean isEnable){
		sbmCSave.setEnabled(isEnable);
	}
	
	public boolean isDeCipherSaveEnable(){
		return sbmDSave.isEnabled();
	}
	
	public void setDeCipherSaveEnabled(boolean isEnable){
		sbmDSave.setEnabled(isEnable);
	}
	
	public boolean isFixKeySelected(){
		return sbmFixKey.isSelected();
	}
	
	private void InitCiMenu(){
		mnCiph=new JMenu("��������");

		mnCiph.setMnemonic(KeyEvent.VK_A);
		mnCiph.getAccessibleContext().setAccessibleDescription(
		        "���� ��� ������ � ������ ���������� (������� ��������)");
		
		//add(mnCiph);
		
		sbmCSipher=new JMenuItem("�����������", KeyEvent.VK_S);
		sbmCSipher.setActionCommand("cipher");
		sbmCSipher.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_S, ActionEvent.ALT_MASK));
		sbmCSipher.getAccessibleContext().setAccessibleDescription(
				"������� ����� � ������� ��������� ��������� ����������");
		sbmCSipher.addActionListener(this);
		mnCiph.add(sbmCSipher);

		sbmCUndo=new JMenuItem("��������", KeyEvent.VK_Z);
		sbmCUndo.setActionCommand("ci_undo");
		sbmCUndo.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_Z, ActionEvent.ALT_MASK));
		sbmCUndo.getAccessibleContext().setAccessibleDescription(
				"������ ���������� ��������");
		sbmCUndo.addActionListener(this);
		mnCiph.add(sbmCUndo);
		
		mnCiph.addSeparator();
		
		sbmCNew=new JMenuItem("�����", KeyEvent.VK_N);
		sbmCNew.setActionCommand("ci_new");
		sbmCNew.getAccessibleContext().setAccessibleDescription(
				"����� ���� � ������� ���������");
		sbmCNew.addActionListener(this);
		mnCiph.add(sbmCNew);
		
		sbmCOpen=new JMenuItem("�������", KeyEvent.VK_O);
		sbmCOpen.setActionCommand("ci_open");
		sbmCOpen.getAccessibleContext().setAccessibleDescription(
				"������� ���� � ������� ���������");
		sbmCOpen.addActionListener(this);
		mnCiph.add(sbmCOpen);
		
		
		sbmCSave=new JMenuItem("���������", KeyEvent.VK_O);
		sbmCSave.setActionCommand("ci_save");
		sbmCSave.getAccessibleContext().setAccessibleDescription(
				"������� ���� � ������� ���������");
		sbmCSave.addActionListener(this);
		mnCiph.add(sbmCSave);
		
		add(mnCiph);
	}	
	
	private void InitExMenu(){
		mnExit=new JMenuItem("��x��", KeyEvent.VK_X);
		mnExit.setActionCommand("exit");
		mnExit.getAccessibleContext().setAccessibleDescription(
		        "����� �� ���������");
		mnExit.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_X, ActionEvent.ALT_MASK));
		mnExit.addActionListener(this);

		add(new JMenu("Menu3").add(mnExit));
	}
	
	private void InitDeMenu(){
		mnDeCiph=new JMenu("����������");

		mnDeCiph.setMnemonic(KeyEvent.VK_A);
		mnDeCiph.getAccessibleContext().setAccessibleDescription(
		        "���� ��� ������ � ������ ������������ (������ ��������)");
		
		sbmDeSipher=new JMenuItem("�����������", KeyEvent.VK_D);
		sbmDeSipher.setActionCommand("decipher");
		sbmDeSipher.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_D, ActionEvent.ALT_MASK));
		sbmDeSipher.getAccessibleContext().setAccessibleDescription(
				"��������� ����� � ������ ��������� ��������� ����������");
		sbmDeSipher.addActionListener(this);
		mnDeCiph.add(sbmDeSipher);

		sbmDUndo=new JMenuItem("��������", KeyEvent.VK_U);
		sbmDUndo.setActionCommand("de_undo");
		sbmDUndo.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_U, ActionEvent.ALT_MASK));
		sbmDUndo.getAccessibleContext().setAccessibleDescription(
				"������ ���������� ��������");
		sbmDUndo.addActionListener(this);
		mnDeCiph.add(sbmDUndo);
		
		mnDeCiph.addSeparator();

		sbmDNew=new JMenuItem("�����", KeyEvent.VK_Y);
		sbmDNew.setActionCommand("de_new");
		sbmDNew.getAccessibleContext().setAccessibleDescription(
				"����� ���� � ������ ���������");
		sbmDNew.addActionListener(this);
		mnDeCiph.add(sbmDNew);
		
		sbmDOpen=new JMenuItem("�������", KeyEvent.VK_I);
		sbmDOpen.setActionCommand("de_open");
		sbmDOpen.getAccessibleContext().setAccessibleDescription(
				"������� ���� � ������ ���������");
		sbmDOpen.addActionListener(this);
		mnDeCiph.add(sbmDOpen);
		
		
		sbmDSave=new JMenuItem("���������", KeyEvent.VK_J);
		sbmDSave.setActionCommand("de_save");
		sbmDSave.getAccessibleContext().setAccessibleDescription(
				"������� ���� � ������ ���������");
		sbmDSave.addActionListener(this);
		mnDeCiph.add(sbmDSave);
		
		add(mnDeCiph);
	}
	
	private void InitSetMenu(){
		
		mnSett=new JMenu("���������");

		mnSett.setMnemonic(KeyEvent.VK_A);
		mnSett.getAccessibleContext().setAccessibleDescription(
		        "��������� ����������-������������");
		
		//add(mnCiph);
		
		ButtonGroup group = new ButtonGroup();
		sbmAES = new JRadioButtonMenuItem("AES");
		sbmAES.setSelected(true);
		// rbMenuItem.setMnemonic(KeyEvent.VK_AR);
		sbmAES.setActionCommand("aes");
		sbmAES.addActionListener(this);
		sbmAES.addItemListener(this);
		group.add(sbmAES);
		mnSett.add(sbmAES);

		sbmDES = new JRadioButtonMenuItem("DES");
		//rbMenuItem.setMnemonic(KeyEvent.VK_O);
		sbmDES.setActionCommand("des");
		sbmDES.addActionListener(this);
		sbmDES.addItemListener(this);
		group.add(sbmDES);
		mnSett.add(sbmDES);
		
		sbmDESede = new JRadioButtonMenuItem("DESede");
		//rbMenuItem.setMnemonic(KeyEvent.VK_O);
		sbmDESede.setActionCommand("desede");
		sbmDESede.addActionListener(this);
		sbmDESede.addItemListener(this);
		group.add(sbmDESede);
		mnSett.add(sbmDESede);
		
		
		smbBlowfish = new JRadioButtonMenuItem("Blowfish");
		//rbMenuItem.setMnemonic(KeyEvent.VK_O);
		smbBlowfish.setActionCommand("Blowfish");
		smbBlowfish.addActionListener(this);
		smbBlowfish.addItemListener(this);
		
		group.add(smbBlowfish);
		mnSett.add(smbBlowfish);
		
		mnSett.addSeparator();
		
		sbmInputKey=new JMenuItem("������ ����", KeyEvent.VK_D);
		sbmInputKey.setActionCommand("key");
		//sbmInputKey.setAccelerator(KeyStroke.getKeyStroke(
		//KeyEvent.VK_D, ActionEvent.ALT_MASK));
		sbmInputKey.getAccessibleContext().setAccessibleDescription(
				"���� ����� ����������-������������");
		sbmInputKey.addActionListener(this);
		mnSett.add(sbmInputKey);
		
		sbmFixKey = new JCheckBoxMenuItem("������������� ����");
		//sbmFixKey.setMnemonic(KeyEvent.VK_C);
		sbmFixKey.setActionCommand("fix_key");
		sbmFixKey.addActionListener(this);
		sbmFixKey.addItemListener(this);
		mnSett.add(sbmFixKey);
		
		add(mnSett);
	}
	
	public JMainMenu(){
		InitCiMenu();
		InitDeMenu();
		InitSetMenu();
		InitExMenu();

	}

	public Boolean isKeyFix(){
		return sbmFixKey.isSelected();
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
		// TODO Auto-generated method stub
		// fireCheckMenuChange(arg0);
		arg0.getStateChange();
		String sType=arg0.getItemSelectable().getClass().getSimpleName();
		
		String sComm="";
		
		Boolean isCheck=arg0.getStateChange()== ItemEvent.SELECTED;
		
		fireCheckMenuChange(sType, sComm, isCheck);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		fireSubMenuPressed(e.getActionCommand());
		if(e.getActionCommand().equals("key")){ fireKeyChanged(e.getActionCommand()); }
	}
}
