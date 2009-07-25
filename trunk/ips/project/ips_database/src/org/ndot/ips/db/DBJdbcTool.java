package org.ndot.ips.db;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ndot.ips.db.rowmapper.IpsInTransflowRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

@SuppressWarnings("unchecked")
public class DBJdbcTool extends /* JdbcDaoSupport */JdbcTemplate {
	private static final Log log = LogFactory.getLog(DBJdbcTool.class);

	public List find(String tableName, String[] columnNames, String whereContent) {

		try {
			String sql = "select ";
			if (null == columnNames || columnNames.length < 1) {
				sql += " * from " + tableName + " " + whereContent;
			} else {
				for (int i = 0; i < columnNames.length; i++) {
					sql += columnNames[i];
					if (i < columnNames.length - 1) {
						sql += " ,";
					}
				}
				sql += " from  " + tableName + " " + whereContent;
				log.debug("Execute " + sql);
			}
			return this.queryForList(sql);
		} catch (RuntimeException re) {
			log.error("Ndot find failed", re);
			throw re;
		}
	}

	public Object queryForObject(Class clazz, String tableName,
			String[] cloumnNames, String[] conditions) {
		String sql = genSql(tableName, cloumnNames, conditions);
		return this.queryForObject(sql, ParameterizedBeanPropertyRowMapper
				.newInstance(clazz));
	}

	public List queryInTransflowForList(Class clazz, String tableName,
			String[] columnNames, String[] conditions) {
		String sql = genSql(tableName, columnNames, conditions);
		return this.query(sql, new IpsInTransflowRowMapper());
	}

	private String genSql(String tableName, String[] columnNames,
			String[] conditions) {
		String sql = "select ";
		if (null == columnNames || columnNames.length < 1) {
			sql += " * ";
		} else {
			for (int i = 0; i < columnNames.length; i++) {
				sql += columnNames[i];
				if (i < columnNames.length - 1) {
					sql += " ,";
				}
			}
		}
		sql += " from  " + tableName;
		if (null != conditions || conditions.length > 0) {
			sql += " where ";
			for (int j = 0; j < conditions.length; j++) {
				sql += " " + conditions[j];
				if (j < conditions.length - 1) {
					sql += " and ";
				}
			}
		}
		log.debug("Execute " + sql);
		return sql;
	}
}
