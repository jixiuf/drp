package org.jixiuf.drp.pojo;

// default package

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Flowcard entity.流向单主表
 */
@Entity

@DiscriminatorValue("F")
public class Flowcard extends AbstractFlowcard implements java.io.Serializable {


}