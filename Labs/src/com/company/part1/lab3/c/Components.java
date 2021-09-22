package com.company.part1.lab3.c;

public class Components {
    private Component component1;
    private Component component2;

    public Components(Component component1, Component component2) {
        this.component1 = component1;
        this.component2 = component2;
    }

    public Component getComponent1() {
        return component1;
    }

    public Component getComponent2() {
        return component2;
    }
    public static String componentToString(Component component)
    {
        switch (component)
        {
            case TOBACCO -> {
                return "TOBACCO";
            }
            case PAPER -> {
                return "PAPER";
            }
            case MATCHES -> {
                return "MATCHES";
            }
        }
        return "";
    }
    public static Component intToComponent(int index)
    {
        switch (index)
        {
            case 1: return Component.PAPER;
            case 2: return Component.MATCHES;
            default:
        }
        return Component.TOBACCO;
    }
    public static Components createComponents()
    {
        int index1 = (int) (Math.random()*3);
        int index2 = index1;
        while (index2 == index1) index2 = (int) (Math.random()*3);
        return new Components(intToComponent(index1), intToComponent(index2));
    }
}
