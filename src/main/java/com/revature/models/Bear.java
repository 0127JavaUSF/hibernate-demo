package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

/*
 * We will use JPA annotations to define this class as an entity
 */

@Entity // Must be used if we want Hibernate to manage it
// @Table - Optional annotation that provides additional table configuration
@Table(name="bears")
@Check(constraints = "id < 1")
public class Bear {
	// BY DEFAULT all properties will be treated as columns
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String breed;
	private String color;
	
	// @Column - Optional column configuration
	@Column(name="people_eaten")
	private int peopleEaten;

	/** Height in meters when on two legs **/
	@Column(columnDefinition = "float")
	private double height;
	private double weight;
	
	@Column(unique=true)
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPeopleEaten() {
		return peopleEaten;
	}

	public void setPeopleEaten(int peopleEaten) {
		this.peopleEaten = peopleEaten;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breed == null) ? 0 : breed.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + peopleEaten;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bear other = (Bear) obj;
		if (breed == null) {
			if (other.breed != null)
				return false;
		} else if (!breed.equals(other.breed))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (peopleEaten != other.peopleEaten)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	public Bear(int id, String breed, String color, int peopleEaten, double height, double weight, String name) {
		super();
		this.id = id;
		this.breed = breed;
		this.color = color;
		this.peopleEaten = peopleEaten;
		this.height = height;
		this.weight = weight;
		this.name = name;
	}

	public Bear() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Bear [id=" + id + ", breed=" + breed + ", color=" + color + ", peopleEaten=" + peopleEaten + ", height="
				+ height + ", weight=" + weight + ", name=" + name + "]";
	}

}
