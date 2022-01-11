package com.hungdoan;

import com.hungdoan.model.Statement;
import com.hungdoan.model.Table;
import com.hungdoan.type.ExecuteResult;
import com.hungdoan.type.MetaCommandResult;
import com.hungdoan.type.PreparedResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandInterface {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private Compiler compiler = new Compiler();

    private VirtualMachine virtualMachine = new VirtualMachine();

    private Table table = new Table();

    public static void main(String[] args) throws IOException {
        CommandInterface main = new CommandInterface();
        main.perform();
    }

    private void perform() throws IOException {
        while (true) {
            String line = printPrompt();

            //do meta commands
            if (line.charAt(0) == '.') {
                MetaCommandResult metaCommandResult = compiler.executeMetaCommand(line);
                switch (metaCommandResult) {
                    case META_COMMAND_SUCCESS: {
                        continue;
                    }
                    case META_COMMAND_UNRECOGNIZED_COMMAND: {
                        System.out.format("Unrecognized command '%s' \n", line);
                        continue;
                    }
                    default: {
                        exit();
                    }
                }
            }

            // preparing statement progress
            Statement statement = new Statement();
            PreparedResult preparedResult = compiler.prepareStatement(line, statement);
            switch (preparedResult) {
                case PREPARE_SUCCESS: {
                    System.out.format("Do the prepare statement progress here \n");
                    break;
                }
                case PREPARE_UNRECOGNIZED_STATEMENT: {
                    System.out.format("Unrecognized keyword at start of '%s'\n", line);
                    continue;
                }
                default: {
                    continue;
                }
            }

            // executing statement progress
            ExecuteResult executeResult = virtualMachine.executeStatement(statement, table);
            switch (executeResult) {
                case EXECUTE_SUCCESS: {
                    System.out.println("Executed");
                    break;
                }
                case EXECUTE_TABLE_FULL: {
                    System.out.println("Error: Table full\n");
                    continue;
                }
                default: {
                    continue;
                }
            }
        }
    }

    public void exit() {
        try {
            this.reader.close();
            Runtime.getRuntime().exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String printPrompt() {
        System.out.print("db > ");
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
