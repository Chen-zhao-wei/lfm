package com.example.lfm;

import com.example.lfm.controller.PrintController;

public class main {
    public static void main(String[] args) {
        PrintController aliPayController = new PrintController();
        aliPayController.getOrderInfo(null);
    }
}
