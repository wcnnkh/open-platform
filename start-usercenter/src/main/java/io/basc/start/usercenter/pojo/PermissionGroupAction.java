package io.basc.start.usercenter.pojo;

import java.io.Serializable;

import io.basc.framework.jdbc.template.annotation.Table;
import io.basc.framework.orm.annotation.PrimaryKey;
import lombok.Data;

@Table
@Data
public class PermissionGroupAction implements Serializable {
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	private int groupId;
	@PrimaryKey
	private String actionId;
}
