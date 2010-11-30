package org.jixiuf.drp.service;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "p")
//继承策略，只用一张表存储， 通过表中一个TYPE_ID的列来区分不同的子类
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="IS_DISTRIBUTION_FLAG",
    discriminatorType=DiscriminatorType.STRING
    ,length=1
)
public class P {

	String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
