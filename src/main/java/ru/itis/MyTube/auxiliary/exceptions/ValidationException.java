package ru.itis.MyTube.auxiliary.exceptions;

import java.util.Map;

public class ValidationException extends Exception {
    private Map<String, String> problems;

    public ValidationException(Map<String, String> problems) {
        this.problems = problems;
    }

    public Map<String, String> getProblems() {
        return problems;
    }
}
