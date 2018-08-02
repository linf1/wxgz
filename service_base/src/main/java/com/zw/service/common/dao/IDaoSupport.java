package com.zw.service.common.dao;

import com.github.pagehelper.PageInfo;
import com.zw.service.exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface IDaoSupport {

    void exeSql(String sql) throws DAOException;

    /**
     * 执行添加，修改，删除操作
     * @param sql sql语句
     * @return 返回影响行数
     * @throws DAOException sql异常
     */
    int executeSql(String sql) throws DAOException;

    <T> void exeSql(String mapperid, T entity) throws DAOException;

    void exeSql(List<String> sql) throws DAOException;

    Map findForMap(String sql) throws DAOException;

    <T, K> T findForObject(String mapperid, K entity) throws DAOException;

    List<Map> findForList(String sql) throws DAOException;

    <T, K> List<K> findForList(String mapperid, T entity) throws DAOException;

    PageInfo<Map> findForList(String sql, int pageNum, int pageSize) throws DAOException;

    int getCount(String sql) throws DAOException;

    Connection getConnection() throws DAOException;

}
