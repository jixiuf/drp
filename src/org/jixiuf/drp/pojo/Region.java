package org.jixiuf.drp.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Region entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "REGION")
public class Region implements java.io.Serializable {

	// Fields

	private String id;
	private Region parent;
	private String name;
	private Byte level;
	private Boolean leaf;
	private Set<AbstractClient> clients = new HashSet<AbstractClient>(0);
	private Set<Region> children = new HashSet<Region>(0);

	// Constructors

	/** default constructor */
	public Region() {
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

	@JoinColumn(name = "PID")
	@ManyToOne(fetch = FetchType.LAZY)
	public Region getParent() {
		return parent;
	}

	public void setParent(Region parent) {
		this.parent = parent;
	}

	@Column(name = "NAME", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LEVEL_", precision = 2, scale = 0)
	public Byte getLevel() {
		return this.level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	@Column(name = "LEAF_", precision = 1, scale = 0)
	public Boolean getLeaf() {
		return this.leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@OneToMany( fetch = FetchType.LAZY, mappedBy = "parent")
	public Set<Region> getChildren() {
		return children;
	}

	public void setChildren(Set<Region> children) {
		this.children = children;
	}

}