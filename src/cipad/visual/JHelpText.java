package cipad.visual;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import cipad.Cipherpad;

public class JHelpText extends JEditorPane  {
	public JHelpText(){
		// setLineWrap(true);
		// setWrapStyleWord(true);
		
		setEditable(false);
		setContentType("text/html");
		//setText(loadText());
		java.net.URL helpURL = getClass().getResource("CipadHelp.html");
		Cipherpad.PrintDebug(helpURL.toString());
		try {
			//this.setPage(loadText());
			this.setPage(helpURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setDocument(new HTMLDocument());
		

		//txtHelp.setDocument(doc);
		// txtHelp.setEnabled(false);
	}
	
	public JScrollPane getScrolledTextArea(){
		return new JScrollPane(this);
	}
	
	private String loadText() {
        StringBuilder sb = new StringBuilder();
        try {
        	//char[] buffer = new char[(int)flCurrent.length()];
            //reader.read(buffer);
        	// Cipherpad.PrintDebug(getClass().getResource("CipadHelp").toString());
        	
        	// BufferedInputStream is = (BufferedInputStream) getClass().getResourceAsStream("CipadHelp");
        	BufferedInputStream is = new BufferedInputStream(getClass().getResourceAsStream("CipadHelp"));
        	// InputStream is = (InputStream) getClass().getResourceAsStream("CipadHelp");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "Cp1251"));
            while (true) {
                String line =br.readLine();
                if (line == null)
                    break;
                sb.append(line).append("\n");
            }
        } catch (IOException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.flush();
            pw.close();
            sb.append("Error while loading text: ").append("\n\n");
            sb.append(sw.getBuffer().toString());
        }
        return sb.toString();
    }

}
