package org.jixiuf.drp.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 *  会计核算期 entity.
 */
@Entity
@Table(name = "FISCALPERIOD")
public class FiscalPeriod implements java.io.Serializable {

	// Fields

	private String id;
	private Short year;
	private Byte month;
	private Date startdate;
	private Date enddate;
	private String status;


	// Constructors

	/** default constructor */
	public FiscalPeriod() {
	}

	/** full constructor */
	public FiscalPeriod(Short year, Byte month, Date startdate,
			Date enddate, String status
		) {
		this.year = year;
		this.month = month;
		this.startdate = startdate;
		this.enddate = enddate;
		this.status = status;

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

	@Column(name = "YEAR", precision = 4, scale = 0)
	public Short getYear() {
		return this.year;
	}

	public void setYear(Short year) {
		this.year = year;
	}

	@Column(name = "MONTH", precision = 2, scale = 0)
	public Byte getMonth() {
		return this.month;
	}

	public void setMonth(Byte month) {
		this.month = month;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE", length = 7)
	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE", length = 7)
	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {

		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "year="+getYear()+
		"  month="+getMonth()+
		"     startDate="+getStartdate()+

		"    endDate="+getEnddate()+
		"    status= "+getStatus();
	}


}