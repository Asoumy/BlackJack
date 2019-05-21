public enum Color {
	HEART("♥"),
	SPADE("♠"),
	CLUB("♣"),
	DIAMOND("♦");

	private String symbole;

	private Color(String symbole) {
		this.symbole = symbole;
	}

	public String getSymbole() {
		String result = null;
		switch(this.name()) {
			case "HEART":
				result = "♥";
				break;
			case "SPADE":
				result = "♠";
				break;
			case "CLUB":
				result = "♣";
				break;
			case "DIAMOND":
				result = "♦";
				break;
		}
		return result;
	}
}
