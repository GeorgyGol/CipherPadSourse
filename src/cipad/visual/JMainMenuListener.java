package cipad.visual;

public interface JMainMenuListener {
	public void onMuniItemPressed(String mnCommand);
	//public void onCheckItemChange(ItemEvent arg0);
	public void onCheckItemChange(String sType, String sComm, Boolean bState);
	public void onKeyChanged(String strNewKey);
}
