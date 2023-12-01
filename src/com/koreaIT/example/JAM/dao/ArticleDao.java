package com.koreaIT.example.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class ArticleDao {

	private Connection conn;
	
	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public int doWrite(int memberId, String title, String body) {
		SecSql sql = SecSql.from("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", memberId = ?", memberId);
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		
		return DBUtil.insert(conn, sql);
	}

	public List<Map<String, Object>> showList() {
		SecSql sql = SecSql.from("SELECT a.*, m.name AS `writerName`");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
		sql.append("ORDER BY a.id DESC");
		
		return DBUtil.selectRows(conn, sql);
	}

	public Map<String, Object> showDetail(int id) {
		SecSql sql = SecSql.from("SELECT a.*, m.name AS `writerName`");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRow(conn, sql);
	}

	public int getArticleCnt(int id) {
		SecSql sql = SecSql.from("SELECT COUNT(*) FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRowIntValue(conn, sql);
	}

	public void doModify(String title, String body, int id) {
		SecSql sql = SecSql.from("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);
		
		DBUtil.update(conn, sql);
	}

	public void doDelete(int id) {
		SecSql sql = SecSql.from("DELETE FROM article");
		sql.append("WHERE id = ?", id);
		
		DBUtil.delete(conn, sql);		
	}
}
