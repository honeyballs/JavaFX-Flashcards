package model;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Flashcard {
	
	private StringProperty question;
	private StringProperty answer;
	private IntegerProperty noRight;
	private IntegerProperty noWrong;
	private BooleanProperty showQuestion;
	private StringProperty imagePath;
	
	public Flashcard(String question, String answer, int right, int wrong, String path) {
		this.question = new SimpleStringProperty(question);
		this.answer = new SimpleStringProperty(answer);
		this.noRight = new SimpleIntegerProperty(right);
		this.noWrong = new SimpleIntegerProperty(wrong);
		this.showQuestion = new SimpleBooleanProperty(true);
		this.imagePath = new SimpleStringProperty(path);
	}

	public String getQuestion() {
		return question.get();
	}

	public void setQuestion(String question) {
		this.question.set(question);
	}

	public String getAnswer() {
		return answer.get();
	}

	public void setAnswer(String answer) {
		this.answer.set(answer);
	}

	public int getNoRight() {
		return noRight.get();
	}

	public void setNoRight(int noRight) {
		this.noRight.set(noRight);
	}

	public int getNoWrong() {
		return noWrong.get();
	}

	public void setNoWrong(int noWrong) {
		this.noWrong.set(noWrong);
	}
	
	public void incrementRight() {
		this.noRight.set(getNoRight() + 1);
	}
	
	public void incrementWrong() {
		this.noWrong.set(getNoWrong() + 1);
	}

	public boolean isShowQuestion() {
		return showQuestion.get();
	}

	public void setShowQuestion(boolean showQuestion) {
		this.showQuestion.set(showQuestion);
	}
	
	public String getImagePath() {
		return imagePath.get();
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath.set(imagePath);;
	}

	public String getPercentage() {
		if (getNoRight() + getNoWrong() == 0) {
			return "Not answered yet";
		} else {
			float quote = (float) getNoRight() / (getNoRight() + getNoWrong()) * 100;
			DecimalFormat df = new DecimalFormat("#.###", new DecimalFormatSymbols(Locale.US));
			df.setRoundingMode(RoundingMode.CEILING);
			return df.format(quote) + "%";
		}
	}

	/* Methods for observable list */
	public StringProperty question() {
		return question;
	}
	
	public StringProperty answer() {
		return answer;
	}
	
	public IntegerProperty noRight() {
		return noRight;
	}
	
	public IntegerProperty noWrong() {
		return noWrong;
	}
	
	public BooleanProperty showQuestion() {
		return showQuestion;
	}
	
	public StringProperty imagePath() {
		return imagePath;
	}

}
