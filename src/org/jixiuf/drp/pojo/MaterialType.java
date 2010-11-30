package org.jixiuf.drp.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * 物料的类型 ，如 中成药 ,医疗器械，记录在抽象数据字典中
 *
 * @author Administrator
 *
 */
@Entity

@DiscriminatorValue(MaterialType.MATERIAL_TYPE_FLAG)
public class MaterialType extends AbstractDataDict {

}
