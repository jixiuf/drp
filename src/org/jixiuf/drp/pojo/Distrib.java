package org.jixiuf.drp.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(AbstractClient.DISTRIB_FLAG)
public class Distrib extends AbstractClient {
	private DistribType distribType;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "CATEGORY_ID")
	public DistribType getDistribType() {
		return this.distribType;
	}

	public void setDistribType(DistribType distribType) {
		this.distribType = distribType;
	}

}
