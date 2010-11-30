package org.jixiuf.drp.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(AbstractClient.TERMIANL_FLAG)
public class Terminal extends AbstractClient {
	TerminalType terminalType;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "CATEGORY_ID")
	public TerminalType getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}

}
