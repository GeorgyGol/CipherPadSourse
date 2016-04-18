package cipad.visual.toppane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultEditorKit;

import cipad.visual.JMainFrame;

public class JTopPanePopupMenu extends JPopupMenu implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3680720097606496441L;
	
	private ArrayList<JPopupListener> listeners = new ArrayList<JPopupListener>();

	
    public void addListener(JPopupListener listener) {
        listeners.add(listener);
    }

    public void removeListener(JPopupListener listener) {
        listeners.remove(listener);
    }

    private void fireMenuPressed(String strCommand) {
        for(JPopupListener listener : listeners) {
            listener.onClickItem(strCommand);  
        }
    }
	
	private JMenuItem mnNew, mnOpen, mnSave;
	private JMenuItem mnUndo, mnPaste, mnCopy, mnCut;
	private JMenuItem mnCiDe;
	
	private JMenuItem ConstructItem(JMenuItem mnWhat, String strCap, String strComm){
		mnWhat=new JMenuItem(strCap);
		mnWhat.setActionCommand(strComm);
		mnWhat.addActionListener(this);
		return mnWhat;
	}
	
	public JTopPanePopupMenu(String sType){
		if(sType.equals(JMainFrame.sTopName)){
			add(ConstructItem(mnCiDe, "Шифровать", "cipher"));
		}
		else {
			add(ConstructItem(mnCiDe, "Дешифровать", "decipher"));
		}

		this.addSeparator();
		
		add(mnCut=ConstructItem(mnCut, "Вырезать", "cut"));
		//add(DefaultEditorKit.cutAction);
		add(mnCopy=ConstructItem(mnCopy, "Копировать", "copy"));
		add(mnPaste=ConstructItem(mnPaste, "Вставить", "paste"));
		this.addSeparator();
		
		add(ConstructItem(mnNew, "Новый", "new"));
		add(ConstructItem(mnOpen, "Открыть", "open"));
		add(mnSave=ConstructItem(mnSave, "Сохранить", "save"));
		// this.addSeparator();
		//add(DefaultEditorKit.selectAllAction);
	}

	public boolean isSaveEnable(){
		return mnSave.isEnabled();
	}
	
	public void setSaveEnable(boolean b){
		mnSave.setEnabled(b);
	}
	
	public boolean isCopyEnable(){
		return mnCopy.isEnabled();
	}
	
	public void setCopyEnable(boolean b){
		mnCopy.setEnabled(b);
	}
	
	public boolean isPasteEnable(){
		return mnPaste.isEnabled();
	}
	
	public void setPasteEnable(boolean b){
		mnPaste.setEnabled(b);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		fireMenuPressed(e.getActionCommand());
	}
}
