package com.hungdoan;

import com.hungdoan.model.Page;
import com.hungdoan.model.Row;
import com.hungdoan.model.Table;

import static com.hungdoan.common.Constant.ROWS_PER_PAGE;

public class SerializationUtils {

    public static Row serializeRow(Row source, Row destination) {
        destination.setId(source.getId());
        System.arraycopy(source.getEmail(), 0, destination.getEmail(), 0, source.getEmail().length);
        System.arraycopy(source.getUsername(), 0, destination.getUsername(), 0, source.getUsername().length);
        return destination;
    }

    public static Row deserializeRow(Row source, Row destination) {
        destination.setId(source.getId());
        System.arraycopy(source.getEmail(), 0, destination.getEmail(), 0, source.getEmail().length);
        System.arraycopy(source.getUsername(), 0, destination.getUsername(), 0, source.getUsername().length);
        return destination;
    }

    public static Row findRowSlotByRowNumber(Table table, int rowNumber) {
        int page_num = rowNumber / ROWS_PER_PAGE;
        Page page = table.getPage()[page_num];
        if (page == null) {
            // Allocate memory only when we try to access page
            page = table.getPage()[page_num] = new Page(new Row[ROWS_PER_PAGE]);
            return page.getRows()[0] = new Row();
        }
        int rowOffset = rowNumber % ROWS_PER_PAGE;
        return page.getRows()[rowOffset];
    }

    void freeTable(Table table) {
        Page[] pages = table.getPage();
        for (Page page : pages) {
            Row[] rows = page.getRows();
            for (Row row : rows) {
                row = null;
            }
            page = null;
        }
        table = null;
    }
}
