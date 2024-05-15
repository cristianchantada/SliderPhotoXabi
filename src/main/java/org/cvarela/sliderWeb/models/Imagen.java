package org.cvarela.sliderWeb.models;

public class Imagen {

	private String src;
	private boolean isVisible; 
	
	public Imagen() {}
	
	public Imagen(String src, boolean isVisible) {
		this.src = src;
		this.isVisible = isVisible;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	
	
	
}
