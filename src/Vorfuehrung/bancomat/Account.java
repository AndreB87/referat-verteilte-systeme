package bancomat;

import mware_lib.objects.MWareObject;

/**
 * Automatisch vom IDLCompiler erzeugte Datei
 */
public class Account extends _AccountImplBase {

    private MWareObject rawObjectRef;

    public Account(MWareObject rawObjectRef){
        this.rawObjectRef = rawObjectRef;
    }

    @Override
    public double deposit(double param0) {
        String message = rawObjectRef.getName() + "!deposit!" + valueToString(param0) + ":double\n";
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
    public double withdraw(double param0) {
        String message = rawObjectRef.getName() + "!withdraw!" + valueToString(param0) + ":double\n";
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

    public String valueToString(Object value) {
        return String.valueOf(value);
    }
}
