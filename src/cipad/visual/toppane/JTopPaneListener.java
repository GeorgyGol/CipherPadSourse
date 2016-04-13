package cipad.visual.toppane;

public interface JTopPaneListener {
	void onFileNameChanged(String strNewName, String sSourse);
	void onTextChanged(String sSourse);
	void onPopupMenuClick(String sCommand, String sName);
}
