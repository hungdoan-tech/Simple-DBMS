package com.hungdoan;

import com.hungdoan.model.Row;
import com.hungdoan.model.Statement;
import com.hungdoan.model.Table;
import com.hungdoan.type.ExecuteResult;

import static com.hungdoan.SerializationUtils.*;
import static com.hungdoan.common.Constant.TABLE_MAX_ROWS;
import static com.hungdoan.type.ExecuteResult.EXECUTE_SUCCESS;
import static com.hungdoan.type.ExecuteResult.EXECUTE_TABLE_FULL;

public class VirtualMachine {

    private ExecuteResult executeInsert(Statement statement, Table table) {
        if (table.getNumberOfRows() >= TABLE_MAX_ROWS) {
            return EXECUTE_TABLE_FULL;
        }
        Row rowToInsert = statement.getRowToInsert();
        Row rowInDb = findRowSlotByRowNumber(table, table.getNumberOfRows());
        rowToInsert = serializeRow(rowToInsert, rowInDb);
        table.setNumberOfRows(table.getNumberOfRows() + 1);
        return EXECUTE_SUCCESS;
    }

    private ExecuteResult executeSelect(Statement statement, Table table) {
        Row selectedRow = new Row();
        for (int i = 0; i < table.getNumberOfRows(); i++) {
            Row rowInDB = findRowSlotByRowNumber(table, i);
            selectedRow = deserializeRow(rowInDB, selectedRow);
            System.out.println(selectedRow);
        }
        return EXECUTE_SUCCESS;
    }

    public ExecuteResult executeStatement(Statement statement, Table table) {
        switch (statement.getType()) {
            case STATEMENT_INSERT: {
                return executeInsert(statement, table);
            }
            case STATEMENT_SELECT: {
                return executeSelect(statement, table);
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + statement.getType());
            }
        }
    }
}
