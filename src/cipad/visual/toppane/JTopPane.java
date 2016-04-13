package cipad.visual.toppane;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.JTextComponent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import cipad.crypto.TextTransfer;
import cipad.visual.JFileChooserTXT;


public class JTopPane extends JPanel implements DocumentListener, JPopupListener  {
	
	/**
	 * 
	 */
	
		
		
	
	class PopupListener extends MouseAdapter {
		JTopPanePopupMenu popup;
 
        PopupListener(JTopPanePopupMenu popupMenu) {
            popup = popupMenu;
        }
 
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }
 
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }
 
        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
            	popup.setPasteEnable(true);

            	TextTransfer t=new TextTransfer();
            	try {
					if(t.getClipboardContents().equals("")){
						popup.setPasteEnable(false);
					}
				} catch (UnsupportedFlavorException | IOException e1) {
					popup.setPasteEnable(false);
					// e1.printStackTrace();
				}
            	
            	popup.setCopyEnable(edtPlane.getSelectedText() != null);
            	
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
    }
	
	private JTopPanePopupMenu popup;
	
	private static final long serialVersionUID = -5365089569596194777L;
	private final String cstrNewFileTempl="cipherpad_new";
	private int lNewFileCnt=0;
	
	private File flCurrent;
	private JCipherEditor edtPlane;
	private String sName="";
	
	private JLabel jStat;
	
	private ArrayList<JTopPaneListener> listeners = new ArrayList<JTopPaneListener>();

	private boolean isTextChange=false;
	
    public void addListener(JTopPaneListener listener) {
        listeners.add(listener);
    }

    public void removeListener(JTopPaneListener listener) {
        listeners.remove(listener);
    }

    private void fireFileNameChangedListeners(String strNewName) {
        for(JTopPaneListener listener : listeners) {
            listener.onFileNameChanged(strNewName, sName);
        }
    }

    private void firePopupClick(String sComm) {
        for(JTopPaneListener listener : listeners) {
            listener.onPopupMenuClick(sComm, sName); 
        }
    }
    
    private void fireTextChange(){
    	isTextChange=true;
    	popup.setSaveEnable(true);
    	for(JTopPaneListener listener : listeners) {
            listener.onTextChanged(sName);
        }
    }
    
    private String ConstructFileName(int lCnt){
    	return sName + "_" + cstrNewFileTempl + lCnt;
    }
    public JTopPane(){
		super();
		setBorder(new EmptyBorder(3, 3, 3, 3));
        setLayout(new GridBagLayout());
    }
	
	public JTopPane(String strName){
		this();
        sName=strName;
    }
	
	public JPanel InitPanel() {
		
		/*
		 //Create the text area for the status log and configure it.
        changeLog = new JTextArea(5, 30);
        changeLog.setEditable(false);
        JScrollPane scrollPaneForLog = new JScrollPane(changeLog);
 
        //Create a split pane for the change log and the text area.
        JSplitPane splitPane = new JSplitPane(
                                       JSplitPane.VERTICAL_SPLIT,
                                       scrollPane, scrollPaneForLog);
        splitPane.setOneTouchExpandable(true);
 
        //Create the status area.
        JPanel statusPane = new JPanel(new GridLayout(1, 1));
        CaretListenerLabel caretListenerLabel =
                new CaretListenerLabel("Caret Status");
        statusPane.add(caretListenerLabel);
 
        //Add the components.
        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(statusPane, BorderLayout.PAGE_END);
		*/
		
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        jStat=new JLabel("simple text");
        jStat.setFont(new Font(jStat.getFont().getFontName(), Font.ITALIC, 10));
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,0,0,0);  //top padding
        
        add(jStat, c);
        
        edtPlane=new JCipherEditor();
        edtPlane.getDocument().addDocumentListener(this);
        edtPlane.setBorder(BorderFactory.createLineBorder(Color.black));
        
        popup=new JTopPanePopupMenu(sName);
        
        popup.addListener(this);
        MouseListener popupListener = new PopupListener(popup);
        edtPlane.addMouseListener(popupListener);
        
        c.weightx = 1.0;
        c.weighty = 1.0;
        //c.gridwidth = 6;
        c.gridheight=GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5,0,5,0);  //top padding
        add(edtPlane.getScrolledTextArea(), c);
 
        CreaterNewFile();
        return this;
    }
	
	private int isSaveFile(){
		
		if(isTextChange){
		 	return JOptionPane.showInternalConfirmDialog(this.getParent(), "Текущий файл не сохранен - сохранить?", 
					"Сохранение файла", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
		}
		return 1;
	}
	
	public void CreaterNewFile(){
		flCurrent=new File(ConstructFileName(++lNewFileCnt));
		edtPlane.setText("");
		popup.setSaveEnable(false);
		isTextChange=false;
		jStat.setText(flCurrent.getAbsolutePath());
		fireFileNameChangedListeners(flCurrent.getName());
	}
	
	public void SaveFile(){
		
		System.out.println(flCurrent.getName() + " " + ConstructFileName(lNewFileCnt));
		
		if(flCurrent.getName().equalsIgnoreCase(ConstructFileName(lNewFileCnt))){
			// ask new name
			final JFileChooserTXT fc = new JFileChooserTXT();
			
			int returnVal = fc.showSaveDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	System.out.println(fc.getSelectedFile().getAbsolutePath());
	            flCurrent=fc.getSelectedFile();
	        }
	        else return;
		}
		try(FileWriter wr=new FileWriter(flCurrent)){
				wr.write(edtPlane.getText());
				isTextChange=false;
				jStat.setText(flCurrent.getAbsolutePath());
				popup.setSaveEnable(false);
				// System.out.println("saved in " + flCurrent.getAbsolutePath());
			}
			catch(IOException ex){
				JOptionPane.showInternalMessageDialog(this.getParent(), ex.getMessage(),
        				"OpenFile ERROR", JOptionPane.ERROR_MESSAGE); 
			}
	}
	
	public void OpenFile(){
		final JFileChooserTXT fc = new JFileChooserTXT();
		
		int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	flCurrent = fc.getSelectedFile();
            //This is where a real application would open the file.
            try(FileReader reader = new FileReader(flCurrent))
            {
            	char[] buffer = new char[(int)flCurrent.length()];
                reader.read(buffer);
                edtPlane.setText(new String(buffer));
                isTextChange=false;
                popup.setSaveEnable(false);
                jStat.setText(flCurrent.getAbsolutePath());
                fireFileNameChangedListeners(flCurrent.getAbsolutePath());
            }
            catch(IOException ex){
            	JOptionPane.showInternalMessageDialog(this.getParent(), ex.getMessage(),
        				"OpenFile ERROR", JOptionPane.ERROR_MESSAGE); 
            }   
            
        } else {
        	// edtPlane.setText("Open command cancelled by user.");
        }
	}
	
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		fireTextChange();
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		fireTextChange();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		fireTextChange();
	}

	public String getText(){
		if(edtPlane.getSelectedText() != null){
			return edtPlane.getSelectedText();
		}
		else {
			return edtPlane.getText();
		}
	}

	public void setText(String strNew){

		if(edtPlane.getSelectedText() == null){
			edtPlane.setText(strNew);
		}
		else {
			int lE=edtPlane.getSelectionEnd();
			edtPlane.insert("\n" + strNew, lE);
			edtPlane.setSelectionStart(lE+1);
			edtPlane.setSelectionEnd(strNew.length()+2 + lE);
		}
		
	}
	
	public void insertText(String strNew){
		
		if(edtPlane.getSelectedText() == null){
			edtPlane.insert(strNew, edtPlane.getCaretPosition());
		}
		else {
			int lE=edtPlane.getSelectionEnd();
			edtPlane.insert("\n" + strNew, lE);
			edtPlane.setSelectionStart(lE+1);
			edtPlane.setSelectionEnd(strNew.length()+2 + lE);
		}
		
	}
	
	public void deleteSelection(){
		if(edtPlane.getSelectedText() != null){
			int iStart=edtPlane.getSelectionStart();
			int iEnd=edtPlane.getSelectionEnd();
			
			edtPlane.setText(edtPlane.getText().substring(0, iStart) + edtPlane.getText().substring(iEnd));
			edtPlane.setCaretPosition(iStart);
		}
	}
	
	public void Undo(){
		
	}
	
	@Override
	public void onClickItem(String sCommand) {
		firePopupClick(sCommand);
		// System.out.println(sCommand);
		
	}
}
