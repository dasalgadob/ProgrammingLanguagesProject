import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;


class TestParserAnalyser {

	@Test
	void testSee() {
		ParserAnalyser pa = new ParserAnalyser(null);
		pa.setCurrentToken(new TokenData(Token.CBRACE,"{",1,1));
		assertTrue("No se encontro que el siguiente fuera de tipo CBRACE ",pa.see(Token.CBRACE));
		;
	}

}
