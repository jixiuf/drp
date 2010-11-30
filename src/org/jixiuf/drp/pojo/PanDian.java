package org.jixiuf.drp.pojo;

// default package

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Flowcard entity. 盘点信息主表
 */
@Entity

@DiscriminatorValue("P")
public class PanDian extends AbstractFlowcard implements java.io.Serializable {

}