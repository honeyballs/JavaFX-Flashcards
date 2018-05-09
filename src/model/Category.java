package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
	
	private String name;
	private List<Category> subcategories;
	private List<Flashcard> cards;
	
	public Category(String name) {
		this.name = name;
		this.subcategories = new ArrayList<Category>();
		this.cards= new ArrayList<Flashcard>();
	}
	
	public void addCategory(Category category) {
		this.subcategories.add(category);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Category> getSubcategories() {
		return subcategories;
	}
	
	public List<Flashcard> getCards() {
		return cards;
	}

	public void addCard(Flashcard card) {
		this.cards.add(card);
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
}
