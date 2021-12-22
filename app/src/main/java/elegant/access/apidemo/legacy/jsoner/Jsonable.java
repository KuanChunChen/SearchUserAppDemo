package elegant.access.apidemo.legacy.jsoner;

public class Jsonable {

    public String toJson() {
        return Jsoner.getInstance().toJson(this);
    }

//    public String buildParamsQ() {
//        return DesCrypto.iGetDesString(toJson());
//    }
}
