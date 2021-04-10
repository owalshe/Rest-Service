package rest.service;

public enum Rows {
	
	ODD("Odd"),
	EVEN("Even");
	
	private String label;

	Rows(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
