package interview.wordcount.javatask;

import java.io.EOFException;
import java.util.Random;

public class SlowCharacterReaderImpl extends FastCharacterReaderImpl {

	protected Random random = new Random();
	
	@Override
	public char nextCharacter() throws EOFException, InterruptedException {
		Thread.sleep(random.nextInt(200));
		return super.nextCharacter();
	}
	
}
