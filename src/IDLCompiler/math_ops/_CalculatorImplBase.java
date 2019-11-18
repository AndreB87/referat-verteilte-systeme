package math_ops;

import mware_lib.objects.MWareObject;

public abstract class _CalculatorImplBase{

    public abstract double add(double a, double b);

    public abstract String getStr(double a);

    public static _CalculatorImplBase narrowCast(Object rawObjectRef) {
        return new Calculator((MWareObject)rawObjectRef);
    }
}
