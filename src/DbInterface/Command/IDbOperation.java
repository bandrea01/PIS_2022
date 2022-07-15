package DbInterface.Command;

public interface IDbOperation {
    DbOperationResult execute();
    void close();
}
