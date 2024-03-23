package com.ua.money24.model.response;


import java.util.List;

public record ExecAsPublicResponse(Integer statusCode, String statusMessage, Result result) {
    public record Result(List<Rate> rates, String message) {
        public record Rate(Integer id, Integer groupID, Integer regionID, String currCode, Integer currId, String type, double rate,
                           double prevRate, String status, String date) {
        }
    }
}
