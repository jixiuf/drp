package org.jixiuf.drp.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 分销商的类型 ，如一级分销商，二级分销商，记录在抽象数据字典中
 *
 * @author Administrator
 *
 */
@Entity
//DistribType.DISTRIB_TYPE_FLAG="D";
//即表中的TYPE_ID列的值 为DISTRIB_TYPE_FLAG的记录表示DistribType
//@DiscriminatorValue(DistribType.DISTRIB_TYPE_FLAG  )
@Table(name="V_DISTRIBTYPE")

public class DistribType   {

	private String id;
	private String name;
	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
