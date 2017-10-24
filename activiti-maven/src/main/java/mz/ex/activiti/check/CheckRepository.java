package mz.ex.activiti.check;

import java.util.regex.Pattern;

public class CheckRepository {
    private static CheckRepository ourInstance = new CheckRepository();

    public static CheckRepository getInstance() {
        return ourInstance;
    }

    private CheckRepository() {
    }

    public Check getCheck(String checkId) {
        String[] split = checkId.split(Pattern.quote("-"));
        return new Check(split[0],split[1]);
    }
}
