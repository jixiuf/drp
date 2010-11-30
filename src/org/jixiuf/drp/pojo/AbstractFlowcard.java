package org.jixiuf.drp.pojo;

// default package

import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * Flowcard entity.流向单主表
 */
@Entity
@Table(name = "FLOWCARD")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "flag", discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class AbstractFlowcard implements java.io.Serializable {

	public final static String STATUS_L = "L"; // 刚录入
	public final static String STATUS_S = "S";// 已送审
	public final static String STATUS_A = "A";// 已审核通过
	public final static String STATUS_F = "F";// 已复审通过,如果，复审没通过 ，流向单状态向被重置为S,并且复审人会标上复审人，
	//如此就可以判断此流向单是被 驳回的，而不是由录入人员直接
	// Fields
	private String id;
	private String flowcardNo; // 流向单号

	private String status;// 流向单 状态
	private FiscalPeriod fiscalPeriod;
	private Distrib distrib;
	private User recorder;
	private Date recordDate;
	// 抽查人
	private User spotter;
	private Date spotDate;

	private String spotDesc;
	// 调整的数量及原因在明细表中
	private User adjuster;
	private Date adjusterDate;
	private User fushen;
	private Date fushenDate;
	// Constructors
	List<FlowCardDetail> flowcardDetails;

	/** default constructor */
	public AbstractFlowcard() {
	}

	public AbstractFlowcard(String id) {
		this.id = id;
	}

	@Column(name = "flowcard_no", length = 12)
	public String getFlowcardNo() {
		return flowcardNo;
	}

	public void setFlowcardNo(String flowcardNo) {
		this.flowcardNo = flowcardNo;
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

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FISCALPERIODID", nullable = false)
	public FiscalPeriod getFiscalPeriod() {
		return this.fiscalPeriod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRI_ID", nullable = false)
	public Distrib getDistrib() {
		return this.distrib;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECORDER_ID", nullable = false)
	public User getRecorder() {
		return this.recorder;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RECORDER_DATE", nullable = false, length = 7)
	public Date getRecordDate() {
		return this.recordDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPOTTER_ID")
	public User getSpotter() {
		return this.spotter;
	}

	@Column(name = "SPOTTER_DESC", length = 50)
	public String getSpotDesc() {
		return this.spotDesc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADJUSTER_ID")
	public User getAdjuster() {
		return this.adjuster;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ADJUSTER_DATE", length = 7)
	public Date getAdjusterDate() {
		return this.adjusterDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUSHEN_ID")
	public User getFushen() {
		return this.fushen;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FUSHEN_DATE", length = 7)
	public Date getFushenDate() {
		return this.fushenDate;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "spotdate", length = 7)
	public Date getSpotDate() {
		return spotDate;
	}
	public void setFushenDate(Date fushenDate) {
		this.fushenDate = fushenDate;
	}

	public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	public void setDistrib(Distrib distrib) {
		this.distrib = distrib;
	}

	public void setRecorder(User recorder) {
		this.recorder = recorder;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public void setSpotter(User spotter) {
		this.spotter = spotter;
	}

	public void setSpotDesc(String spotDesc) {
		this.spotDesc = spotDesc;
	}

	public void setAdjuster(User adjuster) {
		this.adjuster = adjuster;
	}

	public void setAdjusterDate(Date adjusterDate) {
		this.adjusterDate = adjusterDate;
	}

	public void setFushen(User fushen) {
		this.fushen = fushen;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "flowcard")
	public List<FlowCardDetail> getFlowcardDetails() {
		return flowcardDetails;
	}

	public void setFlowcardDetails(List<FlowCardDetail> flowcardDetails) {
		this.flowcardDetails = flowcardDetails;
	}



	public void setSpotDate(Date spotDate) {
		this.spotDate = spotDate;
	}

}