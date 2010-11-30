package org.jixiuf.drp.pojo;

// default package

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
 * FlowCardDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOWCARD_DETAIL")
public class FlowCardDetail implements java.io.Serializable {

	// Fields

	private String id;
	Flowcard flowcard;
	private Material material;
	private Double materialCount;
	// 需方客户
	private AbstractClient client;

	private Double adjustCount;
	private String adjustReason;

	// Constructors

	/** default constructor */
	public FlowCardDetail() {
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	@ManyToOne
	@JoinColumn(name = "FLOWCARD_ID"  )
	public Flowcard getFlowcard() {
		return flowcard;
	}

	public void setFlowcard(Flowcard flowcard) {
		this.flowcard = flowcard;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MATERIAL_ID")
	public Material getMaterial() {
		return this.material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@Column(name = "MATERIAL_COUNT", precision = 12)
	public Double getMaterialCount() {
		return this.materialCount;
	}

	public void setMaterialCount(Double materialCount) {
		this.materialCount = materialCount;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENT_ID")
	public AbstractClient getClient() {
		return this.client;
	}

	public void setClient(AbstractClient client) {
		this.client = client;
	}

	@Column(name = "ADJUST_COUNT", precision = 12)
	public Double getAdjustCount() {
		return this.adjustCount;
	}

	public void setAdjustCount(Double adjustCount) {
		this.adjustCount = adjustCount;
	}

	@Column(name = "ADJUST_REASON", length = 100)
	public String getAdjustReason() {
		return this.adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}


}