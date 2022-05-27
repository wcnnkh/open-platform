package io.basc.start.app.address.model;

import java.util.List;

import io.basc.start.app.address.pojo.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AddressTree extends Address {
	private static final long serialVersionUID = 1L;
	private List<AddressTree> subList;
}
