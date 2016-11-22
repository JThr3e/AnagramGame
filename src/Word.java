//Struct used in main program
public class Word{
		private String word;
		private String aID;
		public Word(String word, String aID){
			this.word = word;
			this.aID = aID;
		}
		public String getWord() {
			return word;
		}
		public void setWord(String word) {
			this.word = word;
		}
		public String getaID() {
			return aID;
		}
		public void setaID(String aID) {
			this.aID = aID;
		}
		
		public int hashCode() {
			return aID.hashCode();
		}
		@Override
		public String toString() {
			return word;
		}
		
	}