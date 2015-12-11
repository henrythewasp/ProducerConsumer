package com.finalcut.util;

abstract class Doc {
	public String getText() { throw new UnsupportedOperationException(); }
	public boolean isPoisonPill() { return false; }
}
