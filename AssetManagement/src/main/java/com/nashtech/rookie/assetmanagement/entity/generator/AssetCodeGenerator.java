package com.nashtech.rookie.assetmanagement.entity.generator;

import java.io.Serializable;
import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.nashtech.rookie.assetmanagement.entity.Asset;

public class AssetCodeGenerator implements IdentifierGenerator{

	private String prefix;
	private String cateroryId;
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Asset asset = (Asset) object;
		String query = "SELECT c.categoryCode FROM Category c where c.id=:categoryId";
	    prefix = session.createQuery(query, String.class).setParameter("categoryId", asset.getCategory().getId()).getSingleResult();
	    query = "SELECT a.assetCode FROM Asset a where a.assetCode LIKE :prefix";
	    Stream<String> ids = session.createQuery(query, String.class).setParameter("prefix", prefix + "%").list().stream();
	    Long max = ids.map(o -> o.replace(prefix, "")).mapToLong(Long::parseLong).max().orElse(0L);
	    return prefix + (String.format("%04d", max + 1));
	}

	

}
