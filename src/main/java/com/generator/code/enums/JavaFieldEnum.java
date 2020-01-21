package com.generator.code.enums;

/**
 * @Description: java字段类型enum
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/1/15
 */
public enum JavaFieldEnum {
    VARCHAR(12, "String"),
    BIGINT(-5, "Long"),
    INT(4, "Integer"),
    TINYINT(-6, "Integer"),
    DECIMAL(3, "BigDecimal"),
    DATE(91, "Date"),
    BIT(-7, "Boolean"),
    BLOB(-4, "byte[]"),
    DATETIME(93, "Date"),
    DOUBLE(8, "Double"),
    FLOAT(6, "Float"),
    SMALLINT(5, "Integer"),
    NUMERIC(2, "Double"),
    CHAR(1, "String"),
    LONGVARCHAR(-1, "String"),
    TIME(92, "Time"),
    BINARY(-2, "String"),
    VARBINARY(-3, "String"),
    LONGVARBINARY(-4, "String");

    private int id;

    private String name;

    private JavaFieldEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 状态转换 toId
     *
     * @param value
     * @return
     */
    public static int convertToId(String value) {
        if (value.trim().equals(VARCHAR.name)) {
            return VARCHAR.getId();
        } else if (value.trim().equals(BIGINT.name)) {
            return BIGINT.getId();
        } else if (value.trim().equals(INT.name)) {
            return INT.getId();
        } else if (value.trim().equals(TINYINT.name)) {
            return TINYINT.getId();
        } else if (value.trim().equals(DECIMAL.name)) {
            return DECIMAL.getId();
        } else if (value.trim().equals(DATE.name)) {
            return DATE.getId();
        } else if (value.trim().equals(BIT.name)) {
            return BIT.getId();
        } else if (value.trim().equals(BLOB.name)) {
            return BLOB.getId();
        } else if (value.trim().equals(DATETIME.name)) {
            return DATETIME.getId();
        } else if (value.trim().equals(DOUBLE.name)) {
            return DOUBLE.getId();
        } else if (value.trim().equals(FLOAT.name)) {
            return FLOAT.getId();
        } else if (value.trim().equals(SMALLINT.name)) {
            return SMALLINT.getId();
        } else if (value.trim().equals(NUMERIC.name)) {
            return NUMERIC.getId();
        } else if (value.trim().equals(CHAR.name)) {
            return CHAR.getId();
        } else if (value.trim().equals(LONGVARCHAR.name)) {
            return LONGVARCHAR.getId();
        } else if (value.trim().equals(TIME.name)) {
            return TIME.getId();
        } else if (value.trim().equals(CHAR.name)) {
            return CHAR.getId();
        } else if (value.trim().equals(BINARY.name)) {
            return BINARY.getId();
        } else if (value.trim().equals(VARBINARY.name)) {
            return VARBINARY.getId();
        } else {
            return LONGVARBINARY.getId();
        }
    }

    /**
     * 状态转换 toName
     *
     * @param id
     * @return
     */
    public static String convertToName(int id) {
        if (id == VARCHAR.getId()) {
            return VARCHAR.name;
        } else if (id == BIGINT.getId()) {
            return BIGINT.name;
        } else if (id == INT.getId()) {
            return INT.name;
        } else if (id == TINYINT.getId()) {
            return TINYINT.name;
        } else if (id == DECIMAL.getId()) {
            return DECIMAL.name;
        } else if (id == DATE.getId()) {
            return DATE.name;
        } else if (id == BIT.getId()) {
            return BIT.name;
        } else if (id == BLOB.getId()) {
            return BLOB.name;
        } else if (id == DATETIME.getId()) {
            return DATETIME.name;
        } else if (id == DOUBLE.getId()) {
            return DOUBLE.name;
        } else if (id == FLOAT.getId()) {
            return FLOAT.name;
        } else if (id == SMALLINT.getId()) {
            return SMALLINT.name;
        } else if (id == NUMERIC.getId()) {
            return NUMERIC.name;
        } else if (id == CHAR.getId()) {
            return CHAR.name;
        } else if (id == LONGVARCHAR.getId()) {
            return LONGVARCHAR.name;
        } else if (id == TIME.getId()) {
            return TIME.name;
        } else if (id == CHAR.getId()) {
            return CHAR.name;
        } else if (id == BINARY.getId()) {
            return BINARY.name;
        } else if (id == VARBINARY.getId()) {
            return VARBINARY.name;
        } else {
            return LONGVARBINARY.name;
        }
    }
}
