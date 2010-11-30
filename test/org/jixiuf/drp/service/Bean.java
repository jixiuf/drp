package org.jixiuf.drp.service;

import javax.persistence.Entity;

@Entity
public class Bean {
	String name;

	public Bean(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
