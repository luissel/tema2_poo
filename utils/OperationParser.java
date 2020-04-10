package utils;

import java.util.ArrayList;
import java.util.Arrays;


public final class OperationParser {
    private static final int SECOND = 2;
    private static final int THIRD = 3;
    private static final int FOURTH = 4;
    private static OperationFactory operationFactory = OperationFactory.getInstance();

    /**
     * Parses an operation.
     *
     * @param newOperation operation string
     * @return the operation
     */
    public AbstractOperation parseOperation(String newOperation) {
        ArrayList<String> operationArgs
                = new ArrayList<>(Arrays.asList(newOperation.split("\\s+")));

        if (operationArgs.get(0).toLowerCase().equals("vcs")) {
            return parseVcsOperation(newOperation, operationArgs);
        } else {
            return parseFileSystemOperation(newOperation, operationArgs);
        }
    }

    private AbstractOperation parseVcsOperation(String newOperation,
                                                ArrayList<String> operationArgs) {
        // TODO parse vcs operations
        ArrayList<String> parsedArgs = null;
        operationArgs.remove(0);
        String specificCommand = operationArgs.remove(0);

        switch (specificCommand.toLowerCase()) {
            case "status":
                return operationFactory.createOperation(OperationType.STATUS, operationArgs);
            case "branch":
                if (operationArgs.size() == 1) {
                    parsedArgs = parsePathAndName(operationArgs.get(0));
                }

                return operationFactory.createOperation(OperationType.BRANCH, parsedArgs);
            case "commit":
                //retin mesajul comenzii intr-un string, apoi il adaug in parsedArgs
                if (operationArgs.size() != 0) {
                    String args = operationArgs.get(1);
                    args += " ";
                    for (int i = 2; i < operationArgs.size(); i++) {
                        args += operationArgs.get(i);
                        if (i != operationArgs.size() - 1) {
                            args += " ";
                        }
                    }
                    parsedArgs = parsePathAndName(args);
                }
                return operationFactory.createOperation(OperationType.COMMIT, parsedArgs);
            case "checkout":
                if (operationArgs.size() == 1) {
                    parsedArgs = parsePathAndName(operationArgs.get(0));
                } else {
                        parsedArgs = parsePathAndName(operationArgs.get(0));
                        parsedArgs.add(operationArgs.get(1));
                }
                return operationFactory.createOperation(OperationType.CHECKOUT, parsedArgs);
            case "log":
                return operationFactory.createOperation(OperationType.LOG, operationArgs);
            case "rollback":
                return operationFactory.createOperation(OperationType.ROLLBACK, operationArgs);
            default:
                return operationFactory.
                        createOperation(OperationType.VCS_INVALID_OPERATION, operationArgs);
        }
    }

    private AbstractOperation parseFileSystemOperation(String newOperation,
                                                       ArrayList<String> operationArgs) {
        ArrayList<String> parsedArgs = null;
        String specificCommand = operationArgs.remove(0);

        switch (specificCommand.toLowerCase()) {
            case "cat":
                return operationFactory.createOperation(OperationType.CAT, operationArgs);
            case "cd":
                return operationFactory.createOperation(OperationType.CHANGEDIR, operationArgs);
            case "ls":
                return operationFactory.createOperation(OperationType.LIST, operationArgs);
            case "mkdir":
                if (operationArgs.size() == 1) {
                    parsedArgs = parsePathAndName(operationArgs.get(0));
                }

                return operationFactory.createOperation(OperationType.MAKEDIR, parsedArgs);
            case "rm":
            case "rmdir":
                operationArgs.add(0, specificCommand);

                return operationFactory.createOperation(OperationType.REMOVE, operationArgs);
            case "touch":
                if (operationArgs.size() == 1) {
                    parsedArgs = parsePathAndName(operationArgs.get(0));
                }

                return operationFactory.createOperation(OperationType.TOUCH, parsedArgs);
            case "writetofile":
                parsedArgs = new ArrayList<String>();

                parsedArgs.add(operationArgs.remove(0));
                parsedArgs.add(newOperation.split("\\s+", THIRD)[SECOND]);

                return operationFactory.createOperation(OperationType.WRITETOFILE, parsedArgs);
            case "print":
                return operationFactory.createOperation(OperationType.PRINT, operationArgs);
            default:
                return operationFactory
                        .createOperation(OperationType.FILESYSTEM_INVALID_OPERATION, operationArgs);
        }
    }

    private ArrayList<String> parsePathAndName(String toParse) {
        ArrayList<String> parsedArgs = new ArrayList<String>();

        if (toParse.contains("/")) {
            parsedArgs.add(toParse.substring(0, toParse.lastIndexOf("/")));
            parsedArgs.add(toParse.substring(toParse.lastIndexOf("/") + 1));
        } else {
            parsedArgs.add("");
            parsedArgs.add(toParse);
        }

        return parsedArgs;
    }
}
