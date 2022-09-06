package com.devcollege.exceptions;

public class NotFoundException extends RuntimeException {
    String value;
    String fieldName;
    String passedValue;

    @Override
    public String toString() {
        return "" +
                "value='" + value + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", passedValue='" + passedValue + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

//    @Override
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPassedValue() {
        return passedValue;
    }

    public void setPassedValue(String passedValue) {
        this.passedValue = passedValue;
    }
    public NotFoundException(String value, String fieldName, String passedValue) {
        super(String.format("%s not found %s: %s ",value, fieldName,passedValue));
        this.value = value;
        this.fieldName = fieldName;
        this.passedValue = passedValue;
    }
}
