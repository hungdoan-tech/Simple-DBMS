package com.hungdoan.model;

import static com.hungdoan.common.Constant.TABLE_MAX_PAGES;

public class Table {

    private int numberOfRows;

    private Page[] page = new Page[TABLE_MAX_PAGES];

    public Table() {

    }

    public Table(int numberOfRows, Page[] page) {
        this.numberOfRows = numberOfRows;
        this.page = page;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Page[] getPage() {
        return page;
    }

    public void setPage(Page[] page) {
        this.page = page;
    }
}
