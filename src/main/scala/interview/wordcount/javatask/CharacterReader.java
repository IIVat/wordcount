package interview.wordcount.javatask;

import java.io.EOFException;

public interface CharacterReader {
	
	/** 
	 * Return the next character of the stream, or EOFException 
	 * when the end of the stream is reached 
	 */
	public char nextCharacter() throws EOFException, InterruptedException;

	/**
	 * Close the input stream. This must be called after the 
	 * last character has been read to dispose of the stream
	 */
	public void close();
	
}

