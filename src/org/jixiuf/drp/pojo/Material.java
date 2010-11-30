package org.jixiuf.drp.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * MATERIAL entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MATERIAL")
public class Material implements java.io.Serializable {

	// Fields

	private String id;
	// 物料代码
	private String no;
	// 物料的类型
	private MaterialType materialType;
	// 物料的单位
	private MaterialItemUnit materialItemUnit;

	private String name;// 名称
	private String guige;// 规格
	private String xinghao;// 型号
	private String pictFilename;



	// Constructors

	/** default constructor */
	public Material() {
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TYPE_ID")
	public MaterialType getMaterialType() {
		return this.materialType;
	}

	public void setMaterialType(MaterialType materialType) {
		this.materialType = materialType;
	}

	public void setMaterialItemUnit(MaterialItemUnit materialItemUnit) {
		this.materialItemUnit = materialItemUnit;
	}

	@ManyToOne(  fetch = FetchType.EAGER  )
	@JoinColumn(name = "UNITTYPE")
	public MaterialItemUnit getMaterialItemUnit() {
		return this.materialItemUnit;
	}

	@Column(name = "NO", length = 32)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "NAME", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "GUIGE", length = 30)
	public String getGuige() {
		return this.guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	@Column(name = "XINGHAO", length = 30)
	public String getXinghao() {
		return this.xinghao;
	}

	public void setXinghao(String xinghao) {
		this.xinghao = xinghao;
	}

	@Column(name = "PICT_FILENAME", length = 50)
	public String getPictFilename() {
		return this.pictFilename;
	}

	public void setPictFilename(String pictFilename) {
		this.pictFilename = pictFilename;
	}



}