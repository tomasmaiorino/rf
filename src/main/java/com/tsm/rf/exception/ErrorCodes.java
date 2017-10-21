package com.tsm.rf.exception;

public interface ErrorCodes {

    public static final String DESTINATION_ACCOUNT_REQUIRED = "destinationAccountRequired";
    public static final String DESTINATION_ACCOUNT_INVALID = "destinationAccountInvalid";

    public static final String ORIGIN_ACCOUNT_REQUIRED = "originAccountRequired";
    public static final String ORIGIN_ACCOUNT_INVALID = "originAccountInvalid";

    public static final String SCHEDULE_DATE_REQUIRED = "scheduleDateRequired";
    public static final String SCHEDULE_DATE_INVALID = "scheduleDateInvalid";

    public static final String TRANSFER_VALUE_REQUIRED = "transferValueRequired";
    public static final String TRANSFER_VALUE_INVALID = "transferValueInvalid";

    public static final String TRANSFERS_NOT_FOUND = "destinationAccountRequired";
    public static final String INAPPLICABLE_TAX = "inapplicableTax";

}
