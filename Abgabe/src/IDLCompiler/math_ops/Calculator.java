package math_ops;

import mware_lib.objects.MWareObject;

/**
 * Automatisch vom IDLCompiler erzeugte Datei
 */
public class Calculator extends _CalculatorImplBase {

    private MWareObject rawObjectRef;

    public Calculator(MWareObject rawObjectRef){
        this.rawObjectRef = rawObjectRef;
    }

    @Override
    public double add(double a, double b) {
        String message = rawObjectRef.getName() + "!add!" + valueToString(a) + ":double," + valueToString(b) + ":double\n";
        String result = "";
        try {
            result = rawObjectRef.callMethod(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!result.isEmpty()) {
            String[] parts = result.split(":");
            switch(parts[0]) {
            case "result":
                return Double.parseDouble(parts[1]);
            case "exception":
                throw new RuntimeException(parts[2]);
            }
        }
        throw new RuntimeException("Unknown Error");
    }

    @Override
    public String getStr(double a) {
        String message = rawObjectRef.getName() + "!getStr!" + valueToString(a) + ":double\n";
        String result = "";
        try {
            result = rawObjectRef.callMethod(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!result.isEmpty()) {
            String[] parts = result.split(":");
            switch(parts[0]) {
            case "result":
                return parts[1];
            case "exception":
                throw new RuntimeException(parts[2]);
            }
        }
        throw new RuntimeException("Unknown Error");
    }

    public String valueToString(Object value) {
        return String.valueOf(value);
    }
}
