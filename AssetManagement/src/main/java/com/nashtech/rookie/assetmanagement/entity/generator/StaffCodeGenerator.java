package com.nashtech.rookie.assetmanagement.entity.generator;

import java.io.Serializable;
import java.util.stream.Stream;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class StaffCodeGenerator implements IdentifierGenerator {

	private String prefix = "SD";



	@Override
	public Serializable generate(SharedSessionContractImplementor s, Object obj) {
		String query = "SELECT u.staffCode FROM User u";
	    Stream<String> ids = s.createQuery(query, String.class).stream();
	    Long max = ids.map(o -> o.replace(prefix, "")).mapToLong(Long::parseLong).max().orElse(0L);
	    return prefix + (String.format("%04d", max + 1));
	}


}





