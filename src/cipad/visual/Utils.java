package cipad.visual;

import java.io.File;

public class Utils {

	    public final static String txt = "txt";
	    public final static String rtf = "rtf";
	    public final static String html = "html";
	    public final static String htm = "htm";
	    public final static String eml = "eml";

	    /*
	     * Get the extension of a file.
	     */  
	    public static String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }
}
