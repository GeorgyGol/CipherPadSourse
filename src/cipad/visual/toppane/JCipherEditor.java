package cipad.visual.toppane;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JCipherEditor extends JTextArea {
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	public JCipherEditor(){
		super();
		setLineWrap(true);
		setWrapStyleWord(true);
		//this.getDocument().addUndoableEditListener(arg0);
	}
	
	public JScrollPane getScrolledTextArea(){
		return new JScrollPane(this);
	}
	
	
}
