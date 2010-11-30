package org.jixiuf.drp.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 库存信息表（台帐信息） 特定分销商的某个物料在某个核算期间内是数量及变化 Inventory entity. @author MyEclipse
 * Persistence Tools
 */
@Entity
@Table(name = "INVENTORY")
public class Inventory implements java.io.Serializable {

	// Fields

	private String id;
	private  Distrib distrib; // 分销 商，
	private Material material; // 物料
	private FiscalPeriod fiscalPeriod;// 合算期间
	private Double initcount;// 期初数量，现存数量，
	private Double incount; // 当前核算期间内物料 的入库数量，
	private Double outcount;// 出库数量（在）
	// N-->S-->Y
	private String status;// 状态，是否可用，初始化后并不可用，须经确认才可使用
	public final static String AVAILABLE = "Y";
	public final static String NONE_AVAILABLE = "N";
	//送审状态
	public final static String SEND_TO_CHECK= "S";

	// Constructors

	/** default constructor */
	public Inventory() {
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




	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MAT_ID", nullable = false)
	public Material getMaterial() {
		return this.material;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRI_ID", nullable = false)
	public Distrib getDistrib() {
		return distrib;
	}

	public void setDistrib(Distrib distrib) {
		this.distrib = distrib;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FISCALPERIODID" ,nullable=true)
	public FiscalPeriod getFiscalPeriod() {
		return this.fiscalPeriod;
	}

	public void setFiscalPeriod(FiscalPeriod fiscalperiod) {
		this.fiscalPeriod = fiscalperiod;
	}

	@Column(name = "INITCOUNT", precision = 12)
	public Double getInitcount() {
		return this.initcount;
	}

	public void setInitcount(Double initcount) {
		this.initcount = initcount;
	}

	@Column(name = "INCOUNT", precision = 12)
	public Double getIncount() {
		return this.incount;
	}

	public void setIncount(Double incount) {
		this.incount = incount;
	}

	@Column(name = "OUTCOUNT", precision = 12)
	public Double getOutcount() {
		return this.outcount;
	}

	public void setOutcount(Double outcount) {
		this.outcount = outcount;
	}
	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Transient
	public Double getCurrentAdjustCount() {
		return this.getIncount() - this.getOutcount();
	}


}