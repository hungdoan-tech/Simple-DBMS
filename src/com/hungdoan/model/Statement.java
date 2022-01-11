package com.hungdoan.model;

import com.hungdoan.type.StatementType;

public class Statement {

    private StatementType type;

    private Row rowToInsert;

    public Statement() {

    }

    public Statement(StatementType type, Row row) {
        this.type = type;
        this.rowToInsert = row;
    }

    public StatementType getType() {
        return type;
    }

    public void setStyle(StatementType style) {
        this.type = style;
    }

    public Row getRowToInsert() {
        return rowToInsert;
    }

    public void setRowToInsert(Row rowToInsert) {
        this.rowToInsert = rowToInsert;
    }
}
