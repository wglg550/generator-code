package com.generator.code.jdbc;

import com.generator.code.config.JdbcConfig;
import com.generator.code.enums.JavaFieldEnum;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @Description: jdbc连接类
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/1/13
 */
@Slf4j
@Component
public class JdbcConnect {
//    static final String URL = "jdbc:mysql://127.0.0.1:3306/spring_cloud_demo?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
//    static final String USER = "root";
//    static final String PASSWORD = "123456789";
//    static final String DRIVE_CLASS = "com.mysql.cj.jdbc.Driver";

    @Resource
    JdbcConfig jdbcConfig;

    /**
     * java jdbc连接
     */
//    public void jdbcConn() throws SQLException {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        // 1.加载驱动程序
//        try {
//            Class.forName(jdbcConfig.getDriveClass());
//            // 2.获得数据库链接
//            conn = DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUsername(), jdbcConfig.getPassword());
//            DatabaseMetaData databaseMetaData = conn.getMetaData();
//            String sql = "select * from s_user";
//            ps = conn.prepareStatement(sql);
//            ResultSetMetaData rsmd = ps.getMetaData();
//            int size = rsmd.getColumnCount();   //返回列数
//            log.info("size:{}", size);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (Objects.nonNull(conn)) {
//                conn.close();
//            }
//            if (Objects.nonNull(ps)) {
//                ps.close();
//            }
//        }
//    }

