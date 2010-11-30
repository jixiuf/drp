package org.jixiuf.drp.service;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("c2")
public class C2 extends P {
	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}

	String c2;
	Bean b ;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="bean")
	public Bean getB() {
		return b;
	}

	public void setB(Bean b) {
		this.b = b;
	}


}
