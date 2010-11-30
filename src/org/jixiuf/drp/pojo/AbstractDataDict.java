package org.jixiuf.drp.pojo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 此类为一个数据字典DistribType 等类是此类的子类， 通过表中一个TYPE_ID的列来区分不同的子类
 * 继承关系 ，存在一个表中
 */
@Entity
@Table(name = "DATA_DICT")
//继承策略，只用一张表存储， 通过表中一个TYPE_ID的列来区分不同的子类
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="TYPE_ID",
    discriminatorType=DiscriminatorType.STRING
    ,length=1

)
public  abstract class AbstractDataDict implements java.io.Serializable {

	// Fields

	private String id;
	private String name;



	// 物料类别的标志
	public static final String MATERIAL_TYPE_FLAG = "M";
	// 物料的单位的标志
	public static final String MATERIAL_UNIT_TYPE_FLAG = "U";


	// Constructors

	/** default constructor */
	public AbstractDataDict() {
	}

	/** full constructor */
	public AbstractDataDict(String name) {
		this.name = name;


	}

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