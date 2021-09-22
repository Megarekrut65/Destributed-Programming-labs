package com.company.part1.lab3.c;

public class Table {
    private Components components;

    public Table() {
        this.components = null;
    }

    public void putComponents(Components components)
    {
        this.components = components;
    }
    public boolean checkComponents()
    {
        return components != null;
    }
    public Components getComponents(Component component)
    {
        if(components != null && component != components.getComponent1() &&
                component != components.getComponent2()) {
            Components res = components;
            components = null;
            return res;
        }
        return null;
    }
}
