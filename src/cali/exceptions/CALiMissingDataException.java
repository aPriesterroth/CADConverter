package cali.exceptions;

public class CALiMissingDataException extends CALiException {

    public CALiMissingDataException(String component, String attribute, String value) {
        super("Unable to locate " + component + " with " + attribute + ": " + value);
    }
}
