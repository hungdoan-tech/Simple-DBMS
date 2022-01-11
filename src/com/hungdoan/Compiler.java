package com.hungdoan;

import com.hungdoan.model.Row;
import com.hungdoan.model.Statement;
import com.hungdoan.type.MetaCommandResult;
import com.hungdoan.type.PreparedResult;

import java.util.Locale;

import static com.hungdoan.type.MetaCommandResult.META_COMMAND_SUCCESS;
import static com.hungdoan.type.MetaCommandResult.META_COMMAND_UNRECOGNIZED_COMMAND;
import static com.hungdoan.type.PreparedResult.PREPARE_SUCCESS;
import static com.hungdoan.type.PreparedResult.PREPARE_UNRECOGNIZED_STATEMENT;
import static com.hungdoan.type.StatementType.STATEMENT_INSERT;
import static com.hungdoan.type.StatementType.STATEMENT_SELECT;

public class Compiler {

    public MetaCommandResult executeMetaCommand(String line) {
        if (line.equals(".exit")) {
            Runtime.getRuntime().exit(0);
            return META_COMMAND_SUCCESS;
        } else {
            return META_COMMAND_UNRECOGNIZED_COMMAND;
        }
    }

    public PreparedResult prepareStatement(String line, Statement statement) {
        line = line.toLowerCase(Locale.ROOT);
        if (line.startsWith("insert")) {
            statement.setStyle(STATEMENT_INSERT);
            Object[] a = Formatter.scanFormatString("insert %d %s %s", line);
            int id = (Integer) a[0];
            String username = (String) a[1];
            String email = (String) a[2];
            Row row = new Row(id, username.toCharArray(), email.toCharArray());
            statement.setRowToInsert(row);
            return PREPARE_SUCCESS;
        }
        if (line.startsWith("select")) {
            statement.setStyle(STATEMENT_SELECT);
            return PREPARE_SUCCESS;
        }
        return PREPARE_UNRECOGNIZED_STATEMENT;
    }
}
