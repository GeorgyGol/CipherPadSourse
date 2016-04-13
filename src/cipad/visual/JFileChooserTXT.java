package cipad.visual;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class JFileChooserTXT extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6391314516929823748L;

	public JFileChooserTXT(){
		super();
		setFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
				 if (f.isDirectory()) {
				        return true;
				    }

				    String extension = Utils.getExtension(f);
				    if (extension != null) {
				        if (extension.equals(Utils.txt) ||
				            extension.equals(Utils.eml) ||
				            extension.equals(Utils.htm) ||
				            extension.equals(Utils.html) ||
				            extension.equals(Utils.rtf)) {
				                return true;
				        } else {
				            return false;
				        }
				    }
				return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "txt-files (txt, html, eml, rtf)";
			}
				
			});
	}
}
