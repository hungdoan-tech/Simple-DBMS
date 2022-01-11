package com.hungdoan.model;

import static com.hungdoan.common.Constant.ROWS_PER_PAGE;

public class Page {

    private Row[] rows = new Row[ROWS_PER_PAGE];

    public Page() {

    }

    public Page(Row[] rows) {
        this.rows = rows;
    }

    public Row[] getRows() {
        return rows;
    }

    public void setRows(Row[] rows) {
        this.rows = rows;
    }
}
