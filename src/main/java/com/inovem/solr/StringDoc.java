package com.inovem.solr;

class StringDoc extends Doc {
	private String text;

	public StringDoc(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
