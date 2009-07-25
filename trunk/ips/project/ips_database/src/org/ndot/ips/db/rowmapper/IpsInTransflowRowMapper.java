package org.ndot.ips.db.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ndot.ips.db.pojo.IpsInTransflow;
import org.ndot.ips.db.pojo.IpsInTransflowId;
import org.springframework.jdbc.core.RowMapper;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_database
 * 
 * 文件名： IpsInTransflowRowMapper.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-28
 * 
 */
public class IpsInTransflowRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpsInTransflowId id = new IpsInTransflowId();
		IpsInTransflow obj = new IpsInTransflow();
		id.setIntranscode(rs.getString("INTRANSCODE"));
		id.setProcessno(rs.getString("PROCESSNO"));
		obj.setId(id);
		obj.setChkmacflag(rs.getString("chkmacflag"));
		obj.setChkpinflag(rs.getString("chkpinflag"));
		obj.setDestnodeid(rs.getString("destnodeid"));
		obj.setFlowprocessno(rs.getString("flowprocessno"));
		obj.setLaunchtranscode(rs.getString("launchtranscode"));
		obj.setOvertime(rs.getString("overtime"));
		obj.setQueryorgjnlflag(rs.getString("queryorgjnlflag"));
		obj.setResendflag(rs.getString("resendflag"));
		obj.setReverseflag(rs.getString("reverseflag"));
		obj.setSafflag(rs.getString("safflag"));
		obj.setStarttag(rs.getString("starttag"));
		obj.setTranname(rs.getString("tranname"));
		obj.setTranscategory(rs.getString("transcategory"));
		obj.setTranstat(rs.getString("transtat"));
		return obj;
	}
}
