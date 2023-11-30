package com.koreaIT.example.JAM.dao;

import java.sql.Connection;
import java.util.Map;

import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class MemberDao {

	private Connection conn;
	
	public MemberDao(Connection conn) {
		this.conn = conn;
	}

	public boolean isLoginIdDup(String loginId) {
		SecSql sql = SecSql.from("SELECT COUNT(*) > 0 FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public void doJoin(String loginId, String loginPw, String name) {
		SecSql sql = SecSql.from("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);
		
		DBUtil.insert(conn, sql);
	}

	public Map<String, Object> getMemberByLoginId(String loginId) {
		SecSql sql = SecSql.from("SELECT * FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRow(conn, sql);
	}

}
