package DbInterface.Command;

import java.util.ArrayList;
import java.util.List;

public class DbOperationExecutor {
    private final List<IDbOperation> dbOperationList = new ArrayList<>();

    public DbOperationResult executeOperation(IDbOperation dbOperation){
        dbOperationList.add(dbOperation);
        return dbOperation.execute();
    }

    public void close(IDbOperation dbOperation){
        dbOperation.close();
    }
}
