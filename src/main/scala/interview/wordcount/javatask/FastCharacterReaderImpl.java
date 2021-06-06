package interview.wordcount.javatask;

import java.io.EOFException;

public class FastCharacterReaderImpl implements CharacterReader {

	protected int position = 0;
	protected String data;
	
	protected static String characterData = "Then hear me, gracious sovereign, and you peers,\n" +
			"That owe yourselves, your lives and services\n" +
			"To this imperial throne. There is no bar\n" +
			"To make against your highness' claim to France\n" +
			"But this, which they produce from Pharamond,\n" +
			"'In terram Salicam mulieres ne succedant:'\n" +
			"'No woman shall succeed in Salique land:'\n" +
			"Which Salique land the French unjustly gloze\n" +
			"To be the realm of France, and Pharamond\n" +
			"The founder of this law and female bar.\n" +
			"Yet their own authors faithfully affirm\n" +
			"That the land Salique is in Germany,\n" +
			"Between the floods of Sala and of Elbe;\n" +
			"Where Charles the Great, having subdued the Saxons,\n" +
			"There left behind and settled certain French;\n" +
			"Who, holding in disdain the German women\n" +
			"For some dishonest manners of their life,\n" +
			"Establish'd then this law; to wit, no female\n" +
			"Should be inheritrix in Salique land:\n" +
			"Which Salique, as I said, 'twixt Elbe and Sala,\n" +
			"Is at this day in Germany call'd Meisen.\n" +
			"Then doth it well appear that Salique law\n" +
			"Was not devised for the realm of France:\n" +
			"Nor did the French possess the Salique land\n" +
			"Until four hundred one and twenty years\n" +
			"After defunction of King Pharamond,\n" +
			"Idly supposed the founder of this law;\n" +
			"Who died within the year of our redemption\n" +
			"Four hundred twenty-six; and Charles the Great\n" +
			"Subdued the Saxons, and did seat the French\n" +
			"Beyond the river Sala, in the year\n" +
			"Eight hundred five. Besides, their writers say,\n" +
			"King Pepin, which deposed Childeric,\n" +
			"Did, as heir general, being descended\n" +
			"Of Blithild, which was daughter to King Clothair,\n" +
			"Make claim and title to the crown of France.\n" +
			"Hugh Capet also, who usurped the crown\n" +
			"Of Charles the duke of Lorraine, sole heir male\n" +
			"Of the true line and stock of Charles the Great,\n" +
			"To find his title with some shows of truth,\n" +
			"'Through, in pure truth, it was corrupt and naught,\n" +
			"Convey'd himself as heir to the Lady Lingare,\n" +
			"Daughter to Charlemain, who was the son\n" +
			"To Lewis the emperor, and Lewis the son\n" +
			"Of Charles the Great. Also King Lewis the Tenth,\n" +
			"Who was sole heir to the usurper Capet,\n" +
			"Could not keep quiet in his conscience,\n" +
			"Wearing the crown of France, till satisfied\n" +
			"That fair Queen Isabel, his grandmother,\n" +
			"Was lineal of the Lady Ermengare,\n" +
			"Daughter to Charles the foresaid duke of Lorraine:\n" +
			"By the which marriage the line of Charles the Great\n" +
			"Was re-united to the crown of France.\n" +
			"So that, as clear as is the summer's sun.\n" +
			"King Pepin's title and Hugh Capet's claim,\n" +
			"King Lewis his satisfaction, all appear\n" +
			"To hold in right and title of the female:\n" +
			"So do the kings of France unto this day;\n" +
			"Howbeit they would hold up this Salique law\n" +
			"To bar your highness claiming from the female,\n" +
			"And rather choose to hide them in a net\n" +
			"Than amply to imbar their crooked titles\n" +
			"Usurp'd from you and your progenitors";

	public FastCharacterReaderImpl(String data) {
		this.data = data;
	}
	
	public FastCharacterReaderImpl() {
		this(characterData);
	}

	@Override
	public char nextCharacter() throws EOFException, InterruptedException {
		if (position >= data.length())
			throw new EOFException();
		
		return data.charAt(position++);
	}

	@Override
	public void close() {
	}
	
}