    /**
     * spring jdbc连接
     */
    public Multimap springJdbcConn() throws MetaDataAccessException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(jdbcConfig.getDriveClass());
        dataSource.setUrl(jdbcConfig.getUrl());
        dataSource.setUsername(jdbcConfig.getUsername());
        dataSource.setPassword(jdbcConfig.getPassword());
        Multimap<String, Map<String, String>> map = (Multimap<String, Map<String, String>>) JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {
            @Override
            public Object processMetaData(DatabaseMetaData databaseMetaData) throws SQLException {
                ResultSet rs = null;
                Multimap<String, Map<String, String>> map = LinkedHashMultimap.create();
                try {
                    List<String> list = jdbcConfig.getTables();
                    if (Objects.isNull(list) || list.size() == 0) {
                        throw new RuntimeException("表名称没有配置");
                    }
                    for (String s : list) {
                        Map pkMap = new HashMap();
                        rs = databaseMetaData.getPrimaryKeys(jdbcConfig.getDatabase(), null, s);
                        while (rs.next()) {
                            String tableName = rs.getString("TABLE_NAME");
                            String columnName = rs.getString("COLUMN_NAME");//列名
                            String pkName = rs.getString("PK_NAME"); //主键名称
                            pkMap.put("PK_NAME", columnName);
                            log.info("PK-tableName:{},columnName:{},pkName:{}", tableName, columnName, pkName);
                        }
                        /**
                         * mysql数据库指定第一个参数,oracle指定第二个参数，否则会扫描全库所有目标表
                         */
                        rs = databaseMetaData.getColumns(jdbcConfig.getDatabase(), null, s, null);
                        while (rs.next()) {
                            String tableName = rs.getString("TABLE_NAME");  //表名
                            String columnName = rs.getString("COLUMN_NAME");  //列名
                            int dataType = rs.getInt("DATA_TYPE");     //对应的java.sql.Types的SQL类型(列类型ID)
                            String dataTypeName = rs.getString("TYPE_NAME");  //java.sql.Types类型名称(列类型名称)
                            int columnSize = rs.getInt("COLUMN_SIZE");  //列大小
                            int decimalDigits = rs.getInt("DECIMAL_DIGITS");  //小数位数
                            String isNullAble = rs.getString("IS_NULLABLE");
                            String remarks = rs.getString("REMARKS");  //列描述
                            String columnDef = rs.getString("COLUMN_DEF");  //默认值
                            int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");    // 对于 char 类型，该长度是列中的最大字节数
                            int ordinalPosition = rs.getInt("ORDINAL_POSITION");   //表中列的索引（从1开始）
                            String[] columnNames = columnName.split("_");
                            String javaBean = columnName;
                            if (Objects.nonNull(columnNames) && columnNames.length > 1) {
                                StringJoiner sj = new StringJoiner("");
                                sj.add(columnNames[0]);
                                for (int i = 1; i < columnNames.length; i++) {
                                    sj.add(columnNames[i].replaceFirst(columnNames[i].substring(0, 1), columnNames[i].substring(0, 1).toUpperCase()));
                                }
                                javaBean = sj.toString();
                            }
                            Map m = new HashMap();
                            m.put("COLUMN_NAME", columnName);
                            m.put("JAVA_BEAN", javaBean);
                            m.put("GET_BEAN", new StringJoiner("").add("get").add(javaBean.replaceFirst(javaBean.substring(0, 1), javaBean.substring(0, 1).toUpperCase())).toString());
                            m.put("SET_BEAN", new StringJoiner("").add("set").add(javaBean.replaceFirst(javaBean.substring(0, 1), javaBean.substring(0, 1).toUpperCase())).toString());
                            m.put("DATA_TYPE", JavaFieldEnum.convertToName(dataType));
                            m.put("TYPE_NAME", dataTypeName);
                            m.put("IS_NULLABLE", isNullAble);
                            m.put("REMARKS", remarks);
                            m.put("COLUMN_DEF", columnDef);
                            if (Objects.equals(pkMap.get("PK_NAME"), columnName)) {
                                m.put("PK", true);
                            } else {
                                m.put("PK", false);
                            }
                            if (!map.containsValue(m)) {
                                map.put(tableName, m);
                                log.info("tableName:{},columnName:{},dataType:{},dataTypeName:{},columnSize:{},decimalDigits:{},isNullAble:{},remarks:{},columnDef:{},charOctetLength:{},ordinalPosition:{}",
                                        tableName, columnName, dataType, dataTypeName, columnSize, decimalDigits, isNullAble, remarks, columnDef, charOctetLength, ordinalPosition);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (Objects.nonNull(rs)) {
                        rs.close();
                    }
                }
                return map;
            }
        });
        return map;
//        List<String> list = jdbcConfig.getTables();
//        if (Objects.isNull(list) || list.size() == 0) {
//            throw new RuntimeException("表名称没有配置");
//        }
//        for (String s : list) {
//            StringJoiner stringJoiner = new StringJoiner("").add(jdbcConfig.getQuery()).add(" " + s);
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(stringJoiner.toString());
//            SqlRowSetMetaData sqlRowSetMetaData = rowSet.getMetaData();
//            if (sqlRowSetMetaData.getColumnCount() > 0) {
//                for (int i = 1; i <= sqlRowSetMetaData.getColumnCount(); i++) {
//                    log.info("i:{},columnName:{}", i, sqlRowSetMetaData.getColumnName(i));
//                    log.info("i:{},columnType:{}", i, sqlRowSetMetaData.getColumnType(i));
//                    log.info("i:{},ColumnTypeName:{}", i, sqlRowSetMetaData.getColumnTypeName(i));
//                    log.info("i:{},CatalogName:{}", i, sqlRowSetMetaData.getCatalogName(i));
//                    log.info("i:{},ColumnClassName:{}", i, sqlRowSetMetaData.getColumnClassName(i));
//                    log.info("i:{},ColumnLabel:{}", i, sqlRowSetMetaData.getColumnLabel(i));
//                    log.info("i:{},Precision:{}", i, sqlRowSetMetaData.getPrecision(i));
//                    log.info("i:{},Scale:{}", i, sqlRowSetMetaData.getScale(i));
//                    log.info("i:{},SchemaName:{}", i, sqlRowSetMetaData.getSchemaName(i));
//                    log.info("i:{},TableName:{}", i, sqlRowSetMetaData.getTableName(i));
//                }
//            }
//            log.info("meteData:{}", JSONObject.toJSONString(rowSet.getMetaData()));
//        }
//        return LinkedHashMultimap.create();
    }

//    public static void main(String[] args) throws SQLException {
//        jdbcConn();
//        springJdbcConn();
//    }
}
