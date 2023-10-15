package io.basc.start.template.pojo;

import io.basc.framework.jdbc.template.annotation.Index;
import io.basc.framework.orm.annotation.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(primaryKeys = "templateId")
public class AttributeTemplate extends AttributeTemplateDescribe {
	private static final long serialVersionUID = 1L;
	@Index
	private long parentTemplateId;
}