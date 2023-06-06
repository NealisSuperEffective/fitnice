package com.nashss.se.fitnice.activity.requests;

public class GetWorkoutRequest {
    private final String date;
//    private final String name;

    private GetWorkoutRequest(String date) {
        this.date = date;
//        this.name = name;
    }
    public String getDate() {
        return date;
    }

//    public String getName() {
//        return name;
//    }

    @Override
    public String toString() {
        return "GetWorkoutRequest{" +
                "date='" + date + '\'' +
//                "name='" + name + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetWorkoutRequest.Builder builder() {
        return new GetWorkoutRequest.Builder();
    }

    public static class Builder {

        private String date;
//        private String name;

        public GetWorkoutRequest.Builder withWorkoutDate(String date) {
            this.date = date;
            return this;
        }
//        public GetWorkoutRequest.Builder withWorkoutName(String name) {
//            this.name = name;
//            return this;
//        }


        public GetWorkoutRequest build() {
            return new GetWorkoutRequest(date);
        }
    }
}
