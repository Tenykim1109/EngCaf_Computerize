package com.student.app;

public final class student
{
    private static String ID;
    private static int money;
    private static String order;
    public static student INSTANCE = new student();

    public final String getID() {
        return ID;
    }

    public final void setID(String var1) {ID = var1; }
    public final int getMoney() {
        return money;
    }
    public final void setMoney(int var1) {
        money = var1;
    }
    public final String getOrder() {
        return order;
    }

    public final void setOrder(String var1) { order = var1; }

    private student() {
    }

    static {
        student var0 = new student();
        INSTANCE = var0;
        ID = "";
        order = "";
    }
}
