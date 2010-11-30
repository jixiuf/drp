package org.jixiuf.drp.pojo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "CLIENT" )
//继承策略，只用一张表存储， 通过表中一个TYPE_ID的列来区分不同的子类
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="IS_DISTRIBUTION_FLAG",
    discriminatorType=DiscriminatorType.STRING
    ,length=1
)

public   class AbstractClient implements java.io.Serializable {

	// Fields
	/**
	 * isDistributionFlag 属性的两个枚举
	 * 一个是分销商，一个是终端客户
	 */
	public final static String DISTRIB_FLAG = "D";
	public final static String TERMIANL_FLAG = "C";

	private String id;
	private String clientno;
	private String name;
	private String bankno;
	private String phone;
	private String address;
	private String zipcode;
	private String contactor;

	private Region region;

	// Constructors

	/** default constructor */
	public AbstractClient() {
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

	@ManyToOne(  fetch = FetchType.EAGER  )
	@JoinColumn(name = "REGIONID")
	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}


	@Column(name = "CLIENTNO", length = 32)
	public String getClientno() {
		return this.clientno;
	}

	public void setClientno(String clientno) {
		this.clientno = clientno;
	}

	@Column(name = "NAME", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(  name = "BANKNO"  )
	public String getBankno() {
		return this.bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}

	@Column(name = "PHONE", length = 15)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ADDRESS", length = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ZIPCODE", length = 15)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "CONTACTOR", length = 30)
	public String getContactor() {
		return this.contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}




}