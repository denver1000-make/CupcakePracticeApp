package com.denprog.codefestpractice2.destinations.checkout;

public class CheckOutState  {
    public static final class Loading extends CheckOutState {}
    public static final class Complete extends CheckOutState {}

    public static final class Error extends CheckOutState {
        public String errorMessage;

        public Error(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
