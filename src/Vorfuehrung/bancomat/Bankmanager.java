package bancomat;

import mware_lib.objects.MWareObject;

/**
 * Automatisch vom IDLCompiler erzeugte Datei
 */
public class Bankmanager extends _BankmanagerImplBase {

    private MWareObject rawObjectRef;

    public Bankmanager(MWareObject rawObjectRef){
        this.rawObjectRef = rawObjectRef;
    }

    @Override
    public String getAccountID(int key) {
        String message = rawObjectRef.getName() + "!getAccountID!" + valueToString(key) + ":int\n";
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
