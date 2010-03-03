package org.ndot.spring25.mutiDataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TestService extends JdbcDaoSupport {
	public void insert(String sql, String dbName) {
		DynamicDataSource.currentLookupKey.remove();
		DynamicDataSource.currentLookupKey.set(dbName);
		this.getJdbcTemplate().execute(sql);
	}
}
