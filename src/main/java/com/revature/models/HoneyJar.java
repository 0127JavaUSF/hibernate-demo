package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Do you think Bears will share their honey jar?
 * For the sake of this exercise we're going to treat the relationship
 * between bears and honeyjars as a one to one relationship.
 */

@Entity
@Table(name = "honey_jars")
@Check(constraints = "honey_volume >= 0 AND max_volume > 0 AND honey_volume <= max_volume")
@JsonIgnoreProperties({"bear"})
public class HoneyJar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/** Volume of honey in liters **/
	@Column(name = "honey_volume", nullable = false)
	Double honeyVolume;

	/** Maximum volume of honey jar in liters **/
	@Column(name = "max_volume", nullable = false)
	Double maxVolume;

	@OneToOne(mappedBy="honeyJar")
	private Bear bear;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getHoneyVolume() {
		return honeyVolume;
	}

	public void setHoneyVolume(double honeyVolume) {
		this.honeyVolume = honeyVolume;
	}

	public double getMaxVolume() {
		return maxVolume;
	}

	public void setMaxVolume(double maxVolume) {
		this.maxVolume = maxVolume;
	}

	public Bear getBear() {
		return bear;
	}

	public void setBear(Bear bear) {
		this.bear = bear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(honeyVolume);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		temp = Double.doubleToLongBits(maxVolume);
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
		HoneyJar other = (HoneyJar) obj;
		if (Double.doubleToLongBits(honeyVolume) != Double.doubleToLongBits(other.honeyVolume))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(maxVolume) != Double.doubleToLongBits(other.maxVolume))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HoneyJar [id=" + id + ", honeyVolume=" + honeyVolume + ", maxVolume=" + maxVolume + "]";
	}

	public HoneyJar(int id, double honeyVolume, double maxVolume) {
		super();
		this.id = id;
		this.honeyVolume = honeyVolume;
		this.maxVolume = maxVolume;
	}

	public HoneyJar() {
		super();
		// TODO Auto-generated constructor stub
	}

}
