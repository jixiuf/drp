package org.jixiuf.drp.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 *
 * 物料的单位 如:箱、盒、片，
 *
 * @author Administrator
 *
 */
@Entity

@DiscriminatorValue(MaterialItemUnit.MATERIAL_UNIT_TYPE_FLAG)
public class MaterialItemUnit extends AbstractDataDict {

}
