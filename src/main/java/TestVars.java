
import java.util.concurrent.ConcurrentHashMap;

public class TestVars {

    public static ConcurrentHashMap<String, Object> testVarsMap = new ConcurrentHashMap<>();

    private static String getThread() {
        return String.valueOf(Thread.currentThread().getId());
    }

    private static String getFullVarName(String varName) {
        return getThread() + "/" + varName;
    }

    public static void setVar(String varName, Object value) {
        if (testVarsMap.containsKey(getFullVarName(varName)))
            testVarsMap.replace(getFullVarName(varName), value);
        else
            testVarsMap.put(getFullVarName(varName), value);
    }

    public static Object getVar(String varName) {
        String threadSlashToRemove = getThread() + "/";
        varName = getFullVarName(varName);
        varName = varName.substring(varName.indexOf(threadSlashToRemove));
        Object result = testVarsMap.get(varName);
        return result;
    }

}
